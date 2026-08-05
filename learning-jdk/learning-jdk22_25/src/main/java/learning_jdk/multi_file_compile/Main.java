package learning_jdk.multi_file_compile;

// javac learning_jdk/multi_file_compile/Main.java
// java learning_jdk/multi_file_compile/Main.java
public class Main {
    void main() {
        int[] nums = { 1, 2, 3, 4, 5 };
        int max = ArrayTools.max(nums);
        System.out.println("max = " + max);
    }
}
