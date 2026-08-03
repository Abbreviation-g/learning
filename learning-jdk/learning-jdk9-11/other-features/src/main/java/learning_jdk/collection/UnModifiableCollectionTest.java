package learning_jdk.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 测试of和copyOf方法
 * UnModifiableCollectionTest
 */
public class UnModifiableCollectionTest {
    public static void main(String[] args) {
        beforeJava9();
        afterJava9();

        beforeJava8CopyOf();
        afterJava8CopyOf();
    }

    public static void beforeJava9() {
        try {
            List<String> list = Collections.unmodifiableList(new ArrayList<>());
            Collections.addAll(list, "1", "2");
            list.add("3");
        } catch (UnsupportedOperationException e) {
            System.out.println("不支持此操作");
            System.err.println(e);
        }
        try {
            Set<String> list = Collections.unmodifiableSet(new HashSet<>());
            Collections.addAll(list, "1", "2");
            list.add("3");
        } catch (UnsupportedOperationException e) {
            System.out.println("不支持此操作");
            System.err.println(e);
        }
        try {
            HashMap<Integer, String> map = new HashMap<>();
            map.put(1, "1");
            map.put(2, "2");
            map.put(3, "3");

            Map<Integer, String> unmap = Collections.unmodifiableMap(map);
            unmap.put(4, "4");
        } catch (UnsupportedOperationException e) {
            System.out.println("不支持此操作");
            System.err.println(e);
        }
    }

    public static void afterJava9() {
        try {
            List<String> list = List.of("1", "2");
            list.add("3");
        } catch (UnsupportedOperationException e) {
            System.out.println("不支持此操作");
            System.err.println(e);
        }
        try {
            Set<String> list = Set.of("1", "2");
            list.add("3");
        } catch (UnsupportedOperationException e) {
            System.out.println("不支持此操作");
            System.err.println(e);
        }
        try {
            Map<Integer, String> unmap = Map.of(1, "1", 2, "2", 3, "3");
            unmap.put(4, "4");
        } catch (UnsupportedOperationException e) {
            System.out.println("不支持此操作");
            System.err.println(e);
        }
    }

    /**
     * 不可变集合的元素会跟随源集合的元素的改变而改变
     */
    public static void beforeJava8CopyOf() {
        List<String> srcList = new ArrayList<>();
        srcList.add("1");
        srcList.add("2");
        List<String> unlist = Collections.unmodifiableList(srcList);
        System.out.println("src新增元素之前， 不可变集合的元素如下: " + unlist);
        srcList.add("3");
        System.out.println("src新增元素之后， 不可变集合的元素如下: " + unlist);

        Map<Integer, String> srcMap = new HashMap<>();
        srcMap.put(1, "1");
        srcMap.put(2, "2");
        Map<Integer, String> unmap = Collections.unmodifiableMap(srcMap);
        System.out.println("src新增元素之前， 不可变集合的元素如下: " + unmap);

        srcMap.put(3, "3");
        System.out.println("src新增元素之后， 不可变集合的元素如下: " + unmap);
    }

    /**
     * copyOf方法，创建的新集合不会随源集合的变化而变化，且新集合不可变
     * 更安全
     */
    public static void afterJava8CopyOf() {
        // static <E> List<E> copyOf(Collection<? extends E> coll) {
        //     return ImmutableCollections.listCopy(coll);
        // }
        List<String> srcList = new ArrayList<>();
        srcList.add("1");
        srcList.add("2");
        List<String> unlist = List.copyOf(srcList);
        System.out.println("src新增元素之前， 不可变集合的元素如下: " + unlist);
        srcList.add("3");
        System.out.println("src新增元素之后， 不可变集合的元素如下: " + unlist);

        Map<Integer, String> srcMap = new HashMap<>();
        srcMap.put(1, "1");
        srcMap.put(2, "2");
        Map<Integer, String> unmap = Map.copyOf(srcMap);
        System.out.println("src新增元素之前， 不可变集合的元素如下: " + unmap);

        srcMap.put(3, "3");
        System.out.println("src新增元素之后， 不可变集合的元素如下: " + unmap);
        unmap.put(4, "4");
    }
}
