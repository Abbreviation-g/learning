package learning_jdk.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class CollectionTest {
    public static void main(String[] args) {
        afterJava21();
    }

    public static void beforeJava21() {
        ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, "a", "b", "c");
        System.out.println("第一个元素: " + arrayList.get(0));
        System.out.println("最后一个元素: " + arrayList.get(arrayList.size() - 1));

        LinkedList<String> linkedList = new LinkedList<>();
        Collections.addAll(linkedList, "a", "b", "c");
        System.out.println("第一个元素: " + linkedList.getFirst());
        System.out.println("最后一个元素: " + linkedList.getLast());

        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
        Collections.addAll(linkedHashSet, "a", "b", "c");

        TreeSet<String> treeSet = new TreeSet<>();
        Collections.addAll(treeSet, "a", "b", "c");

        LinkedHashMap<Integer, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(1, "a");
        linkedHashMap.put(2, "b");

        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(1, "a");
        treeMap.put(2, "b");
    }

    public static void afterJava21() {
        ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, "a", "b", "c");
        System.out.println("第一个元素: " + arrayList.getFirst());
        System.out.println("最后一个元素: " + arrayList.getLast());
        System.out.println("集合反转之前: " + arrayList);
        List<String> reversedList = arrayList.reversed();
        System.out.println("集合反转之后: " + reversedList);

        LinkedList<String> linkedList = new LinkedList<>();
        Collections.addAll(linkedList, "a", "b", "c");
        System.out.println("第一个元素: " + linkedList.getFirst());
        System.out.println("最后一个元素: " + linkedList.getLast());
        System.out.println("集合反转之前: " + linkedList);
        reversedList = linkedList.reversed();
        System.out.println("集合反转之后: " + reversedList);

        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
        Collections.addAll(linkedHashSet, "a", "b", "c");
        System.out.println("第一个元素: " + linkedHashSet.getFirst());
        System.out.println("最后一个元素: " + linkedHashSet.getLast());
        System.out.println("集合反转之前: " + linkedHashSet);
        Set<String> reversedSet = linkedHashSet.reversed();
        System.out.println("集合反转之后: " + reversedSet);

        TreeSet<String> treeSet = new TreeSet<>();
        Collections.addAll(treeSet, "a", "b", "c");
        System.out.println("第一个元素: " + treeSet.getFirst());
        System.out.println("最后一个元素: " + treeSet.getLast());
        System.out.println("集合反转之前: " + treeSet);
        reversedSet = treeSet.reversed();
        System.out.println("集合反转之后: " + reversedSet);

        LinkedHashMap<Integer, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(1, "a");
        linkedHashMap.put(2, "b");
        System.out.println("第一个元素: " + linkedHashMap.firstEntry());
        System.out.println("最后一个元素: " + linkedHashMap.lastEntry());
        Map<Integer, String> reversedMap = linkedHashMap.reversed();
        System.out.println("集合反转之前: " + linkedHashMap);
        System.out.println("集合反转之后: " + reversedMap);

        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(1, "a");
        treeMap.put(2, "b");
        System.out.println("第一个元素: " + treeMap.firstEntry());
        System.out.println("最后一个元素: " + treeMap.lastEntry());
        reversedMap = treeMap.reversed();
        System.out.println("集合反转之前: " + treeMap);
        System.out.println("集合反转之后: " + reversedMap);
    }
}
