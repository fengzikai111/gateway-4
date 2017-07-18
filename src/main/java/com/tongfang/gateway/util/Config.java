package com.tongfang.gateway.util;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件信息
 *
 * @author lyl
 */
public class Config {
    private String gid;

    //master URL
    private String masterUrl;
    private String kfkaUrl;
    //selfCheckServer
    private String selfCheckServer;
    private String etcdV2Server;


    //cmd Topic
    private String cmd;
    private String dev;
    //设备采集点元数据总线 topic名称
    private String meta;
    //实时通道
    private String realTime;
    //forward topic
    private String forwardHeartTopic;

    //认证账号
    private String userName;
    //认证密码
    private String password;
    //经过清洗后的合并实时与数据历史数据总线 topic名称
    //qos
    private int qos;
    private String debugMode;
    private String teraAcl;

    private static Config INSTANCE;

    public static Config getInstance() {
        if (INSTANCE == null) try {
            INSTANCE = new Config();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return INSTANCE;
    }


    private Config() throws IOException {
        Properties prop = new Properties();
        InputStream in = null;
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            in = Object.class.getResourceAsStream("/mqtt.properties");
        } else {
            in = new FileInputStream(new File("mqtt.properties"));
        }

        prop.load(in);

        setGid(prop.getProperty("gid"));
        setMasterUrl(prop.getProperty("masterUrl"));
        setCmd(prop.getProperty("cmd"));

        setDev(prop.getProperty("dev"));
        setMeta(prop.getProperty("meta"));
        setQos(Integer.parseInt(prop.getProperty("qos")));
        setKfkaUrl(prop.getProperty("kfka"));
        setSelfCheckServer(prop.getProperty("selfCheckServer"));
        setRealTime(prop.getProperty("realTime"));
        setEtcdV2Server(prop.getProperty("etcdserverv2"));
        setForwardHeartTopic(prop.getProperty("forwardheartTopic"));
        setDebugMode(prop.getProperty("debugMode"));
        in.close();

        if (debugMode.equals("true")) {
            setTeraAcl(Resources.toString(Resources.getResource("acl_sid_keys.json"), Charsets.UTF_8));
        }
    }

    public String getEtcdV2Server() {
        return etcdV2Server;
    }

    public void setEtcdV2Server(String etcdV2Server) {
        this.etcdV2Server = etcdV2Server;
    }

    public String getForwardHeartTopic() {
        return forwardHeartTopic;
    }

    public void setForwardHeartTopic(String forwardHeartTopic) {
        this.forwardHeartTopic = forwardHeartTopic;
    }

    public String getRealTime() {
        return realTime;
    }

    public void setRealTime(String realTime) {
        this.realTime = realTime;
    }

    public String getSelfCheckServer() {
        return selfCheckServer;
    }

    public void setSelfCheckServer(String selfCheckServer) {
        this.selfCheckServer = selfCheckServer;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getKfkaUrl() {
        return kfkaUrl;
    }

    public void setKfkaUrl(String kfkaUrl) {
        this.kfkaUrl = kfkaUrl;
    }

    public int getQos() {
        return qos;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }

    public String getDev() {
        return dev;
    }

    public void setDev(String dev) {
        this.dev = dev;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getMasterUrl() {
        return masterUrl;
    }

    public void setMasterUrl(String masterUrl) {
        this.masterUrl = masterUrl;
    }

    public String getDebugMode() {
        return debugMode;
    }

    public void setDebugMode(String debugMode) {
        this.debugMode = debugMode;
    }

    public String getTeraAcl() {
        return teraAcl;
    }

    public void setTeraAcl(String teraAcl) {
        this.teraAcl = teraAcl;
    }
}
