package com.randioo.mahjong_public_server;

import com.randioo.mahjong_public_server.protocol.Fight.SCFightApplyExitGame;
import com.randioo.mahjong_public_server.protocol.ServerMessage.SC;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;

@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // TODO Auto-generated method stub
        super.channelActive(ctx);
        System.out.println("active");
      
    }
    
    

    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        System.out.println("server received data :" + msg);
        SC sc = SC.newBuilder().setSCFightApplyExitGame(SCFightApplyExitGame.newBuilder()).build();

        ctx.writeAndFlush(sc);// 写回数据，
    }

    public void channelReadComplete(ChannelHandlerContext ctx) {
        System.out.println("channelReadComplete");
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER) // flush掉所有写回的数据
                .addListener(ChannelFutureListener.CLOSE); // 当flush完成后关闭channel
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();// 捕捉异常信息
        ctx.close();// 出现异常时关闭channel
    }
}