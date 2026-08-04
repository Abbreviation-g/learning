package learning_jdk.switch_statement;

import java.util.Scanner;

public class SwitchTest {
    static final Scanner input = new Scanner(System.in);
    public static void main(String[] args) {

        beforeJava17();
        afterJava17();
        afterJava172();
        afterJava173();
        afterJava174();

        input.close();
    }

    public static void beforeJava17() {
        
        System.out.println("请输入星期几:");
        int week = input.nextInt();
        switch (week) {
            case 1:
                System.out.println("今天是星期一");
                break;
            case 2:
                System.out.println("今天是星期二");
                break;
            case 3:
                System.out.println("今天是星期三");
                break;
            case 4:
                System.out.println("今天是星期四");
                break;
            case 5:
                System.out.println("今天是星期五");
                break;
            case 6:
                System.out.println("今天是星期六");
                break;
            case 7:
                System.out.println("今天是星期日");
                break;

            default:
                System.out.println("ERROR");
                ;
        }
        
    }

    public static void afterJava17() {
        System.out.println("请输入星期几:");
        int week = input.nextInt();
        switch (week) {
            case 1 -> System.out.println("今天是星期一");
            case 2 -> System.out.println("今天是星期二");
            case 3 -> System.out.println("今天是星期三");
            case 4 -> System.out.println("今天是星期四");
            case 5 -> System.out.println("今天是星期五");
            case 6 -> System.out.println("今天是星期六");
            case 7 -> System.out.println("今天是星期日");
            default -> System.out.println("ERROR");
        }
        
    }

    public static void afterJava172() {
        
        System.out.println("请输入星期几:");
        int week = input.nextInt();
        String message = switch (week) {
            case 1 -> "今天是星期一";
            case 2 -> "今天是星期二";
            case 3 -> "今天是星期三";
            case 4 -> "今天是星期四";
            case 5 -> "今天是星期五";
            case 6 -> "今天是星期六";
            case 7 -> "今天是星期日";
            default -> "ERROR";
        };
        
        System.out.println("message: " + message);
    }

    public static void afterJava173() {
        
        System.out.println("请输入星期几:");
        int week = input.nextInt();
        String message = switch (week) {
            case 1 -> "今天是星期一";
            case 2 -> "今天是星期二";
            case 3 -> "今天是星期三";
            case 4 -> "今天是星期四";
            case 5 -> "今天是星期五";
            case 6 -> "今天是星期六";
            case 7 -> "今天是星期日";
            default -> {
                System.out.println("输入错误");
                yield "Error";
            }
        };
        
        System.out.println("message: " + message);
    }

    public static void afterJava174() {
        
        System.out.println("请输入星期几:");
        int week = input.nextInt();
        String message = switch (week) {
            case 1, 2, 3, 4, 5 -> "今天是工作日";
            case 6, 7 -> "今天是周末";
            default -> {
                System.out.println("输入错误");
                yield "Error";
            }
        };
        
        System.out.println("message: " + message);
    }
}
