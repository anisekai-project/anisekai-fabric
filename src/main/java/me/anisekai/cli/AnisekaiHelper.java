package me.anisekai.cli;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;

@SuppressWarnings("SpellCheckingInspection")
public final class AnisekaiHelper {

    private AnisekaiHelper() {}

    private static String map(String identifier, CharSequence wood, String content) {

        return content.replace(String.format("##%s##", identifier), wood);
    }

    private static String mapWood(String wood, String content) {

        return map("WOOD", wood, content);
    }

    private static String mapLog(String wood, String content) {

        Map<String, String> overrides = new HashMap<>();
        overrides.put("bamboo", "bamboo_block");
        overrides.put("crimson", "crimson_stem");
        overrides.put("warped", "warped_stem");

        return map("LOG", overrides.getOrDefault(wood, String.format("%s_log", wood)), content);
    }

    private static String mapPlanks(String wood, String content) {

        return map("PLKS", String.format("%s_planks", wood), content);
    }

    private static String mapSlabs(String wood, String content) {

        return map("SLAB", String.format("%s_slab", wood), content);
    }

    private static String mapStairs(String wood, String content) {

        return map("STRS", String.format("%s_stairs", wood), content);
    }

    private static String mapStrippedLog(String wood, String content) {

        Map<String, String> overrides = new HashMap<>();
        overrides.put("bamboo", "stripped_bamboo_block");
        overrides.put("crimson", "stripped_crimson_stem");
        overrides.put("warped", "stripped_warped_stem");
        return map("STPD", overrides.getOrDefault(wood, String.format("stripped_%s_log", wood)), content);
    }

    private static String doMapping(String wood, String content) {

        List<BiFunction<String, String, String>> mappers = Arrays.asList(
                AnisekaiHelper::mapWood,
                AnisekaiHelper::mapLog,
                AnisekaiHelper::mapPlanks,
                AnisekaiHelper::mapSlabs,
                AnisekaiHelper::mapStairs,
                AnisekaiHelper::mapStrippedLog
        );

        String newContent = content;
        for (BiFunction<String, String, String> mapper : mappers) {
            newContent = mapper.apply(wood, newContent);
        }
        return newContent;
    }

    private static void walk(File file, Consumer<File> onFileFound) {

        if (file.isFile()) {
            return;
        }
        for (File content : Objects.requireNonNull(file.listFiles())) {
            if (content.isFile()) {
                onFileFound.accept(content);
            } else if (content.isDirectory()) {
                walk(content, onFileFound);
            }
        }
    }

    public static final List<String> WOODS = Arrays.asList(
            "acacia",
            "bamboo",
            "birch",
            "cherry",
            "crimson",
            "dark_oak",
            "jungle",
            "mangrove",
            "oak",
            "spruce",
            "warped"
    );


    public static void main(String... str) throws Exception {

        File input  = new File("generator-source");
        File output = new File("src/main/resources");

        BiFunction<File, String, File> toOuput = (file, name) -> {
            File   parent = file.getParentFile();
            String abs    = parent.getAbsolutePath();
            String out    = abs.replace(input.getAbsolutePath(), output.getAbsolutePath());
            File   dir    = new File(out);
            dir.mkdirs();
            return new File(dir, name);
        };


        List<File> files = new ArrayList<>();
        walk(input, files::add);


        for (File file : files) {
            String content = Files.readString(file.toPath());

            for (String wood : WOODS) {
                String filename = String.format("%s_%s", wood, file.getName());
                File   apply    = toOuput.apply(file, filename);

                String newContent = doMapping(wood, content);
                System.out.printf("Writing file %s%n", apply.toPath());
                Files.writeString(
                        apply.toPath(),
                        newContent,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING
                );
            }
        }

        System.out.printf("Deployed %s files for %s source files.", files.size() * WOODS.size(), files.size());
    }

}
