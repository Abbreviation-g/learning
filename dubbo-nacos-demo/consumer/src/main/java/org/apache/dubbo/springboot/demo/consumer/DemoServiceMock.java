package org.apache.dubbo.springboot.demo.consumer;

import org.apache.dubbo.springboot.demo.DemoService;

public class DemoServiceMock implements DemoService {
    @Override
    public String sayHello(String name) {
        return "this is mock data of DemoService";
    }
}
