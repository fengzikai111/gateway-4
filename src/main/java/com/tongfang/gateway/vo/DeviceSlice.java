package com.tongfang.gateway.vo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Created by wangzhen on 2017/6/10.
 */
public class DeviceSlice extends HashMap<String, SidContent> {
    //TODO: status for history and realtime;
    static Logger LOGGER = LoggerFactory.getLogger(DeviceSlice.class);

    public String extractSid() {
        if (size() == 1)
            for (Entry<String, SidContent> entry : this.entrySet()) {
                return entry.getKey();
            }
        LOGGER.warn("extract sid from map wrong");
        throw new RuntimeException("extract sid from map wrong");
    }

    public SidContent extractContent() {
        if (size() == 1)
            for (Entry<String, SidContent> entry : this.entrySet()) {
                return entry.getValue();
            }
        LOGGER.warn("extract sid from map wrong");
        throw new RuntimeException("extract sid from map wrong");
    }

    public void concatPath(String gid) {
        if (size() == 1)
            for (Entry<String, SidContent> entry : this.entrySet()) {
                entry.getValue().path += "/" + gid;
            }
        else {
            LOGGER.warn("concat path from map wrong");
            throw new RuntimeException("concat path from map wrong");
        }
    }

    public String extractPath() {
        if (size() == 1)
            for (Entry<String, SidContent> entry : this.entrySet()) {
                return entry.getValue().path;
            }
        LOGGER.warn("extract path from map wrong");
        throw new RuntimeException("extract path from map wrong");
    }

}
