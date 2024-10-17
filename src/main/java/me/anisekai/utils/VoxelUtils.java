package me.anisekai.utils;

import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public final class VoxelUtils {

    private VoxelUtils() {}

    public static VoxelShape make(Iterable<VoxelShape> voxels) {

        VoxelShape shape = VoxelShapes.empty();

        for (VoxelShape voxel : voxels) {
            shape = VoxelShapes.combine(shape, voxel, BooleanBiFunction.OR);
        }

        return shape;
    }

}
