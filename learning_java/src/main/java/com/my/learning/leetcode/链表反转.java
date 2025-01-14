package com.my.learning.leetcode;

import java.util.stream.IntStream;

public class 链表反转 {
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
        list = recursionReverse(list);
        printList(list);
    }

    private static Node reverse(Node head) {
        Node prev = null;
        Node curr = head;
        while (curr != null) {
            Node next_temp = curr.next;
            curr.next = prev;

            prev = curr;
            curr = next_temp;
        }
        return prev;
    }

    private static Node recursionReverse(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node newHead = recursionReverse(head.next);

        Node current = head.next;
        current.next = head;
        head.next = null;

//        head.next.next = head;
//        head.next = null;

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
