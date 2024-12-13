# dubbo-nacos-demo


[dubbo结合nacos实现分布式负载均衡的案例](https://streamcomputing.feishu.cn/docx/X3S3dVrqLos6CwxOcmCcZOOtnuc)

## 创建项目
参考 dubbo的JavaSDK手册[基于 Spring Boot Starter 开发微服务应用](https://cn.dubbo.apache.org/zh-cn/overview/mannual/java-sdk/quick-start/spring-boot/)  
创建项目：dubbo-nacos-demo

## 使用nacos为注册中心
dubbo官方案例的注册中心为zookeeper，因此需要
参考 dubbo [JavaSDK/参考手册/注册中心/Nacos](https://cn.dubbo.apache.org/zh-cn/overview/mannual/java-sdk/reference-manual/registry/nacos/)  
将配置中心改为nacos
