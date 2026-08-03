package learning_jdk.diamond;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * 钻石操作符<>允许我们在构造函数中省略类型参数，但是只能用于构造器
 * java7/8限制了钻石操作符<>不能用于匿名内部类
 * java9解除了该限制
 * DiamondTest
 */
public class DiamondTest {
    public static void main(String[] args) {

    }

    public static void beforeJava7() {
        List<String> list = new ArrayList<String>();
        list.add("1");
    }

    public static void afterJava7() {
        List<String> list = new ArrayList<>();
        list.add("1");
    }

    public static void beforeJava9() {
        Predicate<String> p = new Predicate<String>() {
            @Override
            public boolean test(String t) {
                return t.equalsIgnoreCase("true");
            }
        };
        System.out.println(p.test("1"));
    }

    public static void afterJava9() {
        Predicate<String> p = new Predicate<>() {
            @Override
            public boolean test(String t) {
                return t.equalsIgnoreCase("true");
            }
        };
        System.out.println(p.test("1"));
    }
}
