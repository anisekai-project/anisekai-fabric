package me.anisekai.registries.blocks;

import me.anisekai.AnisekaiMod;
import me.anisekai.blocks.ChairBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public final class ModBlockChairs {

    public static final Block ACACIA = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "acacia_chair"),
            new ChairBlock(Blocks.ACACIA_STAIRS)
    );

    public static final Block BAMBOO = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "bamboo_chair"),
            new ChairBlock(Blocks.BAMBOO_STAIRS)
    );

    public static final Block BIRCH = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "birch_chair"),
            new ChairBlock(Blocks.BIRCH_STAIRS)
    );

    public static final Block CHERRY = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "cherry_chair"),
            new ChairBlock(Blocks.CHERRY_STAIRS)
    );

    public static final Block CRIMSON = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "crimson_chair"),
            new ChairBlock(Blocks.CRIMSON_STAIRS)
    );

    public static final Block DARK_OAK = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "dark_oak_chair"),
            new ChairBlock(Blocks.DARK_OAK_STAIRS)
    );

    public static final Block JUNGLE = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "jungle_chair"),
            new ChairBlock(Blocks.JUNGLE_STAIRS)
    );

    public static final Block MANGROVE = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "mangrove_chair"),
            new ChairBlock(Blocks.MANGROVE_STAIRS)
    );

    public static final Block OAK = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "oak_chair"),
            new ChairBlock(Blocks.OAK_STAIRS)
    );

    public static final Block SPRUCE = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "spruce_chair"),
            new ChairBlock(Blocks.SPRUCE_STAIRS)
    );

    public static final Block WARPED = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "warped_chair"),
            new ChairBlock(Blocks.WARPED_STAIRS)
    );

    private ModBlockChairs() {}

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
