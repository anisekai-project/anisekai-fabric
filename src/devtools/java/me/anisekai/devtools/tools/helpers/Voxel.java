package me.anisekai.devtools.tools.helpers;

import org.json.JSONArray;

public class Voxel {

    private static final double RESOLUTION = 16;

    private final double minX;
    private final double minY;
    private final double minZ;
    private final double maxX;
    private final double maxY;
    private final double maxZ;

    public Voxel(JSONArray from, JSONArray to) {

        this.minX = from.getDouble(0) / RESOLUTION;
        this.minY = from.getDouble(1) / RESOLUTION;
        this.minZ = from.getDouble(2) / RESOLUTION;
        this.maxX = to.getDouble(0) / RESOLUTION;
        this.maxY = to.getDouble(1) / RESOLUTION;
        this.maxZ = to.getDouble(2) / RESOLUTION;
    }

    @Override
    public String toString() {

        return String.format(
                "VoxelShapes.cuboid(%s, %s, %s, %s, %s, %s)",
                this.minX,
                this.minY,
                this.minZ,
                this.maxX,
                this.maxY,
                this.maxZ
        );
    }

}
