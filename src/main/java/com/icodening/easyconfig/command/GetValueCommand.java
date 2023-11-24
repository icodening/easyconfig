package com.icodening.easyconfig.command;

import com.icodening.easyconfig.config.Config;
import com.icodening.easyconfig.util.LoadedConfigs;
import org.jline.reader.Completer;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;

import java.util.List;

/**
 * @author icodening
 * @date 2023.11.09
 */
public class GetValueCommand implements Command {

    private final Completer propertyKeyCompleter = new PropertyKeyCompleter(false);

    @Override
    public String getName() {
        return "get";
    }

    @Override
    public AttributedString handle(CommandContext context, List<String> args) {
        if (args.size() != 2) {
            return new AttributedString("Incorrect usage, eg. 'get key'", new AttributedStyle().foreground(AttributedStyle.RED));
        }
        Config config = LoadedConfigs.getConfig();
        if (config == null) {
            return new AttributedString("No config loaded.", new AttributedStyle().foreground(AttributedStyle.RED));
        }
        String property = config.getProperty(args.get(0));
        return new AttributedString(property, new AttributedStyle().foreground(AttributedStyle.GREEN));
    }

    @Override
    public Completer getCompleter() {
        return propertyKeyCompleter;
    }
}
