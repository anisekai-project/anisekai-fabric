package me.anisekai.devtools.data;

import me.anisekai.devtools.DevUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GeneratorSettings {

    private final String                        extendName;
    private final boolean                       skipGeneration;
    private final Map<BlockVariant, JSONObject> variants = new LinkedHashMap<>();

    public GeneratorSettings(Configuration configuration, JSONObject settings) {

        this.extendName     = settings.has("extends") ? settings.getString("extends") : null;
        this.skipGeneration = settings.has("skipGeneration") && settings.getBoolean("skipGeneration");

        if (settings.has("usingVariant")) {
            JSONObject variantData = settings.getJSONObject("usingVariant");

            String             name        = variantData.getString("name");
            BlockVariantsGroup bvg         = configuration.getVariantsGroup(name);
            List<String>       excludeList = DevUtils.optReadArray(variantData, "exclude", JSONArray::getString);

            List<BlockVariant> blockVariants = bvg.getVariants()
                                                  .stream()
                                                  .filter(variant -> !excludeList.contains(variant.getName()))
                                                  .sorted(Comparator.comparing(BlockVariant::getName))
                                                  .toList();

            JSONObject maps = variantData.has("maps") ? variantData.getJSONObject("maps") : new JSONObject();
            blockVariants.forEach(blockVariant -> this.variants.put(blockVariant, new JSONObject(maps.toMap())));
        }
    }

    public boolean shouldSkipGeneration() {

        return this.skipGeneration;
    }

    public boolean hasExtendName() {

        return this.extendName != null;
    }

    public String getExtendName() {

        return this.extendName;
    }

    public boolean isUsingVariants() {

        return !this.variants.isEmpty();
    }

    public Map<BlockVariant, JSONObject> getVariants() {

        return this.variants;
    }

}
