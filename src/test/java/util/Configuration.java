package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Configuration {
    private Properties prop = new Properties();
    private String configPropPath;

    public Configuration() throws IOException {
        String configPath = readEnvironment();
        loadConfigProperties(configPath);
    }

    public String getApiBaseUrl() {
        return prop.getProperty("api.base.url");
    }
    public String getApiKey() {
        return prop.getProperty("api.key");
    }

    private void loadConfigProperties(String path) throws IOException {
        InputStream in = new FileInputStream(path);
        prop.load(new InputStreamReader(in, StandardCharsets.UTF_8));
    }

    public String readEnvironment() {
        return configPropPath = System.getProperty("user.dir") + "\\src\\test\\resources\\config.properties";
    }
}
