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
 * transferTo
 */
public class TransferToTest {

    public static void main(String[] args) throws URISyntaxException {
        beforeJava9Copy();
        afterJava9Copy();
    }

    private static File getFile(String resourceName) {
        URL resource = ReadAllBytesTest.class.getClassLoader().getResource(resourceName);
        File file = null;
        try {
            file = Paths.get(resource.toURI()).toFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static void beforeJava9Copy(){
        File outputFile = new File(System.getProperty("java.io.tmpdir"),"atguigu2.txt");
        System.out.println(outputFile);
        try (
            FileInputStream fis = new FileInputStream(getFile("atguigu.txt"));
            FileOutputStream fos = new FileOutputStream(outputFile)
        ){
            byte[] buff = new byte[10];
            int len = -1;
            while((len = fis.read(buff)) != -1) {
                fos.write(buff, 0, len);
            }
        } catch(IOException e) {
            System.err.println(e);
        }
    }

    /**
     * transferTo
     */
    public static void afterJava9Copy(){
        File outputFile = new File(System.getProperty("java.io.tmpdir"),"atguigu3.txt");
        System.out.println(outputFile);
        try (
            FileInputStream fis = new FileInputStream(getFile("atguigu.txt"));
            FileOutputStream fos = new FileOutputStream(outputFile)
        ){
            fis.transferTo(fos);
        } catch(IOException e) {
            System.err.println(e);
        }
    }
}
