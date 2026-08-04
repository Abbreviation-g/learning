package learning_jdk.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Java8FlatMap {
    public static void main(String[] args) {
        map();
        flatMap();
    }

    public static void map() {
        // 第一种方式
        String[] words = new String[] { "Hello", "World" };
        List<String[]> a = Arrays.stream(words)
                .map(word -> word.split(""))
                .distinct()
                .collect(Collectors.toList());
        a.forEach(System.out::println);
        // 这个实现方式是由问题的，
        // 传递给map方法的lambda为每个单词生成了一个String[](String列表)。
        // 因此，map返回的流实际上是Stream<String[]> 类型的。
        // 你真正想要的是用Stream<String>来表示一个字符串。
    }

    public static void flatMap() {
        String[] words = new String[] { "Hello", "World" };

        // 第二种方式
        Stream<String> stream1 = Arrays.stream(words);
        Stream<String[]> stream2 = stream1.map(word -> word.split(""));
        Stream<String> stream3 = stream2.flatMap(arr->Arrays.stream(arr));
        List<String> a = stream3.distinct().collect(Collectors.toList());
        System.out.println(a);
        
        List<String> b = Arrays.stream(words)
                .map(word -> word.split(""))
                .flatMap(Arrays::stream) // public static Stream<String> stream(String[] array)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(b);
        // 使用flatMap方法的效果是，各个数组并不是分别映射一个流，而是映射成流的内容，
        // 所有使用map(Array::stream)时生成的单个流被合并起来，即扁平化为一个流。
    }
}
