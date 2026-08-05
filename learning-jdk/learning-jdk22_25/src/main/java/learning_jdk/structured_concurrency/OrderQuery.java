package learning_jdk.structured_concurrency;

import java.util.concurrent.TimeUnit;

public class OrderQuery {
    public static OrderEntities.Order fetchOrder(String orderId) throws Exception {
        System.out.println("开始查询订单: " + orderId);

        TimeUnit.MILLISECONDS.sleep(1600);

        if(orderId.startsWith("order")) {
            throw new RuntimeException("订单取消"+orderId);
        }

        System.out.println("订单查询完成: " + orderId);
        return new OrderEntities.Order(orderId, 299.1, "已支付");
    }
}
