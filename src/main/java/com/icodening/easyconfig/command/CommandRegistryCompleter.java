package com.icodening.easyconfig.command;

import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author icodening
 * @date 2023.11.05
 */
public class CommandRegistryCompleter implements Completer {

    private CommandRegistry commandRegistry;

    public void setCommandRegistry(CommandRegistry commandRegistry) {
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {
        //first word is command
        if (line.wordIndex() == 0) {
            for (Command command : commandRegistry.getCommands()) {
                candidates.add(new Candidate(command.getName()));
            }
            return;
        }
        List<String> words = new ArrayList<>(line.words());
        String commandName = words.get(0);
        Command command = commandRegistry.getCommand(commandName);
        if (command == null) {
            return;
        }
        Completer completer = command.getCompleter();
        if (completer == null) {
            return;
        }
        completer.complete(reader, line, candidates);
    }
}
