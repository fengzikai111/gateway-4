package com.tongfang.gateway.etcdv2;

import com.tongfang.gateway.Const;

import java.util.List;

public class EtcdNode {
    public String key;
    public long createdIndex;
    public long modifiedIndex;
    public String value;

    // For TTL keys
    public String expiration;
    public Integer ttl;

    // For listings
    public boolean dir;
    public List<EtcdNode> nodes;

    @Override
    public String toString() {
        return Const.GSON.toJson(this);
    }
}
