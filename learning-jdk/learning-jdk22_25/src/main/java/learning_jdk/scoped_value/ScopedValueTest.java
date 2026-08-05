package learning_jdk.scoped_value;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ScopedValueTest {
    private static final ScopedValue<String> userId = ScopedValue.newInstance();
    private static final ScopedValue<String> orderId = ScopedValue.newInstance();

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int index = i + 1;
            executorService.submit(() -> {
                handleThread("user" + index, "order" + index);
                System.out.println(index + " done");
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(100, TimeUnit.SECONDS);
        System.out.println("all done");
    }

    public static void handleThread(String user, String order) {
        try {
            ScopedValue.where(userId, user).where(orderId, order).run(() -> {
                handleOrder();
            });
            TimeUnit.MILLISECONDS.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void handleOrder() {
        System.out.println(
                Thread.currentThread().getName() + "->订单处理开始, userId: " + userId.get() + "\torderId: " + orderId.get());
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(
                Thread.currentThread().getName() + "<-订单处理完成, userId: " + userId.get() + "\torderId: " + orderId.get());
    }
}
