package learning_jdk.stream;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class StreamDropWhileTest {

    public static void main(String[] args) {
        beforeJava9();
        afterJava9();
    }

    public static void beforeJava9() {
        List<Integer> numbers = List.of(1, 3, 6, 2, 4);
        // 丢弃所有小于5的数字，遇到>=5就停止
        // 使用AtomicBoolean作为状态标志
        AtomicBoolean catchNumber = new AtomicBoolean(false);
        List<Integer> result = numbers.stream().filter(n -> {
            if (catchNumber.get()) {
                return true;
            }
            if (n < 5) {
                return false;
            } else {
                catchNumber.set(true);
                return true;
            }
        }).collect(Collectors.toList());
        System.out.println(result); // [6, 2, 4]
    }

    public static void afterJava9() {
        List<Integer> numbers = List.of(1, 3, 6, 2, 4);
        // 丢弃所有小于5的数字，遇到>=5就停止
        List<Integer> result = numbers.stream().dropWhile(n -> n < 5).collect(Collectors.toList());
        System.out.println(result); // [6, 2, 4]
    }
}
