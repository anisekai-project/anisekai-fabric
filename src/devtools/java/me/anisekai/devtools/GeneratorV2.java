package me.anisekai.devtools;

import me.anisekai.devtools.data.BlockVariant;
import me.anisekai.devtools.data.Configuration;
import me.anisekai.devtools.data.GeneratorSettings;
import me.anisekai.devtools.tools.Shape;
import me.anisekai.devtools.tools.TemplatingLayer;
import me.anisekai.devtools.tools.Voxel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class GeneratorV2 {

    private static final File RESOURCES = new File(DevTools.DEVTOOLS_RESOURCES, "generator");

    private final Configuration   configuration;
    private final TemplatingLayer templatingLayer;
    private final List<File>      files;
    private final String          namespace;

    private final Map<String, JSONObject> outputs;
    private final List<Shape>             shapes;

    public GeneratorV2(Configuration configuration) throws IOException {

        this(configuration, DevTools.MOD_ID);
    }

    public GeneratorV2(Configuration configuration, String namespace) throws IOException {

        this.configuration   = configuration;
        this.templatingLayer = new TemplatingLayer();
        this.namespace       = namespace;

        this.outputs = new TreeMap<>();
        this.shapes  = new ArrayList<>();

        this.templatingLayer.addLayer(new HashMap<>() {{
            this.put("namespace", namespace);
        }});

        try (Stream<Path> stream = Files.list(RESOURCES.toPath())) {
            this.files = stream.map(Path::toFile)
                               .filter(file -> file.getName().endsWith(".json"))
                               .toList();
        }
    }

    private void tag(String namespace, String tag, String id) {

        String tagPath = "data/%s/tags/%s.json".formatted(namespace, tag);

        if (!this.outputs.containsKey(tagPath)) {
            JSONObject data = new JSONObject();
            data.put("replace", false);
            data.put("values", new JSONArray());
            this.outputs.put(tagPath, data);
        }

        this.outputs.get(tagPath).getJSONArray("values").put(id);
    }

    public void generate() throws IOException {

        for (File file : this.files) {
            Logger.log("From %s", file.getName());
            String filename = file.getName().replace(".json", "");
            String content  = DevIO.getFileContent(file);

            this.templatingLayer.addLayer(new HashMap<>() {{
                this.put("name", filename);
                this.put("id", String.format("%s:%s", GeneratorV2.this.namespace, filename));
            }});

            String     replaced = this.templatingLayer.doTemplating(content);
            JSONObject source   = new JSONObject(replaced);

            this.handleHitboxes(filename, source);
            this.handleTags(source);
            this.handleFiles(source);
            this.handleDynamic(source);

            this.templatingLayer.dropLayer();
        }
    }

    private void handleTags(JSONObject source) {

        if (!source.has("tags")) {
            return;
        }

        JSONArray tags = source.getJSONArray("tags");

        for (int i = 0; i < tags.length(); i++) {
            JSONObject tag       = tags.getJSONObject(i);
            String     namespace = tag.getString("namespace");
            String     value     = tag.getString("value");

            String name = this.templatingLayer.getKey("item", "name");
            String id   = String.format("%s:%s", this.namespace, name);

            this.tag(namespace, value, id);
        }
    }

    private void handleHitboxes(String name, JSONObject source) {

        if (!source.has("hitboxes")) {
            return;
        }

        if (!source.has("files")) {
            throw new IllegalStateException("Cannot extract hitboxes without files declaration");
        }

        JSONObject hitboxes = source.getJSONObject("hitboxes");
        JSONObject files    = source.getJSONObject("files");

        for (String path : hitboxes.keySet()) {
            String alias = hitboxes.getString(path);

            if (!files.has(path)) {
                throw new IllegalStateException(String.format(
                        "Missing static declaration file '%s' for hitbox alias of '%s' in file '%s'.",
                        path,
                        alias,
                        name
                ));
            }

            JSONObject declaration = files.getJSONObject(path);
            JSONArray  elements    = declaration.getJSONArray("elements");

            Shape shape = new Shape(alias);
            for (int i = 0; i < elements.length(); i++) {
                JSONObject element = elements.getJSONObject(i);
                JSONArray  from    = element.getJSONArray("from");
                JSONArray  to      = element.getJSONArray("to");

                Voxel voxel = new Voxel(from, to);
                shape.add(voxel);
            }
            this.shapes.add(shape);
        }
    }

    private void handleDynamic(JSONObject source) {

        if (!source.has("dynamic")) {
            return;
        }

        JSONObject dynamic = source.getJSONObject("dynamic");

        for (String generationName : dynamic.keySet()) {
            JSONObject generationData     = dynamic.getJSONObject(generationName);
            JSONObject generationSettings = generationData.getJSONObject("settings");

            GeneratorSettings settings = new GeneratorSettings(this.configuration, generationSettings);

            if (settings.shouldSkipGeneration()) {
                continue;
            }

            if (!settings.isUsingVariants()) {
                throw new IllegalStateException("Cannot generate dynamic variants without variants setup");
            }

            JSONObject dataFiles = new JSONObject();
            JSONArray  dataTags  = new JSONArray();

            this.doExtendsProcessing(settings, dynamic, dataFiles, dataTags);
            this.copyFileDeclarations(generationData, dataFiles);
            this.copyTagDeclarations(generationData, dataTags);

            for (BlockVariant variant : settings.getVariants().keySet()) {
                String blockVariant = String.format("%s_%s", variant.getName(), this.templatingLayer.getKey("name"));

                this.templatingLayer.addLayer(new HashMap<>() {{
                    this.put("item", blockVariant);
                    this.put("type", variant.getName());
                    this.put("id", String.format("%s:%s", GeneratorV2.this.namespace, blockVariant));
                    variant.getAliases().forEach(key -> this.put(key, variant.getAlias(key)));
                }});

                JSONObject maps = this.templatingLayer.doTemplating(settings.getVariants().get(variant));

                this.templatingLayer.addLayer(new HashMap<>() {{
                    maps.keySet().forEach(key -> this.put(key, maps.getString(key)));
                }});

                JSONObject variantData = this.templatingLayer.doTemplating(dataFiles);

                JSONObject data = new JSONObject();
                data.put("files", variantData);
                data.put("tags", dataTags);

                this.handleFiles(data);
                this.handleTags(data);

                this.templatingLayer.dropLayer();
                this.templatingLayer.dropLayer();
            }
        }
    }

    private void handleFiles(JSONObject source) {

        if (!source.has("files")) {
            return;
        }

        JSONObject files = source.getJSONObject("files");
        for (String path : files.keySet()) {
            this.outputs.put(path, files.getJSONObject(path));
        }
    }

    private void doExtendsProcessing(GeneratorSettings settings, JSONObject dynamic, JSONObject files, JSONArray tags) {

        if (!settings.hasExtendName()) {
            return;
        }

        JSONObject        extendData     = dynamic.getJSONObject(settings.getExtendName());
        GeneratorSettings extendSettings = new GeneratorSettings(
                this.configuration,
                extendData.getJSONObject("settings")
        );

        // Do the extension logic first so that the latest extends overrides correctly.
        if (extendSettings.hasExtendName()) {
            this.doExtendsProcessing(extendSettings, dynamic, files, tags);
        }

        this.copyFileDeclarations(extendData, files);
        this.copyTagDeclarations(extendData, tags);
    }

    private void copyFileDeclarations(JSONObject source, JSONObject target) {

        if (source.has("files")) {
            JSONObject data = source.getJSONObject("files");
            data.keySet().forEach(innerKey -> target.put(innerKey, data.getJSONObject(innerKey)));
        }
    }

    private void copyTagDeclarations(JSONObject source, JSONArray target) {

        if (source.has("tags")) {
            JSONArray data = source.getJSONArray("tags");
            data.forEach(target::put);
        }
    }

    public void save(File outputRoot) throws IOException {

        for (String path : this.outputs.keySet()) {
            File output   = new File(outputRoot, path);
            Path relative = outputRoot.toPath().relativize(output.toPath());
            Logger.log(relative.toString());
            DevIO.setFileJson(output, this.outputs.get(path));
        }

        JSONObject hitboxes = new JSONObject();
        for (Shape shape : this.shapes) {
            JSONArray voxels = new JSONArray();
            shape.getVoxels().stream().map(Voxel::toJSON).forEach(voxels::put);
            hitboxes.put(shape.getName(), voxels);
        }

        File hitboxesFile = new File(outputRoot, "hitboxes.json");
        Path relative     = outputRoot.toPath().relativize(hitboxesFile.toPath());
        Logger.log(relative.toString());
        DevIO.setFileJson(hitboxesFile, hitboxes);
    }

}
