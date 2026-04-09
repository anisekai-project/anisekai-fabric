package me.anisekai.devtools;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DevIO {

    private static final Pattern TEMPLATE_PATTERN = Pattern.compile("\\{\\{([a-z]+)}}");

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

    public static void setFileContent(File file, CharSequence content) throws IOException {

        if (!ensureParentExists(file)) {
            return;
        }

        // Safety check: Ensure every template texts has been replaced to avoid any errors at runtime.
        Matcher matcher = TEMPLATE_PATTERN.matcher(content);
        if (matcher.find()) {
            String unmapped = matcher.group();
            throw new RuntimeException(String.format(
                    "Found unmapped key %s for file %s\n%s\n\n",
                    unmapped,
                    file.getCanonicalPath(),
                    content
            ));
        }

        Files.writeString(
                file.toPath(),
                content,
                StandardOpenOption.WRITE,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        );
    }

    public static void setFileJson(File file, JSONObject json) throws IOException {

        setFileContent(file, json.toString(2));
    }

}
