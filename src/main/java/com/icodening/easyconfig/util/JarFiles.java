package com.icodening.easyconfig.util;

import java.io.FileFilter;

/**
 * @author icodening
 * @date 2023.11.12
 */
public abstract class JarFiles {

    private static final FileFilter INSTANCE = pathname -> pathname.isFile() && isJarFile(pathname.getName());

    private JarFiles() {
    }

    public static FileFilter filter() {
        return INSTANCE;
    }

    public static boolean isJarFile(String path) {
        return path.endsWith(".jar");
    }
}
