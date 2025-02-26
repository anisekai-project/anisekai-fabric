package me.anisekai.registries;

import me.anisekai.AnisekaiMod;
import me.anisekai.enums.CoinEnum;
import me.anisekai.enums.WoodEnum;
import me.anisekai.registries.variants.BlockVariant;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ModItems {

    private static BlockItem createBlockItem(String name, Block block) {

        return Registry.register(
                Registries.ITEM,
                AnisekaiMod.id(name),
                new BlockItem(block, new Item.Settings().maxCount(64))
        );
    }


    public static final Map<WoodEnum, BlockItem> CHAIRS      = BlockVariant.WOODS.createBlockItems(ModBlocks.CHAIRS, "%s_chair", 16);
    public static final Map<WoodEnum, BlockItem> HALF_SLABS  = BlockVariant.WOODS.createBlockItems(ModBlocks.HALF_SLABS, "%s_half_slab", 64);
    public static final Map<WoodEnum, BlockItem> NIGHTSTANDS = BlockVariant.WOODS.createBlockItems(ModBlocks.NIGHTSTANDS, "%s_nightstand", 8);
    public static final Map<WoodEnum, BlockItem> STAIRCASES  = BlockVariant.WOODS.createBlockItems(ModBlocks.STAIRCASES, "%s_staircase", 64);
    public static final Map<WoodEnum, BlockItem> STOOL       = BlockVariant.WOODS.createBlockItems(ModBlocks.STOOLS, "%s_stool", 16);
    public static final Map<WoodEnum, BlockItem> TABLES      = BlockVariant.WOODS.createBlockItems(ModBlocks.TABLES, "%s_table", 8);
    public static final Map<CoinEnum, Item>      COINS       = BlockVariant.COINS.createItems("%s_coin", variant -> new Item(new Item.Settings().maxCount(64)));

    public static final Item FISHING_BASKET = createBlockItem("fishing_basket", ModBlocks.FISHING_BASKET);
    public static final Item CONDENSER      = createBlockItem("condenser", ModBlocks.CONDENSER);


    private ModItems() {}

    public static List<Item> items() {

        List<Item> items = new ArrayList<>();
        items.addAll(CHAIRS.values());
        items.addAll(HALF_SLABS.values());
        items.addAll(NIGHTSTANDS.values());
        items.addAll(STAIRCASES.values());
        items.addAll(STOOL.values());
        items.addAll(TABLES.values());

        items.addAll(COINS.values());

        items.add(FISHING_BASKET);
        items.add(CONDENSER);

        return items;
    }

    public static void addToInventory() {

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL)
                       .register(content -> content.add(() -> ModItems.FISHING_BASKET));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE)
                       .register(content -> content.add(() -> ModItems.CONDENSER));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).
                       register(content -> {

                           for (WoodEnum value : WoodEnum.values()) {
                               content.addAfter(
                                       () -> value.asButton().asItem(),
                                       () -> CHAIRS.get(value),
                                       () -> HALF_SLABS.get(value),
                                       () -> NIGHTSTANDS.get(value),
                                       () -> STAIRCASES.get(value),
                                       () -> STOOL.get(value),
                                       () -> TABLES.get(value)
                               );
                           }
                       });
    }

}
