package me.anisekai.registries.items;

import me.anisekai.AnisekaiMod;
import me.anisekai.registries.blocks.ModBlockStairs;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public final class ModItemStairs {

    public static final int STACK_AMOUNT = 64;

    public static final Item ACACIA = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "acacia_stairs"),
            new BlockItem(ModBlockStairs.ACACIA, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item BAMBOO = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "bamboo_stairs"),
            new BlockItem(ModBlockStairs.BAMBOO, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item BIRCH = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "birch_stairs"),
            new BlockItem(ModBlockStairs.BIRCH, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item CHERRY = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "cherry_stairs"),
            new BlockItem(ModBlockStairs.CHERRY, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item CRIMSON = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "crimson_stairs"),
            new BlockItem(ModBlockStairs.CRIMSON, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item DARK_OAK = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "dark_oak_stairs"),
            new BlockItem(ModBlockStairs.DARK_OAK, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item JUNGLE = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "jungle_stairs"),
            new BlockItem(ModBlockStairs.JUNGLE, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item MANGROVE = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "mangrove_stairs"),
            new BlockItem(ModBlockStairs.MANGROVE, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item OAK = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "oak_stairs"),
            new BlockItem(ModBlockStairs.OAK, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item SPRUCE = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "spruce_stairs"),
            new BlockItem(ModBlockStairs.SPRUCE, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item WARPED = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "warped_stairs"),
            new BlockItem(ModBlockStairs.WARPED, new FabricItemSettings().maxCount(STACK_AMOUNT))
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

    private ModItemStairs() {}

}
