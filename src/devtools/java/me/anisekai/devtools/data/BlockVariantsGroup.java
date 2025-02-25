package me.anisekai.devtools.data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BlockVariantsGroup {

    private final String             name;
    private final List<BlockVariant> variants;

    public BlockVariantsGroup(String name, JSONArray source) {

        this.name     = name;
        this.variants = new ArrayList<>();

        for (int j = 0; j < source.length(); j++) {
            JSONObject value = source.getJSONObject(j);

            String variantName = value.getString("name");

            if (value.has("mappings")) {
                JSONObject mappings = value.getJSONObject("mappings");
                this.variants.add(new BlockVariant(variantName, mappings));
            } else {
                this.variants.add(new BlockVariant(variantName));
            }
        }
    }

    public String getName() {

        return this.name;
    }

    public List<BlockVariant> getVariants() {

        return this.variants;
    }

}
