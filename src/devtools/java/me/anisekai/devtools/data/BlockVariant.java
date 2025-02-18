package me.anisekai.devtools.data;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BlockVariant {

    private final String              name;
    private final Map<String, String> aliases;

    public BlockVariant(String name, JSONObject source) {

        this.name    = name;
        this.aliases = new HashMap<>();
        source.keySet().forEach(key -> this.aliases.put(key, source.getString(key)));
    }

    public String getName() {

        return this.name;
    }

    public String getAlias(String key) {
        if (!this.aliases.containsKey(key)) {
            throw new IllegalArgumentException("Unknown alias " + key + " in variant " + this.name);
        }
        return this.aliases.get(key);
    }

    public Set<String> getAliases() {
        return this.aliases.keySet();
    }
}
