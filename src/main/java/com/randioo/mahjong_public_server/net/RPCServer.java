package com.randioo.mahjong_public_server.net;

import com.google.protobuf.GeneratedMessage;

/**
 * 
 * @author AIM
 *
 */
public abstract class RPCServer {

    protected final RPCServer rpcServer;
    protected RPCListener rpcListener = new DefaultRPCListener();

    public RPCServer() {
        this.rpcServer = this;
    }

    public void listen(RPCListener rpcListener) {
        this.rpcListener = rpcListener;
    }

    protected RPCHandler rpcHandler;

    public void setRPCHandler(RPCHandler handler) {
        this.rpcHandler = handler;
    }

    protected RPCHeartTimeoutHandler rpcHeartTimeoutHandler;

    public void setRpcHeartHandler(RPCHeartTimeoutHandler rpcHeartHandler) {
        this.rpcHeartTimeoutHandler = rpcHeartHandler;
    }

    protected GeneratedMessage sc = null;
    protected GeneratedMessage cs = null;

    protected GeneratedMessage scHeart = null;
    protected GeneratedMessage heartRequest = null;
    protected GeneratedMessage heartResponse = null;

    protected int idleTime;
    protected int responseTime;
    protected boolean heartSwitch;

    /** 端口号 */
    protected int port;

    public void setProtobufInstance(GeneratedMessage cs, GeneratedMessage sc) {
        this.sc = sc;
        this.cs = cs;
    }

    protected abstract void init();

    protected abstract void fireStart();

    /**
     * 设置心跳协议
     * 
     * @param scHeart
     * @param heartRequest
     * @param heartResponse
     * @author wcy 2017年8月14日
     */
    public void setHeartProtocol(GeneratedMessage scHeart, GeneratedMessage heartRequest, GeneratedMessage heartResponse) {
        this.scHeart = scHeart;
        this.heartRequest = heartRequest;
        this.heartResponse = heartResponse;
    }

    public void setHeartParam(int idleTime, int responseTime) {
        this.idleTime = idleTime;
        this.responseTime = responseTime;
    }

    /**
     * 开启心跳
     * 
     * @param heart
     * @author wcy 2017年8月14日
     */
    public void startHeartSwitch(boolean heart) {
        this.heartSwitch = heart;
    }

    public void start(int port) {
        this.port = port;
        if (heartSwitch) {
            if (rpcHeartTimeoutHandler == null) {
                throw new RuntimeException("not set heartHandler");
            }
        }

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
