package utils;

import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
    @SneakyThrows
    public static Properties getProperties(String fileName) {
        Properties propertiesDriver;
        InputStream inputStreamDriver = PropertyUtil.class.getClassLoader().getResourceAsStream(fileName);
        propertiesDriver = new Properties();
        propertiesDriver.load(inputStreamDriver);
        return propertiesDriver;
    }
}
