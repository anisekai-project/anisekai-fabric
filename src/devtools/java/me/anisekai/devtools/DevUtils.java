package me.anisekai.devtools;

import me.anisekai.devtools.tools.Voxel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public final class DevUtils {

    private DevUtils() {}

    public static <T> List<T> readArray(JSONArray array, BiFunction<JSONArray, Integer, T> function) {

        List<T> result = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            result.add(function.apply(array, i));
        }
        return result;
    }

    public static <T> List<T> optReadArray(JSONObject obj, String key, BiFunction<JSONArray, Integer, T> function) {

        if (obj.has(key)) {
            return readArray(obj.getJSONArray(key), function);
        } else {
            return Collections.emptyList();
        }
    }

    public static class Java {

        public static final String TAB = "    ";

        public static String put(String key, String name) {

            return String.format(
                    "%sthis.put(\n%s%s, %s\n%s)",
                    TAB.repeat(2),
                    TAB.repeat(4),
                    key,
                    name,
                    TAB.repeat(2)
            );
        }

        public static String id(String name) {

            return String.format("Identifier.of(MOD_ID, \"%s\")", name);
        }

        public static String makeShape(String collection) {
            return String.format("OrientableShape.makeShape(%s)", collection);
        }

        public static String emptyList() {

            return "Collections.emptyList()";
        }

        public static String singletonList(Voxel voxel) {

            return String.format("Collections.singletonList(\n%s\n%s)", voxel(voxel), TAB.repeat(4));
        }

        public static String listOf(List<Voxel> voxels) {
            String voxelString = voxels.stream().map(Java::voxel).collect(Collectors.joining(",\n"));
            return String.format("Arrays.asList(\n%s\n%s)", voxelString, TAB.repeat(4));
        }

        public static String voxel(Voxel voxel) {

            return String.format(
                    "%sVoxelShapes.cuboid(%s, %s, %s, %s, %s, %s)",
                    TAB.repeat(6),
                    voxel.minX,
                    voxel.minY,
                    voxel.minZ,
                    voxel.maxX,
                    voxel.maxY,
                    voxel.maxZ
            );
        }

    }


}
