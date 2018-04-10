package org.gdz.netty.controller;

import org.gdz.netty.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * @author guandezhi
 * @ClassName: org.gdz.netty.controller
 * @Description:
 * @date 2018/4/4 16:42
 */
@RestController
public class DemoController implements DemoFacade {

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Resource
    private DemoService demoService;

    @RequestMapping(value = "/testMethod")
    public String testMethod(String msg) {
        logger.info("接收到请求，msg={}", msg);
        demoService.sendMsg(msg);
        return "Ok";
    }

}
