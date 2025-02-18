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
import java.util.*;

@SuppressWarnings("UseOfSystemOutOrSystemErr")
public final class RecipeGenerator {

    private static final File RECIPES_DIR = new File(DevTools.DEVTOOLS_RESOURCES, "recipes");

    private final Map<String, JSONObject> tagging = new HashMap<>();

    public RecipeGenerator(Configuration configuration) throws IOException {

        File[] contentFile = Objects.requireNonNull(RECIPES_DIR.listFiles());

        for (File sourceFile : contentFile) {
            Logger.logn("Reading %s", sourceFile.getName());

            String    content     = sourceFile.getName().replace(".json", "");
            JSONArray contentData = DevIO.getFileArray(sourceFile);

            for (int i = 0; i < contentData.length(); i++) {
                Logger.logn("Recipe at index %d", i);
                JSONObject recipe = contentData.getJSONObject(i);

                JSONObject generator = recipe.getJSONObject("generator");
                JSONObject elements  = recipe.getJSONObject("elements");

                if (generator.has("useVariant")) {
                    List<BlockVariant> variants = this.getUsableVariants(configuration, generator);

                    for (BlockVariant variant : variants) {
                        Logger.logn("Creating recipe for variant %s", variant.getName());
                        Map<String, String> replacements    = DevTools.getVariantTemplateReplacement(variant, content);
                        JSONObject          variantElements = this.doTemplating(elements, replacements);
                        this.writeRecipe(content, variantElements);
                        Logger.unnest();
                    }
                } else {
                    Map<String, String> replacements    = DevTools.getTemplateReplacement(content);
                    JSONObject          variantElements = this.doTemplating(elements, replacements);
                    this.writeRecipe(content, variantElements);
                }
                Logger.unnest();
            }
        }
    }

    private List<BlockVariant> getUsableVariants(Configuration configuration, JSONObject generator) {

        BlockVariantsGroup group       = configuration.getVariantsGroup(generator.getString("useVariant"));
        List<String>       excludeList = new ArrayList<>();

        if (generator.has("exclude")) {
            JSONArray exclude = generator.getJSONArray("exclude");
            exclude.forEach(obj -> excludeList.add(obj.toString()));
        }

        return group.getVariants().stream()
                    .filter(variant -> !excludeList.contains(variant.getName()))
                    .toList();
    }

    private void writeRecipe(String category, JSONObject content) throws IOException {

        String name = content.getString("name");
        content.remove("name");
        setFileContent(DevPaths.recipe(DevTools.MOD_ID, category, name), content);
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
