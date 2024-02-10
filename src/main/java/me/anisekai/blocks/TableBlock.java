package me.anisekai.blocks;

import me.anisekai.blocks.composed.OrientableBlock;
import me.anisekai.utils.VoxelUtils;
import net.minecraft.block.Block;
import net.minecraft.block.Waterloggable;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.Arrays;

public class TableBlock extends OrientableBlock implements Waterloggable {

    private static final VoxelShape SHAPE = VoxelUtils.make(Arrays.asList(
            VoxelShapes.cuboid(0.3125, 0, 0.3125, 0.6875, 0.0625, 0.6875),
            VoxelShapes.cuboid(0.4375, 0.0625, 0.4375, 0.5625, 0.875, 0.5625),
            VoxelShapes.cuboid(0, 0.875, 0, 1, 1, 1)
    ));

    public TableBlock(Block block) {

        super(block, SHAPE);
    }

}
