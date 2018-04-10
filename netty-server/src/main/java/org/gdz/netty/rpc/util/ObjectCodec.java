package org.gdz.netty.rpc.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Netty编解码
 * @author guandezhi
 * @ClassName: org.gdz.netty.rpc.util
 * @Description:
 * @date 2018/4/3 14:06
 */
public class ObjectCodec extends MessageToMessageCodec<ByteBuf, Object> {

    private static final Logger logger = LoggerFactory.getLogger(ObjectCodec.class);
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, List<Object> list) throws Exception {
        logger.info("encode...");
        byte[] bytes = ObjectSerializerUtils.serializer(o);
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(bytes);
        list.add(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        logger.info("decode...");
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        Object deSerializer = ObjectSerializerUtils.deSerializer(bytes);
        list.add(deSerializer);
    }
}
