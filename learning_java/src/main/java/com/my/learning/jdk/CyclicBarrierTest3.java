package com.my.learning.jdk;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CyclicBarrierTest3 {
    private static final int THREAD_COUNT = 10;
    private static final int BATCH_SIZE = 3;
    private static final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(BATCH_SIZE);
    private static final AtomicInteger sum = new AtomicInteger(0);
    private static CyclicBarrier barrier;

    public static void main(String[] args) throws InterruptedException {
        final long start = System.currentTimeMillis();
        final CountDownLatch count = new CountDownLatch(THREAD_COUNT);
        final CyclicBarrier barrier = new CyclicBarrier(BATCH_SIZE, () -> {
            long end = System.currentTimeMillis();
            System.out.println("导入" + 3 + "条数据，至此总共用时：" + (end - start)
                    + "毫秒");
        });

        for (int i = 0; i < 10; i++) {
            final int threadID = i + 1;
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(new Random().nextInt(10));// 模拟业务操作
                    System.out.println(threadID + "完成导入操作。");
                    count.countDown();
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    System.out.println("触发BrokenBarrierException异常。");
                }
            }).start();
        }
        count.await();

        if (barrier.getNumberWaiting() != 0) {
            System.out.println("不是整数倍。都已执行完，重置CyclicBarrier。");
            barrier.reset();
        }

        System.out.println("====主线程结束====");
    }
}
