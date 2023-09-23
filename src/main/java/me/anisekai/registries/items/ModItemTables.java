package me.anisekai.registries.items;

import me.anisekai.AnisekaiMod;
import me.anisekai.registries.blocks.ModBlockTables;
import me.anisekai.registries.blocks.ModBlockTables;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public final class ModItemTables {

    public static final int STACK_AMOUNT = 8;

    public static final Item ACACIA = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "acacia_table"),
            new BlockItem(ModBlockTables.ACACIA, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item BAMBOO = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "bamboo_table"),
            new BlockItem(ModBlockTables.BAMBOO, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item BIRCH = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "birch_table"),
            new BlockItem(ModBlockTables.BIRCH, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item CHERRY = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "cherry_table"),
            new BlockItem(ModBlockTables.CHERRY, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item CRIMSON = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "crimson_table"),
            new BlockItem(ModBlockTables.CRIMSON, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item DARK_OAK = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "dark_oak_table"),
            new BlockItem(ModBlockTables.DARK_OAK, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item JUNGLE = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "jungle_table"),
            new BlockItem(ModBlockTables.JUNGLE, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item MANGROVE = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "mangrove_table"),
            new BlockItem(ModBlockTables.MANGROVE, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item OAK = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "oak_table"),
            new BlockItem(ModBlockTables.OAK, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item SPRUCE = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "spruce_table"),
            new BlockItem(ModBlockTables.SPRUCE, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item WARPED = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "warped_table"),
            new BlockItem(ModBlockTables.WARPED, new FabricItemSettings().maxCount(STACK_AMOUNT))
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

    private ModItemTables() {}

}
