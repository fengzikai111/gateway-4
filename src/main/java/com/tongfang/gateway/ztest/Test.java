package com.tongfang.gateway.ztest;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Test {
	static int i = 0;
	public static void main(String[] args) throws MqttException, InterruptedException {
		try{
		final MqttClient grealtime = new MqttClient("tcp://172.21.1.5:1883", "ssss");
		MqttConnectOptions connOpts = new MqttConnectOptions();
		connOpts.setServerURIs(new String[0]);
		
		connOpts.setCleanSession(false);
		grealtime.connect(connOpts);
		grealtime.setCallback(new MqttCallback(){
			public void messageArrived(String topic, MqttMessage message) throws Exception {
				System.out.println(new String(message.getPayload()));
			}
			public void deliveryComplete(IMqttDeliveryToken token) {
				System.out.println("sleep hh" +token);
			}
			public void connectionLost(Throwable cause) {
				System.out.println("sleep hh e3");
				cause.printStackTrace();
			}
		});
			grealtime.subscribe("reg", 2);
			grealtime.subscribe("reg", 2);
		}catch (Exception e){
			System.out.println("sleep e");
			e.printStackTrace();
		}
		
	}
}
