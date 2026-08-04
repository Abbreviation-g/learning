package learning_jdk.switchs;

import learning_jdk.switchs.AnimalEntities.Animal;
import learning_jdk.switchs.AnimalEntities.Cat;
import learning_jdk.switchs.AnimalEntities.Dog;
import learning_jdk.switchs.AnimalEntities.Pig;
import learning_jdk.switchs.ShapeEntities.Circle;
import learning_jdk.switchs.ShapeEntities.Rectangle;
import learning_jdk.switchs.ShapeEntities.Shape;
import learning_jdk.switchs.ShapeEntities.Square;
import learning_jdk.switchs.ShapeEntities.Triangle;

public class SwitchTest {
    public static void main(String[] args) {
        testAnimal();
        testCaseWhen();
        testCaseRecord();
        testCaseSealed();
    }

    public static Animal buy(String name) {
        return switch (name) {
            case "cat" -> new Cat();
            case "dog" -> new Dog();
            case "pig" -> new Pig();
            default -> null;
        };
    }

    /**
     * switch模式匹配
     * switch表达式可以直接匹配对象类型，并且可以在case中直接声明变量，避免了传统的instanceof+强制类型转换的写法。
     */
    public static void testAnimal() {
        Animal a = buy("dog");

        switch (a) {
            case Pig p -> p.beenEaten();
            case Dog d -> d.watchHouse();
            case Cat c -> c.catchMouse();
            case null -> System.out.println("没有买到动物");
            default -> System.out.println("未知动物");
            // 对于非密封类，必须提供default分支，否则编译报错
            // 如果是密封类，则不需要default分支
        }
    }

    public static void testCaseWhen() {
        System.out.println(validateData(null));
        System.out.println(validateData("   "));
        System.out.println(
                validateData("This is a very long string that exceeds the maximum length of 255 characters. "));
        System.out.println(validateData(-5));
        System.out.println(validateData(150));
        System.out.println(validateData(Double.NaN));
        System.out.println(validateData(50));
        System.out.println(validateData(-50));
    }

    public static Object validateData(Object data) {
        return switch (data) {
            case null -> null;
            case String s when s.trim().isBlank() -> "空字符串";
            case String s when s.length() > 16 -> s.substring(0, 16);
            case Integer i when i < 0 -> 0;
            case Integer i when i > 100 -> 100;
            case Double d when d.isNaN() || d < 0.0 -> 0.0;
            default -> data;
        };
    }

    public static void testCaseRecord() {
        System.out.println(checkPoint(new Point(1, 2)));
    }

    private record Point(int x, int y) {
    }

    public static String checkPoint(Object obj) {
        return switch (obj) {
            case Point(int x, int y) when x == 0 && y == 0 -> "点在原点";
            case Point(int x, int y) when x == y -> "点在y=x直线上";
            case Point(int x, int y) -> "点: (" + x + ", " + y + ")";
            case Point p -> "点: (" + p.x() + ", " + p.y() + ")";
            default -> "未知对象";
        };
    }

    public static void testCaseSealed(){
        
        Shape shape = new Circle(5.0);
        checkShape(shape);

        shape = new Rectangle(4.0, 6.0);
        checkShape(shape);

        shape = new Triangle(3.0, 4.0);
        checkShape(shape);
    }
    public static void checkShape(Shape shape) {
        // Square必须在Rectangle的case之前，否则会被Rectangle匹配到
        switch (shape) {
            case Circle c -> System.out.println("Circle with radius: " + c.getRadius());
            case Square s -> System.out.println("Square with side: " + s.getWidth());
            case Rectangle r -> System.out.println("Rectangle with width: " + r.getWidth() + " and height: " + r.getHeight());
            case Triangle t -> System.out.println("Triangle with base: " + t.getBase() + " and height: " + t.getHeight());
        }
    }
}
