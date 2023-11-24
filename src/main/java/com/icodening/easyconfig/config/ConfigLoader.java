package com.icodening.easyconfig.config;

import java.util.Properties;

/**
 * @author icodening
 * @date 2023.11.16
 */
public interface ConfigLoader {

    Properties load(String content);

}
