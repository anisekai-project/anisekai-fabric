package me.anisekai.registries;

import me.anisekai.registries.items.*;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.List;

public final class ModItems {

    private ModItems() {}

    public static List<Item> items() {

        List<Item> items = new ArrayList<>();
        items.addAll(ModItemChairs.items());
        items.addAll(ModItemHalfSlabs.items());
        items.addAll(ModItemStairs.items());
        items.addAll(ModItemStools.items());
        items.addAll(ModItemTables.items());
        return items;
    }

    @SuppressWarnings("UnstableApiUsage")
    public static void addToInventory() {

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(content -> {

            content.addAfter(
                    () -> Items.ACACIA_BUTTON,
                    () -> ModItemChairs.ACACIA,
                    () -> ModItemHalfSlabs.ACACIA,
                    () -> ModItemStairs.ACACIA,
                    () -> ModItemStools.ACACIA,
                    () -> ModItemTables.ACACIA
            );

            content.addAfter(
                    () -> Items.BAMBOO_BUTTON,
                    () -> ModItemChairs.BAMBOO,
                    () -> ModItemHalfSlabs.BAMBOO,
                    () -> ModItemStairs.BAMBOO,
                    () -> ModItemStools.BAMBOO,
                    () -> ModItemTables.BAMBOO
            );

            content.addAfter(
                    () -> Items.BIRCH_BUTTON,
                    () -> ModItemChairs.BIRCH,
                    () -> ModItemHalfSlabs.BIRCH,
                    () -> ModItemStairs.BIRCH,
                    () -> ModItemStools.BIRCH,
                    () -> ModItemTables.BIRCH
            );

            content.addAfter(
                    () -> Items.CHERRY_BUTTON,
                    () -> ModItemChairs.CHERRY,
                    () -> ModItemHalfSlabs.CHERRY,
                    () -> ModItemStairs.CHERRY,
                    () -> ModItemStools.CHERRY,
                    () -> ModItemTables.CHERRY
            );

            content.addAfter(
                    () -> Items.CRIMSON_BUTTON,
                    () -> ModItemChairs.CRIMSON,
                    () -> ModItemHalfSlabs.CRIMSON,
                    () -> ModItemStairs.CRIMSON,
                    () -> ModItemStools.CRIMSON,
                    () -> ModItemTables.CRIMSON
            );

            content.addAfter(
                    () -> Items.DARK_OAK_BUTTON,
                    () -> ModItemChairs.DARK_OAK,
                    () -> ModItemHalfSlabs.DARK_OAK,
                    () -> ModItemStairs.DARK_OAK,
                    () -> ModItemStools.DARK_OAK,
                    () -> ModItemTables.DARK_OAK
            );

            content.addAfter(
                    () -> Items.JUNGLE_BUTTON,
                    () -> ModItemChairs.JUNGLE,
                    () -> ModItemHalfSlabs.JUNGLE,
                    () -> ModItemStairs.JUNGLE,
                    () -> ModItemStools.JUNGLE,
                    () -> ModItemTables.JUNGLE
            );

            content.addAfter(
                    () -> Items.MANGROVE_BUTTON,
                    () -> ModItemChairs.MANGROVE,
                    () -> ModItemHalfSlabs.MANGROVE,
                    () -> ModItemStairs.MANGROVE,
                    () -> ModItemStools.MANGROVE,
                    () -> ModItemTables.MANGROVE
            );

            content.addAfter(
                    () -> Items.OAK_BUTTON,
                    () -> ModItemChairs.OAK,
                    () -> ModItemHalfSlabs.OAK,
                    () -> ModItemStairs.OAK,
                    () -> ModItemStools.OAK,
                    () -> ModItemTables.OAK
            );

            content.addAfter(
                    () -> Items.SPRUCE_BUTTON,
                    () -> ModItemChairs.SPRUCE,
                    () -> ModItemHalfSlabs.SPRUCE,
                    () -> ModItemStairs.SPRUCE,
                    () -> ModItemStools.SPRUCE,
                    () -> ModItemTables.SPRUCE
            );

            content.addAfter(
                    () -> Items.WARPED_BUTTON,
                    () -> ModItemChairs.WARPED,
                    () -> ModItemHalfSlabs.WARPED,
                    () -> ModItemStairs.WARPED,
                    () -> ModItemStools.WARPED,
                    () -> ModItemTables.WARPED
            );
        });
    }

}
