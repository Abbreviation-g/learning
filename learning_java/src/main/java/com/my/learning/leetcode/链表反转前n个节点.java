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
//        list = recursionReverse(list, 3);
        printList(list);
        list = reverseFirstN(list, 3);
        printList(list);
    }

    /**
     * 2.反转链表前n个节点 非递归
     * https://www.cnblogs.com/icecrea/p/12018677.html
     */
    public static Node reverseFirstN(Node head, int n) {
        Node nNext = head;
        for (int i = 0; i < n; i++) {
            nNext = nNext.next;
        }
        Node pre = null;
        Node next;
        Node cur = head;
        while (cur != nNext) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        head.next = nNext;
        return pre;
    }

    /**
     * 3.反转链表后n个节点
     * https://www.cnblogs.com/icecrea/p/12018677.html
     * @param head
     * @param n
     * @return
     */
    public Node reverseLastN(Node head, int n) {
        Node slow = head;
        Node fast = head;
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }
        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }

        Node cur = slow.next;
        Node pre = null;
        Node next;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }

        slow.next = pre;
        return head;
    }

    /**
     * 4.反转链表其中一部分 从第from个节点到第to个节点的部分
     */
    public Node reversePart(Node head, int from, int to) {
        int len = 0;
        Node cur = head;
        Node startPre = null;
        Node end = null;
        while (cur != null) {
            len++;
            if (len == from - 1) {
                startPre = cur;
            }
            if (len == to) {
                end = cur;
            }
            cur = cur.next;
        }

        //考虑from是1的情况
        Node start = startPre == null ? head : startPre.next;
        Node endNext = end.next;

        cur = start;
        Node pre = null;
        Node next;
        while (cur != endNext) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }

        if (startPre != null) {
            startPre.next = pre;
        } else {
            head = pre;
        }
        start.next = endNext;
        return head;
    }

    /**
     * 5.反转链表相邻节点 非递归
     */
    public Node reverseInPairs(Node head) {
        Node dummy = new Node(0);
        dummy.next = head;
        Node cur = dummy;
        while (cur.next != null && cur.next.next != null) {
            Node p = cur.next;
            Node q = cur.next.next;

            cur.next = q;
            p.next = q.next;
            q.next = p;
            cur = p;
        }
        return dummy.next;
    }


    /**
     * 5.反转链表相邻节点 递归
     */
    public Node reverseInPairsRecur(Node head) {
        if ((head == null) || (head.next == null)) {
            return head;
        }
        Node next = head.next;
        head.next = reverseInPairsRecur(head.next.next);
        next.next = head;
        return next;
    }

    /**
     * 1.反转整个链表 非递归
     */
    public Node reverseAllList(Node head) {
        Node pre = null;
        Node next;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }


    /**
     * 6.k个一组反转链表
     */
    public Node reverseKGroup(Node head, int k) {
        Node dummy = new Node(0);
        dummy.next = head;

        Node pre = dummy;
        Node end = dummy;

        //k个为一组，分为子链表。pre记录子链表的头节点的前驱节点，end记录子链表尾节点
        while (end.next != null) {
            for (int i = 0; i < k && end != null; i++) {
                end = end.next;
            }
            if (end == null) {
                break;
            }
            Node start = pre.next;
            Node endNext = end.next;
            end.next = null;
            pre.next = reverseAllList(start);

            //重新续上反转后的子链表
            //start此时反转后，变成尾节点
            start.next = endNext;
            pre = start;
            end = start;
        }
        return dummy.next;
    }
    /**
     * 7.判断链表是否回文结构
     */
    public boolean isPalindrome(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        Node fast = head;
        Node slow = head;
        // 根据快慢指针，找到链表的中点
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        boolean result = true;

        Node rHead = reverseAllList(slow.next);
        Node cur = rHead;
        while (cur != null) {
            if (head.value != cur.value) {
                result = false;
                break;
            }
            head = head.next;
            cur = cur.next;
        }

        //将后半部分链表反转回来，并拼接
        slow.next = reverseAllList(rHead);
        return result;
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
