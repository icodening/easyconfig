package com.icodening.easyconfig.config.mustache;

import com.icodening.easyconfig.config.ConfigLoader;

/**
 * @author icodening
 * @date 2023.11.16
 */
public interface MustacheHandlerProvider {

    ConfigLoader configLoader();

    MustacheResolver mustacheResolver();

    MustacheTemplateUpdater mustacheTemplateUpdater();
}
