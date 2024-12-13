package com.streamcomputing.ai;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.samples.tri.grpc.Greeter;
import org.apache.dubbo.samples.tri.grpc.GreeterGrpc;
import org.apache.dubbo.samples.tri.grpc.GreeterReply;
import org.apache.dubbo.samples.tri.grpc.GreeterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Slf4j
@Component
public class Task implements CommandLineRunner {
    @DubboReference
    private Greeter greeter;

    @Override
    public void run(String... args) throws Exception {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                GreeterRequest request = GreeterRequest.newBuilder().setName("[grpc client]")
                        .build();
                GreeterReply reply = greeter.greet(request);
                log.info("GrpcClient.unary");
                log.info("Grpc client received reply <- \"{}\"", reply.getMessage());
                log.info("\n\n");
            }
        };
        timer.schedule(task, 5*1000, 10*1000);
    }
}