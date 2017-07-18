package com.tongfang.gateway.callback.service;

import com.tongfang.gateway.Const;
import com.tongfang.gateway.KafkaClient;
import com.tongfang.gateway.util.Config;
import com.tongfang.gateway.vo.WrapDevice;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 动态单例Topic
 *
 * @author lyl
 */
public class SingletonTopic {
    private static Logger LOGGER = LoggerFactory.getLogger(SingletonTopic.class);
    private static Config CONFIG = Config.getInstance();

    private static SingletonTopic INSTANCE;
    private MqttAsyncClient client;
    static KafkaClient kafkaClient = KafkaClient.getInstance();

    static {
        kafkaClient.start();
    }

    private static IMqttMessageListener DATA_LISTENER = new IMqttMessageListener() {
        @Override
        public void messageArrived(String sid, MqttMessage mqttMessage) throws Exception {
            WrapDevice wrapDevice = Const.GSON.fromJson(mqttMessage.toString(), WrapDevice.class);
            wrapDevice.getMsgMap().concatPath(CONFIG.getGid());
            String msgType = wrapDevice.getMsgMap().extractContent().getMsgtype();

            LOGGER.trace("receive stream data {} from forward", msgType);
            LOGGER.debug("receive stream data size {} from forward", msgType.length());

            if (wrapDevice.getTaskType() == Const.REAL_TIME) {

                if (msgType.equals(Const.DEVICE_DATA)) {
                    kafkaClient.send(CONFIG.getRealTime(), wrapDevice.getMsgMap(), Const.DEFAULT_RETRY);
                } else if (msgType.equals(Const.META_DATA)) {
                    kafkaClient.send(CONFIG.getMeta(), wrapDevice.getMsgMap(), Const.DEFAULT_RETRY);
                } else {
                    LOGGER.warn("not support type {}", msgType);
                    throw new RuntimeException("not support type");
                }

            }

            if (msgType.equals(Const.DEVICE_DATA)) {
                kafkaClient.send(CONFIG.getDev(), wrapDevice.getMsgMap(), Const.DEFAULT_RETRY);
            }
        }
    };

    private SingletonTopic() {
        try {
            client = new MqttAsyncClient(CONFIG.getMasterUrl(), CONFIG.getGid() + "_" + Const.CLIENT_DATA,
                    new MemoryPersistence());

            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(false);
            options.setAutomaticReconnect(true);

            client.connect(options).waitForCompletion();
        } catch (MqttException e) {
            LOGGER.warn("sub data client error with code {}", e.getReasonCode(), e);
            e.printStackTrace();
        }
    }

    public static SingletonTopic getInstance() {
        if (INSTANCE == null) INSTANCE = new SingletonTopic();
        return INSTANCE;
    }

    public void sub(String topic) {
        try {
            LOGGER.debug("sub data with topic {}",topic);
            client.subscribe(topic, CONFIG.getQos(), null, null, DATA_LISTENER);
        } catch (MqttException e) {
            LOGGER.warn("sub sid {} topic exception with code {}", topic, e.getReasonCode(), e);
            e.printStackTrace();
        }
    }
}
