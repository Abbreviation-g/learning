package learning_jdk.structured_concurrency;

public class OrderEntities {
    public static record Order(String orderId, double amount, String status){}
    public static record User(String userId, String name, String email){}
}
