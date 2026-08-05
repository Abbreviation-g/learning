package learning_jdk.structured_concurrency;

import java.util.concurrent.Executors;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.TimeUnit;
// cd .\learning-jdk25_preview\
// java --enable-preview -cp target/classes learning_jdk.structured_concurrency.ScopedValueVirtualThreadTest
public class ScopedValueVirtualThreadTest {
    private static final ScopedValue<String> USER_ID = ScopedValue.newInstance();
    private static final ScopedValue<String> REQUEST_ID = ScopedValue.newInstance();

    public static void main(String[] args) {
        System.out.println("ScopedValue + 虚拟线程展示");

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 3; i++) {
                int requestNum = i + 1;
                executor.submit(() -> processUserRequest("user_" + requestNum, "req_" + requestNum));
            }
        }
    }

    private static void processUserRequest(String userId, String requestId) {
        // 绑定作用域值并执行请求
        ScopedValue.where(USER_ID, userId).where(REQUEST_ID, requestId)
                .run(() -> {
                    System.out.println("->开始处理用户请求: " + userId + ":" + requestId);
                    executeParallelTasks();
                    System.out.println("<-完成处理用户请求: " + userId + ":" + requestId);
                });
    }

    private static void executeParallelTasks() {
        try (var scope = StructuredTaskScope.open()) {
            // 并行执行多个子任务
            var userProfileTask = scope.fork(() -> fetchUserProfile());
            var productListTask = scope.fork(() -> fetchProductList());
            var recommendationsTask = scope.fork(() -> getRecommendations());

            // 等待所有任务完成
            scope.join();

            // 收集结果
            String userProfile = userProfileTask.get();
            String products = productListTask.get();
            String recommendations = recommendationsTask.get();

            System.out.printf("%s, 聚合结果[用户:%s],[产品:%s],[推荐:%s]", USER_ID.get(), userProfile, products, recommendations);
            System.out.println();
        } catch (Exception e) {
            System.out.println("任务执行失败: " + e.getMessage() + "\tuser:" + USER_ID.get());
        }
    }

    private static String fetchUserProfile() {
        System.out.printf("获取用户档案, user: %s, 请求: %s, 线程: %s", USER_ID.get(), REQUEST_ID.get(), Thread.currentThread());
        System.out.println();
        try {
            TimeUnit.MICROSECONDS.sleep(200);
        } catch (InterruptedException e) {
        }
        return "profile_" + USER_ID.get();
    }

    private static String fetchProductList() {
        System.out.printf("获取产品列表, user: %s, 请求: %s, 线程: %s", USER_ID.get(), REQUEST_ID.get(), Thread.currentThread());
        System.out.println();
        try {
            TimeUnit.MICROSECONDS.sleep(200);
        } catch (InterruptedException e) {
        }
        return "product_" + USER_ID.get();
    }

    private static String getRecommendations() {
        System.out.printf("获取推荐列表, user: %s, 请求: %s, 线程: %s", USER_ID.get(), REQUEST_ID.get(), Thread.currentThread());
        System.out.println();
        try {
            TimeUnit.MICROSECONDS.sleep(200);
        } catch (InterruptedException e) {
        }
        return "recommendations_" + USER_ID.get();
    }
}
