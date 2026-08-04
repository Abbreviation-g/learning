package learning_jdk.instance_of;

public class Cat extends Animal {

    @Override
    public void eat() {
        System.out.println("猫吃老鼠");
    }

    public void catchMouse() {
        System.out.println("猫抓老鼠");
    }

}
