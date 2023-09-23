package me.anisekai.registries.items;

import me.anisekai.AnisekaiMod;
import me.anisekai.registries.blocks.ModBlockHalfSlabs;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public final class ModItemHalfSlabs {

    public static final int STACK_AMOUNT = 64;

    public static final Item ACACIA = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "acacia_half_slab"),
            new BlockItem(ModBlockHalfSlabs.ACACIA, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item BAMBOO = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "bamboo_half_slab"),
            new BlockItem(ModBlockHalfSlabs.BAMBOO, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item BIRCH = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "birch_half_slab"),
            new BlockItem(ModBlockHalfSlabs.BIRCH, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item CHERRY = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "cherry_half_slab"),
            new BlockItem(ModBlockHalfSlabs.CHERRY, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item CRIMSON = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "crimson_half_slab"),
            new BlockItem(ModBlockHalfSlabs.CRIMSON, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item DARK_OAK = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "dark_oak_half_slab"),
            new BlockItem(ModBlockHalfSlabs.DARK_OAK, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item JUNGLE = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "jungle_half_slab"),
            new BlockItem(ModBlockHalfSlabs.JUNGLE, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item MANGROVE = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "mangrove_half_slab"),
            new BlockItem(ModBlockHalfSlabs.MANGROVE, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item OAK = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "oak_half_slab"),
            new BlockItem(ModBlockHalfSlabs.OAK, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item SPRUCE = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "spruce_half_slab"),
            new BlockItem(ModBlockHalfSlabs.SPRUCE, new FabricItemSettings().maxCount(STACK_AMOUNT))
    );

    public static final Item WARPED = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "warped_half_slab"),
            new BlockItem(ModBlockHalfSlabs.WARPED, new FabricItemSettings().maxCount(STACK_AMOUNT))
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

    private ModItemHalfSlabs() {}

}
