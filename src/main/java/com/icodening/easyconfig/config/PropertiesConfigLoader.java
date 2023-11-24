package com.icodening.easyconfig.config;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

/**
 * @author icodening
 * @date 2023.11.16
 */
public class PropertiesConfigLoader implements ConfigLoader {

    @Override
    public Properties load(String content) {
        Properties properties = new Properties();
        try {
            StringReader reader = new StringReader(content);
            properties.load(reader);
            return properties;
        } catch (IOException e) {
            return properties;
        }
    }
}
