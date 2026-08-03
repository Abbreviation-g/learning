package learning_jdk.interface_private;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        MyInterface.method1();
        MyInterface.method2();

        A a = new Main().new A();
        a.doWork();
        a.methodA();
        a.methodB();
        // a.method1();
        // A.method2();
    }

    class A implements MyInterface {

        @Override
        public void doWork() {
            methodA();
            methodB();
        }
    }
}