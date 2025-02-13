package me.anisekai.devtools;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class DevIO {

    private static boolean ensureParentExists(File file) {

        File parent = file.getParentFile();
        if (parent.exists()) {
            return true;
        }
        return parent.mkdirs();
    }

    public static String getFileContent(String path) throws IOException {

        return getFileContent(new File(path));
    }

    public static String getFileContent(File file) throws IOException {

        return Files.readString(file.toPath());
    }

    public static JSONObject getFileJson(String path) throws IOException {

        return getFileJson(new File(path));
    }

    public static JSONObject getFileJson(File file) throws IOException {

        return new JSONObject(getFileContent(file));
    }

    public static void setFileContent(String path, CharSequence content) throws IOException {

        setFileContent(new File(path), content);
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
