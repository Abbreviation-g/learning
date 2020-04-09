package com.my.learning.leetcode;

import java.util.ArrayList;
import java.util.List;

public class _2两数之和链表 {
	public static void main(String[] args) {
//		输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
//		输出：7 -> 0 -> 8
//		原因：342 + 465 = 807
		
		// (2 -> 4 -> 3) 
		long number1 = 9L;
		long number2 = 9999999991L;
		ListNode l1 = createListNode(number1);
		ListNode l2 = createListNode(number2);
		System.out.println(l1);
		System.out.println(l2);
		
		ListNode sumList = addTwoNumbers(l1, l2);
		System.out.println(sumList);
	}
	
	public static ListNode createListNode(long number) {
		ListNode list = null;
		ListNode lastNode = null;
		int i=0;
		if(number == 0) {
			list = new ListNode(0);
			return list;
		}
		while(number>0) {//
			i=(int) (number%10);
			if(list == null) {
				list = new ListNode(i);
				lastNode = list;
			} else {
				ListNode temp = new ListNode(i);
				lastNode.next = temp;
				lastNode = temp;
			}
			
			number = number/10;
		}
		return list;
	}
	
	public static long listNodeToInt(ListNode list) {
		List<Integer> nums = new ArrayList<Integer>();
		nums.add(list.val);
		ListNode lastNode = list;
		while((lastNode = lastNode.next)!=null) {
			nums.add(lastNode.val);
		}
		long number = 0;
		long times = 1; // 倍数
		for (Integer i : nums) {
			number+=i*times;
			times*=10;
		}
		return number;
	}
	
	public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		long number1 = listNodeToInt(l1);
		long number2 = listNodeToInt(l2);
		long sum = number1 +number2;
		ListNode sumList = createListNode(sum);
        return sumList;
    }
}

class ListNode {
	int val;
	ListNode next;

	ListNode(int x) {
		val = x;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(val);
		ListNode lastNode = this;
		while((lastNode = lastNode.next) != null) {
			result.append(" -> ");
			result.append(lastNode.val);
		}
		return result.toString();
	}
}
