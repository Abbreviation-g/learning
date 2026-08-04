package learning_jdk.sealed;

/**
 * ‌密封类型必须显式声明允许的子类‌：任何使用 sealed 修饰的类或接口，必须在声明后紧跟 permits 子句列出所有直接子类/实现类。
 * 密封类的直接子类必须使用以下修饰符之一 sealed non-sealed final
 * Shape
 */
public abstract sealed class Shape permits Circle, Rectangle, Triangle {
    /**
     * 计算面积
     * @return
     */
    public abstract double area();
}
