package com.randioo.mahjong_public_server;

import com.randioo.mahjong_public_server.protocol.Fight.SCFightApplyExitGame;
import com.randioo.mahjong_public_server.protocol.Fight.SCFightHu;
import com.randioo.mahjong_public_server.protocol.ServerMessage.SC;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("active");
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("channelRead");
        System.out.println("server received data :" + msg);
        SC sc = SC.newBuilder().setSCFightApplyExitGame(SCFightApplyExitGame.newBuilder()).build();

        ctx.writeAndFlush(sc);// 写回数据，
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("channelInactive");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("channelRegistered");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("channelUnregistered");
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("channelWritabilityChanged");
    }

    public void channelReadComplete(ChannelHandlerContext ctx) {
        System.out.println("channelReadComplete");
        ctx.writeAndFlush(SC.newBuilder().setSCFightHu(SCFightHu.newBuilder()).build());

        // ctx.writeAndFlush(Unpooled.EMPTY_BUFFER) // flush掉所有写回的数据
        // .addListener(ChannelFutureListener.CLOSE); // 当flush完成后关闭channel
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // cause.printStackTrace();// 捕捉异常信息
        System.out.println("close");
        ctx.close();// 出现异常时关闭channel
    }
}