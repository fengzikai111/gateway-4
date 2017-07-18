package com.tongfang.gateway.etcdv2;

import com.tongfang.gateway.Const;

public class EtcdResult {
    // General values
    String action;
    EtcdNode node;
    EtcdNode prevNode;

    public EtcdNode getNode() {
        return node;
    }

    @Override
    public String toString() {
        return Const.GSON.toJson(this);
    }
}
