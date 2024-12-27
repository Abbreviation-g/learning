package com.my.learning.jdk;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 有一个物业经理，派出手下11个员工去收取物业费，每回来3个员工，就将收取的物业费打包存款
 */
public class CyclicBarrierSimpleTest {
    private static final int THREAD_COUNT = 9;
    private static final int BATCH_SIZE = 3;
    private static final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(BATCH_SIZE);
    private static final AtomicInteger sum = new AtomicInteger(0);
    private static CyclicBarrier barrier;

    public static void main(String[] args) {
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
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    System.out.println("触发BrokenBarrierException异常。");
                }
            });
        }

        try {
            executor.shutdown();
            if (executor.awaitTermination(10, TimeUnit.SECONDS)) {
                System.out.println("线程执行完毕");
            } else {
                System.out.println("线程超时");
            }

            executor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("Total sum: " + sum.get());
    }

    private static void calcSum(int size) {
        List<Integer> batch = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            try {
                batch.add(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.print("已收取: " + batch);
        int batchSum = batch.stream().mapToInt(Integer::intValue).sum();
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
