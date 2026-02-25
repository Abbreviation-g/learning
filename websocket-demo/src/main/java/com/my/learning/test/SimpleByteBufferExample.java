package com.my.learning.test;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;

public class SimpleByteBufferExample {

    private static final int BUFFER_SIZE_LIMIT = 4096;
    private static final Random random = new Random();

    public static void main(String[] args) {
        // 创建一个容量为 4096 的 ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFER_SIZE_LIMIT);

        // 模拟生成一些不定长度的字节数组
        for (int i = 0; i < 100; i++) { // 假设循环100次
            // 随机生成一个 1 到 1500 字节长度的数组
            int randomLength = random.nextInt(1500) + 400;
            byte[] dataToAdd = new byte[randomLength];
            random.nextBytes(dataToAdd); // 填充随机数据

            System.out.printf("第 %d 次循环，准备添加 %d 字节的数据\n", i + 1, dataToAdd.length);

            // 尝试将数据放入 ByteBuffer
            putDataIntoBuffer(byteBuffer, dataToAdd);
        }

        // 程序结束前，检查并处理 ByteBuffer 中可能剩余的数据
        if (byteBuffer.position() > 0) {
            System.out.println("程序结束，处理 ByteBuffer 中最后剩余的 " + byteBuffer.position() + " 字节数据:");
            byteBuffer.flip();
            byte[] finalData = new byte[byteBuffer.remaining()];
            byteBuffer.get(finalData);
            System.out.println("剩余数据 (长度: " + finalData.length + "): " + Arrays.toString(Arrays.copyOfRange(finalData, 0, Math.min(10, finalData.length))) + "...");
            byteBuffer.clear();
        }
    }

    /**
     * 将数据放入 ByteBuffer，并在达到限制时处理数据
     *
     * @param buffer 要放入数据的 ByteBuffer
     * @param data   要放入的字节数组
     */
    public static void putDataIntoBuffer(ByteBuffer buffer, byte[] data) {
        // 检查是否会导致溢出
        if (buffer.position() + data.length > BUFFER_SIZE_LIMIT) {
            System.out.println("  -> 添加 " + data.length + " 字节会导致缓冲区溢出 (当前位置: " + buffer.position() + ")");

            // --- 策略一：清空缓冲区 ---
            // 如果当前数据加上现有数据会超出限制，则先清空现有数据
            if (buffer.position() > 0) {
                 System.out.println("  -> 缓冲区已满，执行清空操作，丢弃之前累积的 " + buffer.position() + " 字节数据。");
                 buffer.clear(); // 清空现有数据
            }

            // 此时 buffer.position() == 0
            // 检查新数据是否能一次性放入
            if (data.length <= BUFFER_SIZE_LIMIT) {
                 System.out.println("  -> 新数据长度 (" + data.length + ") 在限制范围内，直接放入。");
                 buffer.put(data);
            } else {
                 // 如果单个数据块本身就超过了 4096，可以选择截断或抛出异常
                 System.out.println("  -> 单个数据块 (" + data.length + ") 超过缓冲区大小限制 (" + BUFFER_SIZE_LIMIT + ")，将被截断。");
                 buffer.put(data, 0, BUFFER_SIZE_LIMIT); // 只放入前4096字节
                 // 触发清空（或者处理，这里选择清空）
                 System.out.println("  -> 缓冲区达到限制，执行清空操作。");
                 buffer.clear();
                 // 如果需要处理被截断后剩余的数据，可以在这里递归或循环处理
                 // putDataIntoBuffer(buffer, Arrays.copyOfRange(data, BUFFER_SIZE_LIMIT, data.length));
            }

            return; // 数据已处理（放入或丢弃），函数返回
        }

        // 如果没有溢出风险，则直接放入
        buffer.put(data);
        System.out.println("  -> 成功添加 " + data.length + " 字节。当前缓冲区大小: " + buffer.position());

        // 检查是否刚好达到 4096 字节
        if (buffer.position() >= BUFFER_SIZE_LIMIT) {
            System.out.println("  -> 缓冲区刚好达到或超过 " + BUFFER_SIZE_LIMIT + " 字节，执行清空操作。");
            // 在这里你可以选择取出数据，而不是仅仅清空
            // 例如：
            // byte[] dataToProcess = new byte[buffer.position()];
            // buffer.flip(); // 为读取做准备
            // buffer.get(dataToProcess);
            // process(dataToProcess); // 处理数据
            buffer.clear(); // 清空缓冲区
        }
    }

    /**
     * 模拟处理数据的方法
     * @param data 要处理的数据
     */
    /*
    private static void process(byte[] data) {
        System.out.println("处理了 " + data.length + " 字节的数据。");
        // 这里可以是发送到网络、写入文件等操作
    }
    */
}