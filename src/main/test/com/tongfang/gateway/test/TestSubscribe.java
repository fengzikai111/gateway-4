package com.tongfang.gateway.test;

import org.eclipse.paho.client.mqttv3.*;



public class TestSubscribe {
	static MqttClient grealtime ;
	
	void sendMsg(String topic ,String msg,int i) throws MqttPersistenceException, MqttException{
		
		
		 MqttMessage message = new MqttMessage(msg.getBytes());
	      //设置消息类型
	     message.setQos(2);
	    System.out.println("发送 :"+ message.getId());
        grealtime.publish(topic,message);
        
		System.out.println("结束");
		
		
//	    grealtime.disconnect();
	}
	
	
	public static void main(String[] args) throws MqttException, InterruptedException {
//		grealtime = new MqttClient("tcp://192.168.203.27:1883", "users"); 
		 grealtime = new MqttClient("tcp://127.0.0.1:61667", "users");
//		while(true){
		 MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(false);
			IMqttToken token = grealtime.connectWithResult(connOpts);
		 TestSubscribe text = new TestSubscribe();
//		 for(int i =0;i<1000;i++){v nc
		 String str = "{\"id\":1087,\"taskType\":0,\"createTime\":1492234997019,\"stauts\":0,\"type\":3,\"pubId\":\"pubRealcolin5\",\"qos\":2,\"msgMap\":{\"f3b5234c2e58480ee780bdca4d17644466\":{\"msgtype\":\"devicedata\",\"at\":1492232595,\"debugmode\":\"off\",\"path\":\"fc503fd13ec248be9cbc2d438803c0fa/ec8211db5d6944eb9057aa12b46894eb/0292e9e9fd4645c8af19ac26747839f9/colin5\",\"data\":{\"msgtype\":\"devicedata\",\"debugmode\":\"off\",\"at\":1492232595,\"tag\":[\"quality\"],\"datastream\":[{\"id\":\"floatrandom1\",\"value\":1091.34,\"quality\":192},{\"id\":\"floatrandom2\",\"value\":1091.34,\"quality\":192},{\"id\":\"floatrandom3\",\"value\":1091.34,\"quality\":192},{\"id\":\"floatrandom4\",\"value\":1091.34,\"quality\":192},{\"id\":\"floatrandom5\",\"value\":1091.34,\"quality\":192},{\"id\":\"floatrandom6\",\"value\":1091.34,\"quality\":192},{\"id\":\"floatrandom7\",\"value\":1091.34,\"quality\":192},{\"id\":\"floatrandom8\",\"value\":1091.34,\"quality\":192},{\"id\":\"floatrandom9\",\"value\":1091.34,\"quality\":192},{\"id\":\"floatrandom10\",\"value\":1091.34,\"quality\":192},{\"id\":\"random1\",\"value\":0.0,\"quality\":192},{\"id\":\"random2\",\"value\":0.0,\"quality\":192},{\"id\":\"random3\",\"value\":0.0,\"quality\":192},{\"id\":\"random4\",\"value\":0.0,\"quality\":192},{\"id\":\"random5\",\"value\":0.0,\"quality\":192},{\"id\":\"random6\",\"value\":0.0,\"quality\":192},{\"id\":\"random7\",\"value\":0.0,\"quality\":192},{\"id\":\"random8\",\"value\":0.0,\"quality\":192},{\"id\":\"random9\",\"value\":0.0,\"quality\":192},{\"id\":\"random10\",\"value\":0.0,\"quality\":192}]}}},\"key\":\"fc503fd13ec248be9cbc2d438803c0fa\"}";
			 text.sendMsg("heart","dddddddddddddddddd",2);
//		 }
		 
//			Thread.sleep(1000);
//		}
//			 {"at":1493968921,"createTime":1493968909423,"id":-1,"key":"edf7dfe1307011e7bea80242ac110002","msgMap":{"edf7dfe1307011e7bea80242ac110002":{"path":"edf7dfe1307011e7bea80242ac110002/52633567a6824e80bcef9b9d4697461c/8ceb5007a53a4cafb3143b8e04e2a549/colin120","at":1493968921,"data":{"datastream":[{"id":"S"},{"id":"JY_1_JAODU"},{"id":"FAILCODE"},{"id":"MS"},{"id":"DEV_STATUS4"},{"id":"AX_X_I"},{"id":"JY_3_JAODU"},{"id":"DW_PNLV"},{"id":"WG_GL_3S"},{"id":"CAX_V_XW"},{"id":"WCNV.GriPPV"},{"id":"DEV_STATUS"},{"id":"CANGW_WD"},{"id":"DEV_STATUS3"},{"id":"DSZ_ZS"},{"id":"BX_X_I"},{"id":"WG_GL"},{"id":"DI_BPQDLQTZ"},{"id":"YG_GL"},{"id":"WTUR.WVir120s"},{"id":"CANGN_WD"},{"id":"GONLV_YS"},{"id":"BCX_V_XW"},{"id":"WTUR.W120s"},{"id":"WTUR.WVir20s"},{"id":"FENGXANG"},{"id":"PPM_SET"},{"id":"WCNV.GriA"},{"id":"WTUR.VAr600s"},{"id":"WTUR.VAr120s"},{"id":"FENGSU"},{"id":"WTRF.MCB35kV"},{"id":"WTRF.MCB690V"},{"id":"STATE_AVAILE"},{"id":"WTUR.VAr20s"},{"id":"WTUR.W600s"},{"id":"WTUR.W20s"},{"id":"CX_X_I"},{"id":"ID"},{"id":"GSZ_ZS"},{"id":"WTUR.WVir600s"},{"id":"WTRF.MCBGs"},{"id":"YG_GL_3S"},{"id":"FDJ_DZRZ_WD"},{"id":"JY_2_JAODU"},{"id":"ABX_V_XW"}],"at":1493968921,"msgtype":"metadata"},"msgtype":"metadata"}},"qos":2,"stauts":0,"taskType":0,"type":0}
	}
	
	
}
