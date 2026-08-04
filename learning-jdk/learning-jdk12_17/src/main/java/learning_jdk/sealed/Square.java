package learning_jdk.sealed;

/**
 * 密封类的直接子类必须使用以下修饰符之一 sealed non-sealed final
 * Square
 */
public final class Square extends Rectangle {
    public Square(double side) {
        super(side, side);
    }
}
