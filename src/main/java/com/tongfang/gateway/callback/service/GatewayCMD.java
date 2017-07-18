package com.tongfang.gateway.callback.service;

import com.google.gson.reflect.TypeToken;
import com.tongfang.gateway.Const;
import com.tongfang.gateway.EtcdClient;
import com.tongfang.gateway.util.Config;
import com.tongfang.gateway.vo.CMDBean;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * 命令通道 连接类
 *
 * @author lyl
 */
public class GatewayCMD {
    private static Logger LOGGER = LoggerFactory.getLogger(GatewayCMD.class);
    private static Config CONFIG = Config.getInstance();

    private static Type ACL_TYPE = new TypeToken<List<String>>() {
    }.getType();

    public static void subCmdTopic() throws Exception {
        MqttAsyncClient client = new MqttAsyncClient(CONFIG.getMasterUrl(), CONFIG.getGid() + "_" + Const.CLIENT_CMD,
                new MemoryPersistence());

        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setAutomaticReconnect(true);

        LOGGER.debug("is debug model {}",CONFIG.getDebugMode());

        if (CONFIG.getDebugMode().equals(Const.DEBUG)) {
            List<String> sidList = Const.GSON.fromJson(CONFIG.getTeraAcl(), ACL_TYPE);
            LOGGER.debug("gateway engin run in debug mode and acl is {}", CONFIG.getTeraAcl());

            if(!sidList.isEmpty())sidList.forEach(sid->{
                SingletonTopic.getInstance().sub(sid);
            });
        } else {
            List<String> fidIndex = EtcdClient.getInstance().queryFidIndexByGid(CONFIG.getGid());

            EtcdClient.getInstance().getSidPathMap(fidIndex).forEach((sidK, sidPath) -> {
                SingletonTopic.getInstance().sub(sidK);
            });
        }

        client.connect(options).waitForCompletion();
        LOGGER.debug("connect cmd for sub new sid");

        client.subscribe(CONFIG.getCmd(), CONFIG.getQos(), null, null, (String topic, MqttMessage mqtMsg) -> {
            String messageStr = new String(mqtMsg.getPayload());
            try {
                LOGGER.debug("sub back create sid {} cmd from newTopic", messageStr);
                CMDBean newSidCmd = Const.GSON.fromJson(messageStr, CMDBean.class);

                if (newSidCmd.getType() == Const.CREATE_CMD_CODE) {
                    SingletonTopic.getInstance().sub(newSidCmd.getMsg());
                }
            } catch (Exception e) {
                LOGGER.error("sub data by cmd sid error", e);
                e.printStackTrace();
            }
        });

    }

}
