package me.anisekai.devtools.tools;

import me.anisekai.devtools.DevIO;
import me.anisekai.devtools.DevPaths;
import me.anisekai.devtools.DevTools;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

@SuppressWarnings("UseOfSystemOutOrSystemErr")
public final class BlockGenerator {

    private static final File RESOURCES = new File(DevTools.DEVTOOLS_RESOURCES, "blocks");

    private static final File CONFIGURATION = new File(RESOURCES, "config.json");
    private static final File ITEMS_DIR     = new File(RESOURCES, "items");

    private final String                           modId;
    private final Map<String, Map<String, String>> mapping = new HashMap<>();
    private final Map<String, JSONObject>          tagging = new HashMap<>();

    public BlockGenerator() throws IOException {

        this.modId = DevTools.getModId();
        this.loadWoodMapping();

        File[] furnitureFiles = Objects.requireNonNull(ITEMS_DIR.listFiles());

        for (File sourceFile : furnitureFiles) {
            System.out.printf("@ %s%n", sourceFile.getName());

            String     block     = sourceFile.getName().replace(".json", "");
            JSONObject blockData = DevIO.getFileJson(sourceFile);

            JSONObject generator = blockData.getJSONObject("generator");
            JSONObject settings  = blockData.getJSONObject("settings");
            JSONObject parts     = blockData.getJSONObject("parts");

            boolean useWoodVariant = generator.getBoolean("useWoodVariants");

            // Write base block.
            for (String partName : parts.keySet()) {
                JSONObject part = parts.getJSONObject(partName);

                if (part.has("base")) {
                    String     name = this.getBlockPartName(block, partName);
                    String     path = DevPaths.baseBlock(this.modId, name);
                    JSONObject json = part.getJSONObject("base");
                    setFileContent(path, json);
                }
            }

            if (useWoodVariant) {
                for (String wood : this.mapping.keySet()) {
                    System.out.printf(" > Creating %s variant...%n", wood);
                    Map<String, String> replacements = this.getWoodTemplateReplacement(wood, block);
                    this.writeBlock(replacements, settings, parts);
                }
            } else {
                System.out.printf(" > Creating %s ...%n", block);
                this.writeBlock(this.getTemplateReplacement(block), settings, parts);
            }
        }

        System.out.printf("Writing tags...%n");
        for (String path : this.tagging.keySet()) {
            setFileContent(path, this.tagging.get(path));
        }
    }

    private void writeBlock(Map<String, String> replacements, JSONObject settings, JSONObject parts) throws IOException {

        String id   = replacements.get("id");
        String item = replacements.get("item");

        JSONObject outputSettings = this.doTemplating(settings, replacements);
        JSONObject outputParts    = this.doTemplating(parts, replacements);

        for (String partName : outputParts.keySet()) {
            JSONObject part = outputParts.getJSONObject(partName);
            String     name = this.getBlockPartName(item, partName);
            this.optionalJsonWrite(part, "block", name, DevPaths::block);
        }

        this.optionalJsonWrite(outputSettings, "item", item, DevPaths::item);
        this.optionalJsonWrite(outputSettings, "blockstates", item, DevPaths::blockstates);
        this.optionalJsonWrite(outputSettings, "loot_table", item, DevPaths::lootTable);
        this.optionalJsonWrite(outputSettings, "recipe", item, DevPaths::recipe);

        JSONArray tags = outputSettings.getJSONArray("tags");
        for (int i = 0; i < tags.length(); i++) {
            JSONObject tag     = tags.getJSONObject(i);
            String     tagPath = DevPaths.tag(tag.getString("namespace"), tag.getString("value"));
            this.addTaggedItem(tagPath, id);
        }
    }

    private String getBlockPartName(String name, String part) {

        return part.isEmpty() ? name : String.format("%s_%s", name, part);
    }

    private void loadWoodMapping() throws IOException {

        JSONObject config = DevIO.getFileJson(CONFIGURATION);
        JSONObject woods  = config.getJSONObject("wood");

        for (String woodType : woods.keySet()) {
            Map<String, String> woodBlockMapping = new HashMap<>();
            JSONObject          woodConfig       = woods.getJSONObject(woodType);
            for (String woodBlockType : woodConfig.keySet()) {
                woodBlockMapping.put(woodBlockType, woodConfig.getString(woodBlockType));
            }
            this.mapping.put(woodType, woodBlockMapping);
        }
    }

    private void optionalJsonWrite(JSONObject json, String key, String name, BiFunction<String, String, String> pathBuilder) throws IOException {

        if (json.has(key)) {
            String     path = pathBuilder.apply(this.modId, name);
            JSONObject data = json.getJSONObject(key);
            setFileContent(path, data);
        }
    }

    private void addTaggedItem(String tag, String item) {

        if (!this.tagging.containsKey(tag)) {
            JSONObject data = new JSONObject();
            data.put("replace", false);
            data.put("values", new JSONArray());
            this.tagging.put(tag, data);
        }

        this.tagging.get(tag).getJSONArray("values").put(item);
    }

    private Map<String, String> getTemplateReplacement(String block) {

        Map<String, String> replacements = new HashMap<>();
        replacements.put("mod", this.modId);
        replacements.put("name", block);
        replacements.put("item", block);
        replacements.put("id", String.format("%s:%s", this.modId, block));
        return replacements;
    }

    private Map<String, String> getWoodTemplateReplacement(String wood, String block) {

        String variant = String.format("%s_%s", wood, block);

        Map<String, String> replacements = this.getTemplateReplacement(block);
        replacements.put("item", variant);
        replacements.put("type", wood);
        replacements.put("id", String.format("%s:%s", this.modId, variant));
        replacements.putAll(this.mapping.get(wood));
        return replacements;
    }

    private JSONObject doTemplating(JSONObject json, Map<String, String> templating) {

        String content = json.toString();

        for (Map.Entry<String, String> entry : templating.entrySet()) {
            String key   = String.format("{{%s}}", entry.getKey());
            String value = entry.getValue();

            content = content.replace(key, value);
        }

        return new JSONObject(content);
    }

    private static void setFileContent(String path, JSONObject data) throws IOException {

        System.out.printf("    -> Writing file %s%n", path);
        File file = new File(DevTools.MAIN_RESOURCES, path);
        DevIO.setFileContent(file, data.toString(2));
    }

}
