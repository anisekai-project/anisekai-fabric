package me.anisekai.devtools;

public final class DevPaths {

    private DevPaths() {}

    public static String baseBlock(String namespace, String name) {

        return "assets/%s/models/block/base/%s.json".formatted(namespace, name);
    }

    public static String block(String namespace, String name) {

        return "assets/%s/models/block/%s.json".formatted(namespace, name);
    }

    public static String item(String namespace, String name) {

        return "assets/%s/models/item/%s.json".formatted(namespace, name);
    }

    public static String blockstates(String namespace, String name) {

        return "assets/%s/blockstates/%s.json".formatted(namespace, name);
    }

    public static String lootTable(String namespace, String name) {

        return "data/%s/loot_table/blocks/%s.json".formatted(namespace, name);
    }

    public static String recipe(String namespace, String category, String name) {

        return "data/%s/recipe/%s/%s.json".formatted(namespace, category, name);
    }

    public static String tag(String namespace, String name) {

        return "data/%s/tags/%s.json".formatted(namespace, name);
    }

}
