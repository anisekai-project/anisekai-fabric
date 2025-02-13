package me.anisekai.devtools;

import me.anisekai.devtools.tools.BlockGenerator;
import me.anisekai.devtools.tools.HitboxesGenerator;

import java.io.File;
import java.io.IOException;

public final class DevTools {

    public static final File SRC = new File("src");

    public static final File MAIN     = new File(SRC, "main");
    public static final File DEVTOOLS = new File(SRC, "devtools");

    public static final File MAIN_JAVA     = new File(MAIN, "java");

    public static final File MAIN_RESOURCES     = new File(MAIN, "resources");
    public static final File DEVTOOLS_RESOURCES = new File(DEVTOOLS, "resources");

    public static final File FABRIC_MOD_INFO = new File(MAIN_RESOURCES, "fabric.mod.json");

    private DevTools() {}

    public static void main(String... args) throws IOException {

        if (args.length == 0) {
            return;
        }

        switch (args[0]) {
            case "blocks":
                new BlockGenerator();
                break;
            case "hitboxes":
                new HitboxesGenerator();
                break;
            default:
                System.err.println("Unknown command: " + args[0]);
        }
    }

    public static String getModId() throws IOException {

        return DevIO.getFileJson(FABRIC_MOD_INFO).getString("id");
    }

}
