package org.apache.dubbo.springboot.demo.consumer;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.springboot.demo.AsyncService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Component
public class AsyncTask implements CommandLineRunner {
    @DubboReference(timeout = 10 * 1000, mock = "org.apache.dubbo.springboot.demo.consumer.AsyncServiceMock")
    private AsyncService asyncService;

    @Override
    public void run(String... args) throws Exception {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    receive();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    private void receive() {
        CompletableFuture<String> world = asyncService.sayHello("world");
        world.whenComplete((value, error) -> {
            if (Objects.nonNull(error)) {
                System.out.println("this is a error.");
                error.printStackTrace();
            } else {
                System.out.println(value);
            }
        });
    }
}