package com.icodening.easyconfig;

import com.icodening.easyconfig.command.Command;
import com.icodening.easyconfig.command.CommandContext;
import com.icodening.easyconfig.command.CommandRegistryCompleter;
import com.icodening.easyconfig.command.DefaultCommandRegistry;
import com.icodening.easyconfig.command.DescribeCommand;
import com.icodening.easyconfig.command.EmptyArgumentsCommand;
import com.icodening.easyconfig.command.GetValueCommand;
import com.icodening.easyconfig.command.MetadataCommand;
import com.icodening.easyconfig.command.RemoveCommand;
import com.icodening.easyconfig.command.ResetCommand;
import com.icodening.easyconfig.command.SaveCommand;
import com.icodening.easyconfig.command.SetValueCommand;
import com.icodening.easyconfig.command.StacktraceCommand;
import com.icodening.easyconfig.config.ImmutableFileConfig;
import com.icodening.easyconfig.config.mustache.MustacheFileConfig;
import com.icodening.easyconfig.metadata.AdditionalMetadataRepository;
import com.icodening.easyconfig.util.GlobalThrowableStore;
import com.icodening.easyconfig.util.LoadedConfigs;
import com.icodening.easyconfig.util.Locations;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.ParsedLine;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * @author icodening
 * @date 2023.11.09
 */
public class EasyConfigMain {

    private static final String[] LIB_PATH = {Locations.self(), "lib"};

    private static final String APP_NAME = "EasyConfig";

    private static final String PROMPT = "easyconfig $ ";

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Please input the config file.");
            return;
        }
        loadConfig(args[0]);
        for (String path : LIB_PATH) {
            loadJarsByDirectory(path);
        }
        DefaultCommandRegistry commandRegistry = buildDefaultCommandRegistry();
        CommandRegistryCompleter commandRegistryCompleter = new CommandRegistryCompleter();
        commandRegistryCompleter.setCommandRegistry(commandRegistry);
        Terminal terminal = TerminalBuilder.builder()
                .encoding(StandardCharsets.UTF_8)
                .build();
        LineReader reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .completer(commandRegistryCompleter)
                .parser(new DefaultParser())
                .appName(APP_NAME)
                .build();
        while (true) {
            String commandName = null;
            try {
                CommandContext context = CommandContext.createContext();
                reader.readLine(PROMPT);
                ParsedLine parsedLine = reader.getParsedLine();
                List<String> words = new ArrayList<>(parsedLine.words());
                commandName = words.get(0);
                Command command = commandRegistry.getCommand(commandName);
                if (command == null) {
                    AttributedString noCommandErrorString = new AttributedString("No command found for '" + commandName + "'", new AttributedStyle().foreground(AttributedStyle.RED));
                    noCommandErrorString.println(terminal);
                    continue;
                }
                //remove command name
                words.remove(0);
                context.setCommandName(commandName);
                AttributedString renderString = command.handle(context, words);
                if (renderString != null) {
                    renderString.println(terminal);
                    terminal.flush();
                }
                if (command instanceof ExitCommand) {
                    return;
                }
            } catch (UserInterruptException ignored) {
            } catch (Throwable throwable) {
                AttributedString errorString = new AttributedString("'" + commandName + "' execute fail. You can use 'stacktrace' to print details. ", new AttributedStyle().foreground(AttributedStyle.RED));
                errorString.println(terminal);
                GlobalThrowableStore.setThrowable(throwable);
            } finally {
                CommandContext.clear();
            }
        }
    }

    private static DefaultCommandRegistry buildDefaultCommandRegistry() {
        DefaultCommandRegistry commandRegistry = new DefaultCommandRegistry();
        commandRegistry.register(new ExitCommand());
        commandRegistry.register(new StacktraceCommand());
        commandRegistry.register(new MetadataCommand());
        commandRegistry.register(new SetValueCommand());
        commandRegistry.register(new DescribeCommand());
        commandRegistry.register(new SaveCommand());
        commandRegistry.register(new RemoveCommand());
        commandRegistry.register(new GetValueCommand());
        commandRegistry.register(new ResetCommand());
        return commandRegistry;
    }

    private static void loadJarsByDirectory(String path) {
        AdditionalMetadataRepository.asyncLoad(new File(path));
    }

    private static void loadConfig(String configFile) {
        try {
            MustacheFileConfig fileConfig = MustacheFileConfig.fromFile(configFile);
            LoadedConfigs.setConfig(fileConfig);
            LoadedConfigs.setFileConfig(fileConfig);
            String originalContent = "";
            File file = new File(configFile);
            if (file.exists()) {
                originalContent = new String(Files.readAllBytes(file.toPath()));
            }
            LoadedConfigs.setOriginalFileConfig(new ImmutableFileConfig(file, originalContent));
        } catch (IOException e) {
            System.err.println("Load config fail. " + e.getMessage());
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private static class ExitCommand implements EmptyArgumentsCommand {

        @Override
        public String getName() {
            return "exit";
        }

        @Override
        public AttributedString handle(CommandContext context, List<String> args) {
            //do nothing
            return new AttributedString("Bye!", new AttributedStyle().foreground(AttributedStyle.GREEN));
        }
    }
}
