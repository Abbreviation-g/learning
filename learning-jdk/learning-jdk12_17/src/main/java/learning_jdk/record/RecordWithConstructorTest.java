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
public class RecordWithConstructorTest {

    public static void main(String[] args) {
        EmployeeRecord employeeRecord = new EmployeeRecord("李四", 30);
        System.out.println(employeeRecord.toString());
        System.out.println(employeeRecord.name());
        System.out.println(employeeRecord.age());

        try {
            EmployeeRecord employeeRecord2 = new EmployeeRecord("王五", -1);
            System.out.println(employeeRecord2.toString());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(EmployeeRecord.DEFAULT_EMPLOYEE.toString());
        System.out.println(EmployeeRecord.createDefaultEmployee().toString());
    }

    // after java 16
    public record EmployeeRecord(String name, int age) {
        // 1. 自定义构造器
        public EmployeeRecord {
            if (age < 0) {
                throw new IllegalArgumentException("年龄不能为负数");
            }
        }

        // 2. 自定义方法
        public boolean isAdult() {
            return age >= 18;
        }

        // 3. 重写自动生成的tostring
        @Override
        public String toString() {
            return """
                    {
                        "name": "%s",
                        "age": %d
                    }
                    """.formatted(name, age);
        }

        // 4. 静态方法和字段
        public static final EmployeeRecord DEFAULT_EMPLOYEE = new EmployeeRecord("默认员工", 18);

        public static EmployeeRecord createDefaultEmployee() {
            return new EmployeeRecord("默认员工", 18);
        }
    }
}
