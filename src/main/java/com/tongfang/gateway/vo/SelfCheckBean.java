package com.tongfang.gateway.vo;

import java.util.Date;

/**
 * 心跳自检实体
 *
 * @author lyl
 */

public class SelfCheckBean {
    //创建时间
    Date time;
    //状态 0正常 1 不正常
    int state;
    //标识名称
    String name;

    public SelfCheckBean(Date time, int state, String name) {
        this.time = time;
        this.state = state;
        this.name = name;
    }
}
