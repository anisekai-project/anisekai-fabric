package me.anisekai.utils;

import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;

public class BlockUtils {

    public static boolean isContextWater(ItemPlacementContext ctx) {
        return ctx.getWorld().getFluidState(ctx.getBlockPos()).isOf(Fluids.WATER);
    }

}
