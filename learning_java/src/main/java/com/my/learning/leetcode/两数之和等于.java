package com.my.learning.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class 两数之和等于 {
	public static void main(String[] args) {
		int[] arr = {3,2,4};
		int target = 6;
		
		System.out.println("twoSum----");
		System.out.println(Arrays.toString(twoSum(arr, target)));
		System.out.println("-----");
		int[][] pairs= 打印两数和等于某值的下标_for(arr,target);
		for (int[] is : pairs) {
			System.out.println(Arrays.toString(is));
		}
		System.out.println("打印两数和等于某值的下标_map-----");
		打印两数和等于某值的下标_map(arr,target);
		System.out.println("打印两数和等于某值的下标_map_2-----");
		打印两数和等于某值的下标_map_2(arr,target);
		System.out.println("打印两数和等于某值的下标_map_3-----");
		Map<Integer, List<Integer>> result  = 打印两数和等于某值的下标_map_3(arr,target);
		System.out.println(result);
	}
	
	public static int[] twoSum(int[] nums, int target) {
        // 值，下表
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
			if(map.containsKey(target - nums[i])){
                return new int[]{map.get(target-nums[i]), i};
            }
            map.put(nums[i], i);
		}

        return null;
    }
	
	public static int[][] 打印两数和等于某值的下标_for(int[] arr, int target) {
		List<int[]> result = new ArrayList<int[]>();
		for (int i = 0; i < arr.length-1; i++) {
			for (int j = i+1; j < arr.length; j++) {
				if(arr[i] + arr[j] == target) {
					result.add(new int[]{i,j});
					System.out.println(i+", " +j);
				}
			}
		}
		return result.toArray(new int[result.size()][] );
	}
	
	public static void 打印两数和等于某值的下标_map(int[] arr, int target) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < arr.length; i++) {
			map.put(arr[i], i);
		}
		for (int i = 0; i < arr.length-1; i++) {
			if(map.containsKey(target - arr[i]) && map.get(target- arr[i])>i) {
				System.out.println(i+", "+map.get(target- arr[i]));
			}
		}
	}
	
	public static void 打印两数和等于某值的下标_map_2(int[] arr, int target) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < arr.length; i++) {
			if(map.containsKey(target- arr[i])) {
				System.out.println(i+", "+ map.get(target- arr[i]));
			}
			map.put(arr[i], i);
		}
	}
	
	public static Map<Integer, List<Integer>> 打印两数和等于某值的下标_map_3(int[] arr, int target) {
		// 下表，下表[]
		Map<Integer, List<Integer>> result = new HashMap<Integer, List<Integer>>();
		// 	 值，下标
		Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
		for (int i = 0; i < arr.length; i++) {
			if(map.containsKey(target- arr[i])) {
				result.put(i, map.get(target-arr[i]));
			}
			if(map.containsKey(arr[i])) {
				map.get(arr[i]).add(i);
			} else {
				List<Integer> indexs = new ArrayList<Integer>();
				indexs.add(i);
				map.put(arr[i], indexs);
			}
		}
		return result;
	}
}
