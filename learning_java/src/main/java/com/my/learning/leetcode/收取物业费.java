package com.my.learning.leetcode;

import java.util.concurrent.*;

public class 收取物业费 {
    private static final int EMPLOYEE_COUNT = 11;
    private static final int THRESHOLD_AMOUNT = 100;
    private static BlockingQueue<Integer> feeQueue = new ArrayBlockingQueue<>(EMPLOYEE_COUNT);
    private static int totalAmount = 0;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(EMPLOYEE_COUNT + 1);
        CountDownLatch countDownLatch = new CountDownLatch(EMPLOYEE_COUNT);
        // 启动经理线程
        executorService.execute(() -> {
            try {
                while (countDownLatch.getCount() > 0 || !feeQueue.isEmpty()) {
                    Integer amount = feeQueue.poll();
                    if (amount != null) {
                        totalAmount += amount;
                        System.out.println("经理收到了 " + amount + " 元，总金额：" + totalAmount);

                        if (totalAmount >= THRESHOLD_AMOUNT) {
                            System.out.println("经理将 " + totalAmount + " 元交给上级");
                            totalAmount = 0;
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
                    feeQueue.put(amount);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                countDownLatch.countDown();
            });
        }

        // 关闭线程池
        executorService.shutdown();
        try {
            countDownLatch.await(); // 等待所有员工线程完成
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 最后处理剩余金额
        if (totalAmount > 0) {
            System.out.println("最后的员工到达后，经理将剩余的 " + totalAmount + " 元交给上级");
        }
    }
}
