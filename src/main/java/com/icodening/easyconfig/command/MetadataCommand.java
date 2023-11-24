package com.icodening.easyconfig.command;

import com.icodening.easyconfig.metadata.AdditionalMetadataRepository;
import com.icodening.easyconfig.util.Locations;
import com.icodening.easyconfig.util.StringUtil;
import org.jline.builtins.Completers;
import org.jline.reader.Completer;
import org.jline.utils.AttributedString;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author icodening
 * @date 2023.11.09
 */
public class MetadataCommand implements Command {

    private final Completer completer = new Completers.DirectoriesCompleter(new File(Locations.self()));

    @Override
    public String getName() {
        return "meta";
    }

    @Override
    public AttributedString handle(CommandContext context, List<String> args) {
        List<String> arguments = new ArrayList<>(args);
        arguments.removeIf(StringUtil::isBlack);
        if (arguments.isEmpty()) {
            return new AttributedString("No classpath added.");
        }
        for (String filePath : arguments) {
            File f = new File(filePath);
            AdditionalMetadataRepository.asyncLoad(f);
        }
        return new AttributedString("load additional metadata.");
    }

    @Override
    public Completer getCompleter() {
        return completer;
    }
}
