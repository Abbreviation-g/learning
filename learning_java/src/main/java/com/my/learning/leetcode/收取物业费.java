package com.my.learning.leetcode;

import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class 收取物业费 {
    private static final int EMPLOYEE_COUNT = 11;
    private static final int THRESHOLD_AMOUNT = 100;
    private static BlockingQueue<Integer> feeQueue = new ArrayBlockingQueue<>(EMPLOYEE_COUNT);
    private static BlockingQueue<Integer> managerQueue = new ArrayBlockingQueue<>(EMPLOYEE_COUNT);
    private static AtomicInteger totalAmount = new AtomicInteger(0);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(EMPLOYEE_COUNT + 1);
        CountDownLatch countDownLatch = new CountDownLatch(EMPLOYEE_COUNT);
        // 启动经理线程
        executorService.execute(() -> {
            try {
                System.out.println("executorService.execute(() -> {\t"+countDownLatch.getCount() +", "+feeQueue.size());
                while (countDownLatch.getCount() > 0 || !feeQueue.isEmpty()) {
                    System.out.println("while (countDownLatch.getCount() > 0 || !feeQueue.isEmpty())\t" +countDownLatch.getCount() +", "+feeQueue.size());
                    Integer amount = feeQueue.take();
                    if (amount != null) {
                        managerQueue.put(amount);
                        int currentAmount = totalAmount.addAndGet(amount);
                        System.out.println("经理收到了 " + amount + " 元，总金额：" + currentAmount);

                        if (currentAmount >= THRESHOLD_AMOUNT) {
                            String managerAmounts = String.join("+", managerQueue.stream().map(Objects::toString).toList());
                            System.out.println("经理将 " + managerAmounts + "=" + currentAmount + " 元交给上级");
                            totalAmount.set(0);
                            managerQueue.clear();
                        }
                    }
                }
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        });

        // 启动员工线程
        for (int i = 1; i <= EMPLOYEE_COUNT; i++) {
            final int employeeId = i;
            executorService.execute(() -> {
                int amount = (int) (Math.random() * 100) + 1; // 模拟收取随机金额
                System.out.println("员工 " + employeeId + " 收取了 " + amount + " 元");
                try {
                    Thread.sleep(500);
                    feeQueue.put(amount);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                countDownLatch.countDown();
                System.out.println("员工 " + employeeId + " 收取完毕"+countDownLatch.getCount());
            });
        }

        // 关闭线程池
        executorService.shutdown();
        try {
            countDownLatch.await(); // 等待所有员工线程完成

            if (executorService.awaitTermination(20, TimeUnit.SECONDS)) {
                System.out.println("线程执行完毕");
            } else {
                System.out.println("线程超时");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 最后处理剩余金额
        int currentAmount = totalAmount.get();
        if (currentAmount > 0) {
            System.out.println("最后的员工到达后，经理将剩余的 " + currentAmount + " 元交给上级");
        }
    }
}
