package com.my.learning.jdk;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @author liuxinghao
 * @version 1.0 Created on 2014年9月17日
 */
public class CyclicBarrierTest2 {
    public static void main(String[] args) throws InterruptedException {
        final long start = System.currentTimeMillis();
        final CountDownLatch count = new CountDownLatch(10);
        final CyclicBarrier barrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                long end = System.currentTimeMillis();
                System.out.println("导入" + 3 + "条数据，至此总共用时：" + (end - start)
                        + "毫秒");
            }
        });

        for (int i = 0; i < 10; i++) {
            final int threadID = i + 1;
            new Thread(new Runnable() {
                @Override
                public void run() {
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
