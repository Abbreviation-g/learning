package learning_jdk.sealed;

/**
 * Triangle可以被任意继承，解除限制
 * Triangle
 */
public non-sealed class Triangle extends Shape {
    private final double base;
    private final double height;

    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    @Override
    public double area() {
        return 0.5 * base * height;
    }
}
