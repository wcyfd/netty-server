package com.randioo.mahjong_public_server.net;

import java.net.SocketAddress;

/**
 * 连接通道
 * 
 * @author AIM
 *
 */
public class RPCChannel {
    protected SocketAddress socketAddress;

    public void setSocketAddress(SocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }
}
