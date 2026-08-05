package learning_jdk.stable_value;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.function.Supplier;

public class ConfigManager {
    private static final Supplier<Config> configSupplier = 
        StableValue.supplier(ConfigManager::loadConfigFromFile);

    private static final ConfigManager INSTANCE = new ConfigManager();

    private ConfigManager() {
        System.out.println("ConfigManager created");
    }

    public Config getConfig() {
        return configSupplier.get();
    }

    private static Config loadConfigFromFile() {
        System.out.println("---开始解析配置文件(仅执行一次)");
        Properties properties = new Properties();
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("app.properties")) {
            if (input == null) {
                throw new RuntimeException("配置文件不存在");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("解析配置文件失败" + e.getMessage());
        }
        return new Config(properties.getProperty("url"),
                properties.getProperty("username"),
                properties.getProperty("password"),
                properties.getProperty("logLevel"));
    }

    public static ConfigManager getInstance() {
        return INSTANCE;
    }
}
