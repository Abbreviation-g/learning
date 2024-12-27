package com.my.learning.jdk;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CyclicBarrierTest {
    private static final int THREAD_COUNT = 4;
    private static final int BATCH_SIZE = 3;
    private static final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(BATCH_SIZE);
    private static final AtomicInteger sum = new AtomicInteger(0);
    private static CyclicBarrier barrier;

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        barrier = new CyclicBarrier(BATCH_SIZE, () -> {
            calcSum(BATCH_SIZE);
        });

        for (int i = 0; i < THREAD_COUNT; i++) {
            final int index = i;
            executor.execute(() -> {
                try {
                    // 将数据放入队列
                    queue.put(getNumber(index));
                    countDownLatch.countDown();
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    System.out.println("触发BrokenBarrierException异常。");
                }
            });
        }

        try {
            countDownLatch.await();
            int numberWaiting = barrier.getNumberWaiting();
            if (numberWaiting != 0) {
                System.out.println("不是整数倍。都已执行完，重置CyclicBarrier。");
                barrier.reset();
                calcSum(numberWaiting);
            }

            executor.shutdown();
            if (executor.awaitTermination(10, TimeUnit.SECONDS)) {
                System.out.println("线程执行完毕");
            } else {
                System.out.println("线程超时");
            }
            System.out.println("All threads finished.");

            executor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("Total sum: " + sum.get());
    }

    private static void calcSum(int size) {
        int batchSum = 0;
        for (int i = 0; i < size; i++) {
            try {
                batchSum += queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Batch sum: " + batchSum);
        sum.addAndGet(batchSum);
    }

    static Random random = new Random();

    private static int getNumber(int index) {
        try {
            Thread.sleep(random.nextInt(1));
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return index;
    }
}
