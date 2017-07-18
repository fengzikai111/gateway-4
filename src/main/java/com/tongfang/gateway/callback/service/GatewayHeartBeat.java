package com.tongfang.gateway.callback.service;

import com.tongfang.gateway.Const;
import com.tongfang.gateway.util.Config;
import com.tongfang.gateway.util.SleepHelper;
import com.tongfang.gateway.vo.SelfCheckBean;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;


/**
 * forward 心跳连接接受类
 *
 * @author lyl
 */
public class GatewayHeartBeat {
    private static Logger LOGGER = LoggerFactory.getLogger(GatewayHeartBeat.class);
    private static Config CONFIG = Config.getInstance();


    public static void subHeartBeat() throws Exception {
        MqttAsyncClient client = new MqttAsyncClient(CONFIG.getMasterUrl(), CONFIG.getGid() + "_" + Const.CLIENT_HEART,
                new MemoryPersistence());

        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setAutomaticReconnect(true);

        client.connect(options).waitForCompletion();
        client.subscribe(CONFIG.getForwardHeartTopic(), CONFIG.getQos(), null, null, (String topic, MqttMessage mqtMsg) -> {
            LOGGER.trace("receive heart beat {} from forward", mqtMsg.toString());
            LOGGER.debug("receive heart beat size {} from forward", mqtMsg.toString().length());

            doPost(CONFIG.getSelfCheckServer(), mqtMsg.toString());
        });
    }

    public static void startLocalHeart() {
        new Thread(() -> {
            while (true) {
                try (SleepHelper helper = new SleepHelper(Const.HEART_INTERVAL_MS)) {
                    SelfCheckBean scb = new SelfCheckBean(new Date(), Const.CONNECTED, CONFIG.getGid());

                    doPost(CONFIG.getSelfCheckServer(), Const.GSON.toJson(scb));
                } catch (Exception e) {
                    LOGGER.error("heart beat some error", e);
                    e.printStackTrace();
                }
             }
        }).start();

    }

    private static int doPost(String urlStr, String strInfo) throws Exception {
        int reStr = 0;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.setUseCaches(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Cache-Control", "no-cache");
            httpConn.setRequestProperty("Content-Type", "text/plain");
            httpConn.connect();
            DataOutputStream out = new DataOutputStream(httpConn.getOutputStream());
            out.writeBytes(new String(strInfo.getBytes("utf-8")));
            out.flush();
            out.close();
            reStr = httpConn.getResponseCode();
        } catch (Exception e) {
            LOGGER.error("post keepalive error", e);
        }
        return reStr;

    }

}
