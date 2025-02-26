package me.anisekai.devtools.tools;

import me.anisekai.devtools.DevIO;
import me.anisekai.devtools.DevTools;
import me.anisekai.devtools.Logger;
import me.anisekai.devtools.tools.helpers.Shape;
import me.anisekai.devtools.tools.helpers.Voxel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HitboxesGenerator {


    private static final File TOOL_DIR  = new File(DevTools.DEVTOOLS_RESOURCES, "hitboxes");
    private static final File ITEMS_DIR = new File(DevTools.DEVTOOLS_RESOURCES, "minecraft");

    private final List<Shape> shapes;

    public HitboxesGenerator() throws IOException {

        JSONObject fabricMod = DevIO.getFileJson(DevTools.FABRIC_MOD_INFO);
        this.shapes = new ArrayList<>();

        this.readShapes();

        String entryPoint = fabricMod
                .getJSONObject("entrypoints")
                .getJSONArray("main")
                .getString(0);

        String rootPackage   = entryPoint.substring(0, entryPoint.lastIndexOf('.')) + ".utils";
        String packageAsPath = rootPackage.replace('.', '/');
        File   packageFile   = new File(DevTools.MAIN_JAVA, packageAsPath);
        File   inputFile     = new File(TOOL_DIR, "OrientableShape.java");
        File   outputFile    = new File(packageFile, "OrientableShape.java");
        String shapes = this.shapes.stream()
                                   .sorted()
                                   .map(Shape::toString)
                                   .collect(Collectors.joining(";\n\n")) + ";";

        String classContent = DevIO.getFileContent(inputFile);
        classContent = classContent.replace("{{package}}", rootPackage);
        classContent = classContent.replace("{{shapes}}", shapes);
        classContent = classContent.replace("{{main}}", entryPoint);

        DevIO.setFileContent(outputFile, classContent);
    }


    private String getPartName(String name, String part) {

        return part.isEmpty() ? name : String.format("%s_%s", name, part);
    }

    private void readShapes() throws IOException {

        File[] contentFile = Objects.requireNonNull(ITEMS_DIR.listFiles());

        for (File sourceFile : contentFile) {
            Logger.logn("Reading %s", sourceFile.getName());

            String     contentName  = sourceFile.getName().replace(".json", "");
            JSONArray  contentArray = DevIO.getFileArray(sourceFile);
            JSONObject contentData  = contentArray.getJSONObject(0);

            JSONObject contentElements = contentData.getJSONObject("elements");

            if (!contentElements.has("models")) {
                Logger.log("Skipping %s (no model)", sourceFile.getName());
                Logger.unnest();
                continue;
            }

            JSONObject models = contentElements.getJSONObject("models");

            for (String modelName : models.keySet()) {
                String     name = this.getPartName(contentName, modelName);
                JSONObject part = models.getJSONObject(modelName);

                if (part.has("base")) {
                    JSONObject base     = part.getJSONObject("base");
                    JSONArray  elements = base.getJSONArray("elements");
                    Shape      shape    = new Shape(name);

                    for (int i = 0; i < elements.length(); i++) {
                        JSONObject element = elements.getJSONObject(i);
                        JSONArray  from    = element.getJSONArray("from");
                        JSONArray  to      = element.getJSONArray("to");
                        Voxel      v       = new Voxel(from, to);

                        Logger.log(v.toString());
                        shape.add(v);
                    }

                    this.shapes.add(shape);
                }
            }
            Logger.unnest();
        }
    }

}
