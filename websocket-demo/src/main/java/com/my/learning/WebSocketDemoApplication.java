package com.my.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebSocketDemoApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(WebSocketDemoApplication.class, args);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}