package com.my.learning.jdk;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StremTest {
    public static void main(String[] args) {
        int[] intArr = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, };

// int[] -> List<Integer>
        List<Integer> integerList = Arrays.stream(intArr).boxed().collect(Collectors.toList());
// List<Integer> -> int[]
        intArr = integerList.stream().mapToInt(Integer::intValue).toArray();

// int[] -> Integer[]
        Integer[] integerArr = Arrays.stream(intArr).boxed().toArray(Integer[]::new);
// Integer[] -> int[]
        intArr = Arrays.stream(integerArr).mapToInt(Integer::valueOf).toArray();

// Integer[] -> List<Integer>
        integerList = Arrays.asList(integerArr);
// List<Integer> -> Integer[]
        integerArr = integerList.toArray(new Integer[integerList.size()]);

        System.out.println(Arrays.toString(integerArr));
    }
}
