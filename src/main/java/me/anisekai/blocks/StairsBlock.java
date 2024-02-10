package me.anisekai.blocks;

import me.anisekai.blocks.composed.OrientableBlock;
import me.anisekai.utils.VoxelUtils;
import net.minecraft.block.Block;
import net.minecraft.block.Waterloggable;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.Arrays;

public class StairsBlock extends OrientableBlock implements Waterloggable {

    // <editor-fold desc="Voxels">

    public static final VoxelShape VOXEL_NORTH = VoxelUtils.make(Arrays.asList(
            VoxelShapes.cuboid(0.0625, 0.0625, 0, 0.9375, 0.125, 0.25),
            VoxelShapes.cuboid(0.0625, 0.5625, 0.5, 0.9375, 0.625, 0.75),
            VoxelShapes.cuboid(0.0625, 0.3125, 0.25, 0.9375, 0.375, 0.5),
            VoxelShapes.cuboid(0.0625, 0.8125, 0.75, 0.9375, 0.875, 1),
            VoxelShapes.cuboid(0.9375, 0, 0, 1, 0.25, 0.25),
            VoxelShapes.cuboid(0.9375, 0.25, 0.25, 1, 0.5, 0.5),
            VoxelShapes.cuboid(0.9375, 0.75, 0.75, 1, 1, 1),
            VoxelShapes.cuboid(0.9375, 0.5, 0.5, 1, 0.75, 0.75),
            VoxelShapes.cuboid(0, 0, 0, 0.0625, 0.25, 0.25),
            VoxelShapes.cuboid(0, 0.25, 0.25, 0.0625, 0.5, 0.5),
            VoxelShapes.cuboid(0, 0.75, 0.75, 0.0625, 1, 1),
            VoxelShapes.cuboid(0, 0.5, 0.5, 0.0625, 0.75, 0.75)
    ));

    public static final VoxelShape VOXEL_EAST = VoxelUtils.make(Arrays.asList(
            VoxelShapes.cuboid(0.75, 0.0625, 0.0625, 1, 0.125, 0.9375),
            VoxelShapes.cuboid(0.25, 0.5625, 0.0625, 0.5, 0.625, 0.9375),
            VoxelShapes.cuboid(0.5, 0.3125, 0.0625, 0.75, 0.375, 0.9375),
            VoxelShapes.cuboid(0, 0.8125, 0.0625, 0.25, 0.875, 0.9375),
            VoxelShapes.cuboid(0.75, 0, 0.9375, 1, 0.25, 1),
            VoxelShapes.cuboid(0.5, 0.25, 0.9375, 0.75, 0.5, 1),
            VoxelShapes.cuboid(0, 0.75, 0.9375, 0.25, 1, 1),
            VoxelShapes.cuboid(0.25, 0.5, 0.9375, 0.5, 0.75, 1),
            VoxelShapes.cuboid(0.75, 0, 0, 1, 0.25, 0.0625),
            VoxelShapes.cuboid(0.5, 0.25, 0, 0.75, 0.5, 0.0625),
            VoxelShapes.cuboid(0, 0.75, 0, 0.25, 1, 0.0625),
            VoxelShapes.cuboid(0.25, 0.5, 0, 0.5, 0.75, 0.0625)
    ));

    public static final VoxelShape VOXEL_SOUTH = VoxelUtils.make(Arrays.asList(
            VoxelShapes.cuboid(0.0625, 0.0625, 0.75, 0.9375, 0.125, 1),
            VoxelShapes.cuboid(0.0625, 0.5625, 0.25, 0.9375, 0.625, 0.5),
            VoxelShapes.cuboid(0.0625, 0.3125, 0.5, 0.9375, 0.375, 0.75),
            VoxelShapes.cuboid(0.0625, 0.8125, 0, 0.9375, 0.875, 0.25),
            VoxelShapes.cuboid(0, 0, 0.75, 0.0625, 0.25, 1),
            VoxelShapes.cuboid(0, 0.25, 0.5, 0.0625, 0.5, 0.75),
            VoxelShapes.cuboid(0, 0.75, 0, 0.0625, 1, 0.25),
            VoxelShapes.cuboid(0, 0.5, 0.25, 0.0625, 0.75, 0.5),
            VoxelShapes.cuboid(0.9375, 0, 0.75, 1, 0.25, 1),
            VoxelShapes.cuboid(0.9375, 0.25, 0.5, 1, 0.5, 0.75),
            VoxelShapes.cuboid(0.9375, 0.75, 0, 1, 1, 0.25),
            VoxelShapes.cuboid(0.9375, 0.5, 0.25, 1, 0.75, 0.5)
    ));

    public static final VoxelShape VOXEL_WEST = VoxelUtils.make(Arrays.asList(
            VoxelShapes.cuboid(0, 0.0625, 0.0625, 0.25, 0.125, 0.9375),
            VoxelShapes.cuboid(0.5, 0.5625, 0.0625, 0.75, 0.625, 0.9375),
            VoxelShapes.cuboid(0.25, 0.3125, 0.0625, 0.5, 0.375, 0.9375),
            VoxelShapes.cuboid(0.75, 0.8125, 0.0625, 1, 0.875, 0.9375),
            VoxelShapes.cuboid(0, 0, 0, 0.25, 0.25, 0.0625),
            VoxelShapes.cuboid(0.25, 0.25, 0, 0.5, 0.5, 0.0625),
            VoxelShapes.cuboid(0.75, 0.75, 0, 1, 1, 0.0625),
            VoxelShapes.cuboid(0.5, 0.5, 0, 0.75, 0.75, 0.0625),
            VoxelShapes.cuboid(0, 0, 0.9375, 0.25, 0.25, 1),
            VoxelShapes.cuboid(0.25, 0.25, 0.9375, 0.5, 0.5, 1),
            VoxelShapes.cuboid(0.75, 0.75, 0.9375, 1, 1, 1),
            VoxelShapes.cuboid(0.5, 0.5, 0.9375, 0.75, 0.75, 1)
    ));
    // </editor-fold>

    public StairsBlock(Block block) {

        super(block, VOXEL_NORTH, VOXEL_EAST, VOXEL_SOUTH, VOXEL_WEST);
    }

}
