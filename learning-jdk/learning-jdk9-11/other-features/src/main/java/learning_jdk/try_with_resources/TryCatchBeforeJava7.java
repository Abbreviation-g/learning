package learning_jdk.try_with_resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import learning_jdk.iostream.ReadAllBytesTest;

public class TryCatchBeforeJava7 {

    
    public static void main(String[] args) {

    }

    public static void test1(){
        File file = getFile("atguigu.txt");
        readAllLine(file);
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

    public static List<String> readAllLine(File file) {
        List<String> lines = new ArrayList<>();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = null;
            while((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch(IOException e) {
            System.err.println(e);
        } finally {
            if (reader !=  null) {
                try {
                    reader.close();
                } catch (IOException e) {}
            }
        }

        return lines;
    }
}
