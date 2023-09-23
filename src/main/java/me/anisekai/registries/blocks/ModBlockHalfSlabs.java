package me.anisekai.registries.blocks;

import me.anisekai.AnisekaiMod;
import me.anisekai.blocks.HalfSlabBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public final class ModBlockHalfSlabs {

    public static final Block ACACIA = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "acacia_half_slab"),
            new HalfSlabBlock(Blocks.ACACIA_SLAB)
    );

    public static final Block BAMBOO = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "bamboo_half_slab"),
            new HalfSlabBlock(Blocks.BAMBOO_SLAB)
    );

    public static final Block BIRCH = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "birch_half_slab"),
            new HalfSlabBlock(Blocks.BIRCH_SLAB)
    );

    public static final Block CHERRY = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "cherry_half_slab"),
            new HalfSlabBlock(Blocks.CHERRY_SLAB)
    );

    public static final Block CRIMSON = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "crimson_half_slab"),
            new HalfSlabBlock(Blocks.CRIMSON_SLAB)
    );

    public static final Block DARK_OAK = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "dark_oak_half_slab"),
            new HalfSlabBlock(Blocks.DARK_OAK_SLAB)
    );

    public static final Block JUNGLE = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "jungle_half_slab"),
            new HalfSlabBlock(Blocks.JUNGLE_SLAB)
    );

    public static final Block MANGROVE = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "mangrove_half_slab"),
            new HalfSlabBlock(Blocks.MANGROVE_SLAB)
    );

    public static final Block OAK = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "oak_half_slab"),
            new HalfSlabBlock(Blocks.OAK_SLAB)
    );

    public static final Block SPRUCE = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "spruce_half_slab"),
            new HalfSlabBlock(Blocks.SPRUCE_SLAB)
    );

    public static final Block WARPED = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "warped_half_slab"),
            new HalfSlabBlock(Blocks.WARPED_SLAB)
    );

    private ModBlockHalfSlabs() {}

    public static List<Block> blocks() {

        return Arrays.asList(
                ACACIA,
                BAMBOO,
                BIRCH,
                CHERRY,
                CRIMSON,
                DARK_OAK,
                JUNGLE,
                MANGROVE,
                OAK,
                SPRUCE,
                WARPED
        );
    }

}
