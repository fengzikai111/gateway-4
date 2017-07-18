package com.tongfang.gateway.test;

import java.io.*;

public class MainBook {


//    try {
//    System.out.println("���ֽ�Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�����ֽڣ�");
//    // һ�ζ�����ֽ�
//    byte[] tempbytes = new byte[100];
//    int byteread = 0;
//    in = new FileInputStream(fileName);
//    ReadFromFile.showAvailableBytes(in);
//    // �������ֽڵ��ֽ������У�bytereadΪһ�ζ�����ֽ���
//    while ((byteread = in.read(tempbytes)) != -1) {
//        System.out.write(tempbytes, 0, byteread);
//    }
//} catch (Exception e1) {
//    e1.printStackTrace();
//} finally {
//    if (in != null) {
//        try {
//            in.close();
//        } catch (IOException e1) {
//        }
//    }
//}


    public static void main(String[] args) {
        File file = new File("F:\\软件\\sant.txt");
        Reader reader = null;
        InputStream in = null;
        try {
//	        	 System.out.println("���ַ�Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���ֽڣ�");
            // һ�ζ�һ���ַ�
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            boolean flg = false;
            int count = 0;
            int row = 0;
            int page = 2000;
            while ((tempchar = reader.read()) != -1) {
                // ����windows�£�\r\n�������ַ���һ��ʱ����ʾһ�����С�
                // ������������ַ��ֿ���ʾʱ���ỻ�����С�
                // ��ˣ����ε�\r����������\n�����򣬽������ܶ���С�
//	            	 if(((char) tempchar) == '@'){
//	            		 flg = true;
//	            	 }
//	                 if (flg) {
                if (count == page) {
                    break;
                }

                if (count >= page - 20000) {
                    if (row > 80) {
                        System.out.println();
                        row = 0;
                    }
                    System.out.print((char) tempchar);
                    row++;
                }
                count++;
//	                 }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }
}
