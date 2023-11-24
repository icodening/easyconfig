package com.icodening.easyconfig.config.mustache.properties;

import com.icodening.easyconfig.config.mustache.MustacheResolver;

/**
 * @author icodening
 * @date 2023.11.16
 */
public class PropertiesMustacheResolver implements MustacheResolver {

    @Override
    public String resolve(String content) {
        return content.replaceAll("(?m)^(?!#)([^#\\n;=]+)=([^#\\n;]+)", "$1={{$1}}");
    }
}
