package com.randioo.mahjong_public_server.net;

/**
 * 远程连接监听
 * 
 * @author AIM
 *
 */
public interface RPCListener {
    void execute(RPCChannel channel);
}
