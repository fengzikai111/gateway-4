package com.tongfang.gateway.test;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestMqtt {

    static MqttAsyncClient client;

    static IMqttToken token;

    public TestMqtt(String id) throws MqttException {
        client = new MqttAsyncClient("tcp://127.0.0.1:61667", id, new MemoryPersistence());
    }


    public void sendTopic(String topic, String str) throws MqttException {
        MqttMessage message = new MqttMessage(str.getBytes());

        //设置消息类型
        message.setQos(2);
        client.publish(topic, message);

        System.out.println(token.getMessageId());
    }

    public void sendTopic(String topic, String str, int i) throws MqttException {
        MqttMessage message = new MqttMessage(str.getBytes());
        message.setId(i);
        //设置消息类型
        message.setQos(2);

        if (client.isConnected()) {
            client.publish(topic, message);
        }

        System.out.println(token.getMessageId());
    }

    public static void main(String[] args) throws MqttException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
        TestMqtt t = new TestMqtt("test");
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(false);
        token = client.connect(connOpts);
        System.out.println(sdf.format(new Date()));
        for (int i = 0; i < 100; i++) {
            t.sendTopic("test", "a" + i, i);
        }
        System.out.println(sdf.format(new Date()));
    }


}
