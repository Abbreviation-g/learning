package learning_jdk.unname_variable;

import java.util.HashMap;
import java.util.Map;

public class UnNameVariable {
    void main() {
        test1();
        test2();
        test3();
    }

    public static void test1() {
        try {
            int i = 1 / 0;
        } catch (ArithmeticException _) {
            System.err.println("除零异常");
        }
    }

    public static void test2() {
        Map<Integer, String> map = new HashMap<>(Map.of(1, "a", 2, "b"));
        map.replaceAll((_, value) -> value.replaceFirst("^[a-z]", value.toUpperCase()));
        IO.println(map);
    }

    public static void test3() {
        IO.println(checkPersonAge(new Person("null", 19)));
        IO.println(checkPersonAge(new Person("null", 18)));
        IO.println(checkPersonAge(new Person("null", 0)));
        IO.println(checkPersonAge(new Object()));
    }

    private static String checkPersonAge(Object obj) {
        return switch (obj) {
            case Person(String _, int age) when age >= 18 -> "成年";
            case Person(String _, int age) when age < 18 -> "未成年";
            default -> "未知";
        };
    }

    private static record Person(String name, int age) {
    }
}
