package learning_jdk.record;

/**
 * 基本语法
 * public record 类名(实例变量列表) {
 * // 可选：额外的方法或者 静态变量等
 * }
 * 编译器会自动生成
 * 1. 每个实例变量的 private final 字段
 * 2. 初始化所有实例变量的规范构造器
 * 3. 所有实例变量的 public 访问器方法，方法名与实例变量名相同
 * 4. equals()、hashCode()、toString() 方法
 * 5. record类是final的，不能被继承
 * RecordTest
 */
public class RecordTest {

    public static void main(String[] args) {
        Employee employee = new Employee("张三", 20);
        System.out.println(employee.toString());
        System.out.println(employee.name());
        System.out.println(employee.age());

        EmployeeRecord employeeRecord = new EmployeeRecord("李四", 30);
        System.out.println(employeeRecord.toString());
        System.out.println(employeeRecord.name());
        System.out.println(employeeRecord.age());
    }

    // before java 16
    public static class Employee {
        private final String name;
        private final int age;

        public Employee(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String name() {
            return name;
        }

        public int age() {
            return age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            Employee employee = (Employee) o;

            if (age != employee.age)
                return false;
            return name != null ? name.equals(employee.name) : employee.name == null;
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + age;
            return result;
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    // after java 16
    public record EmployeeRecord(String name, int age) {
    }
}
