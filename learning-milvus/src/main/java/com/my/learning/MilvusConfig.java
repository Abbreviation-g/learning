package com.my.learning;

import io.milvus.client.MilvusServiceClient;
import io.milvus.param.ConnectParam;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MilvusConfig {

    @Value("${milvus.host}")
    private String host;

    @Value("${milvus.port}")
    private Integer port;

    @Value("${milvus.username}")
    private String username;

    @Value("${milvus.password}")
    private String password;

    @Bean
    public MilvusServiceClient milvusServiceClient() {
        ConnectParam.Builder builder = ConnectParam.newBuilder()
                .withHost(host)
                .withPort(port);
        if (!StringUtil.isNullOrEmpty(username) && !StringUtil.isNullOrEmpty(password)) {
            builder.withAuthorization(username, password);
        }
        return new MilvusServiceClient(builder.build());
    }
}