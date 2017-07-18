package com.tongfang.gateway.test;

import com.tongfang.gateway.Const;
import com.tongfang.gateway.vo.CMDBean;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Calendar;

public class GateWayTest {
    MqttClient grealtime;
    MqttConnectOptions connOpts;
    public int count = 0;

    public GateWayTest() {
        try {
            grealtime = new MqttClient("tcp://127.0.0.1:61667", "usersddd");
            connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(false);
            connOpts.setMaxInflight(9000);
            grealtime.connect(connOpts);
        } catch (MqttException e) {
            e.printStackTrace();
        }


    }

    public void newTopic(String newTopic) throws MqttException, InterruptedException {
        MqttMessage message = new MqttMessage(newTopic.getBytes());
        message.setQos(2);

        grealtime.publish("newTopic", message);
        CMDBean newSidCmd = Const.GSON.fromJson(newTopic, CMDBean.class);
        createDate(newSidCmd.getMsg());
    }

    public void createDate(String topic) {
        new Thread() {
            public void run() {
                Calendar calendar = Calendar.getInstance();
                for (int i = 0; i < 100; i++) {
                    String str = "{\"id\":" + i + ",\"taskType\":0,\"createTime\":" + calendar.getTime().getTime() + ",\"stauts\":0,\"type\":3,\"pubId\":\"pubRealcolin5\",\"qos\":2,\"msgMap\":{\"" + topic + "\":{\"msgtype\":\"devicedata\",\"at\":1492232595,\"debugmode\":\"off\",\"path\":\"fc503fd13ec248be9cbc2d438803c0fa/ec8211db5d6944eb9057aa12b46894eb/0292e9e9fd4645c8af19ac26747839f9/colin5\",\"data\":{\"msgtype\":\"devicedata\",\"debugmode\":\"off\",\"at\":1492232595,\"tag\":[\"quality\"],\"datastream\":[{\"id\":\"floatrandom1\",\"value\":1091.34,\"quality\":192},{\"id\":\"floatrandom2\",\"value\":1091.34,\"quality\":192},{\"id\":\"floatrandom3\",\"value\":1091.34,\"quality\":192},{\"id\":\"floatrandom4\",\"value\":1091.34,\"quality\":192},{\"id\":\"floatrandom5\",\"value\":1091.34,\"quality\":192},{\"id\":\"floatrandom6\",\"value\":1091.34,\"quality\":192},{\"id\":\"floatrandom7\",\"value\":1091.34,\"quality\":192},{\"id\":\"floatrandom8\",\"value\":1091.34,\"quality\":192},{\"id\":\"floatrandom9\",\"value\":1091.34,\"quality\":192},{\"id\":\"floatrandom10\",\"value\":1091.34,\"quality\":192},{\"id\":\"random1\",\"value\":0.0,\"quality\":192},{\"id\":\"random2\",\"value\":0.0,\"quality\":192},{\"id\":\"random3\",\"value\":0.0,\"quality\":192},{\"id\":\"random4\",\"value\":0.0,\"quality\":192},{\"id\":\"random5\",\"value\":0.0,\"quality\":192},{\"id\":\"random6\",\"value\":0.0,\"quality\":192},{\"id\":\"random7\",\"value\":0.0,\"quality\":192},{\"id\":\"random8\",\"value\":0.0,\"quality\":192},{\"id\":\"random9\",\"value\":0.0,\"quality\":192},{\"id\":\"random10\",\"value\":0.0,\"quality\":192}]}}},\"key\":\"fc503fd13ec248be9cbc2d438803c0fa\"}";
                    calendar.add(Calendar.SECOND, i + 1);
//    				System.out.println(str);
                    MqttMessage message2 = new MqttMessage(str.getBytes());
                    //设置消息类型
                    message2.setQos(2);
                    try {
                        grealtime.publish(topic, message2);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }

    public static void main(String[] args) throws MqttException, InterruptedException {
        GateWayTest st = new GateWayTest();
        String newTopic = "74c2a9b4339d11e7bea80242ac110002";
        st.newTopic("{\"type\":0,\"msg\":\"" + newTopic + "\",\"path\":\"/f3b5234c2e58480780dddbdca4d17644466dd/f3b5234cdddd2e58480dddddd780bdca4d17644466dd/cccddddccccccc/dddddddfffff\"}");
    }


//	public  void sendTopic(String newTopic) throws MqttException{
//		grealtime.connect(connOpts);
//        MqttMessage message = new MqttMessage(newTopic.getBytes());
//        message.setRetained(false);
//        //设置消息类型
//        message.setQos(2);
//        grealtime.
//        grealtime.publish("newTopic", message);
//        grealtime.disconnect();
//		
//	}

//    public static ForwardTaskBean createTask(String topic, int id, Date date) {
//        ForwardTaskBean task = new ForwardTaskBean();
//        task.setId(id);
//        task.setTaskType(0);
//        task.setCreateTime(date);
//		String str = "{"id":1087,"taskType":0,"createTime":1492234997019,"stauts":0,"type":3,"pubId":"pubRealcolin5","qos":2,"msgMap":{"fc503fd13ec248be9cbc2d438803c0fa":{"msgtype":"devicedata","at":1492232595,"debugmode":"off","path":"fc503fd13ec248be9cbc2d438803c0fa/ec8211db5d6944eb9057aa12b46894eb/0292e9e9fd4645c8af19ac26747839f9/colin5","data":{"msgtype":"devicedata","debugmode":"off","at":1492232595,"tag":["quality"],"datastream":[{"id":"floatrandom1","value":1091.34,"quality":192},{"id":"floatrandom2","value":1091.34,"quality":192},{"id":"floatrandom3","value":1091.34,"quality":192},{"id":"floatrandom4","value":1091.34,"quality":192},{"id":"floatrandom5","value":1091.34,"quality":192},{"id":"floatrandom6","value":1091.34,"quality":192},{"id":"floatrandom7","value":1091.34,"quality":192},{"id":"floatrandom8","value":1091.34,"quality":192},{"id":"floatrandom9","value":1091.34,"quality":192},{"id":"floatrandom10","value":1091.34,"quality":192},{"id":"random1","value":0.0,"quality":192},{"id":"random2","value":0.0,"quality":192},{"id":"random3","value":0.0,"quality":192},{"id":"random4","value":0.0,"quality":192},{"id":"random5","value":0.0,"quality":192},{"id":"random6","value":0.0,"quality":192},{"id":"random7","value":0.0,"quality":192},{"id":"random8","value":0.0,"quality":192},{"id":"random9","value":0.0,"quality":192},{"id":"random10","value":0.0,"quality":192}]}}},"key":"fc503fd13ec248be9cbc2d438803c0fa"}";
//		Map<String,Object> map = JacksonUtil.jsonToMap("{\""+topic+"\": {\"msgtype\": \"devicedata or devicedatalegacy\",\"at\": 1000000000,\"debugmode\": \"on\",\"debugsequence\": \"1\",\"path\": \"sid/cid/ccid\",\"data\": {\"msgtype\": \"metadata\",\"debugmode\": \"on\",\"at\": 1000000000,\"datastream\": [{\"id\": \"v1\",\"datatype\": \" v2\",\"scanrate\": \"v3\"}]}}}");
//		task.setMsgStr(str);
//        return task;

}


//	public static void main(String[] args) throws InterruptedException {
////		 List<Integer> listInit = new ArrayList<Integer>();    
////	     for(int i = 0;i < 200;i++){     
////	    	 listInit.add(i);    
////	     } 
//	     List<ForwardTaskBean> listObject= new ArrayList<ForwardTaskBean>();   
//	     for(int i=0; i<200;i++){
//			ForwardTaskBean temp = createTask("test",i);
//			temp.setTopic("test");
//			listObject.add(temp);
//			Thread.sleep(1000);
//		}
//	    Collections.shuffle(listObject);
////	    List<ForwardTaskBean> cool= new ArrayList<ForwardTaskBean>(listObject);   
//	    Collections.sort(listObject,task);
//	    
//	    Date date = listObject.get(0).getCreateTime();
//	    for (int i = 1; i < listObject.size(); i++) {
//	    	long interval  = (listObject.get(i).getCreateTime().getTime()-date.getTime())/1000;
//			System.out.println(listObject.get(i).getId());
//			System.out.println(interval);
//			date = listObject.get(i).getCreateTime();
//		}
//	     
//	     
//	     
//	     
//	     
//	}


//    public static void main(String[] args) throws Exception {
//        List<ForwardTaskBean> list = new ArrayList<ForwardTaskBean>();
//        GateWayTest st = new GateWayTest();
//        for (int i = 0; i < 1; i++) {
//            String newTopic = "74c54046339d11e7bea80242ac110002";
//            st.newTopic("{\"type\":0,\"msg\":\"" + newTopic + "\",\"path\":\"sid_sidvalue/cid_cidvalue/ccid_ccidvalue/fid_fidvalue\"}");
//			new Thread(){  
//				public void run(){
//					try {
//						 List<ForwardTaskBean> listInit = new ArrayList<ForwardTaskBean>();    
//						 Date date = new Date();
//						 
//						 Calendar calendar = Calendar.getInstance();
//					     for(int i = 0;i < 1;i++){  
//					    	 ForwardTaskBean temp = createTask(newTopic,i,calendar.getTime());
//					    	 calendar.add (Calendar.SECOND, i+1);
//					    	 listInit.add(temp);    
//					     } 
////					     Collections.shuffle(listInit);
//						TestMqtt test = new TestMqtt("user"+new Date().getTime());
////						Thread.sleep(1000);
//						for(int i=0; i<listInit.size();i++){
//							
////							if(i == 150){
////								
////							}else{
//								ForwardTaskBean temp = listInit.get(i);
//								temp.setTopic(newTopic);
////								st.sendTopic(JacksonUtil.objectToJSON(temp));
//								test.sendTopic(newTopic,JacksonUtil.objectToJSON(temp));
////							}
//						}
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					
//				}
//			}.start(); 
//		
//        }

//		RedisService.getByTopic("GWTest");

//		
//		
//		list.remove(list.get(list.size()-1));
//		
//		RedisService.setList("A", list);
//		
//		List<ForwardTaskBean> list = RedisService.getByTopic("A");

//		List<ForwardTaskBean> list = RedisService.getByTopic("GWTest");
//		for (int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i).getId());
//		}

//    }

//}
