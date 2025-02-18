package me.anisekai.devtools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public final class DevIO {

    private DevIO() {}

    private static boolean ensureParentExists(File file) {

        File parent = file.getParentFile();
        if (parent.exists()) {
            return true;
        }
        return parent.mkdirs();
    }

    public static String getFileContent(File file) throws IOException {

        return Files.readString(file.toPath());
    }

    public static JSONObject getFileJson(File file) throws IOException {

        return new JSONObject(getFileContent(file));
    }

    public static JSONArray getFileArray(File file) throws IOException {
        return new JSONArray(getFileContent(file));
    }

    public static void setFileContent(File file, CharSequence content) throws IOException {

        if (!ensureParentExists(file)) {
            return;
        }

        Files.writeString(
                file.toPath(),
                content,
                StandardOpenOption.WRITE,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        );
    }

}
