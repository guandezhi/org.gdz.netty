package org.gdz.netty.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.gdz.netty.rpc.util.ObjectCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 初始化Channel
 * @author guandezhi
 * @ClassName: org.gdz.netty.client
 * @Description:
 * @date 2018/4/4 14:29
 */
@Component
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    private static final Logger logger = LoggerFactory.getLogger(ClientChannelInitializer.class);

    /**
     * 初始化方法
     * This method will be called once the Channel was registered.
     * After the method returns this instance will be removed from the ChannelPipeline of the Channel.
     * @param socketChannel
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        logger.info("开始初始化SocketChannel......");
        /**
         * 获取ChannelPipeline
         */
        ChannelPipeline pipeline = socketChannel.pipeline();
        /**
         * 添加ChannelHandler
         */
        // 一个编码器，参数必须是1,2,3,4,8；分别代表能存储二进制的最大长度
        pipeline.addLast(new LengthFieldPrepender(2));
        // 一个解码器
        pipeline.addLast(new LengthFieldBasedFrameDecoder(1024 * 1024, 0, 2, 0, 2));
        // 自定义编码/解码器
        pipeline.addLast(new ObjectCodec());
        // 自定义个适配器
        pipeline.addLast(new ClientChannelHandlerAdapter());

        logger.info("结束初始化SocketChannel......");
    }
}
