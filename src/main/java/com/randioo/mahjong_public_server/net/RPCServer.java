package com.randioo.mahjong_public_server.net;

import com.google.protobuf.GeneratedMessage;

/**
 * 
 * @author AIM
 *
 */
public abstract class RPCServer {
    protected RPCListener rpcListener = new DefaultRPCListener();

    public void listen(RPCListener rpcListener) {
        this.rpcListener = rpcListener;
    }

    protected RPCHandler rpcHandler;

    public void setRPCHandler(RPCHandler handler) {
        this.rpcHandler = handler;
    }

    protected GeneratedMessage sc = null;
    protected GeneratedMessage cs = null;

    protected GeneratedMessage scHeart = null;
    protected GeneratedMessage heartRequest = null;
    protected GeneratedMessage heartResponse = null;

    protected int idleTime;
    protected int responseTime;

    /** 端口号 */
    protected int port;

    public void setProtobufInstance(GeneratedMessage cs, GeneratedMessage sc) {
        this.sc = sc;
        this.cs = cs;
    }

    protected abstract void init();

    protected abstract void fireStart();

    public void addHeart(GeneratedMessage scHeart, GeneratedMessage heartRequest, GeneratedMessage heartResponse,
            int idleTime, int responseTime) {
        this.scHeart = scHeart;
        this.heartRequest = heartRequest;
        this.heartResponse = heartResponse;
        this.idleTime = idleTime;
        this.responseTime = responseTime;
    }

    public void start(int port) {
        this.port = port;
        // 初始化
        this.init();

        if (rpcHandler == null) {
            throw new RuntimeException("rpcHandler is null");
        }

        // 启动
        this.fireStart();
    }

    public abstract void stop();
}
