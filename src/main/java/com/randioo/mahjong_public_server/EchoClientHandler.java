package com.randioo.mahjong_public_server;

import com.randioo.mahjong_public_server.protocol.ClientMessage.CS;
import com.randioo.mahjong_public_server.protocol.Login.LoginGetRoleDataRequest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

@Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    /**
     * 此方法会在连接到服务器后被调用
     */
    public void channelActive(ChannelHandlerContext ctx) {
        // ctx.write(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));

        CS cs = CS.newBuilder().setLoginGetRoleDataRequest(LoginGetRoleDataRequest.newBuilder().setAccount("wcy"))
                .build();
        System.out.println(cs);
        ctx.writeAndFlush(cs);
    }

    /**
     * 此方法会在接收到服务器数据后调用
     */
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
        System.out.println("Client received: " + ByteBufUtil.hexDump(in.readBytes(in.readableBytes())));
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // TODO Auto-generated method stub
        System.out.println(msg);
    }

    /**
     * 捕捉到异常
     */
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
