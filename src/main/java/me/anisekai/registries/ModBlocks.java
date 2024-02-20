package me.anisekai.registries;

import me.anisekai.AnisekaiMod;
import me.anisekai.blocks.FishingBasketBlock;
import me.anisekai.registries.blocks.*;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public final class ModBlocks {

    public static final Block FISHING_BASKET = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "fishing_basket"),
            new FishingBasketBlock(Blocks.BAMBOO_PLANKS)
    );

    private ModBlocks() {}

    public static List<Block> blocks() {

        List<Block> blocks = new ArrayList<>();
        blocks.addAll(ModBlockChairs.blocks());
        blocks.addAll(ModBlockHalfSlabs.blocks());
        blocks.addAll(ModBlockStairs.blocks());
        blocks.addAll(ModBlockTables.blocks());
        blocks.addAll(ModBlockStools.blocks());
        blocks.addAll(ModBlockNightstands.blocks());

        blocks.add(FISHING_BASKET);
        return blocks;
    }

}
