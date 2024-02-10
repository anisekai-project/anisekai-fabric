package me.anisekai.registries.items;

import me.anisekai.AnisekaiMod;
import me.anisekai.registries.blocks.ModBlockStools;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public final class ModItemStools {

    public static final int STACK_AMOUNT = 16;

    public static final Item ACACIA = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "acacia_stool"),
            new BlockItem(ModBlockStools.ACACIA, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item BAMBOO = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "bamboo_stool"),
            new BlockItem(ModBlockStools.BAMBOO, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item BIRCH = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "birch_stool"),
            new BlockItem(ModBlockStools.BIRCH, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item CHERRY = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "cherry_stool"),
            new BlockItem(ModBlockStools.CHERRY, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item CRIMSON = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "crimson_stool"),
            new BlockItem(ModBlockStools.CRIMSON, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item DARK_OAK = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "dark_oak_stool"),
            new BlockItem(ModBlockStools.DARK_OAK, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item JUNGLE = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "jungle_stool"),
            new BlockItem(ModBlockStools.JUNGLE, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item MANGROVE = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "mangrove_stool"),
            new BlockItem(ModBlockStools.MANGROVE, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item OAK = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "oak_stool"),
            new BlockItem(ModBlockStools.OAK, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item SPRUCE = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "spruce_stool"),
            new BlockItem(ModBlockStools.SPRUCE, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item WARPED = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "warped_stool"),
            new BlockItem(ModBlockStools.WARPED, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static List<Item> items() {

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

    private ModItemStools() {}

}
