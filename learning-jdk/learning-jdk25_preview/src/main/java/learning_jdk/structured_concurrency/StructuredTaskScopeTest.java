package learning_jdk.structured_concurrency;

import java.util.concurrent.StructureViolationException;
import java.util.concurrent.StructuredTaskScope;

import learning_jdk.structured_concurrency.OrderEntities.Order;
import learning_jdk.structured_concurrency.OrderEntities.User;

import static learning_jdk.structured_concurrency.OrderEntities.*;

// cd .\learning-jdk25_preview\
// java --enable-preview -cp target/classes learning_jdk.structured_concurrency.StructuredTaskScopeTest

public class StructuredTaskScopeTest {
    public static void main(String[] args) throws Exception {
        System.out.println("开始处理请求...");
        try (var scope = StructuredTaskScope.open()) {
            StructuredTaskScope.Subtask<OrderEntities.User> userSubtask = scope.fork(()-> UserQuery.findUser("user_123"));
            StructuredTaskScope.Subtask<OrderEntities.Order> orderSubtask = scope.fork(()-> OrderQuery.fetchOrder("order_456"));

            scope.join();

            User user= userSubtask.get();
            Order order= orderSubtask.get();
            System.out.println("请求处理完成");
            System.out.println("用户: "+user.name());
            System.out.println("订单: "+order.orderId()+", "+order.amount());
        } catch (StructureViolationException e) {
            System.err.println("请求处理失败. " +e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("请求被中断. " +e.getMessage());
            Thread.currentThread().interrupt();
        } catch (StructuredTaskScope.FailedException e) {
            System.err.println("子任务失败: " + e.getCause().getMessage());
        }
        System.out.println("全部请求处理完成");
    }
    
}
