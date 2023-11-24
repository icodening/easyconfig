package com.icodening.easyconfig.config.mustache;

import com.icodening.easyconfig.config.AbstractFileConfig;
import com.icodening.easyconfig.config.ConfigLoader;
import com.icodening.easyconfig.config.FileConfig;
import com.icodening.easyconfig.util.StringUtil;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author icodening
 * @date 2023.11.14
 */
public class MustacheFileConfig extends AbstractFileConfig implements FileConfig {

    private final MustacheHandlerProvider handlerProvider;

    private final Set<String> templateKeys = new HashSet<>();

    private String template;

    private MustacheFileConfig(String filePath, MustacheHandlerProvider handlerProvider) {
        super(filePath);
        this.handlerProvider = handlerProvider;
    }

    public void setTemplate(String template) {
        this.template = template;
        Properties properties = handlerProvider.configLoader().load(template);
        this.templateKeys.clear();
        this.templateKeys.addAll(properties.stringPropertyNames());
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        String content = renderString(template);
        internalWrite(outputStream, content);
    }

    @Override
    public void write() throws IOException {
        String content = renderString(template);
        try (FileOutputStream outputStream = new FileOutputStream(getPath())) {
            internalWrite(outputStream, content);
        }
    }

    @Override
    public void setProperty(String name, String value) {
        super.setProperty(name, value);
        if (!templateKeys.contains(name)) {
            String newTemplate = handlerProvider.mustacheTemplateUpdater().addProperty(template, name, value);
            setTemplate(newTemplate);
        }
    }

    @Override
    public void removeProperty(String name) {
        if (StringUtil.isBlack(name)) {
            return;
        }
        String value = getProperty(name);
        super.removeProperty(name);
        String newTemplate = handlerProvider.mustacheTemplateUpdater().removeProperty(template, name, value);
        setTemplate(newTemplate);
    }

    private static void internalWrite(OutputStream outputStream, String content) throws IOException {
        outputStream.write(content.getBytes());
        outputStream.flush();
    }

    private String renderString(String template) {
        Template mustacheTemplate = Mustache.compiler().compile(template);
        return mustacheTemplate.execute(toUnmodifiableMap());
    }

    public static MustacheFileConfig fromFile(String filePath) throws IOException {
        String content = "";
        File file = new File(filePath);
        if (file.exists()) {
            content = new String(Files.readAllBytes(file.toPath()));
        }
        String fileName = file.getName();
        int dotIdx = fileName.lastIndexOf(".");
        if (dotIdx == -1) {
            throw new UnsupportedOperationException("Unknown file type.");
        }
        String suffix = fileName.substring(dotIdx + 1);
        MustacheHandlerProvider provider = MustacheHandlerProviders.getProvider(suffix);
        if (provider == null) {
            throw new UnsupportedOperationException("Unsupported '" + suffix + "' file.");
        }
        ConfigLoader loader = provider.configLoader();
        MustacheFileConfig config = new MustacheFileConfig(filePath, provider);
        Properties properties = loader.load(content);
        String mustache = provider.mustacheResolver().resolve(content);
        config.setTemplate(mustache);
        for (String propertyName : properties.stringPropertyNames()) {
            config.setProperty(propertyName, properties.getProperty(propertyName));
        }
        return config;
    }
}
