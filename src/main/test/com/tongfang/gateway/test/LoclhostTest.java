package com.tongfang.gateway.test;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LoclhostTest {

    MqttClient client;

    static String text = "";

    public LoclhostTest() throws MqttException {
        init();
        client = new MqttClient("tcp://127.0.0.1:61667", "users", new MemoryPersistence());

    }

    public void newTopic(String newTopic) throws MqttException {
        MqttClient grealtime = new MqttClient("tcp://127.0.0.1:61667", "users", new MemoryPersistence());
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(false);
        IMqttToken token = grealtime.connectWithResult(connOpts);
        MqttMessage message = new MqttMessage(newTopic.getBytes());
        message.setRetained(false);
        //设置消息类型
        message.setQos(2);
        grealtime.publish("newTopic", message);
        grealtime.disconnect();
    }

//	public  void sendTopic(String str) throws MqttException{
//		MqttMessage message = new MqttMessage(str.getBytes());
//		 //设置消息类型
//	    message.setQos(2);
//	    MqttConnectOptions connOpts = new MqttConnectOptions();
//		connOpts.setCleanSession(false);
//		if(!client.isConnected()){
//			IMqttToken token = client.connectWithResult(connOpts);
//		}
//		client.publish("ccid_ccidvalue.sid_sidvalue",message);
//		
//	}

    public static void init() {
        File file = new File("f:/log/demo.txt");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            List<String> list = new ArrayList<String>();
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                System.out.println(tempString);
                text += tempString.trim();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

    }


//    public static ForwardTaskBean createTask(String topic, int id, Date date) {
//        ForwardTaskBean task = (ForwardTaskBean) JacksonUtil.jsonToObject(text, new ForwardTaskBean());
//        task.setId(id);
//        task.setTaskType(0);
//        task.setCreateTime(date);
//        Map<String, Object> map = (Map<String, Object>) task.getMsgMap().get("012a08ba989548f8b8bbc80612d7d823");
//        Map<String, Object> map2 = new HashMap<String, Object>();
//        map2.put(topic, map);
//        task.setMsgMap(map2);
////		System.out.println(JacksonUtil.objectToJSON(task));
//        return task;
//    }


    public static void main(String[] args) throws MqttException {
        LoclhostTest st = new LoclhostTest();

        for (int i = 0; i < 10; i++) {
            String newTopic = "012a08ba989548f8b8bbc80612d7d823" + i;
            st.newTopic("{\"type\":0,\"msg\":\"" + newTopic + "\",\"path\":\"sid_sidvalue/cid_cidvalue/ccid_ccidvalue/fid_fidvalue\"}");
            new Thread() {
                public void run() {
                    try {
//                        List<ForwardTaskBean> listInit = new ArrayList<ForwardTaskBean>();
                        Date date = new Date();
                        Calendar calendar = Calendar.getInstance();
                        for (int i = 0; i < 10; i++) {
//                            ForwardTaskBean temp = createTask(newTopic, i, calendar.getTime());
                            calendar.add(Calendar.SECOND, i + 1);
//                            listInit.add(temp);
                        }
                        Thread.sleep(1000);
                        TestMqtt test = new TestMqtt(newTopic);
//                        for (int i = 0; i < listInit.size(); i++) {
//                            test.sendTopic(newTopic, JacksonUtil.objectToJSON(listInit.get(i)));
//                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }.start();

        }
        System.out.println("结束");
    }
}
