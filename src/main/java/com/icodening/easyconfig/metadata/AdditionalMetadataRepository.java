package com.icodening.easyconfig.metadata;

import com.icodening.easyconfig.util.JarFiles;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

/**
 * @author icodening
 * @date 2023.11.09
 */
public final class AdditionalMetadataRepository {

    private static final String ADDITIONAL_METADATA_FILE_NAME = "additional-spring-configuration-metadata.json";

    private static final Map<String, AdditionalMetadataItem> ADDITIONAL_METADATA_ENTRY_MAP = new ConcurrentHashMap<>(1024);

    private static final Set<String> ADDITIONAL_PROPERTY_KEYS = Collections.synchronizedSet(new LinkedHashSet<>(1024));

    private static final ExecutorService VIRTUAL_THREAD_PER_TASK_EXECUTOR = Executors.newVirtualThreadPerTaskExecutor();

    private AdditionalMetadataRepository() {
    }

    public static void load(File file) throws IOException {
        internalLoad(file);
    }

    public static void asyncLoad(File file) {
        VIRTUAL_THREAD_PER_TASK_EXECUTOR.execute(() -> {
            try {
                load(file);
            } catch (IOException ignored) {
            }
        });
    }

    private static void internalLoad(File file) throws IOException {
        if (file.isDirectory()) {
            File[] jars = file.listFiles(JarFiles.filter());
            if (jars != null) {
                for (File jar : jars) {
                    doLoadJarFile(new JarFile(jar));
                }
            }
        } else {
            if (JarFiles.isJarFile(file.getName())) {
                doLoadJarFile(new JarFile(file));
            }
        }
    }

    private static void doLoadJarFile(JarFile file) throws IOException {
        try (JarFile jarFile = file) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry jarEntry = entries.nextElement();
                if (JarFiles.isJarFile(jarEntry.getName())) {
                    try (JarInputStream jarInputStream = new JarInputStream(jarFile.getInputStream(jarEntry))) {
                        ZipEntry zipEntry;
                        while ((zipEntry = jarInputStream.getNextEntry()) != null) {
                            if (zipEntry.getName().endsWith(ADDITIONAL_METADATA_FILE_NAME)) {
                                doLoadAdditionalMetadata(jarInputStream);
                            }
                        }
                    }
                }
                if (jarEntry.getName().endsWith(ADDITIONAL_METADATA_FILE_NAME)) {
                    try (InputStream inputStream = jarFile.getInputStream(jarEntry)) {
                        doLoadAdditionalMetadata(inputStream);
                    }
                }
            }
        }
    }

    private static void doLoadAdditionalMetadata(InputStream entryInputstream) {
        AdditionalMetadata additionalMetadata = AdditionalMetadata.fromInputStream(entryInputstream);
        if (additionalMetadata == null) {
            return;
        }
        for (AdditionalMetadataItem item : additionalMetadata.getProperties()) {
            String name = item.getName();
            ADDITIONAL_METADATA_ENTRY_MAP.put(name, item);
            ADDITIONAL_PROPERTY_KEYS.add(name);
        }
    }

    public static AdditionalMetadataItem getAdditionalMetadataEntry(String propertyName) {
        return ADDITIONAL_METADATA_ENTRY_MAP.get(propertyName);
    }

    public static Set<String> getAllPropertyKey() {
        return Collections.unmodifiableSet(ADDITIONAL_PROPERTY_KEYS);
    }
}
