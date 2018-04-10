package org.gdz.netty.server.listener;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.gdz.netty.rpc.util.ObjectCodec;
import org.gdz.netty.server.adapter.ServerChannelHandlerAdapter;
import org.gdz.netty.server.config.NettyServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * Netty服务器监听器
 * @author guandezhi
 * @ClassName: org.gdz.netty.server.listener
 * @Description:
 * @date 2018/4/3 14:36
 */
@Component
public class NettyServerListener {
    // 日志
    private static final Logger logger = LoggerFactory.getLogger(NettyServerListener.class);


    /**
     * 创建Server端的ServerBootstrap
     * ServerBootstrap是一个启动NIO服务的辅助启动类。你可以在这个服务中直接使用Channel，但是这会是一个复杂的处理过
     * 程，在很多情况下你并不需要这样做。
     */
    ServerBootstrap serverBootstrap = new ServerBootstrap();

    /**
     * NioEventLoopGroup是用来处理IO操作的多线程事件循环器， Netty提供了许多不同的EventLoopGroup的实现用来处理不同的
     * 传输协议。在这个例子中我们实现了一个服务端的应用，因此会有2个NioEventLoopGroup会被使用。第一个经常被叫做boss，用
     * 来接收进来的连接。第二个经常被叫做worker，用来处理已经被接收的连接，一旦boss接收到连接，就会把连接信息注册到worker
     * 上。如何知道多少个线程已经被使用，如何映射到已经创建的Channels上都需要依赖于EventLoopGroup的实现，并且可以通过构
     * 造函数来配置他们的关系。
     */
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();


    /**
     * 注入通道适配器
     */
    @Resource
    private ServerChannelHandlerAdapter serverChannelHandlerAdapter;

    /**
     * 注入配置
     */
    @Resource
    private NettyServerConfig nettyServerConfig;


    /**
     * 开启服务及线程
     */
    @PostConstruct
    public void start() {
        int port = nettyServerConfig.getPort();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class);

        new Thread(() -> {
            try {
                // 设置事件处理
                serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline channelPipeline = socketChannel.pipeline();

                        channelPipeline.addLast(new LengthFieldBasedFrameDecoder(nettyServerConfig.getMaxFrameLength(), 0, 2, 0, 2));
                        channelPipeline.addLast(new LengthFieldPrepender(2));
                        channelPipeline.addLast(new ObjectCodec());
                        channelPipeline.addLast(serverChannelHandlerAdapter);

                    }
                });
                logger.info("netty服务器在{}端口启动监听", port);
                ChannelFuture channelFuture = serverBootstrap.bind(port).sync().channel().closeFuture().sync();
            } catch (InterruptedException e) {
                logger.info("InterruptedException异常={}", e.getMessage());
                e.printStackTrace();
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        }).start();
    }

    /**
     * 关闭服务器方法
     */
    @PreDestroy
    public void close() {
        logger.info("关闭服务器......");
        // 优雅退出
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}
