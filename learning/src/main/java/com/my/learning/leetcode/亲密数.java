package com.my.learning.leetcode;

public class 亲密数 {
    public static void main(String[] args) {
        // 亲密数：a的因子和（不包括a本身）等于b，b的因子和（不包括b本身）等于a，则a和b是亲密数
        int a, j, x, b = 0, s = 0;
        for (a = 1; a <= 1000; a++) {
            b = 0; // b存放i因子之和，所以在每次的时候都要赋0，不能让上次的值覆盖（干扰）
            for (j = 1; j <= a / 2; j++) {
                if (a % j == 0)// 是i的因子
                    b = b + j; // 累加i因子之和
            }
            s = 0;// 存放b的因子之和
            for (x = 1; x <= b/2; x++)
                if (b % x == 0) {
                    s = s + x; // b因子之和
                }
            if (s == a) {
                if (b == a) // 顺便补充完数知识，一个数的因子和（不包括本身）等于该数则该数是完数
                    System.out.println("是完数" + a + ", " + b);
                else
                    System.out.println("是亲密数" + a + "," + b);
            }
        }
    }
}
