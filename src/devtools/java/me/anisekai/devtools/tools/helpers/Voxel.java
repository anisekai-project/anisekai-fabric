package me.anisekai.devtools.tools.helpers;

import org.json.JSONArray;

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

}
