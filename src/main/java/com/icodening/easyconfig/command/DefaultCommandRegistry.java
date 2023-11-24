package com.icodening.easyconfig.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author icodening
 * @date 2023.11.05
 */
public class DefaultCommandRegistry implements CommandRegistry {

    protected final Map<String, Command> commands = new HashMap<>();

    @Override
    public void register(Command command) {
        commands.put(command.getName(), command);
    }

    @Override
    public Command getCommand(String name) {
        return commands.get(name);
    }

    @Override
    public List<Command> getCommands() {
        return new ArrayList<>(commands.values());
    }
}
