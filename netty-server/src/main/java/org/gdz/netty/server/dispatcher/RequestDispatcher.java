package org.gdz.netty.server.dispatcher;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 请求分排器
 * @author guandezhi
 * @ClassName: org.gdz.netty.server.dispatcher
 * @Description:
 * @date 2018/4/3 14:16
 */
@Component
public class RequestDispatcher implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(RequestDispatcher.class);

    private ExecutorService executorService = Executors.newFixedThreadPool(65535);

    /**
     * 上下文
     */
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 发送
     * @param channelHandlerContext
     * @param string
     */
    public void dispatcher(final ChannelHandlerContext channelHandlerContext, final String string) {
        executorService.submit(() -> {
            ChannelFuture channelFuture = null;
                logger.info("接收到的内容是={}", string);
                if (string == null) {
                    channelFuture = channelHandlerContext.writeAndFlush("接收内容为空");
                } else {
                    channelFuture = channelHandlerContext.writeAndFlush("接收到内容为=" + string);
                }
                channelFuture.addListener(ChannelFutureListener.CLOSE);
        });
    }

}
