package io.netty.handler.codec.protobuf;

import static io.netty.buffer.Unpooled.wrappedBuffer;

import java.util.List;

import com.google.protobuf.MessageLite;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

@Sharable
public class ProtobufEncoder extends MessageToMessageEncoder<MessageLite.Builder> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageLite.Builder msg, List<Object> out) throws Exception {
        if (msg instanceof MessageLite) {
            out.add(wrappedBuffer(((MessageLite) msg).toByteArray()));
            return;
        }
        if (msg instanceof MessageLite.Builder) {
            out.add(wrappedBuffer(((MessageLite.Builder) msg).build().toByteArray()));
        }
    }
}
