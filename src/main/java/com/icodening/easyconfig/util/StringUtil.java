package com.icodening.easyconfig.util;

/**
 * @author icodening
 * @date 2023.11.06
 */
public abstract class StringUtil {

    private StringUtil() {
    }

    public static boolean isBlack(String string) {
        return string == null || string.trim().isEmpty();
    }

    public static boolean isNotBlack(String string) {
        return !isBlack(string);
    }
}
