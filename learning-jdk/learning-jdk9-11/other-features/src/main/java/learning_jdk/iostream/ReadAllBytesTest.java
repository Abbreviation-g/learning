package learning_jdk.iostream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

/**
 * readAllBytes
 * transferTo
 */
public class ReadAllBytesTest {

    public static void main(String[] args) throws URISyntaxException {
        beforeJava9ReadAll();
        afterJava9ReadAll();
    }

    public static void beforeJava9ReadAll() throws URISyntaxException {
        // 从 classpath 定位资源文件（放在 src/main/resources 下）
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        ReadAllBytesTest.class.getClassLoader().getResourceAsStream("atguigu.txt"),
                        StandardCharsets.UTF_8))) {
            StringBuilder result = new StringBuilder();
            char[] buffer = new char[8];
            int len;
            while ((len = reader.read(buffer)) != -1) {
                result.append(new String(buffer, 0, len));
            }
            System.out.println(result);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    /**
     * readAllBytes
     * @throws URISyntaxException
     */
    public static void afterJava9ReadAll() throws URISyntaxException {
        try (InputStream stream = ReadAllBytesTest.class.getClassLoader().getResourceAsStream("atguigu.txt")) {
            String result = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            System.out.println(result);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
