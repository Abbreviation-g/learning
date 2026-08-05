package learning_jdk.gather;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;

/**
 * windowSliding    创建滑动窗口
 * windowFixed      创建固定窗口
 * fold             类似于reduce
 * scan             扫描操作，保留中间结果
 * StreamGatherTest
 */
public class StreamGatherTest {
    void main(String[] args) {
        test1();
        test2();
        test3();
        test3_2();
        test4();
    }

    public static void test1() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);

        // 滑动窗口，每三个元素一个窗口，步长为1
        List<List<Integer>> slidingWindows = numbers.stream().gather(Gatherers.windowSliding(3)).toList();
        System.out.println(slidingWindows); // [[1, 2, 3], [2, 3, 4], [3, 4, 5], [4, 5, 6]]

        // 固定窗口，每3个元素一个窗口
        List<List<Integer>> fixedWindows = numbers.stream().gather(Gatherers.windowFixed(3)).toList();
        System.out.println(fixedWindows); // [[1, 2, 3], [4, 5, 6]]
    }

    public static void test2() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);
        // 扫描操作（类似于reduce，但保留中间结果）
        List<Integer> runningTotal = numbers.stream()
                .gather(Gatherers.scan(() -> 0, Integer::sum))
                .toList();
        System.out.println("求和中间结果 :" + runningTotal); // [1, 3, 6, 10, 15, 21]
        System.out.println("求和最终结果: " + runningTotal.getLast());// 21
    }

    public static void test3() {
        List<String> words = List.of("a", "a", "c", "b", "c", "d", "d", "d", "c", "a");
        // 去除相邻的重复元素
        ArrayList<Object> distinctAdjacent = words.stream()
                .gather(Gatherers.scan(() -> new ArrayList<>(), (list, element) -> {
                    if (list.isEmpty() || !list.getLast().equals(element)) {
                        list.add(element);
                    }
                    return list;
                }))
                .reduce((_, second) -> second) // 获取最终结果
                .orElse(new ArrayList<>());

        System.out.println("去重相邻的重复元素: " + distinctAdjacent); // [a, c, b, c, d, c, a]
    }

    public static void test3_2() {
        List<String> words = List.of("a", "a", "c", "b", "c", "d", "d", "d", "c", "a");
        // 去除相邻的重复元素
        ArrayList<Object> distinctAdjacent = words.stream()
                .gather(Gatherers.fold(() -> new ArrayList<>(), (list, element) -> {
                    if (list.isEmpty() || !list.getLast().equals(element)) {
                        list.add(element);
                    }
                    return list;
                }))
                .findFirst()
                .orElse(new ArrayList<>());

        System.out.println("去重相邻的重复元素: " + distinctAdjacent); // [a, c, b, c, d, c, a]
    }


    public static void test4() {
        List<String> items = List.of("a", "b", "c", "SEP", "d", "e", "SEP", "f");

        // fold 按条件分组，遇到特定元素时开始新组
        ArrayList<List<String>> groups = items.stream().gather(
                Gatherers.fold(() -> new ArrayList<List<String>>(),
                        (result, element) -> {
                            if (result.isEmpty()) {
                                result.add(new ArrayList<>());
                            }
                            if ("SEP".equals(element)) {
                                result.add(new ArrayList<>());
                            } else {
                                result.getLast().add(element);
                            }
                            return result;
                        }))
                .findFirst()
                .orElse(new ArrayList<>());

        System.out.println("条件分组: " + groups);
    }
}
