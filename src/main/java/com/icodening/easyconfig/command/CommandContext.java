package com.icodening.easyconfig.command;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

/**
 * @author icodening
 * @date 2023.11.06
 */
public class CommandContext {

    private static final AtomicReference<CommandContext> CONTEXT = new AtomicReference<>();

    @Getter
    @Setter
    private String commandName;

    private final Map<String, Object> attributes = new HashMap<>();

    public void set(String name, Object value) {
        attributes.put(name, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T setIfAbsent(String name, Function<String, T> function) {
        return (T) attributes.computeIfAbsent(name, function);
    }

    public <T> T get(String name, Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        Object value = get(name);
        if (clazz.isInstance(value)) {
            return clazz.cast(value);
        }
        return null;
    }

    private CommandContext() {
    }

    public Object get(String name) {
        return attributes.get(name);
    }

    public static void clear() {
        CONTEXT.set(null);
    }

    public static CommandContext createContext() {
        CommandContext commandContext = new CommandContext();
        CONTEXT.set(commandContext);
        return commandContext;
    }

    public static CommandContext getOrCreateContext() {
        CommandContext commandContext = CONTEXT.get();
        if (commandContext == null) {
            commandContext = createContext();
        }
        return commandContext;
    }
}
