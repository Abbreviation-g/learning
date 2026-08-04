package learning_jdk.switchs;

public class ShapeEntities {
    public static abstract sealed class Shape permits Circle, Rectangle, Triangle {
        /**
         * 计算面积
         * 
         * @return
         */
        public abstract double area();
    }

    public static final class Circle extends Shape {
        private final double radius;

        public Circle(double radius) {
            this.radius = radius;
        }

        public double getRadius() {
            return radius;
        }

        @Override
        public double area() {
            return Math.PI * radius * radius;
        }
    }

    public static sealed class Rectangle extends Shape permits Square {
        private final double width;
        private final double height;

        public Rectangle(double width, double height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public double area() {
            return width * height;
        }

        public double getWidth() {
            return width;
        }

        public double getHeight() {
            return height;
        }
    }

    public static final class Square extends Rectangle {
        public Square(double side) {
            super(side, side);
        }
    }

    public static non-sealed class Triangle extends Shape {
        private final double base;
        private final double height;

        public Triangle(double base, double height) {
            this.base = base;
            this.height = height;
        }

        public double getBase() {
            return base;
        }

        public double getHeight() {
            return height;
        }

        @Override
        public double area() {
            return 0.5 * base * height;
        }
    }

}
