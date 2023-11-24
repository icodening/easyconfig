package com.icodening.easyconfig.command;

import org.jline.reader.Completer;

/**
 * @author icodening
 * @date 2023.11.06
 */
public interface EmptyArgumentsCommand extends Command {

    @Override
    default Completer getCompleter() {
        return null;
    }
}
