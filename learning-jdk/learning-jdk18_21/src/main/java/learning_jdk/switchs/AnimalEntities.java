package learning_jdk.switchs;

public class AnimalEntities {
    public static abstract class Animal {
        public abstract void eat();
    }

    public static class Cat extends Animal {

        @Override
        public void eat() {
            System.out.println("猫吃老鼠");
        }

        public void catchMouse() {
            System.out.println("猫抓老鼠");
        }

    }

    public static class Dog extends Animal {

        @Override
        public void eat() {
            System.out.println("狗吃剩饭");
        }

        public void watchHouse() {
            System.out.println("狗看家");
        }
    }

    public static class Pig extends Animal {

        @Override
        public void eat() {
            System.out.println("猪吃饲料");
        }

        public void beenEaten() {
            System.out.println("猪肉被人吃");
        }
    }
}
