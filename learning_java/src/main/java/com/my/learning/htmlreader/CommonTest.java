package com.my.learning.htmlreader;

import java.util.ArrayList;
import java.util.List;

public class CommonTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        
        for (int i = 0; i < list.size(); i++) {
            String newLine = list.get(i)+";";
            list.set(i, newLine);
        }
        System.out.println(list);
    }
}
