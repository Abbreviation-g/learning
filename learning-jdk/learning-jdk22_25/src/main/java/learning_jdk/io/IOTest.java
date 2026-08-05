package learning_jdk.io;

import java.util.Scanner;

public class IOTest {
    public static void main(String[] args) {
        // beforeJava25();
        afterJava25();
    }

    public static void beforeJava25(){
        Scanner input  = new Scanner(System.in);

        System.out.println("请输入姓名: ");
        String name = input.next();
        System.out.println("name: "+ name);
        input.nextLine();

        System.out.println("请输入一段话");
        String line = input.nextLine();
        System.out.println("content: "+ line);

        input.close();
    }

    public static void afterJava25(){
        System.out.println(System.getProperty("stdin.encoding"));
        System.setProperty("stdin.encoding", "UTF-8");
        String name = IO.readln("请输入姓名");
        IO.println("name: "+name);

        IO.print("请输入一段话:");
        String line = IO.readln();
        System.out.println("content: "+line);

    }

}
