package learning_jdk.multi_file_compile;

import java.util.stream.IntStream;

public class ArrayTools {
    public static int max(int[] nums) {
        return IntStream.of(nums).max().getAsInt();
    }
}
