package learning_jdk.sealed;
// 密封类时一个限制子类的类或者接口，你可以在定义时期明确指定哪些其它类可以继承或者实现它
// 语法
// public sealed class 类名 [extends 父类> [implements <接口1>, <接口2>]] permits 子类1, 子类2, ... {}
// 当父类子类在同一个源文件中，可以省略permits子句，编译器会自动推断出所有的直接子类
// 密封类的直接子类必须使用以下修饰符之一 sealed non-sealed final
// 1. sealed - 该类是密封的，必须明确指定允许继承它的子类
// 2. non-sealed - 该类不是密封的，它可以被任意类继承
// 3. final - 该类是最终的，不能被继承

// 1. 普通类(class)
// 2. 最终类(final class)
// 3. 密封类(sealed class )
// 4. 记录类(record)
// 5. 枚举类(enum)
// 6. 接口(interface)
// 7. 注解类(annotation)
// 8. 抽象类(abstract class)