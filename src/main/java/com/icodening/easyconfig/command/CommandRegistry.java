package com.icodening.easyconfig.command;

import java.util.List;

/**
 * @author icodening
 * @date 2023.11.05
 */
public interface CommandRegistry {

    void register(Command command);

    Command getCommand(String name);

    List<Command> getCommands();
}
