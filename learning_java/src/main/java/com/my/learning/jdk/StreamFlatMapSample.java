package com.my.learning.jdk;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class StreamFlatMapSample {


    @Test
    public void testMap() {
        String[] words = new String[]{"Hello", "World"};
        List<String[]> a = Arrays.stream(words)
                .map(word -> word.split(""))
                .distinct()
                .toList();
        a.forEach(temp -> System.out.println(Arrays.toString(temp)));
    }

    @Test
    public void testFlatMap() {
        String[] words = new String[]{"Hello", "World"};
        List<String> a = Arrays.stream(words)
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .toList();
        a.forEach(temp -> System.out.print(temp));
    }

    List<String[]> eggs = new ArrayList<>();

    @Before
    public void init() {
        // 第一箱鸡蛋
        eggs.add(new String[]{"鸡蛋_1", "鸡蛋_1", "鸡蛋_1", "鸡蛋_1", "鸡蛋_1"});
        // 第二箱鸡蛋
        eggs.add(new String[]{"鸡蛋_2", "鸡蛋_2", "鸡蛋_2", "鸡蛋_2", "鸡蛋_2"});
    }

    // 自增生成组编号
    static int group = 1;
    // 自增生成学生编号
    static int student = 1;

    /**
     * 把二箱鸡蛋分别加工成煎蛋，还是放在原来的两箱，分给2组学生
     */
    @Test
    public void map() {
        eggs.stream()
                .map(x -> Arrays.stream(x).map(y -> y.replace("鸡", "煎")))
                .forEach(x -> System.out.println("组" + group++ + ":" + Arrays.toString(x.toArray())));
        /*
        控制台打印：------------
        组1:[煎蛋_1, 煎蛋_1, 煎蛋_1, 煎蛋_1, 煎蛋_1]
        组2:[煎蛋_2, 煎蛋_2, 煎蛋_2, 煎蛋_2, 煎蛋_2]
         */
    }

    /**
     * 把二箱鸡蛋分别加工成煎蛋，然后放到一起【10个煎蛋】，分给10个学生
     */
    @Test
    public void flatMap() {
        eggs.stream()
                .flatMap(x -> Arrays.stream(x).map(y -> y.replace("鸡", "煎")))
                .forEach(x -> System.out.println("学生" + student++ + ":" + x));
        /*
        控制台打印：------------
        学生1:煎蛋_1
        学生2:煎蛋_1
        学生3:煎蛋_1
        学生4:煎蛋_1
        学生5:煎蛋_1
        学生6:煎蛋_2
        学生7:煎蛋_2
        学生8:煎蛋_2
        学生9:煎蛋_2
        学生10:煎蛋_2
         */
    }

    @Test
    public void testFlapMap2() {
        List<String> list = Arrays.asList("hello", "world");
        List<String> list2 = Arrays.asList("java", "python");
        List<String> result = Stream.concat(list.stream(), list2.stream()).map(
                x -> x.substring(0, 1).toUpperCase() + x.substring(1)
        ).toList();
        System.out.println(result);

        List<List<String>> list3 = Arrays.asList(list, list2);
        List<String> result2 = list3.stream().flatMap(x -> x.stream().map(str -> str.substring(0, 1).toUpperCase() + str.substring(1))).toList();
        System.out.println(result2);
    }

    @Test
    public void testFlapMap3(){
        List<List<String>> nestedList = Arrays.asList(
                Arrays.asList("apple", "banana"),
                Arrays.asList("cherry", "date"),
                Arrays.asList("elderberry", "fig", "grape")
        );

        List<String> flatList = nestedList.stream()
                .flatMap(List::stream)
                .toList();

        System.out.println("Flat List: " + flatList);

    }

    @Test
    public void testFlapMap4(){
        List<String> words = Arrays.asList("apple", "banana", "cherry");

        List<String> characters = words.stream()
                .flatMap(word -> Arrays.stream(word.split("")))
                .toList();

        System.out.println("Characters: " + characters);
    }

    private record User(String name, List<String> addresses) {
        public List<String> getAddresses() {
            return addresses;
        }
    }

    @Test
    public void testFlapMap5(){
        List<User> users = Arrays.asList(
                new User("Alice", Arrays.asList("123 Main St", "456 Oak St")),
                new User("Bob", Arrays.asList("789 Pine St")),
                new User("Charlie", Arrays.asList("101 Maple St", "202 Birch St", "303 Cedar St"))
        );

        List<String> allAddresses = users.stream()
                .flatMap(user -> user.getAddresses().stream())
                .collect(Collectors.toList());

        System.out.println("All Addresses: " + allAddresses);
    }
}
