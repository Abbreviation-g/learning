package learning_jdk.interface_private;

public interface MyInterface {
    // 抽象方法
    void doWork();

    // 静态方法
    static void method1() {
        commonPart();
        System.out.println("Method1");
    }

    // 静态方法
    static void method2() {
        commonPart();
        System.out.println("Method2");
    }

    // 默认方法
    default void methodA() {
        commonHelper();
        commonPart();
        System.out.println("MethodA");
    }

    // 默认方法
    default void methodB() {
        commonHelper();
        System.out.println("MethodB");
    }

    // 私有静态方法
    private static void commonPart() {
        System.out.println("私有静态方法 commonPart");
    }

    // 私有方法
    private void commonHelper() {
        System.out.println("私有方法 commonHelper");
    }
}
