package learning_jdk.stable_value;

public class SingleLazy {
    private static  SingleLazy INSTANCE;
    private SingleLazy(){
        System.out.println("SingleLazy对象被创建");
    }
    public static void method(){
        System.out.println("method 方法被调用");
    }
    public static SingleLazy getInstance(){
        if (INSTANCE == null) {
            synchronized(SingleLazy.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SingleLazy();
                }
            }
        }
        return INSTANCE;
    }

    public static void main(String[] args) {
        SingleLazy.method();
        SingleLazy.getInstance();
    }
}
