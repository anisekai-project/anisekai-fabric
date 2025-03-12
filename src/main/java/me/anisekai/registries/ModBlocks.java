package me.anisekai.registries;

import me.anisekai.AnisekaiMod;
import me.anisekai.blocks.*;
import me.anisekai.enums.GlassEnum;
import me.anisekai.enums.WoodEnum;
import me.anisekai.registries.variants.BlockVariant;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ModBlocks {

    public static final Map<WoodEnum, Block> CHAIRS = BlockVariant.WOODS.createBlocks(
            "%s_chair",
            variant -> new ChairBlock(AbstractBlock.Settings.copy(variant.asStairs()))
    );

    public static final Map<WoodEnum, Block> HALF_SLABS = BlockVariant.WOODS.createBlocks(
            "%s_half_slab",
            variant -> new HalfSlabBlock(AbstractBlock.Settings.copy(variant.asSlab()))
    );

    public static final Map<WoodEnum, Block> NIGHTSTANDS = BlockVariant.WOODS.createBlocks(
            "%s_nightstand",
            variant -> new NightstandBlock(AbstractBlock.Settings.copy(variant.asStairs()))
    );

    public static final Map<WoodEnum, Block> STAIRCASES = BlockVariant.WOODS.createBlocks(
            "%s_staircase",
            variant -> new StaircaseBlock(AbstractBlock.Settings.copy(variant.asStairs()))
    );

    public static final Map<WoodEnum, Block> STOOLS = BlockVariant.WOODS.createBlocks(
            "%s_stool",
            variant -> new StoolBlock(AbstractBlock.Settings.copy(variant.asStairs()))
    );

    public static final Map<WoodEnum, Block> TABLES = BlockVariant.WOODS.createBlocks(
            "%s_table",
            variant -> new TableBlock(AbstractBlock.Settings.copy(variant.asStairs()))
    );

    public static final Map<GlassEnum, Block> GLASS_HALF_SLABS = BlockVariant.GLASSES.createBlocks(
            "%s_half_slab",
            variant -> {
                AbstractBlock.Settings settings = AbstractBlock.Settings.create()
                                                                        .nonOpaque()
                                                                        .allowsSpawning(Blocks::never)
                                                                        .suffocates(Blocks::never)
                                                                        .blockVision(Blocks::never)
                                                                        .solidBlock(Blocks::never);

                if (variant.isStained()) {
                    return new StainedHalfBlock(variant.getColor(), settings);
                } else {
                    return new HalfSlabBlock(settings);
                }
            }
    );

    public static final Block FISHING_BASKET = Registry.register(
            Registries.BLOCK,
            AnisekaiMod.id("fishing_basket"),
            new FishingBasketBlock(AbstractBlock.Settings.copy(Blocks.SHULKER_BOX))
    );

    public static final Block CONDENSER = Registry.register(
            Registries.BLOCK,
            AnisekaiMod.id("condenser"),
            new CondenserBlock(AbstractBlock.Settings.copy(Blocks.COBBLED_DEEPSLATE_STAIRS))
    );

    private ModBlocks() {}

    public static List<Block> blocks() {

        List<Block> blocks = new ArrayList<>();
        blocks.addAll(CHAIRS.values());
        blocks.addAll(HALF_SLABS.values());
        blocks.addAll(GLASS_HALF_SLABS.values());
        blocks.addAll(STAIRCASES.values());
        blocks.addAll(TABLES.values());
        blocks.addAll(STOOLS.values());
        blocks.addAll(NIGHTSTANDS.values());

        blocks.add(FISHING_BASKET);
        blocks.add(CONDENSER);
        return blocks;
    }

}
