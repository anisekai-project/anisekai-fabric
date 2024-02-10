package me.anisekai.blocks;

import me.anisekai.blocks.composed.SeatBlock;
import me.anisekai.interfaces.Seatable;
import me.anisekai.utils.VoxelUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.Arrays;

public class ChairBlock extends SeatBlock implements Waterloggable, Seatable {

    // <editor-fold desc="Voxels">

    private static final VoxelShape VOXEL_NORTH = VoxelUtils.make(Arrays.asList(
            VoxelShapes.cuboid(0.125, 0, 0.125, 0.25, 0.5, 0.25),
            VoxelShapes.cuboid(0.75, 0, 0.125, 0.875, 0.5, 0.25),
            VoxelShapes.cuboid(0.125, 0, 0.75, 0.25, 0.5, 0.875),
            VoxelShapes.cuboid(0.75, 0, 0.75, 0.875, 0.5, 0.875),
            VoxelShapes.cuboid(0.125, 0.625, 0.75, 0.25, 1.375, 0.875),
            VoxelShapes.cuboid(0.75, 0.625, 0.75, 0.875, 1.375, 0.875),
            VoxelShapes.cuboid(0.25, 1.0625, 0.75, 0.75, 1.3125, 0.875),
            VoxelShapes.cuboid(0.125, 0.5, 0.125, 0.875, 0.625, 0.875)
    ));

    private static final VoxelShape VOXEL_EAST = VoxelUtils.make(Arrays.asList(
            VoxelShapes.cuboid(0.75, 0, 0.125, 0.875, 0.5, 0.25),
            VoxelShapes.cuboid(0.75, 0, 0.75, 0.875, 0.5, 0.875),
            VoxelShapes.cuboid(0.125, 0, 0.125, 0.25, 0.5, 0.25),
            VoxelShapes.cuboid(0.125, 0, 0.75, 0.25, 0.5, 0.875),
            VoxelShapes.cuboid(0.125, 0.625, 0.125, 0.25, 1.375, 0.25),
            VoxelShapes.cuboid(0.125, 0.625, 0.75, 0.25, 1.375, 0.875),
            VoxelShapes.cuboid(0.125, 1.0625, 0.25, 0.25, 1.3125, 0.75),
            VoxelShapes.cuboid(0.125, 0.5, 0.125, 0.875, 0.625, 0.875)
    ));

    private static final VoxelShape VOXEL_SOUTH = VoxelUtils.make(Arrays.asList(
            VoxelShapes.cuboid(0.75, 0, 0.75, 0.875, 0.5, 0.875),
            VoxelShapes.cuboid(0.125, 0, 0.75, 0.25, 0.5, 0.875),
            VoxelShapes.cuboid(0.75, 0, 0.125, 0.875, 0.5, 0.25),
            VoxelShapes.cuboid(0.125, 0, 0.125, 0.25, 0.5, 0.25),
            VoxelShapes.cuboid(0.75, 0.625, 0.125, 0.875, 1.375, 0.25),
            VoxelShapes.cuboid(0.125, 0.625, 0.125, 0.25, 1.375, 0.25),
            VoxelShapes.cuboid(0.25, 1.0625, 0.125, 0.75, 1.3125, 0.25),
            VoxelShapes.cuboid(0.125, 0.5, 0.125, 0.875, 0.625, 0.875)
    ));

    private static final VoxelShape VOXEL_WEST = VoxelUtils.make(Arrays.asList(
            VoxelShapes.cuboid(0.125, 0, 0.75, 0.25, 0.5, 0.875),
            VoxelShapes.cuboid(0.125, 0, 0.125, 0.25, 0.5, 0.25),
            VoxelShapes.cuboid(0.75, 0, 0.75, 0.875, 0.5, 0.875),
            VoxelShapes.cuboid(0.75, 0, 0.125, 0.875, 0.5, 0.25),
            VoxelShapes.cuboid(0.75, 0.625, 0.75, 0.875, 1.375, 0.875),
            VoxelShapes.cuboid(0.75, 0.625, 0.125, 0.875, 1.375, 0.25),
            VoxelShapes.cuboid(0.75, 1.0625, 0.25, 0.875, 1.3125, 0.75),
            VoxelShapes.cuboid(0.125, 0.5, 0.125, 0.875, 0.625, 0.875)
    ));

    // </editor-fold>

    public ChairBlock(Block block) {

        super(block, VOXEL_NORTH, VOXEL_EAST, VOXEL_SOUTH, VOXEL_WEST);
    }

    @Override
    public Vec3d getSitOffsetFrom(Vec3d pos) {

        return pos.add(0.5, 0.4, 0.5);
    }

    @Override
    public float getSitYaw(BlockState state) {

        return state.get(FACING).asRotation();
    }

}
