package learning_jdk.stream;

import java.util.Optional;

public class OptionalTest {

    public static void main(String[] args) {
        test1();
        test2();
    }

    public static void test2() {

        Integer value1 = null;
        Integer value2 = Integer.valueOf(10);

        // Optional.ofNullable - 允许传递为 null 参数
        Optional<Integer> a = Optional.ofNullable(value1);

        // Optional.of - 如果传递的参数是 null，抛出异常 NullPointerException
        Optional<Integer> b = Optional.of(value2);
        System.out.println(sum(a, b));
    }

    public static Integer sum(Optional<Integer> a, Optional<Integer> b) {

        // Optional.isPresent - 判断值是否存在

        System.out.println("第一个参数值存在: " + a.isPresent());
        System.out.println("第二个参数值存在: " + b.isPresent());

        // Optional.orElse - 如果值存在，返回它，否则返回默认值
        Integer value1 = a.orElse(Integer.valueOf(0));

        // Optional.get - 获取值，值需要存在
        Integer value2 = b.get();
        return value1 + value2;
    }

    public static void test1() {
        // 1、声明一个空的Optional对象
        Optional<Student> optStu = Optional.empty();

        // 2、依据一个非空值创建一个Optional对象，如果 of() 方法传入NULL 会抛出 NullPointerException 错误
        Student student = new Student(20, "");
        Optional<Student> createOptByOf = Optional.of(student);

        // 3、可接受null的Optional
        Optional<Student> createOptByOf2 = Optional.ofNullable(student);
    }

    static class Student {
        Integer age;
        String name;

        Student(Integer age, String name) {
            this.age = age;
            this.name = name;
        }
    }
}
