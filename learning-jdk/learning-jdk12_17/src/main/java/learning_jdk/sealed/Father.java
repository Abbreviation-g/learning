package learning_jdk.sealed;

/**
 * 当父类子类在同一个源文件中，可以省略permits子句，编译器会自动推断出所有的直接子类
 * Father
 */
public sealed class Father {
}

sealed class Son extends Father {
}

final class GrandSon extends Son {
}

final class Daughter extends Father {
}

/**
 * 解除限制，随意继承
 * Friend
 */
non-sealed class Friend extends Father {
}
class OtherFriend extends Friend {
}