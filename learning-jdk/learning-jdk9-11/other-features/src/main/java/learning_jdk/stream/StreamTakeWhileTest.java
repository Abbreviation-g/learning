package learning_jdk.stream;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class StreamTakeWhileTest {

    public static void main(String[] args) {
        beforeJava9();
        afterJava9();
    }

    public static void beforeJava9() {
        List<Integer> numbers = List.of(1, 3, 6, 2, 4);
        // 取出所有小于5的数字，遇到>=5就停止
        // 使用作为状态标志
        AtomicBoolean catchNumber = new AtomicBoolean(false);
        List<Integer> result = numbers.stream().filter(n -> {
            if (catchNumber.get()) {
                return false;
            }
            if (n < 5) {
                return true;
            } else {
                catchNumber.set(true);
                return false;
            }
        }).toList();
        System.out.println(result); // [1, 3]
    }

    public static void afterJava9() {
        List<Integer> numbers = List.of(1, 3, 6, 2, 4);
        // 取出所有小于5的数字，遇到>=5就停止
        List<Integer> result = numbers.stream().takeWhile(n -> n < 5).toList();
        System.out.println(result); // [1, 3]
    }
}
