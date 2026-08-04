package learning_jdk.instance_of;

public class InstanceOfPatternTest {
    public static void main(String[] args) {
        beforeJava16();
        afterJava16();
    }

    public static Animal buy(String name) {
        return switch (name) {
            case "cat" -> new Cat();
            case "dog" -> new Dog();
            case "pig" -> new Pig();
            default -> null;
        };
    }

    public static void beforeJava16(){
        Animal a = buy("dog");
        a.eat();
        if (a instanceof Dog) {
            Dog d = (Dog) a;
            d.watchHouse();
        } else if (a instanceof Cat){
            Cat c = (Cat) a;
            c.catchMouse();
        } else if (a instanceof Pig){
            Pig p = (Pig) a;
            p.beenEaten();
        }
    }

    public static void afterJava16(){
        Animal a = buy("dog");
        a.eat();
        if (a instanceof Dog d) {
            d.watchHouse();
        } else if (a instanceof Cat c){
            c.catchMouse();
        } else if (a instanceof Pig p){
            p.beenEaten();
        }
    }
}
