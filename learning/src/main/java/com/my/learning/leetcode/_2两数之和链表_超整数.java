package com.my.learning.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class _2两数之和链表_超整数 {
	private static class ListNode {
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
			while ((lastNode = lastNode.next) != null) {
				result.append(" -> ");
				result.append(lastNode.val);
			}
			return result.toString();
		}
	}

	public static void main(String[] args) {
//		[1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1]
//				[5,6,4]
// 	[6,6,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1]

		String number1 = "[1,5,0,0,1]";
		String number2 = "[5,6,4,0,9]";
		ListNode listNode1 = createListNode(number1);
		ListNode listNode2 = createListNode(number2);
		System.out.println(listNode1);
		System.out.println(listNode2);

		ListNode sumListNode = addTwoNumbers(listNode1, listNode2);
		System.out.println(sumListNode);
		Integer.parseInt("2");
	}

	public static ListNode createListNode(String number) {
		number = number.trim();
		if (number.startsWith("[") || number.startsWith("{")) {
			number = number.substring(1);
		}
		if (number.endsWith("]") || number.endsWith("}")) {
			number = number.substring(0, number.length() - 1);
		}
		List<Integer> ns = Arrays.asList(number.split("\\s*,\\s*")).stream().mapToInt(Integer::parseInt).boxed()
				.collect(Collectors.toList());
		ListNode list1 = null;
		ListNode lastNode = null;
		for (Integer n : ns) {
			if (list1 == null) {
				list1 = new ListNode(n);
				lastNode = list1;
			} else {
				lastNode.next = new ListNode(n);
				lastNode = lastNode.next;
			}
		}
		return list1;
	}

	public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode sumList = new ListNode(-1);
		ListNode lastNode = sumList;

		int nextPosition = 0;
		while (l1 != null || l2 != null || nextPosition != 0) {
			int n1 = l1 != null ? l1.val : 0;
			int n2 = l2 != null ? l2.val : 0;
			int sum = n1 + n2 + nextPosition;
			lastNode.next = new ListNode(sum % 10);
			lastNode = lastNode.next;
			
			// 更新下次循环条件
			if (l1 != null) {
				l1 = l1.next;
			}
			if (l2 != null) {
				l2 = l2.next;
			}
			nextPosition = sum / 10;
		}
		return sumList.next;
	}
}
