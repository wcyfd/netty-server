package com.randioo.mahjong_public_server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtoEncoder;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import com.randioo.mahjong_public_server.protocol.ServerMessage.SC;

public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group);
            b.channel(NioSocketChannel.class);
            b.handler(new ChannelInitializer<SocketChannel>() {

                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());// 解码(处理半包)
                    ch.pipeline().addLast(new ProtobufDecoder(SC.getDefaultInstance()));
//                    ch.pipeline().addLast(new StringDecoder(Charset.forName("utf-8")));
                    ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());// 加长度
                    ch.pipeline().addLast(new ProtoEncoder());// 编码

                    ch.pipeline().addLast(new EchoClientHandler());
                }
            });
            ChannelFuture f = b.connect(new InetSocketAddress(host, port)).sync();
            f.addListener(new ChannelFutureListener() {

                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("client connected");
                    } else {
                        System.out.println("server attemp failed");
                        future.cause().printStackTrace();
                    }

                }
            });
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {

        EchoClient echoClient = new EchoClient("127.0.0.1", 10006);
        echoClient.start();
        
        
        // CS cs =
        // CS.newBuilder().setLoginGetRoleDataRequest(LoginGetRoleDataRequest.newBuilder().setAccount("wcy"))
        // .build();
        // echoClient.getChannelFuture().channel().writeAndFlush(cs);

    }

}
