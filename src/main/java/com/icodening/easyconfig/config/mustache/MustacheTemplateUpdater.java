package com.icodening.easyconfig.config.mustache;

/**
 * @author icodening
 * @date 2023.11.16
 */
public interface MustacheTemplateUpdater {

    String addProperty(String template, String name, String value);

    String removeProperty(String template, String name, String value);
}
