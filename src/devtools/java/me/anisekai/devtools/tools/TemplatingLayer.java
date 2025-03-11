package me.anisekai.devtools.tools;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplatingLayer {

    private final List<Map<String, String>> templates;

    public TemplatingLayer() {

        this.templates = new ArrayList<>();
    }

    public void addLayer(Map<String, String> template) {

        Map<String, String> layer = new HashMap<>();

        if (!this.templates.isEmpty()) {
            Map<String, String> last = this.templates.getLast();
            layer.putAll(last);
        }

        layer.putAll(template);

        this.templates.add(layer);
    }

    public void dropLayer() {

        if (!this.templates.isEmpty()) {
            this.templates.removeLast();
        }
    }

    public String doTemplating(String text) {

        if (this.templates.isEmpty()) {
            return text;
        }

        String              output = text;
        Map<String, String> last   = this.templates.getLast();
        for (String key : last.keySet()) {
            String template = String.format("{{%s}}", key);
            String value    = last.get(key);

            output = output.replace(template, value);
        }
        return output;
    }

    public JSONObject doTemplating(JSONObject json) {

        return new JSONObject(this.doTemplating(json.toString()));
    }

    public String getKey(String key) {

        if (this.templates.isEmpty()) {
            return null;
        }
        return this.templates.getLast().get(key);
    }

    public String getKey(String... keys) {

        if (this.templates.isEmpty()) {
            return null;
        }

        Map<String, String> last = this.templates.getLast();

        for (String key : keys) {
            if (last.containsKey(key)) {
                return last.get(key);
            }
        }
        return null;
    }

}
