package com.icodening.easyconfig.command;

import com.icodening.easyconfig.config.Config;
import com.icodening.easyconfig.util.LoadedConfigs;
import org.jline.reader.Completer;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;

import java.util.List;

/**
 * @author icodening
 * @date 2023.11.14
 */
public class RemoveCommand implements Command {

    private final Completer propertyKeyCompleter = new PropertyKeyCompleter(false);

    @Override
    public String getName() {
        return "rm";
    }

    @Override
    public AttributedString handle(CommandContext context, List<String> args) {
        Config config = LoadedConfigs.getConfig();
        if (config == null) {
            return new AttributedString("No config loaded.", new AttributedStyle().foreground(AttributedStyle.RED));
        }
        for (String arg : args) {
            config.removeProperty(arg);
        }
        return null;
    }

    @Override
    public Completer getCompleter() {
        return propertyKeyCompleter;
    }
}
