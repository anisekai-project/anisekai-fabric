package me.anisekai.registries;

import me.anisekai.AnisekaiMod;
import me.anisekai.enums.WoodType;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ModItems {

    private static BlockItem create(WoodType type, String name, Map<WoodType, Block> from, int stackSize) {

        return Registry.register(
                Registries.ITEM,
                type.identifierOf(name),
                new BlockItem(from.get(type), new FabricItemSettings().maxCount(stackSize))
        );
    }

    public static final Map<WoodType, Item> CHAIRS = WoodType.generate(type -> create(
            type, "chair",
            ModBlocks.CHAIRS, 16
    ));

    public static final Map<WoodType, Item> STAIRS = WoodType.generate(type -> create(
            type, "stairs",
            ModBlocks.STAIRS, 64
    ));

    public static final Map<WoodType, Item> STOOLS = WoodType.generate(type -> create(
            type, "stool",
            ModBlocks.STOOLS, 16
    ));

    public static final Map<WoodType, Item> NIGHTSTANDS = WoodType.generate(type -> create(
            type, "nightstand",
            ModBlocks.NIGHTSTANDS, 8
    ));

    public static final Map<WoodType, Item> HALF_SLABS = WoodType.generate(type -> create(
            type, "half_slab",
            ModBlocks.HALF_SLABS, 64
    ));

    public static final Map<WoodType, Item> TABLES = WoodType.generate(type -> create(
            type, "table",
            ModBlocks.TABLES, 16
    ));

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
        items.addAll(CHAIRS.values());
        items.addAll(HALF_SLABS.values());
        items.addAll(STAIRS.values());
        items.addAll(STOOLS.values());
        items.addAll(TABLES.values());
        items.addAll(NIGHTSTANDS.values());

        items.add(FISHING_BASKET);
        items.add(CONDENSER);
        return items;
    }

    @SuppressWarnings("UnstableApiUsage")
    public static void addToInventory() {

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(content -> {
            content.add(() -> ModItems.FISHING_BASKET);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(content -> {
            content.add(() -> ModItems.CONDENSER);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(content -> {

            for (WoodType value : WoodType.values()) {
                content.addAfter(
                        () -> value.asButton().asItem(),
                        () -> CHAIRS.get(value),
                        () -> HALF_SLABS.get(value),
                        () -> STAIRS.get(value),
                        () -> STOOLS.get(value),
                        () -> TABLES.get(value),
                        () -> NIGHTSTANDS.get(value)
                );
            }
        });
    }

}
