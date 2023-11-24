package com.icodening.easyconfig.config;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author icodening
 * @date 2023.11.18
 */
public final class ImmutableFileConfig implements FileConfig {

    private final File file;

    private final String content;

    public ImmutableFileConfig(File file,
                               String content) {
        this.file = file;
        this.content = content;
    }

    @Override
    public String getExtension() {
        return file.getName().substring(file.getName().lastIndexOf(".") + 1);
    }

    @Override
    public String getPath() {
        return file.getPath();
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        outputStream.write(content.getBytes());
    }
}
