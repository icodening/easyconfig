package com.icodening.easyconfig.metadata;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

/**
 * @author icodening
 * @date 2023.11.09
 */
@Getter
@Setter
@ToString
public final class AdditionalMetadataItem {

    private String name;

    private String type;

    private String description;

    private List<String> defaultValue = Collections.emptyList();
}
