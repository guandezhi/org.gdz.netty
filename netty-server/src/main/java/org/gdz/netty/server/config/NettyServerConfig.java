package org.gdz.netty.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Netty服务器配置信息
 * @author guandezhi
 * @ClassName: org.gdz.netty.server.config
 * @Description:
 * @date 2018/4/3 14:10
 */
@Component
@ConfigurationProperties(prefix = "netty")
public class NettyServerConfig {

    /**
     * 端口号
     */
    private int port;

    /**
     * 最大连接数
     */
    private int maxThreads;

    /**
     * 最大数据包长度
     */
    private int maxFrameLength;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    public int getMaxFrameLength() {
        return maxFrameLength;
    }

    public void setMaxFrameLength(int maxFrameLength) {
        this.maxFrameLength = maxFrameLength;
    }
}
