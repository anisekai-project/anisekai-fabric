package me.anisekai.blocks;

import me.anisekai.blocks.composed.OrientableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class HalfSlabBlock extends OrientableBlock {

    private static final VoxelShape SHAPE = VoxelShapes.cuboid(0, 0.75, 0, 1, 1, 1);

    public HalfSlabBlock(Block block) {

        super(block);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {

        // TODO: Add state and more voxel along with it
        return SHAPE;
    }

}
