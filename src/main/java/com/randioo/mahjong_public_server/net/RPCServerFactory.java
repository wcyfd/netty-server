package com.randioo.mahjong_public_server.net;

import com.randioo.mahjong_public_server.net.mina2.Mina2Factory;
import com.randioo.mahjong_public_server.net.netty4.Netty4Factory;

/**
 * 
 * @author AIM
 *
 */
public abstract class RPCServerFactory {
    public enum RPC {
        MINA, NETTY
    }

    public static RPCServerFactory getInstance(RPC type) {
        switch (type) {
        case MINA:
            return new Mina2Factory();
        case NETTY:
            return new Netty4Factory();
        default:
            break;
        }

        return null;
    }

    public abstract RPCServer getRPCServer();
}
