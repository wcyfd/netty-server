package com.randioo.mahjong_public_server;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import com.randioo.mahjong_public_server.protocol.ClientMessage.CS;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

public class EchoServer {
    public static void start(int port) throws InterruptedException {
        ServerBootstrap b = new ServerBootstrap();// 引导辅助程序
        EventLoopGroup group = new NioEventLoopGroup();// 通过nio方式来接收连接和处理连接
        try {
            b.group(group);
            b.channel(NioServerSocketChannel.class);// 设置nio类型的channel
            b.localAddress(new InetSocketAddress(port));// 设置监听端口
            b.childHandler(new ChannelInitializer<SocketChannel>() {// 有连接到达时会创建一个channel
                protected void initChannel(SocketChannel ch) throws Exception {
                    // pipeline管理channel中的Handler，在channel队列中添加一个handler来处理业务
                    // ch.pipeline().addLast(new IdleStateHandler(5, 0, 0,
                    // TimeUnit.SECONDS));

                    ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());// 解码(处理半包)
                    ch.pipeline().addLast(new ProtobufDecoder(CS.getDefaultInstance()));
//                    ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());// 加长度
//                    ch.pipeline().addLast(new ProtobufEncoder());// 编码

                    ch.pipeline().addLast("myHandler", new EchoServerHandler());
                }
            });
            ChannelFuture f = b.bind().sync();// 配置完成，开始绑定server，通过调用sync同步方法阻塞直到绑定成功
            System.out.println(EchoServer.class.getName() + " started and listen on " + f.channel().localAddress());
            f.channel().closeFuture().sync();// 应用程序会一直等待，直到channel关闭
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully().sync();// 关闭EventLoopGroup，释放掉所有资源包括创建的线程
        }
    }

    public static void main(String[] args) {

        try {
            EchoServer.start(10006);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
