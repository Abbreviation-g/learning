package learning_jdk.string;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringMethodTest {

    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
    }

    /**
     * isBlank 空字符串的判断
     */
    public static void test1() {
        String str = "    \n\r    ";
        System.out.println(str.length() == 0); // false
        System.out.println("".equals(str)); // false
        System.out.println(str.isEmpty()); // false
        System.out.println(str.isBlank()); // true
    }

    /**
     * strip() strip可以去除全角空格
     */
    public static void test2() {
        String str = "    \n\r　hello　　    "; // hello前有一个全角空格，后有两个全角空格
        System.out.println("[" + str.trim() + "]"); // [ hello ]// trim无法去除全角空格
        System.out.println("[" + str.strip() + "]"); // [hello] // strip可以去除全角空格
        System.out.println("[" + str.stripLeading() + "]");
        System.out.println("[" + str.stripTrailing() + "]");
    }

    /**
     * repeat 将字符串重复拼接
     */
    public static void test3(){
        String str = "atguigu";
        System.out.println(str.repeat(3)); // atguiguatguiguatguigu
        System.out.println(str.repeat(0)); // 
        // System.out.println(str.repeat(-1)); // 报错
    }

    /**
     * lines() 将字符串按照\n \r \r\n 换行符拆分成流
     */
    public static void test4(){
        String str = "hello\nworld\njava\npython\ncpp\ncsharp";

        // 按照换行符拆分处理
        // 老方法
        List<String> result1=Stream.of("hello\nworld\njava\npython\ncpp\ncsharp".split("\n")).collect(Collectors.toList());
        System.out.println(result1);

        // 新方法 .lines()
        List<String> result2 = str.lines().collect(Collectors.toList());
        System.out.println(result2);
    }
}
