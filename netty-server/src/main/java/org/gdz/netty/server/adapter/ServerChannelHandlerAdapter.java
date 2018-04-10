package org.gdz.netty.server.adapter;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.gdz.netty.server.dispatcher.RequestDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * NettyServer通道适配器
 * @author guandezhi
 * @ClassName: org.gdz.netty.server.adapter
 * @Description:
 * @date 2018/4/3 14:14
 */
@Component
@ChannelHandler.Sharable
public class ServerChannelHandlerAdapter extends ChannelHandlerAdapter {

    /**
     * 日志打印
     */
    private static final Logger logger = LoggerFactory.getLogger(ServerChannelHandlerAdapter.class);

    /**
     * 注入请求分排器
     */
    @Resource
    private RequestDispatcher requestDispatcher;

    /**
     * 异常时执行
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
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
        byte[] bytes = (byte[])msg;
        String request = new String(bytes, "UTF-8");
        System.out.println("Server:" + request);
        requestDispatcher.dispatcher(ctx, request);
    }


}
