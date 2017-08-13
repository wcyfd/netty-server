package com.randioo.mahjong_public_server.net.netty4;

import com.randioo.mahjong_public_server.net.RPCHandler;
import com.randioo.mahjong_public_server.net.RPCSession;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

public class Netty4Session extends RPCSession {

    protected RPCHandler rpcHandler;
    protected ChannelHandlerContext ctx;

    public Netty4Session(RPCHandler handler, ChannelHandlerContext ctx) {
        this.rpcHandler = handler;
        this.ctx = ctx;
    }

    @Override
    public void write(Object obj) {
        ctx.writeAndFlush(obj);

        rpcHandler.sent(this, obj);
    }

    @Override
    public boolean isConnected() {
        return ctx.channel().isOpen();
    }

    @Override
    public <T> T getValue(String key) {
        Channel channel = ctx.channel();
        AttributeKey<T> attributeKey = AttributeKey.valueOf(key);
        T value = channel.attr(attributeKey).get();
        return value;
    }

    @Override
    public <T> void setValue(String key, T value) {
        Channel channel = ctx.channel();
        AttributeKey<T> attributeKey = AttributeKey.valueOf(key);
        channel.attr(attributeKey).set(value);
    }

}
