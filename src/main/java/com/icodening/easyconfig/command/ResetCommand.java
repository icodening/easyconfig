package com.icodening.easyconfig.command;

import com.icodening.easyconfig.config.FileConfig;
import com.icodening.easyconfig.util.LoadedConfigs;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;

import java.io.IOException;
import java.util.List;

/**
 * @author icodening
 * @date 2023.11.18
 */
public class ResetCommand implements EmptyArgumentsCommand {

    @Override
    public String getName() {
        return "reset";
    }

    @Override
    public AttributedString handle(CommandContext context, List<String> args) {
        FileConfig originalFileConfig = LoadedConfigs.getOriginalFileConfig();
        if (originalFileConfig == null) {
            return new AttributedString("No config loaded.", new AttributedStyle().foreground(AttributedStyle.RED));
        }
        try {
            originalFileConfig.write();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new AttributedString("Reset config success.", new AttributedStyle().foreground(AttributedStyle.GREEN));
    }
}
