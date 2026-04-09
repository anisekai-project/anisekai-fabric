package me.anisekai.devtools.data;

import me.anisekai.devtools.DevIO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Configuration {

    private final List<BlockVariantsGroup> variantsGroups = new ArrayList<>();

    public Configuration(File file) throws IOException {

        JSONObject config = DevIO.getFileJson(file);

        JSONArray variantGroups = config.getJSONArray("variants");

        for (int i = 0; i < variantGroups.length(); i++) {
            JSONObject group = variantGroups.getJSONObject(i);

            String    groupName = group.getString("name");
            JSONArray values    = group.getJSONArray("values");

            this.variantsGroups.add(new BlockVariantsGroup(groupName, values));
        }
    }

    public List<BlockVariantsGroup> getVariantsGroups() {

        return this.variantsGroups;
    }

    public BlockVariantsGroup getVariantsGroup(String name) {

        return this.getVariantsGroups()
                   .stream()
                   .filter(g -> g.getName().equals(name))
                   .findFirst()
                   .orElseThrow(() -> new IllegalArgumentException("No such variant: " + name));
    }

}
