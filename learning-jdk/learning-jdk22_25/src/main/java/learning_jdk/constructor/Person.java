package learning_jdk.constructor;

public class Person {
    private String name;
    private int age;

    public Person(){
        super();
    }

    public Person(String name, int age){
        if (age <0) {
            throw new RuntimeException("age >= 0");
        }
        this.name = name;
        this.age = age;
        super();
        // 不可在super()或者this()之前使用本类的实例变量或者实例方法，或者弗雷德实例变量或者实例方法。
        // 可以使用静态变量和方法
        System.out.println("年龄: "+this.age);
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ","+
            "age=" + age + "]";
    }
}
