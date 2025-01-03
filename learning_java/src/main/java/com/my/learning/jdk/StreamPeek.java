package com.my.learning.jdk;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class StreamPeek {

    @Test
    public void PeekExample () {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        // 使用peek方法调试流操作过程
        List<Integer> result = numbers.stream()
                .filter(n -> n % 2 == 0)  // 过滤出偶数
                .peek(n -> System.out.println("Filtered: " + n))  // 查看过滤结果
                .map(n -> n * n)  // 对偶数进行平方
                .peek(n -> System.out.println("Mapped: " + n))  // 查看映射结果
                .collect(Collectors.toList());  // 收集结果

        System.out.println("最终结果: " + result);
    }

    @Test
    public void DebugWithPeek(){
        List<String> words = Arrays.asList("apple", "banana", "cherry", "date");

        words.stream()
                .filter(w -> w.length() > 4)
                .peek(w -> System.out.println("Filtered: " + w))
                .map(String::toUpperCase)
                .peek(w -> System.out.println("Mapped to upper case: " + w))
                .forEach(System.out::println);
    }

    private static final Logger logger = Logger.getLogger(StreamPeek.class.getName());
    @Test
    public void LogWithPeek (){
        List<Integer> numbers = Arrays.asList(10, 20, 30, 40, 50);

        numbers.stream()
                .filter(n -> n > 20)
                .peek(n -> logger.info("After filter: " + n))
                .map(n -> n / 2)
                .peek(n -> logger.info("After map: " + n))
                .forEach(System.out::println);
    }

    @Test
    public void DataValidationWithPeek  () {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

        names.stream()
                .filter(name -> name.length() > 3)
                .peek(name -> {
                    if (name.startsWith("C")) {
                        System.out.println("注意！名字以C开头: " + name);
                    }
                })
                .forEach(System.out::println);
    }
    @Test
    public void PeekVsForEach  () {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        // 使用peek()作为中间操作
        numbers.stream()
                .peek(n -> System.out.println("Peeked: " + n))
                .map(n -> n * 2)
                .forEach(System.out::println);

        System.out.println("--------");

        // 使用forEach()作为终端操作
        numbers.stream()
                .map(n -> n * 2)
                .forEach(n -> System.out.println("ForEach: " + n));

    }
}
