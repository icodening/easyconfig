package com.icodening.easyconfig.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author icodening
 * @date 2023.11.16
 */
public abstract class ConfigLoaders {

    private static final Map<String, ConfigLoader> CONFIG_LOADER_MAP = new HashMap<>(4);

    static {
        CONFIG_LOADER_MAP.put("properties", new PropertiesConfigLoader());
    }

    public static ConfigLoader getLoader(String contentType) {
        return CONFIG_LOADER_MAP.get(contentType);
    }
}
