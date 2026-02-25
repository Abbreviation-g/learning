package com.my.learning.test;

import java.nio.ByteBuffer;

public class CompactExample {
    public static void main(String[] args) {
        // 创建一个容量为 8 的字节缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(8);

        System.out.println("--- 1. 写入数据 (Write Mode) ---");
        buffer.put((byte) 'A');
        buffer.put((byte) 'B');
        buffer.put((byte) 'C');
        buffer.put((byte) 'D');
        buffer.put((byte) 'E');
        buffer.put((byte) 'F');
        // 此时 position = 6, limit = 8 (capacity)

        System.out.println("After writing: pos=" + buffer.position() + ", limit=" + buffer.limit());

        // --- 2. 切换到读取模式 ---
        buffer.flip(); // pos=0, limit=6
        System.out.println("\n--- 2. Flip to Read Mode ---");
        System.out.println("After flip: pos=" + buffer.position() + ", limit=" + buffer.limit());

        // --- 3. 读取部分数据 ---
        System.out.println("\n--- 3. Reading part of the data ---");
        byte b1 = buffer.get(); // 读 'A'
        byte b2 = buffer.get(); // 读 'B'
        System.out.println("Read: " + (char)b1 + ", " + (char)b2);
        System.out.println("After reading 2 bytes: pos=" + buffer.position() + ", limit=" + buffer.limit()); 
        // 此时 pos=2, limit=6
        // 未读的数据是 'C', 'D', 'E', 'F' (位于索引 2-5)

        // --- 4. 切换回写入模式，但不使用 clear，而是使用 compact ---
        // 如果此时调用 buffer.clear(), 会丢失未读的 'C','D','E','F'
        // 如果此时调用 buffer.flip(), 会把已读的数据 'A','B' 再次标记为可读 (pos=0, lim=2)
        // 正确的做法是调用 compact，保留未读数据
        
        System.out.println("\n--- 4. Calling compact() ---");
        buffer.compact(); // 关键操作
        
        System.out.println("After compact: pos=" + buffer.position() + ", limit=" + buffer.limit());
        // 预期: 未读的 'C','D','E','F' 被复制到前面 (pos 0,1,2,3)
        // position 指向 4 (因为有 4 个未读字节被复制了)
        // limit 变为 capacity (8)，表示后面的空间可以写入
        
        // --- 5. 现在可以写入新数据 ---
        System.out.println("\n--- 5. Writing new data after compact ---");
        buffer.put((byte) 'G');
        buffer.put((byte) 'H');
        System.out.println("After writing 'G', 'H': pos=" + buffer.position() + ", limit=" + buffer.limit());
        // pos 应该是 6, limit 是 8

        // --- 6. 最后再切换到读取模式，看看最终效果 ---
        buffer.flip(); // pos=0, limit=6 (因为最后的 position 是 6)
        System.out.println("\n--- 6. Final read to see result ---");
        System.out.println("Final buffer contents:");
        while (buffer.hasRemaining()) {
            System.out.print((char) buffer.get() + " ");
        }
        // 预期输出: C D E F G H
        // 'A', 'B' 被丢弃了，'C'-'F' 被保留并移到了前面，'G', 'H' 是后来写入的。
    }
}