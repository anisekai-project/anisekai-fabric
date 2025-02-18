package me.anisekai.devtools.tools;

import me.anisekai.devtools.DevIO;
import me.anisekai.devtools.DevPaths;
import me.anisekai.devtools.DevTools;
import me.anisekai.devtools.Logger;
import me.anisekai.devtools.data.BlockVariant;
import me.anisekai.devtools.data.BlockVariantsGroup;
import me.anisekai.devtools.data.Configuration;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

public final class DataGenerator {

    private static final File RESOURCES = new File(DevTools.DEVTOOLS_RESOURCES, "minecraft");

    private final Map<String, JSONObject> tagging = new HashMap<>();

    public DataGenerator(Configuration configuration) throws IOException {

        File[] contentFile = Objects.requireNonNull(RESOURCES.listFiles());

        for (File sourceFile : contentFile) {
            Logger.logn("Reading %s", sourceFile.getName());

            String     content     = sourceFile.getName().replace(".json", "");
            JSONObject contentData = DevIO.getFileJson(sourceFile);

            JSONObject settings = contentData.getJSONObject("settings");
            JSONObject elements = contentData.getJSONObject("elements");

            if (elements.has("models")) {
                Logger.logn("Generating base block");
                JSONObject models = elements.getJSONObject("models");

                for (String modelName : models.keySet()) {
                    JSONObject part = models.getJSONObject(modelName);

                    if (part.has("base")) {
                        String     name = this.getBlockPartName(content, modelName);
                        String     path = DevPaths.baseBlock(DevTools.MOD_ID, name);
                        JSONObject json = part.getJSONObject("base");
                        setFileContent(path, json);
                    }
                }
                Logger.unnest();
            }

            if (!settings.has("useVariant")) {
                Logger.logn("Generating content");
                Map<String, String> replacements  = DevTools.getTemplateReplacement(content);
                String              id            = replacements.get("id");
                String              item          = replacements.get("item");
                JSONObject          outputElement = this.doTemplating(elements, replacements);

                this.writeBlock(id, item, outputElement);
                Logger.unnest();
            } else {
                Logger.logn("Generating variants");
                String             useVariantGroup = settings.getString("useVariant");
                BlockVariantsGroup variantGroup    = configuration.getVariantsGroup(useVariantGroup);

                for (BlockVariant variant : variantGroup.getVariants()) {
                    Logger.logn("Generating content for variant %s", variant.getName());
                    Map<String, String> replacements    = DevTools.getVariantTemplateReplacement(variant, content);
                    String              id              = replacements.get("id");
                    String              item            = replacements.get("item");
                    JSONObject          variantElements = this.doTemplating(elements, replacements);
                    this.writeBlock(id, item, variantElements);
                    Logger.unnest();
                }
                Logger.unnest();
            }

            Logger.unnest();
        }
    }

    private void writeBlock(String id, String item, JSONObject elements) throws IOException {

        if (elements.has("models")) {
            JSONObject models = elements.getJSONObject("models");

            for (String modelName : models.keySet()) {
                JSONObject model = models.getJSONObject(modelName);
                String     name  = this.getBlockPartName(item, modelName);
                this.optionalJsonWrite(model, "block", name, DevPaths::block);
            }
        }

        this.optionalJsonWrite(elements, "item", item, DevPaths::item);
        this.optionalJsonWrite(elements, "blockstates", item, DevPaths::blockstates);
        this.optionalJsonWrite(elements, "loot_table", item, DevPaths::lootTable);
        this.optionalJsonWrite(elements, "recipe", item, (mod, name) -> DevPaths.recipe(mod, "blocks", name));

        if (elements.has("tags")) {
            JSONArray tags = elements.getJSONArray("tags");

            for (int i = 0; i < tags.length(); i++) {
                JSONObject tag = tags.getJSONObject(i);
                DevTools.tag(tag.getString("namespace"), tag.getString("value"), id);
            }
        }
    }

    private String getBlockPartName(String name, String part) {

        return part.isEmpty() ? name : String.format("%s_%s", name, part);
    }

    private void optionalJsonWrite(JSONObject json, String key, String name, BiFunction<String, String, String> pathBuilder) throws IOException {

        if (json.has(key)) {
            String     path = pathBuilder.apply(DevTools.MOD_ID, name);
            JSONObject data = json.getJSONObject(key);
            setFileContent(path, data);
        }
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

        Logger.log(path);
        File file = new File(DevTools.MAIN_RESOURCES, path);
        DevIO.setFileContent(file, data.toString(2));
    }

}
