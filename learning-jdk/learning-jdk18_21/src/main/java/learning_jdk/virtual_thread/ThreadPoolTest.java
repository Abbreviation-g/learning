package learning_jdk.virtual_thread;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class ThreadPoolTest {
    public static void main(String[] args) {
        // 方法三： 使用执行器服务(推荐)
        // 模拟用户id列表
        List<Long> userIds = LongStream.range(1, 1001).boxed().collect(Collectors.toList());
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            // 为每个用户id提交处理任务
            List<CompletableFuture<Integer>> futures = userIds.stream()
                    .map(userId -> CompletableFuture.supplyAsync(
                            () -> processSingleBuilder(userId), executor))
                    .toList();
                    // 等待所有任务完成并收集结果
            CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();
            List<Integer> results = futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());

            // futures.stream().map(f->{
            //     try {
            //         return f.get();
            //     } catch (InterruptedException | ExecutionException e) {
            //         e.printStackTrace();
            //         return null;
            //     }
            // }).toList();
            System.out.println("所有用户处理完成, 结果数量: " + results.size());
        } catch (Exception e) {
            System.out.println("处理用户数据时发生异常: " + e.getMessage());
        }
        System.out.println("所有用户处理完成");
    }

    static Random random = new Random();
    public static Integer processSingleBuilder(Long userId) {
        try {
            System.out.println("查询用户数据, userId: " + userId);

            TimeUnit.MILLISECONDS.sleep(50); // 模拟查询用户数据耗时
            TimeUnit.MILLISECONDS.sleep(random.nextInt(100)); // 模拟处理用户数据耗时

            System.out.println("处理用户数据结束, userId: " + userId);
            return random.nextInt(100); // 返回处理结果
        } catch (Exception e) {
            System.out.println("处理用户id " + userId + " 时发生异常: " + e.getMessage());
            Thread.currentThread().interrupt(); // 恢复中断状态
        }
        return null;
    }
}
