package org.apache.dubbo.springboot.demo.provider;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.springboot.demo.DemoService;
import org.springframework.beans.factory.annotation.Value;

@DubboService
public class DemoServiceImpl implements DemoService {
    @Value("${server.port}")
    private String port;

    @Override
    public String sayHello(String name) {
        return port + "\t" + "Hello " + name;
    }
}