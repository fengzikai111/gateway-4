package com.tongfang.gateway.vo;

import com.google.gson.JsonElement;

/**
 * Created by wangzhen on 2017/6/10.
 */
public class SidContent {
    String msgtype;
    Long at;
    String debugmode;
    String path;
    JsonElement data;

    public SidContent(String msgtype, Long at, String debugmode, String path) {
        this.msgtype = msgtype;
        this.at = at;
        this.debugmode = debugmode;
        this.path = path;
    }

    public String getMsgtype() {
        return msgtype;
    }
}
