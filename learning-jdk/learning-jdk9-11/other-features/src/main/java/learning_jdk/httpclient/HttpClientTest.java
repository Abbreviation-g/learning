package learning_jdk.httpclient;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Scanner;
import java.util.Timer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class HttpClientTest {

    public static void main(String[] args) throws Exception {
        // System.out.println("中文");
        // beforeJava11();
        // afterJava11Sync();
        afterJava11Async();
    }

    public static void beforeJava11() throws Exception {
        HttpURLConnection connection = null;
        Scanner reader = null;
        try {
            URL url = new URL("https://www.baidu.com");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("请求失败" + responseCode);
            }

            // 指定 UTF-8 字符集解码响应流，避免中文乱码
            reader = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8);
            StringBuilder result = new StringBuilder();
            while (reader.hasNextLine()) {
                result.append(reader.nextLine());
            }
            System.out.println("响应内容\n" + result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.disconnect();
                } catch (Exception e) {
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                }
            }
        }
    }

    public static void afterJava11Sync() throws Exception {
        HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.baidu.com"))
                .timeout(Duration.ofSeconds(5))
                .header("Accept", "text/html")
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            System.out.println("响应码\n" + response.statusCode());
            System.out.println("响应内容\n" + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void afterJava11Async() throws Exception {
        HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.baidu.com"))
                .timeout(Duration.ofSeconds(5))
                .header("Accept", "text/html")
                .GET()
                .build();
        try {
            CompletableFuture<HttpResponse<String>> future = client.sendAsync(request,
                    HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            future.thenAccept(response -> {
                System.out.println("响应码\n" + response.statusCode());
                System.out.println("响应内容\n" + response.body());
            }).exceptionally(e -> {
                e.printStackTrace();
                return null;
            });
            System.out.println("请求发送完成");
            // 等待请求结束
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
}
