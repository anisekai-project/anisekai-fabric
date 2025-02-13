package me.anisekai.registries;

import me.anisekai.AnisekaiMod;
import me.anisekai.blocks.*;
import me.anisekai.enums.WoodType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ModBlocks {

    private static Block create(WoodType type, String name, Block block) {

        return Registry.register(
                Registries.BLOCK,
                type.identifierOf(name),
                block
        );
    }

    public static final Map<WoodType, Block> CHAIRS = WoodType.generate(type -> create(
            type,
            "chair",
            new ChairBlock(AbstractBlock.Settings.copy(type.asStairs()))
    ));

    public static final Map<WoodType, Block> STAIRS = WoodType.generate(type -> create(
            type,
            "stairs",
            new StaircaseBlock(AbstractBlock.Settings.copy(type.asStairs()))
    ));

    public static final Map<WoodType, Block> STOOLS = WoodType.generate(type -> create(
            type,
            "stool",
            new StoolBlock(AbstractBlock.Settings.copy(type.asStairs()))
    ));

    public static final Map<WoodType, Block> NIGHTSTANDS = WoodType.generate(type -> create(
            type,
            "nightstand",
            new NightstandBlock(AbstractBlock.Settings.copy(type.asStairs()))
    ));

    public static final Map<WoodType, Block> HALF_SLABS = WoodType.generate(type -> create(
            type,
            "half_slab",
            new HalfSlabBlock(AbstractBlock.Settings.copy(type.asSlab()))
    ));

    public static final Map<WoodType, Block> TABLES = WoodType.generate(type -> create(
            type,
            "table",
            new TableBlock(AbstractBlock.Settings.copy(type.asSlab()))
    ));


    public static final Block FISHING_BASKET = Registry.register(
            Registries.BLOCK,
            Identifier.of(AnisekaiMod.MOD_ID, "fishing_basket"),
            new FishingBasketBlock(AbstractBlock.Settings.copy(Blocks.BAMBOO_PLANKS))
    );

    public static final Block CONDENSER = Registry.register(
            Registries.BLOCK,
            Identifier.of(AnisekaiMod.MOD_ID, "condenser"),
            new CondenserBlock(AbstractBlock.Settings.copy(Blocks.DEEPSLATE))
    );

    private ModBlocks() {}

    public static List<Block> blocks() {

        List<Block> blocks = new ArrayList<>();
        blocks.addAll(CHAIRS.values());
        blocks.addAll(HALF_SLABS.values());
        blocks.addAll(STAIRS.values());
        blocks.addAll(TABLES.values());
        blocks.addAll(STOOLS.values());
        blocks.addAll(NIGHTSTANDS.values());

        blocks.add(FISHING_BASKET);
        blocks.add(CONDENSER);
        return blocks;
    }

}
