package org.gdz.netty.config;

import org.gdz.netty.client.NettyClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author guandezhi
 * @ClassName: org.gdz.netty.config
 * @Description:
 * @date 2018/4/4 15:46
 */
@Configuration
public class ClientConfig {

    @Value("${netty.port}")
    private int port;

    @Value("${netty.url}")
    private String url;


    @Bean("nettyClient")
    public NettyClient initNettyClient() {
        return new NettyClient(url, port);
    }
}
