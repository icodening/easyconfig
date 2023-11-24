package com.icodening.easyconfig.command;

import org.jline.utils.AttributedString;

import java.util.List;

/**
 * @author icodening
 * @date 2023.11.06
 */
public class ExitCommand implements EmptyArgumentsCommand {

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public AttributedString handle(CommandContext context, List<String> args) {
        System.out.println("Bye!");
        System.exit(0);
        return null;
    }
}
