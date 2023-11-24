package com.icodening.easyconfig.config;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author icodening
 * @date 2023.11.12
 */
public interface FileConfig {

    String getExtension();

    String getPath();

    default void write() throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(getPath())) {
            this.writeTo(fileOutputStream);
        }
    }

    void writeTo(OutputStream outputStream) throws IOException;

}
