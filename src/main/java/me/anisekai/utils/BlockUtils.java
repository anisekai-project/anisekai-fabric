package me.anisekai.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.Properties;

public class BlockUtils {

    public static boolean isContextWater(ItemPlacementContext ctx) {
        return ctx.getWorld().getFluidState(ctx.getBlockPos()).isOf(Fluids.WATER);
    }

}
