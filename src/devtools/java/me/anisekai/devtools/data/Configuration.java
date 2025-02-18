package me.anisekai.devtools.data;

import me.anisekai.devtools.DevIO;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Configuration {

    private final Map<String, BlockVariantsGroup> groups = new HashMap<>();

    public Configuration(File config) throws IOException {

        JSONObject data = DevIO.getFileJson(config);

        JSONObject groups = data.getJSONObject("variants");
        groups.keySet().forEach(variant -> this.groups.put(variant, new BlockVariantsGroup(variant, groups.getJSONObject(variant))));
    }

    public BlockVariantsGroup getVariantsGroup(String variant) {
        if (!this.groups.containsKey(variant)) {
            throw new IllegalArgumentException("Unknown variant " + variant);
        }
        return this.groups.get(variant);
    }

}
