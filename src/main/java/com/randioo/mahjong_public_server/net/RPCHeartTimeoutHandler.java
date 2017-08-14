package com.randioo.mahjong_public_server.net;

/**
 * 心跳超时接口
 * 
 * @author wcy 2017年8月14日
 *
 */
public interface RPCHeartTimeoutHandler {
    /**
     * 超时
     * 
     * @param rpcServer
     * @param rpcSession
     * @author wcy 2017年8月14日
     */
    void timeout(RPCServer rpcServer, RPCSession rpcSession);
}
