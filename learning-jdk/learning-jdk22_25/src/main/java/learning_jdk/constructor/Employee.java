package learning_jdk.constructor;

public class Employee {
    private static int num = 1;
    private int id;
    private String name;
    protected int age;
    private double salary;

    public Employee(){
        num++;
        id = num;
    }

    public Employee(String name, int age) {
        if(name == null) {
            throw new IllegalArgumentException();
        }

        this();
        this.name = name;
        this.age = age;
    }

    public Employee(String name, int age, double salary){
        if (salary <0) {
            throw new IllegalArgumentException();
        }
        this(name, age);
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ",ame=" + name + ", age=" + age
        + ", salary=" + salary + "]";
    }
}
