package me.anisekai.blocks;

import me.anisekai.blocks.feature.QuarterBlock;
import net.minecraft.block.Stainable;
import net.minecraft.block.Waterloggable;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class StainedHalfBlock extends HalfSlabBlock implements Stainable, Waterloggable, QuarterBlock {

    private final DyeColor color;

    public StainedHalfBlock(DyeColor color, Settings settings) {

        super(settings);
        this.color = color;
    }

    @Override
    public DyeColor getColor() {

        return this.color;
    }

}
