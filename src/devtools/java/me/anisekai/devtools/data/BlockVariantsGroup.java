package me.anisekai.devtools.data;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BlockVariantsGroup {

    private final String             name;
    private final List<BlockVariant> variants;

    public BlockVariantsGroup(String name, JSONObject source) {

        this.name     = name;
        this.variants = new ArrayList<>();
        source.keySet().forEach(key -> this.variants.add(new BlockVariant(key, source.getJSONObject(key))));
    }

    public String getName() {

        return this.name;
    }

    public List<BlockVariant> getVariants() {

        return this.variants;
    }

}
