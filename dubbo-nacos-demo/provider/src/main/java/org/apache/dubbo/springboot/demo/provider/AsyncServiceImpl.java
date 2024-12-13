package org.apache.dubbo.springboot.demo.provider;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.springboot.demo.AsyncService;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.CompletableFuture;

@DubboService
public class AsyncServiceImpl implements AsyncService {
    @Value("${server.port}")
    private String port;

    @Override
    public CompletableFuture<String> sayHello(String name) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("name: " + name + "\t, port: " + port);
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return port + "\t" + "async response from provider: " + name;
        });
    }
}
