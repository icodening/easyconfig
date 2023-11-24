package com.icodening.easyconfig.config.mustache;

import com.icodening.easyconfig.config.ConfigLoader;
import com.icodening.easyconfig.config.ConfigLoaders;
import com.icodening.easyconfig.config.mustache.properties.PropertiesMustacheResolver;
import com.icodening.easyconfig.config.mustache.properties.PropertiesMustacheTemplateUpdater;

import java.util.HashMap;
import java.util.Map;

/**
 * @author icodening
 * @date 2023.11.16
 */
public abstract class MustacheHandlerProviders {

    private MustacheHandlerProviders() {
    }

    public static MustacheHandlerProvider getProvider(String contentType) {
        ConfigLoader loader = ConfigLoaders.getLoader(contentType);
        MustacheResolver resolver = Resolvers.getResolver(contentType);
        MustacheTemplateUpdater mustacheTemplateUpdater = Updaters.getUpdater(contentType);
        if (loader == null
                || resolver == null
                || mustacheTemplateUpdater == null) {
            return null;
        }
        return new DefaultMustacheHandlerProvider(loader, resolver, mustacheTemplateUpdater);
    }

    private static abstract class Resolvers {
        private static final Map<String, MustacheResolver> RESOLVER_MAP = new HashMap<>(4);

        static {
            RESOLVER_MAP.put("properties", new PropertiesMustacheResolver());
        }

        private Resolvers() {
        }

        public static MustacheResolver getResolver(String contentType) {
            return RESOLVER_MAP.get(contentType);
        }
    }

    private static abstract class Updaters {

        private static final Map<String, MustacheTemplateUpdater> UPDATER_MAP = new HashMap<>(4);

        static {
            UPDATER_MAP.put("properties", new PropertiesMustacheTemplateUpdater());
        }

        private Updaters() {
        }

        public static MustacheTemplateUpdater getUpdater(String contentType) {
            return UPDATER_MAP.get(contentType);
        }
    }
}
