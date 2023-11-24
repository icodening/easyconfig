package com.icodening.easyconfig.command;

import com.icodening.easyconfig.metadata.AdditionalMetadataItem;
import com.icodening.easyconfig.metadata.AdditionalMetadataRepository;
import org.jline.reader.Completer;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author icodening
 * @date 2023.11.09
 */
public class DescribeCommand implements Command {

    private final Completer propertyKeyCompleter = new PropertyKeyCompleter();

    @Override
    public String getName() {
        return "desc";
    }

    @Override
    public AttributedString handle(CommandContext context, List<String> args) {
        List<String> arguments = new ArrayList<>(args);
        arguments.removeIf(String::isEmpty);
        if (arguments.isEmpty()) {
            return new AttributedString("No property name.", new AttributedStyle().foreground(AttributedStyle.RED));
        }
        String propertyName = args.get(0);
        AdditionalMetadataItem additionalMetadataItem = AdditionalMetadataRepository.getAdditionalMetadataEntry(propertyName);
        if (additionalMetadataItem == null) {
            return new AttributedString("No description for '" + propertyName + "'.", new AttributedStyle().foreground(AttributedStyle.RED));
        }
        String prettyString = toPrettyString(additionalMetadataItem);
        return new AttributedString(prettyString, new AttributedStyle().foreground(AttributedStyle.GREEN));
    }

    private String toPrettyString(AdditionalMetadataItem additionalMetadataItem) {
        StringJoiner joiner = new StringJoiner("\n");
        joiner.add("Name: " + additionalMetadataItem.getName());
        joiner.add("Description: " + additionalMetadataItem.getDescription());
        joiner.add("Type: " + additionalMetadataItem.getType());
        joiner.add("DefaultValue: " + additionalMetadataItem.getDefaultValue());
        return joiner.toString();
    }

    @Override
    public Completer getCompleter() {
        return propertyKeyCompleter;
    }
}
