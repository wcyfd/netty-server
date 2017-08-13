package com.randioo.mahjong_public_server;

import com.randioo.mahjong_public_server.net.RPCHandler;
import com.randioo.mahjong_public_server.net.RPCServer;
import com.randioo.mahjong_public_server.net.RPCServerFactory;
import com.randioo.mahjong_public_server.net.RPCServerFactory.RPC;
import com.randioo.mahjong_public_server.net.RPCSession;
import com.randioo.mahjong_public_server.protocol.ClientMessage.CS;
import com.randioo.mahjong_public_server.protocol.Fight.FightChiResponse;
import com.randioo.mahjong_public_server.protocol.ServerMessage.SC;

public class PRCMain {
    public static void main(String[] args) {
        RPCServerFactory factory = RPCServerFactory.getInstance(RPC.MINA);
        RPCServer rpcServer = factory.getRPCServer();
        rpcServer.setRPCHandler(new RPCHandler() {

            @Override
            public void open(RPCSession session) {
                System.out.println("open");
            }

            @Override
            public void read(RPCSession session, Object obj) {
                System.out.println(obj);
                session.write(SC.newBuilder().setFightChiResponse(FightChiResponse.newBuilder()).build());
            }

            @Override
            public void sent(RPCSession session, Object obj) {
                System.out.println("already send=>" + obj);
            }

            @Override
            public void exception(RPCSession session, Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void close(RPCSession session) {
                System.out.println(session + " closed ");

            }
        });
        rpcServer.setProtobufInstance(CS.getDefaultInstance(), SC.getDefaultInstance());
        rpcServer.start(10006);

    }
}
