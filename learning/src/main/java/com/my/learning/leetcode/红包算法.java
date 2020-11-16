package com.my.learning.leetcode;

import java.util.Arrays;
import java.util.Random;

public class 红包算法 {
	public static void main(String[] args) {
		double total = 200.00;
		int count = 10;
		
		Random random = new Random();
		int denominator = 0;
		int[] molecular = new int[count];
		for (int i = 0; i < molecular.length; i++) {
			molecular[i] = random.nextInt(10)+1;
			denominator += molecular[i];
		}
		
		double[] ds = new double[count];
		for (int i = 0; i < ds.length; i++) {
			ds[i] = (double)molecular[i] / denominator * total;
		}
		System.out.println(Arrays.toString(ds));
	}
}
