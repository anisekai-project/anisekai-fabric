package me.anisekai.registries.blocks;

import me.anisekai.AnisekaiMod;
import me.anisekai.blocks.StairsBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public final class ModBlockStairs {

    public static final Block ACACIA = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "acacia_stairs"),
            new StairsBlock(Blocks.ACACIA_STAIRS)
    );

    public static final Block BAMBOO = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "bamboo_stairs"),
            new StairsBlock(Blocks.BAMBOO_STAIRS)
    );

    public static final Block BIRCH = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "birch_stairs"),
            new StairsBlock(Blocks.BIRCH_STAIRS)
    );

    public static final Block CHERRY = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "cherry_stairs"),
            new StairsBlock(Blocks.CHERRY_STAIRS)
    );

    public static final Block CRIMSON = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "crimson_stairs"),
            new StairsBlock(Blocks.CRIMSON_STAIRS)
    );

    public static final Block DARK_OAK = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "dark_oak_stairs"),
            new StairsBlock(Blocks.DARK_OAK_STAIRS)
    );

    public static final Block JUNGLE = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "jungle_stairs"),
            new StairsBlock(Blocks.JUNGLE_STAIRS)
    );

    public static final Block MANGROVE = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "mangrove_stairs"),
            new StairsBlock(Blocks.MANGROVE_STAIRS)
    );

    public static final Block OAK = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "oak_stairs"),
            new StairsBlock(Blocks.OAK_STAIRS)
    );

    public static final Block SPRUCE = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "spruce_stairs"),
            new StairsBlock(Blocks.SPRUCE_STAIRS)
    );

    public static final Block WARPED = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "warped_stairs"),
            new StairsBlock(Blocks.WARPED_STAIRS)
    );

    private ModBlockStairs() {}

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
