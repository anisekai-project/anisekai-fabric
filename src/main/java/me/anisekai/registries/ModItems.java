package me.anisekai.registries;

import me.anisekai.AnisekaiMod;
import me.anisekai.registries.items.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public final class ModItems {

    public static final Item FISHING_BASKET = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "fishing_basket"),
            new BlockItem(ModBlocks.FISHING_BASKET, new FabricItemSettings().maxCount(64))
    );

    public static final Item CONDENSER = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "condenser"),
            new BlockItem(ModBlocks.CONDENSER, new FabricItemSettings().maxCount(64))
    );

    private ModItems() {}

    public static List<Item> items() {

        List<Item> items = new ArrayList<>();
        items.addAll(ModItemChairs.items());
        items.addAll(ModItemHalfSlabs.items());
        items.addAll(ModItemStairs.items());
        items.addAll(ModItemStools.items());
        items.addAll(ModItemTables.items());
        items.addAll(ModItemNightstands.items());

        items.add(FISHING_BASKET);
        items.add(CONDENSER);
        return items;
    }

    @SuppressWarnings("UnstableApiUsage")
    public static void addToInventory() {

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(content -> {
            content.add(() -> ModItems.FISHING_BASKET);
            content.add(() -> ModItems.CONDENSER);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(content -> {

            content.addAfter(
                    () -> Items.ACACIA_BUTTON,
                    () -> ModItemChairs.ACACIA,
                    () -> ModItemHalfSlabs.ACACIA,
                    () -> ModItemStairs.ACACIA,
                    () -> ModItemStools.ACACIA,
                    () -> ModItemTables.ACACIA,
                    () -> ModItemNightstands.ACACIA
            );

            content.addAfter(
                    () -> Items.BAMBOO_BUTTON,
                    () -> ModItemChairs.BAMBOO,
                    () -> ModItemHalfSlabs.BAMBOO,
                    () -> ModItemStairs.BAMBOO,
                    () -> ModItemStools.BAMBOO,
                    () -> ModItemTables.BAMBOO,
                    () -> ModItemNightstands.BAMBOO
            );

            content.addAfter(
                    () -> Items.BIRCH_BUTTON,
                    () -> ModItemChairs.BIRCH,
                    () -> ModItemHalfSlabs.BIRCH,
                    () -> ModItemStairs.BIRCH,
                    () -> ModItemStools.BIRCH,
                    () -> ModItemTables.BIRCH,
                    () -> ModItemNightstands.BIRCH
            );

            content.addAfter(
                    () -> Items.CHERRY_BUTTON,
                    () -> ModItemChairs.CHERRY,
                    () -> ModItemHalfSlabs.CHERRY,
                    () -> ModItemStairs.CHERRY,
                    () -> ModItemStools.CHERRY,
                    () -> ModItemTables.CHERRY,
                    () -> ModItemNightstands.CHERRY
            );

            content.addAfter(
                    () -> Items.CRIMSON_BUTTON,
                    () -> ModItemChairs.CRIMSON,
                    () -> ModItemHalfSlabs.CRIMSON,
                    () -> ModItemStairs.CRIMSON,
                    () -> ModItemStools.CRIMSON,
                    () -> ModItemTables.CRIMSON,
                    () -> ModItemNightstands.CRIMSON
            );

            content.addAfter(
                    () -> Items.DARK_OAK_BUTTON,
                    () -> ModItemChairs.DARK_OAK,
                    () -> ModItemHalfSlabs.DARK_OAK,
                    () -> ModItemStairs.DARK_OAK,
                    () -> ModItemStools.DARK_OAK,
                    () -> ModItemTables.DARK_OAK,
                    () -> ModItemNightstands.DARK_OAK
            );

            content.addAfter(
                    () -> Items.JUNGLE_BUTTON,
                    () -> ModItemChairs.JUNGLE,
                    () -> ModItemHalfSlabs.JUNGLE,
                    () -> ModItemStairs.JUNGLE,
                    () -> ModItemStools.JUNGLE,
                    () -> ModItemTables.JUNGLE,
                    () -> ModItemNightstands.JUNGLE
            );

            content.addAfter(
                    () -> Items.MANGROVE_BUTTON,
                    () -> ModItemChairs.MANGROVE,
                    () -> ModItemHalfSlabs.MANGROVE,
                    () -> ModItemStairs.MANGROVE,
                    () -> ModItemStools.MANGROVE,
                    () -> ModItemTables.MANGROVE,
                    () -> ModItemNightstands.MANGROVE
            );

            content.addAfter(
                    () -> Items.OAK_BUTTON,
                    () -> ModItemChairs.OAK,
                    () -> ModItemHalfSlabs.OAK,
                    () -> ModItemStairs.OAK,
                    () -> ModItemStools.OAK,
                    () -> ModItemTables.OAK,
                    () -> ModItemNightstands.OAK
            );

            content.addAfter(
                    () -> Items.SPRUCE_BUTTON,
                    () -> ModItemChairs.SPRUCE,
                    () -> ModItemHalfSlabs.SPRUCE,
                    () -> ModItemStairs.SPRUCE,
                    () -> ModItemStools.SPRUCE,
                    () -> ModItemTables.SPRUCE,
                    () -> ModItemNightstands.SPRUCE
            );

            content.addAfter(
                    () -> Items.WARPED_BUTTON,
                    () -> ModItemChairs.WARPED,
                    () -> ModItemHalfSlabs.WARPED,
                    () -> ModItemStairs.WARPED,
                    () -> ModItemStools.WARPED,
                    () -> ModItemTables.WARPED,
                    () -> ModItemNightstands.WARPED
            );
        });
    }

}
