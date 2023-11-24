package com.icodening.easyconfig.config.mustache.properties;

import com.icodening.easyconfig.config.mustache.MustacheTemplateUpdater;
import com.icodening.easyconfig.util.StringUtil;

/**
 * @author icodening
 * @date 2023.11.16
 */
public class PropertiesMustacheTemplateUpdater implements MustacheTemplateUpdater {

    @Override
    public String addProperty(String template, String name, String value) {
        if (template.endsWith("\n") || StringUtil.isBlack(template)) {
            return template + name + "={{" + name + "}}\n";
        } else {
            return template + "\n" + name + "={{" + name + "}}\n";
        }
    }

    @Override
    public String removeProperty(String template, String name, String value) {
        String regex = name + "=\\{\\{" + name + "\\}\\}\n";
        return template.replaceAll(regex, "");
    }
}
