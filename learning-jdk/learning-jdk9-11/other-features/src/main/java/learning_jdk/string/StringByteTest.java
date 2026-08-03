package learning_jdk.string;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * --add-opens java.base/java.lang=ALL-UNNAMED
 * StringValueTest
 */
public class StringByteTest {

    public static void main(String[] args) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        String s1 = "at;gui-gu";
        String s2 = "at;尚硅谷"; // 
        System.out.println(s1);
        System.out.println(s2);

        // 使用反射，打印s1,s2的byte[]
        printCode(s1); // 0 // LATIN1 每个字符占用一个字节
        printByte(s1); // [97, 116, 59, 103, 117, 105, 45, 103, 117]
        printCode(s2); // 1 // UTF16 每个字符占用两个个字节
        printByte(s2); // [97, 0, 116, 0, 59, 0, 26, 92, 69, 120, 55, -116]

        String s3 = new String(s1.getBytes(), StandardCharsets.UTF_8);
        printCode(s3);
        printByte(s3);

        String s4 = new String(s2.getBytes(), StandardCharsets.UTF_8);
        printCode(s4);
        printByte(s4);
    }

    private static void printByte(String s) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Class<String> clazz = String.class;
        Field valueField =  clazz.getDeclaredField("value");
        valueField.setAccessible(true);

        byte[] value = (byte[]) valueField.get(s);
        System.out.println(Arrays.toString(value));
    }

        private static void printCode(String s) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Class<String> clazz = String.class;
        Field coderField =  clazz.getDeclaredField("coder");
        coderField.setAccessible(true);

        byte coder = (byte) coderField.get(s);
        System.out.println(coder);
    }
}
