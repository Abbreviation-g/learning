package com.my.learning.leetcode;

import java.util.Scanner;

public class 进制转换 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        line = line.substring(2);
        System.out.println(Integer.parseInt(line,16));
        
        scanner.close();
    }
}
