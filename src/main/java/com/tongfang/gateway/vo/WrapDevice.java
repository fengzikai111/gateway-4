package com.tongfang.gateway.vo;

/**
 * Created by wangzhen on 2017/6/13.
 */
public class WrapDevice {
    int taskType;
    DeviceSlice msgMap;

    public int getTaskType() {
        return taskType;
    }

    public DeviceSlice getMsgMap() {
        return msgMap;
    }

    public WrapDevice(int taskType, DeviceSlice msgMap) {
        this.taskType = taskType;
        this.msgMap = msgMap;
    }
}
