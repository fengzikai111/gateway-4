package com.tongfang.gateway;

import com.google.gson.Gson;

/**
 * Created by wangzhen on 2017/6/20.
 */
public class Const {
    public static final int CONNECTED = 1;
    public static final int DISCONNECTED = 0;

    public static final Gson GSON = new Gson();
    public static final int REAL_TIME = 0;
    public static final String DEVICE_DATA = "devicedata";
    public static final String META_DATA = "metadata";

    public static final int CREATE_CMD_CODE = 0;

    public static final String CLIENT_HEART = "hb";
    public static final String CLIENT_CMD = "cmd";
    public static final String CLIENT_DATA = "data";

    public static final long LISTEN_INTERVAL = 5000;
    public static final int NOT_FOUND_BROKERS = 1;
    public static final int CONNECTION_LOSE = 2;
    public static final int CONNECTION_CONNECTED = 3;
    public static final int DEFAULT_RETRY = 3;

    public static final String COLLECTORS="/collectors";
    public static final int HEART_INTERVAL_MS=30000;

    public static final String DEBUG="true";
}
