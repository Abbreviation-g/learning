package learning_jdk.structured_concurrency;

import java.util.concurrent.TimeUnit;

import learning_jdk.structured_concurrency.OrderEntities.Order;

public class UserQuery {
    public static OrderEntities.User findUser(String userId) throws Exception {
        System.out.println("开始查询用户: " + userId);

        TimeUnit.MILLISECONDS.sleep(1600);

        System.out.println("用户查询完成: " + userId);
        return new OrderEntities.User(userId, "name" + userId, "111@1111.com");
    }
}
