package com.my.learning.leetcode;

import java.util.ArrayList;
import java.util.List;

/*
	a
	 \
  d   b
   \ /
c	e
 \ / \
  g	  i
     / \
    j   h
   /
  f
 */
public class 图的遍历 {
    // 将各城市的节点链路以一对一的方式保存到数据库中，
    // 再将这些键值对解析成一个图，
    // 要求给出任意两个节点，求出他们之间的可达路径，并展示最短和最长路径
    public static void main(String[] args) {
        String[][] connections = new String[][] { { "a", "b" }, { "b", "e" }, { "e", "g" }, { "g", "c" }, { "e", "d" },
                { "e", "i" }, { "i", "h" }, { "i", "j" }, { "j", "f" } };
                
        
    }

    public static class Node {
        public List<Node> sibling = new ArrayList<>();
        public Node(String name) {
            
        }
    }
}
