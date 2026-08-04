package learning_jdk.virtual_thread;

import java.util.concurrent.TimeUnit;

public class ThreadTest {
    public static void main(String[] args) {
        System.out.println("-------");
        // 方法一 直接启动
        Thread thread1 = Thread.startVirtualThread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t方法一 直接启动\t" + i);
            }
        });

        // 方法二 通过Thread.Builder创建
        Thread thread2 = Thread.ofVirtual().name("my-virtual-thread").start(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t方法二 通过Thread.Builder创建\t" + i);
            }
        });

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-------");

    }

}
