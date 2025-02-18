package me.anisekai.devtools;

import me.anisekai.devtools.data.BlockVariant;
import me.anisekai.devtools.data.Configuration;
import me.anisekai.devtools.tools.DataGenerator;
import me.anisekai.devtools.tools.HitboxesGenerator;
import me.anisekai.devtools.tools.RecipeGenerator;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public final class DevTools {

    public static final File SRC = new File("src");

    public static final File MAIN     = new File(SRC, "main");
    public static final File DEVTOOLS = new File(SRC, "devtools");

    public static final File MAIN_JAVA = new File(MAIN, "java");

    public static final File MAIN_RESOURCES     = new File(MAIN, "resources");
    public static final File DEVTOOLS_RESOURCES = new File(DEVTOOLS, "resources");

    public static final File FABRIC_MOD_INFO = new File(MAIN_RESOURCES, "fabric.mod.json");
    public static final File STATIC_CONTENT  = new File(DEVTOOLS_RESOURCES, "static");

    private DevTools() {}

    private final static Map<String, JSONObject> TAGS   = new HashMap<>();
    public static        String                  MOD_ID = null;

    public static void main(String... args) throws IOException {

        MOD_ID = DevIO.getFileJson(FABRIC_MOD_INFO).getString("id");

        File configFile = new File(DEVTOOLS_RESOURCES, "config.json");
        if (!configFile.exists()) {
            throw new IllegalStateException("config.json does not exist");
        }

        Configuration configuration = new Configuration(configFile);

        Logger.logn("Generating block and items...");
        new DataGenerator(configuration);
        Logger.unnest();

        Logger.logn("Writing tags...");
        for (String path : TAGS.keySet()) {
            Logger.log(path);
            File file = new File(DevTools.MAIN_RESOURCES, path);
            DevIO.setFileContent(file, TAGS.get(path).toString(2));
        }
        Logger.unnest();

        Logger.logn("Writing recipes...");
        new RecipeGenerator(configuration);
        Logger.unnest();

        Logger.logn("Generating hitboxes...");
        new HitboxesGenerator();
        Logger.unnest();

        Logger.logn("Copying static files...");
        Path src  = STATIC_CONTENT.toPath();
        Path dest = MAIN_RESOURCES.toPath();
        copyFolder(src, dest, StandardCopyOption.REPLACE_EXISTING);
        Logger.unnest();
    }

    private static void copyFolder(Path source, Path target, CopyOption... options) throws IOException {

        Files.walkFileTree(
                source,
                new SimpleFileVisitor<>() {

                    @Override
                    public @NotNull FileVisitResult preVisitDirectory(Path dir, @NotNull BasicFileAttributes attrs) throws IOException {

                        Files.createDirectories(target.resolve(source.relativize(dir).toString()));
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public @NotNull FileVisitResult visitFile(Path file, @NotNull BasicFileAttributes attrs) throws IOException {

                        Logger.log("Copying %s", source.relativize(file).toString());
                        Files.copy(file, target.resolve(source.relativize(file).toString()), options);
                        return FileVisitResult.CONTINUE;
                    }
                }
        );
    }

    public static void tag(String namespace, String tag, String id) {

        String tagPath = DevPaths.tag(namespace, tag);

        if (!TAGS.containsKey(tagPath)) {
            JSONObject data = new JSONObject();
            data.put("replace", false);
            data.put("values", new JSONArray());
            TAGS.put(tagPath, data);
        }

        TAGS.get(tagPath).getJSONArray("values").put(id);
    }

    public static Map<String, String> getTemplateReplacement(String block) {

        Map<String, String> replacements = new HashMap<>();
        replacements.put("mod", MOD_ID);
        replacements.put("name", block);
        replacements.put("item", block);
        replacements.put("id", String.format("%s:%s", MOD_ID, block));
        return replacements;
    }

    public static Map<String, String> getVariantTemplateReplacement(BlockVariant variant, String block) {

        String blockVariant = String.format("%s_%s", variant.getName(), block);

        Map<String, String> replacements = getTemplateReplacement(block);
        replacements.put("item", blockVariant);
        replacements.put("type", variant.getName());
        replacements.put("id", String.format("%s:%s", MOD_ID, blockVariant));
        variant.getAliases().forEach(key -> replacements.put(key, variant.getAlias(key)));
        return replacements;
    }

}
