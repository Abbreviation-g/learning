package com.my.learning.test;

import java.nio.ByteBuffer;

public class FlipExample {
    public static void main(String[] args) {
        // 创建一个容量为 6 的字节缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(6);

        System.out.println("--- 写入模式 (Write Mode) ---");
        System.out.println("Capacity: " + buffer.capacity()); // 6
        System.out.println("Limit: " + buffer.limit());       // 6
        System.out.println("Position: " + buffer.position()); // 0

        // 写入 3 个字节
        buffer.put((byte) 'H');
        buffer.put((byte) 'i');
        buffer.put((byte) '!');

        System.out.println("\n写入 3 个字节后:");
        System.out.println("Limit: " + buffer.limit());       // 6 (不变)
        System.out.println("Position: " + buffer.position()); // 3 (指向下一次写入的位置)

        // --- 关键步骤：切换到读取模式 ---
        buffer.flip(); 

        System.out.println("\n--- 调用 flip() 后 (准备进入 Read Mode) ---");
        System.out.println("Limit: " + buffer.limit());       // 3 (变为原来的 position，即有效数据的边界)
        System.out.println("Position: " + buffer.position()); // 0 (回到起始位置)

        System.out.println("\n--- 读取模式 (Read Mode) ---");
        while (buffer.hasRemaining()) { // 检查 position 是否小于 limit
            byte b = buffer.get();
            System.out.print((char) b + " "); // 输出: H i !
        }
        System.out.println("\n读取完毕后 Position: " + buffer.position()); // 3
    }
}