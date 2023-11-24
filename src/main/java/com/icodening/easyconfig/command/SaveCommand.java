package com.icodening.easyconfig.command;

import com.icodening.easyconfig.config.FileConfig;
import com.icodening.easyconfig.util.LoadedConfigs;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;

import java.io.IOException;
import java.util.List;

/**
 * @author icodening
 * @date 2023.11.14
 */
public class SaveCommand implements EmptyArgumentsCommand {

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public AttributedString handle(CommandContext context, List<String> args) {
        FileConfig fileConfig = LoadedConfigs.getFileConfig();
        if (fileConfig == null) {
            return new AttributedString("No config loaded.", new AttributedStyle().foreground(AttributedStyle.RED));
        }
        try {
            fileConfig.write();
        } catch (IOException e) {
            return new AttributedString("Save config fail.", new AttributedStyle().foreground(AttributedStyle.RED));
        }
        return new AttributedString("Save config success.", new AttributedStyle().foreground(AttributedStyle.GREEN));
    }
}
