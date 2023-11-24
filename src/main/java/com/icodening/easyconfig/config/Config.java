package com.icodening.easyconfig.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author icodening
 * @date 2023.11.12
 */
public interface Config {

    void setProperty(String name, String value);

    void removeProperty(String name);

    String getProperty(String name);

    Map<String, String> toUnmodifiableMap();

    default List<String> getPropertyNames() {
        return new ArrayList<>(toUnmodifiableMap().keySet());
    }
}
