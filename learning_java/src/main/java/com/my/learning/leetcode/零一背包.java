package com.my.learning.leetcode;

import java.util.Arrays;

public class 零一背包 {
//    背包最⼤重量为4
//    重量 价值
//    物品0 1 15
//    物品1 3 20
//    物品2 4 30
    public static void main(String[] args) {
//        test_2_wei_bag_problem1();
        test_2_wei_bag_problem2();
    }

    static void test_2_wei_bag_problem2() {
        int[] weight = { 1, 3, 4 };
        int[] value = { 15, 20, 30 };
        int W = 4;
        int maxValue = maxValue(weight, value, W);
        System.out.println(maxValue);
    }

    public static int maxValue(int[] weight, int[] value, int W) {
        // 这里假定传入的weight和values数组长度总是一致的
        int n = weight.length;
        if (n == 0)
            return 0;
        int[][] dp = new int[n + 1][W + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= W; j++) {
                int valueWith_i;
                // 存放 i 号物品（前提是放得下这件物品）
                if(j - weight[i - 1] >= 0) {
                    valueWith_i = value[i - 1] + dp[i - 1][j - weight[i - 1]];
                } else {
                    valueWith_i = 0;
                }
//                int valueWith_i = (j - weight[i - 1] >= 0) ? (value[i - 1] + dp[i - 1][j - weight[i - 1]]) : 0;
                // 不存放 i 号物品
                int valueWithout_i = dp[i - 1][j];
                dp[i][j] = Math.max(valueWith_i, valueWithout_i);
            }
        }
        printDp(dp);
        return dp[n][W];
    }

    static void printDp(int[][] dp) {
        for (int i = 0; i < dp.length; i++) {
            System.out.println(Arrays.toString(dp[i]));
        }
    }

    static void test_2_wei_bag_problem1() {
        int[] weight = { 2, 3, 4 };
        int[] value = { 15, 20, 30 };
        int bagWeight = 4;
        // ⼆维数组
        int[][] dp = new int[weight.length + 1][bagWeight + 1];
        // 初始化
        for (int j = bagWeight; j >= weight[0]; j--) {
            dp[0][j] = dp[0][j - weight[0]] + value[0];
        }
        // weight数组的⼤⼩ 就是物品个数
        for (int i = 1; i < weight.length; i++) { // 遍历物品
            for (int j = 0; j <= bagWeight; j++) { // 遍历背包容量
                if (j < weight[i])
                    dp[i][j] = dp[i - 1][j];
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i]] + value[i]);
            }
        }
        System.out.println(dp[weight.length - 1][bagWeight]);
    }

}
