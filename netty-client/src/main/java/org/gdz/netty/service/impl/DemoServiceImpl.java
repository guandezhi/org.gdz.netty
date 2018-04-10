package org.gdz.netty.service.impl;

import org.gdz.netty.config.ClientConfig;
import org.gdz.netty.service.DemoService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author guandezhi
 * @ClassName: org.gdz.netty.service.impl
 * @Description:
 * @date 2018/4/4 16:41
 */
@Component
public class DemoServiceImpl implements DemoService {


    @Resource
    private ClientConfig clientConfig;

    @Override
    public void sendMsg(String msg) {
        clientConfig.initNettyClient().remoteCall(msg, 10);
    }
}
