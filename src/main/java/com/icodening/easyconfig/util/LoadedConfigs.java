package com.icodening.easyconfig.util;

import com.icodening.easyconfig.config.Config;
import com.icodening.easyconfig.config.FileConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * @author icodening
 * @date 2023.11.14
 */
public abstract class LoadedConfigs {

    @Getter
    @Setter
    private static Config config;

    @Getter
    @Setter
    private static FileConfig fileConfig;

    @Getter
    @Setter
    private static FileConfig originalFileConfig;

}
