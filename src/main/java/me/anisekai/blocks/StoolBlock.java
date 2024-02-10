package me.anisekai.blocks;

import me.anisekai.blocks.composed.SeatBlock;
import me.anisekai.utils.VoxelUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.Arrays;

public class StoolBlock extends SeatBlock {

    // <editor-fold desc="Voxels">

    private static final VoxelShape VOXEL = VoxelUtils.make(Arrays.asList(
            VoxelShapes.cuboid(0.125, 0, 0.125, 0.25, 0.5, 0.25),
            VoxelShapes.cuboid(0.75, 0, 0.125, 0.875, 0.5, 0.25),
            VoxelShapes.cuboid(0.125, 0, 0.75, 0.25, 0.5, 0.875),
            VoxelShapes.cuboid(0.75, 0, 0.75, 0.875, 0.5, 0.875),
            VoxelShapes.cuboid(0.125, 0.5, 0.125, 0.875, 0.625, 0.875)
    ));

    // </editor-fold>

    public StoolBlock(Block block) {

        super(block, VOXEL);
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
