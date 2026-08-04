package learning_jdk.instance_of;

public class Dog extends Animal{

    @Override
    public void eat() {
        System.out.println("狗吃剩饭");
    }

    public void watchHouse(){System.out.println("狗看家");}
}
