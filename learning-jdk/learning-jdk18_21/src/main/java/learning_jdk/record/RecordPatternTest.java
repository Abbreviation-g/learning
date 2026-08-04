package learning_jdk.record;

/**
 * 记录类对象解构和模式匹配
 * RecordPatternTest
 */
public class RecordPatternTest {
    public static void main(String[] args) {
        Point point = new Point(10, 20);
        checkObjectBeforeJava18(point);
        checkObjectAfterJava18(point);

        Person person = new Person("张三", 20);
        checkObjectBeforeJava18(person);
        checkObjectAfterJava18(person);
    }

    public static void checkObjectBeforeJava18(Object obj) {
        if (obj instanceof Point p) {
            int x = p.x();
            int y = p.y();
            if (x == 0 && y == 0) {
                System.out.println("Point is at the origin");
            } else if (x == y) {
                System.out.println("Point is on the line y=x");
            } else {
                System.out.println("Point: x=" + x + ", y=" + y);
            }
        } else if (obj instanceof Person p) {
            System.out.println("Person: name=" + p.name() + ", age=" + p.age());
        } else {
            System.out.println("Unknown object type");
        }
    }

    public static void checkObjectAfterJava18(Object obj) {
        if (obj instanceof Point(int x, int y)) {
            if (x == 0 && y == 0) {
                System.out.println("Point is at the origin");
            } else if (x == y) {
                System.out.println("Point is on the line y=x");
            } else {
                System.out.println("Point: x=" + x + ", y=" + y);
            }
        } else if (obj instanceof Person(String name, int age)) {
            System.out.println("Person: name=" + name + ", age=" + age);
        } 
        // else if (obj instanceof Circle(double radius)) { // 报错
        //     System.out.println("Circle: radius=" + radius);
        // } 
        else {
            System.out.println("Unknown object type");
        }
    }
}
