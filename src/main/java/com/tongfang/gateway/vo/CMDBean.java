package com.tongfang.gateway.vo;

/**
 * cmd通道json 实体
 *
 * @author lyl
 */
public class CMDBean {
    //命令类型 0:新设备
    int type;
    //设备SID
    String msg;
    //设备 path
    String path;

    public int getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }

    public String getPath() {
        return path;
    }
}
