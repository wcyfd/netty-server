package com.randioo.mahjong_public_server.net.mina2;

import com.randioo.mahjong_public_server.net.RPCServer;
import com.randioo.mahjong_public_server.net.RPCServerFactory;

public class Mina2Factory extends RPCServerFactory {

    @Override
    public RPCServer getRPCServer() {
        return new Mina2RPCServer();
    }

}
