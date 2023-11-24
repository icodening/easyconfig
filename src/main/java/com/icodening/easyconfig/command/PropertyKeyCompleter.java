package com.icodening.easyconfig.command;

import com.icodening.easyconfig.config.Config;
import com.icodening.easyconfig.metadata.AdditionalMetadataRepository;
import com.icodening.easyconfig.util.LoadedConfigs;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author icodening
 * @date 2023.11.10
 */
public class PropertyKeyCompleter implements Completer {

    private final boolean includeAdditional;

    public PropertyKeyCompleter() {
        this(true);
    }

    public PropertyKeyCompleter(boolean includeAdditional) {
        this.includeAdditional = includeAdditional;
    }

    @Override
    public void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {
        Config config = LoadedConfigs.getConfig();
        Set<String> result;
        if (config != null) {
            result = new LinkedHashSet<>(config.getPropertyNames());
        } else {
            result = new LinkedHashSet<>(512);
        }
        if (includeAdditional) {
            result.addAll(AdditionalMetadataRepository.getAllPropertyKey());
        }
        candidates.addAll(result.stream().map(Candidate::new).toList());
    }
}
