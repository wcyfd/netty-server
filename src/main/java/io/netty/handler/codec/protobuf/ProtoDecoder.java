package io.netty.handler.codec.protobuf;

import java.util.List;

import com.google.protobuf.GeneratedMessage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class ProtoDecoder extends MessageToMessageDecoder<ByteBuf> {

    private Class<? extends GeneratedMessage> clazz;
    
    
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        // TODO Auto-generated method stub
        final byte[] array;
        final int offset;
        final int length = msg.readableBytes();
        if (msg.hasArray()) {
            array = msg.array();
            offset = msg.arrayOffset() + msg.readerIndex();
        } else {
            array = new byte[length];
            msg.getBytes(msg.readerIndex(), array, 0, length);
            offset = 0;
        }

        
//        if (extensionRegistry == null) {
//            if (HAS_PARSER) {
////                out.add(prototype.getParserForType().parseFrom(array, offset, length));
//            } else {
//                out.add(prototype.newBuilderForType().mergeFrom(array, offset, length).build());
//            }
//        } else {
//            if (HAS_PARSER) {
////                out.add(prototype.getParserForType().parseFrom(
////                        array, offset, length, extensionRegistry));
//            } else {
//                out.add(prototype.newBuilderForType().mergeFrom(
//                        array, offset, length, extensionRegistry).build());
//            }
//        }
    }

}
