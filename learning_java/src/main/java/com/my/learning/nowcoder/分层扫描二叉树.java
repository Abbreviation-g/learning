package com.my.learning.nowcoder;

import java.util.*;
import java.util.Map.Entry;

public class 分层扫描二叉树 {
    static class TreeNode {
        int value;
        TreeNode left;
        TreeNode right;

        public TreeNode(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value + "";
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(0);
        root.left = new TreeNode(1);
        // root.left.left = new TreeNode(3);
        // root.left.right = new TreeNode(4);
        root.right = new TreeNode(2);
        root.right.left = new TreeNode(5);
        root.right.left.left = new TreeNode(10);
        root.right.right = new TreeNode(6);
        root.right.right.right = new TreeNode(11);

        scan(root);
        // LevelTraverse(root);
    }

    static void scan(TreeNode root) {
        Map<Integer, List<Integer>> deepArrMap = new TreeMap<>();
        deepArrMap.put(0, new ArrayList<>(Arrays.asList(root.value)));
        scan(root, 1, deepArrMap);
        for (Entry<Integer, List<Integer>> entry : deepArrMap.entrySet()) {
            List<Integer> list = entry.getValue();
            if (!list.stream().allMatch(i -> i == -1)) {
                System.out.println(list);
            }
        }
    }

    static void scan(TreeNode node, int deep, Map<Integer, List<Integer>> deepArrMap) {
        List<Integer> list = deepArrMap.get(deep);
        if (list == null) {
            list = new ArrayList<>();
            deepArrMap.put(deep, list);
        }
        if (node == null) {
            list.add(-1);
            list.add(-1);
            return;
        }

        int leftValue = node.left == null ? -1 : node.left.value;
        int rightValue = node.right == null ? -1 : node.right.value;
        list.add(leftValue);
        list.add(rightValue);

        scan(node.left, deep + 1, deepArrMap);
        scan(node.right, deep + 1, deepArrMap);
    }

    // 遍历方法
    public static void LevelTraverse(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);// 将根节点推入队列，此时队列长度为1
        // 特殊判断，如果传入的节点为空，直接退出
        if (root == null)
            return;
        // 用isEmpty()判断队列非空条件
        while (!queue.isEmpty()) {
            int n = queue.size(); // 定义n=队列长度，此时n=1。第一回合for循环结束后将重新赋值
            for (int i = 0; i < n; i++) {
                // 第一回合n=1,所以for循环只执行一次，打印二叉树的根节点root
                TreeNode cur = queue.poll(); // 定义指针cur，将弹出的节点赋给指针
                System.out.print(cur.value + " "); // 将弹出的节点打印出来
                if (cur.left != null) {
                    queue.offer(cur.left); // 如果弹出节点有左节点，则将左节点推入队列
                } else {
                    queue.offer(new TreeNode(-1));
                }
                if (cur.right != null) {
                    queue.offer(cur.right);
                } else {
                    queue.offer(new TreeNode(-1));
                }
            } // for循环结束. 第一回合结束时，根节点root弹出队列，根节点的左右节点（如果存在）将会推入队列，
            // 此时队列长度为2，下一回合for循环将执行两次，打印第二层的2个节点
            System.out.println();
        }
    }
}
