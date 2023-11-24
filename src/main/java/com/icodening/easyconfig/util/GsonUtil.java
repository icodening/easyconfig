package com.icodening.easyconfig.util;

import com.google.gson.Gson;

/**
 * @author icodening
 * @date 2023.11.05
 */
public class GsonUtil {

    private static final Gson GSON = new Gson().newBuilder().setPrettyPrinting().create();

    private GsonUtil() {
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }
}
