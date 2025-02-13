package me.anisekai.devtools.tools;

import me.anisekai.devtools.DevIO;
import me.anisekai.devtools.DevTools;
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
    private static final File FURNITURE = new File(DevTools.DEVTOOLS_RESOURCES, "blocks");
    private static final File ITEMS_DIR = new File(FURNITURE, "items");

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

        File[] blockFiles = Objects.requireNonNull(ITEMS_DIR.listFiles());

        for (File blockFile : blockFiles) {
            String     blockName = blockFile.getName().replace(".json", "");
            JSONObject blockData = DevIO.getFileJson(blockFile);

            JSONObject parts = blockData.getJSONObject("parts");

            for (String partName : parts.keySet()) {
                String     name = this.getPartName(blockName, partName);
                JSONObject part = parts.getJSONObject(partName);

                if (part.has("base")) {
                    JSONObject base     = part.getJSONObject("base");
                    JSONArray  elements = base.getJSONArray("elements");
                    Shape      shape    = new Shape(name);

                    for (int i = 0; i < elements.length(); i++) {
                        JSONObject element = elements.getJSONObject(i);
                        JSONArray  from    = element.getJSONArray("from");
                        JSONArray  to      = element.getJSONArray("to");

                        shape.add(new Voxel(from, to));
                    }

                    this.shapes.add(shape);
                }
            }
        }
    }

}
