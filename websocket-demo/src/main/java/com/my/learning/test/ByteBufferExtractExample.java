package com.my.learning.test;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;

public class ByteBufferExtractExample {

    private static final int THRESHOLD_SIZE = 4096; // 目标大小
    private static final Random random = new Random();

    public static void main(String[] args) {
        // 创建一个足够大的 ByteBuffer，确保能容纳TARGET_SIZE的数据
        // 可以设置稍大一点以避免频繁扩容，但这里为了演示，使用目标大小
        ByteBuffer byteBuffer = ByteBuffer.allocate(THRESHOLD_SIZE * 10); // 分配稍大些的空间，避免 put 时溢出

        // 模拟添加一些不定长度的字节数组
        for (int i = 0; i < 50; i++) { // 假设循环50次
            // 随机生成一个 1 到 1000 字节长度的数组
            int randomLength = random.nextInt(1000) + 1024;
            byte[] dataToAdd = new byte[randomLength];
            random.nextBytes(dataToAdd); // 填充随机数据

            System.out.printf("第 %d 次循环，准备添加 %d 字节的数据\n", i + 1, dataToAdd.length);

            // 将数据放入 ByteBuffer，并检查是否达到 4096 字节
            putDataAndCheck(byteBuffer, dataToAdd);
        }

        // 程序结束前，处理 ByteBuffer 中可能剩余的数据（如果有的话）
        int remaining = byteBuffer.position();
        if (remaining > 0) {
            System.out.println("\n程序结束，ByteBuffer 中剩余 " + remaining + " 字节数据未达到 " + THRESHOLD_SIZE + " 字节，暂不处理。");
            // 如果需要处理剩余数据，可以在这里获取
            // byte[] leftover = new byte[remaining];
            // byteBuffer.flip();
            // byteBuffer.get(leftover);
            // byteBuffer.clear();
        }
    }

    /**
     * 将数据放入 ByteBuffer，并在达到或超过 THRESHOLD_SIZE 时提取并移除前 4096 字节
     *
     * @param buffer 要放入数据的 ByteBuffer
     * @param data   要放入的字节数组
     */
    public static void putDataAndCheck(ByteBuffer buffer, byte[] data) {
        // 将新数据追加到缓冲区当前位置
        buffer.put(data);
        System.out.println("  -> 添加后，缓冲区当前大小: " + buffer.position());

        // 检查是否达到或超过阈值
        while (buffer.position() >= THRESHOLD_SIZE) {
            System.out.println("  -> 缓冲区大小 (" + buffer.position() + ") >= " + THRESHOLD_SIZE + "，准备提取前 " + THRESHOLD_SIZE + " 字节数据。");

            // 1. 准备读取数据：将 limit 设置为 position，position 设置为 0
            buffer.flip();

            // 2. 创建一个 4096 字节的数组来存放要提取的数据
            byte[] extractedData = new byte[THRESHOLD_SIZE];

            // 3. 从 ByteBuffer 中读取 4096 字节到新数组
            buffer.get(extractedData);

            // 4. 打印或处理提取的数据
            System.out.println("  -> 提取了 " + THRESHOLD_SIZE + " 字节的数据。前10个字节示例: " + Arrays.toString(Arrays.copyOfRange(extractedData, 0, Math.min(10, extractedData.length))));

            // 5. 删除已读取的数据（移除前 4096 字节），并将剩余数据移到缓冲区开头
            // compact() 会将未读取的数据（即 [THRESHOLD_SIZE, old_limit) 的部分）移到缓冲区开头 ([0, ...))
            // 并将 position 设置在这些数据之后。
            buffer.compact();

            System.out.println("  -> 提取并删除后，缓冲区当前大小: " + buffer.position() + " (剩余未处理数据)");
        }
    }

}