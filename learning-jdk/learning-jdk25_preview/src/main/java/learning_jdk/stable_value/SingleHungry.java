package learning_jdk.stable_value;

// cd .\learning-jdk25_preview\
// java --enable-preview -cp target/classes learning_jdk.stable_value.SingleHungry

public class SingleHungry {
    private static final SingleHungry INSTANCE = new SingleHungry();
    private SingleHungry(){
        System.out.println("SingleHungry对象被创建");
    }
    public static void method(){
        System.out.println("method 方法被调用");
    }
    public static SingleHungry getInstance(){
        return INSTANCE;
    }

    public static void main(String[] args) {
        SingleHungry.method();
        SingleHungry.getInstance();
        // SingleHungry对象被创建
        // method 方法被调用
    }

}
