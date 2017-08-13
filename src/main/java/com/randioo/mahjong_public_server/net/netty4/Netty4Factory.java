package com.randioo.mahjong_public_server.net.netty4;

import com.randioo.mahjong_public_server.net.RPCServer;
import com.randioo.mahjong_public_server.net.RPCServerFactory;

public class Netty4Factory extends RPCServerFactory {

    @Override
    public RPCServer getRPCServer() {
        return new Netty4RPCServer();
    }

}
