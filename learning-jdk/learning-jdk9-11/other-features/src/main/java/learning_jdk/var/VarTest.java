package learning_jdk.var;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class VarTest {

    public static void main(String[] args) {
        beforeJava10();
        afterJava10();
    }

    public static void beforeJava10(){
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "1");
        map.put(2, "2");

        for(Entry<Integer, String> entry : map.entrySet()) {
            System.out.println(entry.getKey()+", "+entry.getValue());
        }
    }

    public static void afterJava10(){
        var map = new HashMap<>();
        map.put(1, "1");
        map.put(2, "2");

        for(var entry : map.entrySet()) {
            System.out.println(entry.getKey()+", "+entry.getValue());
        }
    }

    // var a = 1; // 不允许 var只允许用于局部变量，不允许用于类成员遍历，方法参数，返回值等

    public static void testVar(){
        var a = 1;
        a = 2; // 允许
        // a  = 1.0; // 不允许
        // a = "";// 不允许
        // a = new Object(); // 不允许
        
        // var b; // 不允许
    }
}
