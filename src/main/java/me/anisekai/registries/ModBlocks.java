package me.anisekai.registries;

import me.anisekai.AnisekaiMod;
import me.anisekai.blocks.chair.ChairBlock;
import me.anisekai.blocks.chair.TableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public final class ModBlocks {

    private ModBlocks() {}

    public static final Block ACACIA_CHAIR = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "acacia_chair"),
            new ChairBlock(Blocks.ACACIA_STAIRS)
    );

    public static final Block BAMBOO_CHAIR = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "bamboo_chair"),
            new ChairBlock(Blocks.BAMBOO_STAIRS)
    );

    public static final Block BIRCH_CHAIR = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "birch_chair"),
            new ChairBlock(Blocks.BIRCH_STAIRS)
    );

    public static final Block CHERRY_CHAIR = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "cherry_chair"),
            new ChairBlock(Blocks.CHERRY_STAIRS)
    );

    public static final Block CRIMSON_CHAIR = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "crimson_chair"),
            new ChairBlock(Blocks.CRIMSON_STAIRS)
    );

    public static final Block DARK_OAK_CHAIR = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "dark_oak_chair"),
            new ChairBlock(Blocks.DARK_OAK_STAIRS)
    );

    public static final Block JUNGLE_CHAIR = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "jungle_chair"),
            new ChairBlock(Blocks.JUNGLE_STAIRS)
    );

    public static final Block MANGROVE_CHAIR = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "mangrove_chair"),
            new ChairBlock(Blocks.MANGROVE_STAIRS)
    );

    public static final Block OAK_CHAIR = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "oak_chair"),
            new ChairBlock(Blocks.OAK_STAIRS)
    );

    public static final Block SPRUCE_CHAIR = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "spruce_chair"),
            new ChairBlock(Blocks.SPRUCE_STAIRS)
    );

    public static final Block WARPED_CHAIR = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "warped_chair"),
            new ChairBlock(Blocks.WARPED_STAIRS)
    );

    public static final Block ACACIA_TABLE = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "acacia_table"),
            new TableBlock(Blocks.ACACIA_STAIRS)
    );

    public static final Block BAMBOO_TABLE = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "bamboo_table"),
            new TableBlock(Blocks.BAMBOO_STAIRS)
    );

    public static final Block BIRCH_TABLE = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "birch_table"),
            new TableBlock(Blocks.BIRCH_STAIRS)
    );

    public static final Block CHERRY_TABLE = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "cherry_table"),
            new TableBlock(Blocks.CHERRY_STAIRS)
    );

    public static final Block CRIMSON_TABLE = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "crimson_table"),
            new TableBlock(Blocks.CRIMSON_STAIRS)
    );

    public static final Block DARK_OAK_TABLE = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "dark_oak_table"),
            new TableBlock(Blocks.DARK_OAK_STAIRS)
    );

    public static final Block JUNGLE_TABLE = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "jungle_table"),
            new TableBlock(Blocks.JUNGLE_STAIRS)
    );

    public static final Block MANGROVE_TABLE = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "mangrove_table"),
            new TableBlock(Blocks.MANGROVE_STAIRS)
    );

    public static final Block OAK_TABLE = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "oak_table"),
            new TableBlock(Blocks.OAK_STAIRS)
    );

    public static final Block SPRUCE_TABLE = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "spruce_table"),
            new TableBlock(Blocks.SPRUCE_STAIRS)
    );

    public static final Block WARPED_TABLE = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "warped_table"),
            new TableBlock(Blocks.WARPED_STAIRS)
    );

    public static List<Block> blocks() {

        return Arrays.asList(
                ACACIA_CHAIR,
                BAMBOO_CHAIR,
                BIRCH_CHAIR,
                CHERRY_CHAIR,
                CRIMSON_CHAIR,
                DARK_OAK_CHAIR,
                JUNGLE_CHAIR,
                MANGROVE_CHAIR,
                OAK_CHAIR,
                SPRUCE_CHAIR,
                WARPED_CHAIR,
                ACACIA_TABLE,
                BAMBOO_TABLE,
                BIRCH_TABLE,
                CHERRY_TABLE,
                CRIMSON_TABLE,
                DARK_OAK_TABLE,
                JUNGLE_TABLE,
                MANGROVE_TABLE,
                OAK_TABLE,
                SPRUCE_TABLE,
                WARPED_TABLE
        );
    }

}
