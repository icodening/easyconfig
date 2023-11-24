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
public class SetValueCommand implements Command {

    private final Completer completer = new PropertyKeyCompleter();

    @Override
    public String getName() {
        return "set";
    }

    @Override
    public AttributedString handle(CommandContext context, List<String> args) {
        if (args.size() != 2) {
            return new AttributedString("Incorrect usage, eg. 'set key value'", new AttributedStyle().foreground(AttributedStyle.RED));
        }
        Config config = LoadedConfigs.getConfig();
        if (config == null) {
            return new AttributedString("No config loaded.", new AttributedStyle().foreground(AttributedStyle.RED));
        }
        config.setProperty(args.get(0), args.get(1));
        return null;
    }

    @Override
    public Completer getCompleter() {
        return completer;
    }
}
