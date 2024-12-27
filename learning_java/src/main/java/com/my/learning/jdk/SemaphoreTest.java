package com.my.learning.jdk;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    /**
     * 相当于一个信号量/控制器(用来做并发控制之类),有点像线程池的感觉,给定一个总量池,如果满了就阻塞
     * <p> 开始指定一个初始值,相当于池大小,执行一次acquire()方法就记录一次,执行release()放就释放一次
     *
     * @param
     * @return void
     * @author xkj
     * @createDate 2020/7/31
     */
//    @Test
    public void testSemaphore() {
        ExecutorService threadPool = Executors.newFixedThreadPool(30);
        Semaphore s = new Semaphore(2);

        for (int i = 0; i < 5; i++) {
            threadPool.execute(() -> {
                try {
                    System.out.println("想要获得---" + Thread.currentThread().getName());
                    s.acquire();// 执行一次,加一次,当大于semaphore的上限值时将会阻塞,直到执行release()释放出位置
                    System.out.println("save data--" + Thread.currentThread().getName());
                    s.release();// 释放, 如果注释这行代码,则"save data" 只会执行两次
                } catch (InterruptedException e) {
                }
            });
        }
    }

    @Test
    public void test2() {
        // 创建一个计数为5的Semaphore对象
        final Semaphore semaphore = new Semaphore(2);

        ExecutorService executorService = Executors.newFixedThreadPool(10); // 10个客户端
        for (int i = 1; i <= 10; i++) {
            final int clientId = i;
            executorService.submit(() -> {
                try {
                    System.out.println("客户端 " + clientId + " 尝试获取打印机...");
                    semaphore.acquire(); // 获取许可，等待打印机空闲
                    System.out.println("客户端 " + clientId + " 获取到打印机，开始打印...");
                    Thread.sleep((long) (Math.random() * 1000)); // 模拟打印过程
                    System.out.println("客户端 " + clientId + " 完成打印，释放打印机。");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    semaphore.release(); // 释放许可，释放打印机
                }
            });
        }
        executorService.shutdown();
    }

    public static void main(String[] args) {
        new SemaphoreTest().test2();
    }

}
