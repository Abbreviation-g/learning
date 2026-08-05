package learning_jdk.stable_value;

import java.util.concurrent.StructureViolationException;
import java.util.concurrent.StructuredTaskScope;

import learning_jdk.structured_concurrency.StructuredTaskScopeTest;

// // cd .\learning-jdk25_preview\
// java --enable-preview -cp target/classes learning_jdk.stable_value.StableValueTest

public class StableValueTest {
    public static void main(String[] args) {
        ConfigManager configManager = ConfigManager.getInstance();

        try (var scope = StructuredTaskScope.open()) {
            for(int i=0;i<3;i++) {
                scope.fork(() -> {
                    System.out.println("->线程"+Thread.currentThread()+"开始获取配置");
                    Config config = configManager.getConfig();
                    System.out.println("<-线程"+Thread.currentThread()+"完成获取配置"+config);
                });
            }
            scope.join();
            System.out.println("主线程获取配置"+configManager.getConfig());
        } catch (StructuredTaskScope.FailedException e) {
            System.err.println("请求处理失败"+e.getCause().getMessage());
        } catch (InterruptedException e) {
            System.out.println("请求终端"+e.getMessage());
        }
        System.out.println("执行完毕");
    }
}
