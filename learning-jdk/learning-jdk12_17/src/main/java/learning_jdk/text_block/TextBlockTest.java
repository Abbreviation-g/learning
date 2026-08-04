package learning_jdk.text_block;

public class TextBlockTest {
    public static void main(String[] args) {
        beforeJava15();
        afterJava15();
        afterJava15_2();
    }

    public static void beforeJava15() {
        String json = "{\n" +
                "    \"name\": \"张三\",\n" +
                "    \"age\": 20,\n" +
                "    \"address\": \"北京市朝阳区\"\n" +
                "}";
        System.out.println(json);
    }

    public static void afterJava15() {
        String json = """
                {
                    "name": "张三",
                    "age": 20,
                    "address": "北京市朝阳区"
                }
                """;
        System.out.println(json);
    }

    public static void afterJava15_2() {
        // \s表示空格
        // \表示不需要换行
        String json = """
                SELECT id, name, age, address\s\
                FROM user\s\
                WHERE age > 18\s\
                ORDER BY age DESC
                """;
        System.out.println(json); // { "name": "张三", "age": 20, "address": "北京市朝阳区" }
    }
}
