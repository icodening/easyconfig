package com.icodening.easyconfig.util;

/**
 * @author icodening
 * @date 2023.11.10
 */
public abstract class Locations {

    public static String self() {
        return System.getProperty("user.dir");
    }
}
