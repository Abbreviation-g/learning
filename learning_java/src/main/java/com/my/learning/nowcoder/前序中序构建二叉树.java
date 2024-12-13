package com.my.learning.nowcoder;

import java.util.Arrays;

public class 前序中序构建二叉树 {
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

    }

    public static TreeNode reConstructTree(int[] preOrder, int[] minOrder) {
        if (preOrder.length == 0 || minOrder.length == 0) {
            return null;
        }
        TreeNode root = new TreeNode(preOrder[0]);
        for (int i = 0; i < minOrder.length; i++) {
            if (minOrder[i] == root.value) {
                int[] preOrderLeft = Arrays.copyOfRange(preOrder, 1, i + 1);
                int[] minOrderLeft = Arrays.copyOfRange(minOrder, 0, i);
                root.left = reConstructTree(preOrderLeft, minOrderLeft);
                int[] preOrderRight = Arrays.copyOfRange(preOrder, i + 1, preOrder.length);
                int[] minOrderRight = Arrays.copyOfRange(minOrder, i + 1, minOrder.length);
                root.right = reConstructTree(preOrderRight, minOrderRight);
            }
        }
        return root;
    }
}
