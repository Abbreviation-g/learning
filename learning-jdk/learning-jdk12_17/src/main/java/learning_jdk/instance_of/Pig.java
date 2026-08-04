package learning_jdk.instance_of;

public class Pig extends Animal{

    @Override
    public void eat() {
        System.out.println("猪吃饲料");
    }

    public void beenEaten(){
        System.out.println("猪肉被人吃");
    }
}
