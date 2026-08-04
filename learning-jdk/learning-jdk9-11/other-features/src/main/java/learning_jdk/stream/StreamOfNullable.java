package learning_jdk.stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamOfNullable {

    public static void main(String[] args) {
        afterJava9();
    }

    public static void afterJava9() {
        String name = "name";
        String nullable = null;

        // 创建一个包含name的stream
        Stream<String> stream1 = Stream.ofNullable(name);
        stream1.forEach(System.out::println);
        // 创建一个空流，但不抛出异常
        Stream<String> stream2 = Stream.ofNullable(nullable);
        stream2.forEach(System.out::println);

        // 在flatmap中安全处理可能为null的元素
        List<Person> persons = List.of(new Person("name1"), new Person("name2"), new Person(null));
        List<String> names=persons.stream().flatMap(p->Stream.ofNullable(p.getName())).collect(Collectors.toList());
        System.out.println(names);

        List<String> names2=persons.stream().map(p->p.getName()).collect(Collectors.toList());
        System.out.println(names2);
    }

    @Getter
    @AllArgsConstructor
    static class Person {
        private String name;
    }
}
