package me.anisekai.registries.items;

import me.anisekai.AnisekaiMod;
import me.anisekai.registries.blocks.ModBlockChairs;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public final class ModItemChairs {

    public static final int STACK_AMOUNT = 16;

    public static final Item ACACIA = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "acacia_chair"),
            new BlockItem(ModBlockChairs.ACACIA, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item BAMBOO = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "bamboo_chair"),
            new BlockItem(ModBlockChairs.BAMBOO, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item BIRCH = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "birch_chair"),
            new BlockItem(ModBlockChairs.BIRCH, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item CHERRY = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "cherry_chair"),
            new BlockItem(ModBlockChairs.CHERRY, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item CRIMSON = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "crimson_chair"),
            new BlockItem(ModBlockChairs.CRIMSON, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item DARK_OAK = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "dark_oak_chair"),
            new BlockItem(ModBlockChairs.DARK_OAK, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item JUNGLE = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "jungle_chair"),
            new BlockItem(ModBlockChairs.JUNGLE, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item MANGROVE = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "mangrove_chair"),
            new BlockItem(ModBlockChairs.MANGROVE, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item OAK = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "oak_chair"),
            new BlockItem(ModBlockChairs.OAK, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item SPRUCE = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "spruce_chair"),
            new BlockItem(ModBlockChairs.SPRUCE, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item WARPED = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "warped_chair"),
            new BlockItem(ModBlockChairs.WARPED, new FabricItemSettings().maxCount(STACK_AMOUNT))
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

    private ModItemChairs() {}

}
