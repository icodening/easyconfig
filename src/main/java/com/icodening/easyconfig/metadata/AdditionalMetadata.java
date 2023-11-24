package com.icodening.easyconfig.metadata;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.icodening.easyconfig.util.GsonUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author icodening
 * @date 2023.11.09
 */
@Getter
@Setter
@ToString
public final class AdditionalMetadata {

    private List<AdditionalMetadataItem> properties = new ArrayList<>();

    public static AdditionalMetadata fromInputStream(InputStream inputStream) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(8192);
            inputStream.transferTo(bos);
            String jsonString = bos.toString();
            JsonObject additionalMetadata = GsonUtil.fromJson(jsonString, JsonObject.class);
            if (additionalMetadata == null) {
                return null;
            }
            JsonArray propertiesArray = additionalMetadata.getAsJsonArray("properties");
            if (propertiesArray == null) {
                return null;
            }
            AdditionalMetadata result = new AdditionalMetadata();
            List<AdditionalMetadataItem> additionalMetadataEntries = new ArrayList<>();
            for (JsonElement element : propertiesArray) {
                JsonObject additionalMetadataEntry = element.getAsJsonObject();
                AdditionalMetadataItem entry = new AdditionalMetadataItem();
                entry.setName(additionalMetadataEntry.get("name").getAsString());
                if (additionalMetadataEntry.get("type") != null) {
                    entry.setType(additionalMetadataEntry.get("type").getAsString());
                }
                if (additionalMetadataEntry.get("description") != null) {
                    entry.setDescription(additionalMetadataEntry.get("description").getAsString());
                }
                JsonElement defaultValueElement = additionalMetadataEntry.get("defaultValue");
                if (defaultValueElement != null) {
                    if (defaultValueElement.isJsonPrimitive()) {
                        entry.setDefaultValue(Collections.singletonList(defaultValueElement.getAsString()));
                    } else {
                        JsonArray defaultValueArray = defaultValueElement.getAsJsonArray();
                        List<String> defaultValueList = new ArrayList<>();
                        for (JsonElement defaultValue : defaultValueArray) {
                            defaultValueList.add(defaultValue.getAsString());
                        }
                        entry.setDefaultValue(defaultValueList);
                    }
                }
                additionalMetadataEntries.add(entry);
            }
            result.setProperties(additionalMetadataEntries);
            return result;
        } catch (IOException e) {
            return null;
        }
    }
}
