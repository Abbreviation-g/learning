package com.my.learning.huawei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
	public static void main(String[] args) {
		int[] nums = {2,7,11,15};
		int target = 66;
		int[] result = test(nums, target);
		System.out.println(Arrays.toString(result));
		
	}
	
	public static int[] test(int[] nums, int target) {
		int target1 = -1;
		int target2 = -1;
		for (int i = 0; i < nums.length; i++) {
			int n1 = nums[i];
			for (int j = 0; j < nums.length; j++) {
				if(j == i) {
					continue;
				}
				int n2 = nums[j];
				if(n1 + n2 == target) {
					target1 = i;
					target2 = j;
					break;
				} 
			}
		}
		if(target1 == -1) {
			return new int[] {-1};
		}
		return new int[] {target1,target2};
	}
}
