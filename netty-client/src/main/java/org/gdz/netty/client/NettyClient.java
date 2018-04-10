package org.gdz.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author guandezhi
 * @ClassName: org.gdz.netty.rpc.util
 * @Description:
 * @date 2018/4/3 15:50
 */
public class NettyClient {
    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);
    private Bootstrap bootstrap;
    private EventLoopGroup worker;
    private int port;
    private String url;
    private int MAX_RETRY_TIMES = 10;

    public NettyClient(String url, int port) {
        this.url = url;
        this.port = port;
        bootstrap = new Bootstrap();
        worker = new NioEventLoopGroup();
        bootstrap.group(worker);
        bootstrap.channel(NioSocketChannel.class);
    }

    public void close() {
        logger.info("关闭资源");
        worker.shutdownGracefully();
    }

    public Object remoteCall(String msg, int retry) {
        try {
            ClientChannelInitializer clientChannelInitializer = new ClientChannelInitializer();
            bootstrap.handler(clientChannelInitializer);
            ChannelFuture channelFuture = bootstrap.connect(url, port).sync();
            channelFuture.channel().writeAndFlush(msg.getBytes()).channel().closeFuture().sync();
            return msg;
        } catch (InterruptedException e) {
            retry++;
            if (retry > MAX_RETRY_TIMES) {
                throw new RuntimeException("调用Wrong");
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                logger.info("第{}次尝试....失败", retry);
                return remoteCall(msg, retry);
            }
        }
    }
}
