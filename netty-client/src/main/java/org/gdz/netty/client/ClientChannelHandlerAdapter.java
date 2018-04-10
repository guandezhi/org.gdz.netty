package org.gdz.netty.client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author guandezhi
 * @ClassName: org.gdz.netty.client
 * @Description:
 * @date 2018/4/4 15:25
 */
@Component
@ChannelHandler.Sharable
public class ClientChannelHandlerAdapter extends ChannelHandlerAdapter{
    private static final Logger logger = LoggerFactory.getLogger(ClientChannelHandlerAdapter.class);

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("exceptionCaught......");
        logger.info("客户端出异常了,异常信息:{}", cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 读取数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("channelRead...");
        System.out.println("Server返回:" + String.valueOf(msg));

    }

}
