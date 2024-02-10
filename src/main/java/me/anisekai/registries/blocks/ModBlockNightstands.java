package me.anisekai.registries.blocks;

import me.anisekai.AnisekaiMod;
import me.anisekai.blockentities.NightstandBlockEntity;
import me.anisekai.blocks.NightstandBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public final class ModBlockNightstands {

    public static final Block ACACIA = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "acacia_nightstand"),
            new NightstandBlock(Blocks.ACACIA_STAIRS)
    );

    public static final Block BAMBOO = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "bamboo_nightstand"),
            new NightstandBlock(Blocks.BAMBOO_STAIRS)
    );

    public static final Block BIRCH = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "birch_nightstand"),
            new NightstandBlock(Blocks.BIRCH_STAIRS)
    );

    public static final Block CHERRY = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "cherry_nightstand"),
            new NightstandBlock(Blocks.CHERRY_STAIRS)
    );

    public static final Block CRIMSON = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "crimson_nightstand"),
            new NightstandBlock(Blocks.CRIMSON_STAIRS)
    );

    public static final Block DARK_OAK = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "dark_oak_nightstand"),
            new NightstandBlock(Blocks.DARK_OAK_STAIRS)
    );

    public static final Block JUNGLE = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "jungle_nightstand"),
            new NightstandBlock(Blocks.JUNGLE_STAIRS)
    );

    public static final Block MANGROVE = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "mangrove_nightstand"),
            new NightstandBlock(Blocks.MANGROVE_STAIRS)
    );

    public static final Block OAK = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "oak_nightstand"),
            new NightstandBlock(Blocks.OAK_STAIRS)
    );

    public static final Block SPRUCE = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "spruce_nightstand"),
            new NightstandBlock(Blocks.SPRUCE_STAIRS)
    );

    public static final Block WARPED = Registry.register(
            Registries.BLOCK,
            new Identifier(AnisekaiMod.MOD_ID, "warped_nightstand"),
            new NightstandBlock(Blocks.WARPED_STAIRS)
    );


    public static final BlockEntityType<NightstandBlockEntity> BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier(AnisekaiMod.MOD_ID, "nightstand"),
            FabricBlockEntityTypeBuilder.create(
                    NightstandBlockEntity::new,
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
            ).build()
    );


    private ModBlockNightstands() {}

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
