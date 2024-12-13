package org.apache.dubbo.springboot.demo.consumer;

import org.apache.dubbo.springboot.demo.AsyncService;

import java.util.concurrent.CompletableFuture;

public class AsyncServiceMock implements AsyncService {
    @Override
    public CompletableFuture<String> sayHello(String name) {
        return CompletableFuture.supplyAsync(() -> {
            return "this is mock data of AsyncService";
        });
    }
}
