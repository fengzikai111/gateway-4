package com.tongfang.gateway.callback.service;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpCommonUtil {

    //退款的请求方法
    public static int doPost(String urlStr, String strInfo) throws Exception {
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
           e.printStackTrace();
        }
        return reStr;

    }

    public static void main(String[] args) throws Exception {
    	doPost("http://192.168.208.3/gateway/v1.0", "{\"time\":\"Jun 22, 2017 4:53:58 PM\",\"state\":1,\"name\":\"f3b5234c2e58480780bdca4d17644466dd\"}");
	}

}