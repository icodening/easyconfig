package com.icodening.easyconfig.config;

/**
 * @author icodening
 * @date 2023.11.16
 */
public abstract class AbstractFileConfig extends DefaultConfig implements FileConfig {

    private final String filePath;

    private final String extension;

    protected AbstractFileConfig(String filePath) {
        this.filePath = filePath;
        this.extension = filePath.substring(filePath.lastIndexOf(".") + 1);
    }

    @Override
    public final String getExtension() {
        return extension;
    }

    @Override
    public final String getPath() {
        return filePath;
    }
}
