package me.anisekai.devtools.tools;

import org.json.JSONArray;
import org.json.JSONObject;

public class Voxel {

    private static final double RESOLUTION = 16;

    public final double minX;
    public final double minY;
    public final double minZ;
    public final double maxX;
    public final double maxY;
    public final double maxZ;

    public Voxel(JSONArray from, JSONArray to) {

        this.minX = from.getDouble(0) / RESOLUTION;
        this.minY = from.getDouble(1) / RESOLUTION;
        this.minZ = from.getDouble(2) / RESOLUTION;
        this.maxX = to.getDouble(0) / RESOLUTION;
        this.maxY = to.getDouble(1) / RESOLUTION;
        this.maxZ = to.getDouble(2) / RESOLUTION;
    }

    public JSONObject toJSON() {

        JSONObject json = new JSONObject();
        json.put("minX", this.minX);
        json.put("minY", this.minY);
        json.put("minZ", this.minZ);
        json.put("maxX", this.maxX);
        json.put("maxY", this.maxY);
        json.put("maxZ", this.maxZ);
        return json;
    }

}
