package me.anisekai.devtools;

import me.anisekai.devtools.data.Configuration;
import org.jetbrains.annotations.NotNull;
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
        GeneratorV2   generator     = new GeneratorV2(configuration);


        Logger.logn("Generating block and items...");
        generator.generate();
        Logger.unnest();

        Logger.logn("Writing files...");
        generator.save(MAIN_RESOURCES);
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

}
