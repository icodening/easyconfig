package com.icodening.easyconfig.command;

import org.jline.reader.Completer;
import org.jline.utils.AttributedString;

import java.util.List;

/**
 * @author icodening
 * @date 2023.11.05
 */
public interface Command {

    String getName();

    AttributedString handle(CommandContext context, List<String> args);

    Completer getCompleter();
}
