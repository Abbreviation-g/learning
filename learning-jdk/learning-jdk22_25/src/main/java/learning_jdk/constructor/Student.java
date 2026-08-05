package learning_jdk.constructor;

public class Student extends Person {
    public Student(String name, int age){
        if (name == null || name.isBlank()) {
            throw new RuntimeException("name is blank");
        }
        super(name, age);
    }
}
