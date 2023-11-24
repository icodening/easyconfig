package com.icodening.easyconfig.config;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author icodening
 * @date 2023.11.12
 */
public class DefaultConfig implements Config {

    private final Map<String, String> properties = new LinkedHashMap<>();

    @Override
    public void setProperty(String name, String value) {
        this.properties.put(name, value);
    }

    @Override
    public void removeProperty(String name) {
        this.properties.remove(name);
    }

    @Override
    public String getProperty(String name) {
        return properties.get(name);
    }

    @Override
    public Map<String, String> toUnmodifiableMap() {
        return Collections.unmodifiableMap(properties);
    }
}
