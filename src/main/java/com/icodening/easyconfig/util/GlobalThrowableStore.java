package com.icodening.easyconfig.util;

import lombok.Getter;
import lombok.Setter;

/**
 * @author icodening
 * @date 2023.11.03
 */
public final class GlobalThrowableStore {

    @Getter
    @Setter
    private static Throwable throwable;

    private GlobalThrowableStore() {
    }
}
