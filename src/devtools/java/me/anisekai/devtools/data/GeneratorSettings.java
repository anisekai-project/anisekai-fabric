package me.anisekai.devtools.data;

import me.anisekai.devtools.DevUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GeneratorSettings {

    private final Map<BlockVariant, JSONObject> variants = new LinkedHashMap<>();

    public GeneratorSettings(Configuration configuration, JSONObject settings) {


        if (settings.has("usingVariants")) {
            List<JSONObject> groups = DevUtils.readArray(settings.getJSONArray("usingVariants"), JSONArray::getJSONObject);

            for (JSONObject group : groups) {
                String             name        = group.getString("name");
                BlockVariantsGroup bvg         = configuration.getVariantsGroup(name);
                List<String>       excludeList = DevUtils.optReadArray(group, "exclude", JSONArray::getString);

                List<BlockVariant> blockVariants = bvg.getVariants()
                                                      .stream()
                                                      .filter(variant -> !excludeList.contains(variant.getName()))
                                                      .sorted(Comparator.comparing(BlockVariant::getName))
                                                      .toList();

                JSONObject maps = group.has("maps") ? group.getJSONObject("maps") : new JSONObject();
                blockVariants.forEach(blockVariant -> this.variants.put(blockVariant, new JSONObject(maps.toMap())));
            }
        }
    }

    public boolean isUsingVariants() {

        return !this.variants.isEmpty();
    }

    public Map<BlockVariant, JSONObject> getVariants() {

        return this.variants;
    }


}
