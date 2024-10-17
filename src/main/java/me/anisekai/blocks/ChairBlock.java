package me.anisekai.blocks;

import me.anisekai.interfaces.Orientable;
import me.anisekai.interfaces.Seatable;
import me.anisekai.utils.RotatableShape;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.util.shape.VoxelShapes;

import java.util.Arrays;

public class ChairBlock extends StoolBlock implements Waterloggable, Seatable, Orientable {

    private static final RotatableShape SHAPE = new RotatableShape(Arrays.asList(
            VoxelShapes.cuboid(0.125, 0, 0.125, 0.25, 0.5, 0.25),
            VoxelShapes.cuboid(0.75, 0, 0.125, 0.875, 0.5, 0.25),
            VoxelShapes.cuboid(0.125, 0, 0.75, 0.25, 0.5, 0.875),
            VoxelShapes.cuboid(0.75, 0, 0.75, 0.875, 0.5, 0.875),
            VoxelShapes.cuboid(0.125, 0.625, 0.75, 0.25, 1.375, 0.875),
            VoxelShapes.cuboid(0.75, 0.625, 0.75, 0.875, 1.375, 0.875),
            VoxelShapes.cuboid(0.25, 1.0625, 0.75, 0.75, 1.3125, 0.875),
            VoxelShapes.cuboid(0.125, 0.5, 0.125, 0.875, 0.625, 0.875)
    ));

    public ChairBlock(AbstractBlock.Settings settings) {

        super(settings);
    }

    @Override
    public RotatableShape getOrientedShapes() {

        return SHAPE;
    }

}
