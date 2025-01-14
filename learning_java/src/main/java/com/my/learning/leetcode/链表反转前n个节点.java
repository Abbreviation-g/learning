package com.my.learning.leetcode;

import java.util.stream.IntStream;

public class 链表反转前n个节点 {
    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

    }

    public static void main(String[] args) {
        int[] array = IntStream.range(1, 6).toArray();
        Node list = buildList(array);
//        reverse(list);
        list = recursionReverse(list, 3);
        printList(list);
    }

    private static Node successor = null;

    private static Node recursionReverse(Node head, int index) {
        if (index == 1) {
            // 记录第 n + 1 个节点
            successor = head.next;
            return head;
        }
        // 以 head.next 为起点，需要反转前 n - 1 个节点
        Node newHead = recursionReverse(head.next, index - 1);

        head.next.next = head;

        // 让反转之后的 head 节点和后面的节点连起来
        head.next = successor;
        return newHead;
    }

    private static Node buildList(int[] array) {
        Node head = new Node(array[0]);
        Node next = head;
        for (int i = 1; i < array.length; i++) {
            Node current = new Node(array[i]);
            next.next = current;

            next = current;
        }

        return head;
    }

    private static void printList(Node head) {
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }
        System.out.println();
    }
}
