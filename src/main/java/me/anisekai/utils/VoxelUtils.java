package me.anisekai.utils;

import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.Collection;

public class VoxelUtils {

    public static VoxelShape make(Collection<VoxelShape> voxels) {

        VoxelShape shape = VoxelShapes.empty();

        for (VoxelShape voxel : voxels) {
            shape = VoxelShapes.combine(shape, voxel, BooleanBiFunction.OR);
        }

        return shape;
    }

}
