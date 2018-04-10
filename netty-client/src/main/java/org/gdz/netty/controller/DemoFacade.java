package org.gdz.netty.controller;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author guandezhi
 * @ClassName: org.gdz.netty.controller
 * @Description:
 * @date 2018/4/4 16:44
 */
@FeignClient(name = "nettyClient")
@RequestMapping("/test")
public interface DemoFacade {

    public String testMethod(String msg);
}
