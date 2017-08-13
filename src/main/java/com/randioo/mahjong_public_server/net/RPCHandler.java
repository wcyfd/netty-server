package com.randioo.mahjong_public_server.net;

public interface RPCHandler {
    /**
     * 打开连接
     * 
     * @param session
     */
    void open(RPCSession session);

    /**
     * 读取信息
     * 
     * @param session
     * @param obj
     */
    void read(RPCSession session, Object obj);

    /**
     * 成功发送消息后的回调
     * 
     * @param session
     * @param obj
     */
    void sent(RPCSession session, Object obj);

    /**
     * 异常捕获
     * 
     * @param session
     * @param e
     */
    void exception(RPCSession session, Throwable e);

    /**
     * 连接关闭
     * 
     * @param session
     */
    void close(RPCSession session);
}
