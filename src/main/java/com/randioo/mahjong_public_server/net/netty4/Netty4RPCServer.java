package com.randioo.mahjong_public_server.net.netty4;

import java.util.concurrent.TimeUnit;

import com.randioo.mahjong_public_server.net.RPCChannel;
import com.randioo.mahjong_public_server.net.RPCServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtoEncoder;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * netty 服务器
 * 
 * @author AIM
 *
 */
public class Netty4RPCServer extends RPCServer {

    private ServerBootstrap b = null;
    private EventLoopGroup boss = null;
    private EventLoopGroup group = null;
    private Channel channel = null;
    

    @Override
    protected void init() {
        boss = new NioEventLoopGroup();// 通过nio方式来接收连接和处理连接
        group = new NioEventLoopGroup();// 通过nio方式来接收连接和处理连接
        b = new ServerBootstrap();// 引导辅助程序
        b.group(boss, group);
        b.channel(NioServerSocketChannel.class);// 设置nio类型的channel
        b.childHandler(new ChannelInitializer<SocketChannel>() {// 有连接到达时会创建一个channel
            protected void initChannel(SocketChannel ch) throws Exception {
                // pipeline管理channel中的Handler，在channel队列中添加一个handler来处理业务
                ch.pipeline().addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
                
                ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());// 解码(处理半包)
                ch.pipeline().addLast(new ProtobufDecoder(cs));
                ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());// 加长度
                ch.pipeline().addLast(new ProtoEncoder());// 编码

                // ch.pipeline().addLast(new EchoServerHandler());
                ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {

                    /**
                     * 创建Session
                     * 
                     * @param ctx
                     * @return
                     */
                    private Netty4Session createSession(ChannelHandlerContext ctx) {
                        Netty4Session session = new Netty4Session(rpcHandler, ctx);
                        return session;
                    }

                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        rpcHandler.open(createSession(ctx));
                    }

                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        rpcHandler.read(createSession(ctx), msg);
                    }

                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                        rpcHandler.exception(createSession(ctx), cause);
                    }

                    @Override
                    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
                        rpcHandler.close(createSession(ctx));
                    }

                });
            }
        }).childOption(ChannelOption.TCP_NODELAY, true);

    }

    @Override
    public void stop() {
        channel.close();
    }

    @Override
    protected void fireStart() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    ChannelFuture f = b.bind(port).sync();// 配置完成，开始绑定server，通过调用sync同步方法阻塞直到绑定成功

                    RPCChannel rpcChannel = new RPCChannel();
                    rpcChannel.setSocketAddress(f.channel().localAddress());

                    if (rpcListener != null) {
                        rpcListener.execute(rpcChannel);
                    }

                    channel = f.channel();
                    channel.closeFuture().sync();// 应用程序会一直等待，直到channel关闭
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        group.shutdownGracefully().sync();// 关闭EventLoopGroup，释放掉所有资源包括创建的线程
                        boss.shutdownGracefully().sync();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
