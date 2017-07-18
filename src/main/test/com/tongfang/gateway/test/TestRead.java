package com.tongfang.gateway.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestRead {
	static GateWayTest st = new GateWayTest();

    public static void main(String[] args) throws Exception {
        File file = new File("f:/log/60SID.txt");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            List<String> list = new ArrayList<String>();
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                list.add(tempString.trim());
            }
            reader.close();
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
                st.newTopic("{\"type\":0,\"msg\":\"" + list.get(i) + "\",\"path\":\"sid_sidvalue/cid_cidvalue/ccid_ccidvalue/fid_fidvalue\"}");
            }


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

}
