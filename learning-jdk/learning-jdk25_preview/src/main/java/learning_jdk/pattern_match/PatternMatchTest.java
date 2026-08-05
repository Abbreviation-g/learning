package learning_jdk.pattern_match;

// java25预览特性
// cd .\learning-jdk25_preview\
// java --enable-preview -cp target/classes learning_jdk.pattern_match.PatternMatchTest
public class PatternMatchTest {
    public static void main(String[] args) {
        System.out.println(testIf(1));
        System.out.println(testIf(1.0));
        System.out.println(testIf(true));

        System.out.println(testSwitch(1));
        System.out.println(testSwitch(1.0));
        System.out.println(testSwitch(true));
    }

    private static String testIf(Object obj){
        if (obj instanceof int i) {
            return "int "+i;
        } else if (obj instanceof double d) {
            return "double "+d;
        } else if(obj instanceof boolean b) {
            return "boolean "+b;
        } else {
            return "未知类型";
        }
    }

    private static String testSwitch(Object obj){
        return switch(obj) {
            case int a -> "int "+a;
            case double d -> "double "+d;
            case boolean b -> "boolean "+b;
            case null,default -> "未知类型";
        };
    }
}
