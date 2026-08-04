package learning_jdk.sealed;

/**
 * 密封类的直接子类必须使用以下修饰符之一 sealed non-sealed final
 * final 不可被继承
 * Circle
 */
public final class Circle extends Shape {
    private final double radius;
    public Circle(double radius) {
        this.radius = radius;
    }
    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}
