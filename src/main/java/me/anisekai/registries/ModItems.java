package me.anisekai.registries;

import me.anisekai.AnisekaiMod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public final class ModItems {

    private static final int CHAIR_STACK_AMOUNT = 16;
    private static final int TABLE_STACK_AMOUNT = 8;

    public static final Item ACACIA_CHAIR = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "acacia_chair"),
            new BlockItem(ModBlocks.ACACIA_CHAIR, new FabricItemSettings().maxCount(CHAIR_STACK_AMOUNT))
    );

    public static final Item BAMBOO_CHAIR = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "bamboo_chair"),
            new BlockItem(ModBlocks.BAMBOO_CHAIR, new FabricItemSettings().maxCount(CHAIR_STACK_AMOUNT))
    );

    public static final Item BIRCH_CHAIR = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "birch_chair"),
            new BlockItem(ModBlocks.BIRCH_CHAIR, new FabricItemSettings().maxCount(CHAIR_STACK_AMOUNT))
    );

    public static final Item CHERRY_CHAIR = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "cherry_chair"),
            new BlockItem(ModBlocks.CHERRY_CHAIR, new FabricItemSettings().maxCount(CHAIR_STACK_AMOUNT))
    );

    public static final Item CRIMSON_CHAIR = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "crimson_chair"),
            new BlockItem(ModBlocks.CRIMSON_CHAIR, new FabricItemSettings().maxCount(CHAIR_STACK_AMOUNT))
    );

    public static final Item DARK_OAK_CHAIR = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "dark_oak_chair"),
            new BlockItem(ModBlocks.DARK_OAK_CHAIR, new FabricItemSettings().maxCount(CHAIR_STACK_AMOUNT))
    );

    public static final Item JUNGLE_CHAIR = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "jungle_chair"),
            new BlockItem(ModBlocks.JUNGLE_CHAIR, new FabricItemSettings().maxCount(CHAIR_STACK_AMOUNT))
    );

    public static final Item MANGROVE_CHAIR = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "mangrove_chair"),
            new BlockItem(ModBlocks.MANGROVE_CHAIR, new FabricItemSettings().maxCount(CHAIR_STACK_AMOUNT))
    );

    public static final Item OAK_CHAIR = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "oak_chair"),
            new BlockItem(ModBlocks.OAK_CHAIR, new FabricItemSettings().maxCount(CHAIR_STACK_AMOUNT))
    );

    public static final Item SPRUCE_CHAIR = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "spruce_chair"),
            new BlockItem(ModBlocks.SPRUCE_CHAIR, new FabricItemSettings().maxCount(CHAIR_STACK_AMOUNT))
    );

    public static final Item WARPED_CHAIR = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "warped_chair"),
            new BlockItem(ModBlocks.WARPED_CHAIR, new FabricItemSettings().maxCount(CHAIR_STACK_AMOUNT))
    );

    public static final Item ACACIA_TABLE = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "acacia_table"),
            new BlockItem(ModBlocks.ACACIA_TABLE, new FabricItemSettings().maxCount(TABLE_STACK_AMOUNT))
    );

    public static final Item BAMBOO_TABLE = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "bamboo_table"),
            new BlockItem(ModBlocks.BAMBOO_TABLE, new FabricItemSettings().maxCount(TABLE_STACK_AMOUNT))
    );

    public static final Item BIRCH_TABLE = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "birch_table"),
            new BlockItem(ModBlocks.BIRCH_TABLE, new FabricItemSettings().maxCount(TABLE_STACK_AMOUNT))
    );

    public static final Item CHERRY_TABLE = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "cherry_table"),
            new BlockItem(ModBlocks.CHERRY_TABLE, new FabricItemSettings().maxCount(TABLE_STACK_AMOUNT))
    );

    public static final Item CRIMSON_TABLE = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "crimson_table"),
            new BlockItem(ModBlocks.CRIMSON_TABLE, new FabricItemSettings().maxCount(TABLE_STACK_AMOUNT))
    );

    public static final Item DARK_OAK_TABLE = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "dark_oak_table"),
            new BlockItem(ModBlocks.DARK_OAK_TABLE, new FabricItemSettings().maxCount(TABLE_STACK_AMOUNT))
    );

    public static final Item JUNGLE_TABLE = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "jungle_table"),
            new BlockItem(ModBlocks.JUNGLE_TABLE, new FabricItemSettings().maxCount(TABLE_STACK_AMOUNT))
    );

    public static final Item MANGROVE_TABLE = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "mangrove_table"),
            new BlockItem(ModBlocks.MANGROVE_TABLE, new FabricItemSettings().maxCount(TABLE_STACK_AMOUNT))
    );

    public static final Item OAK_TABLE = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "oak_table"),
            new BlockItem(ModBlocks.OAK_TABLE, new FabricItemSettings().maxCount(TABLE_STACK_AMOUNT))
    );

    public static final Item SPRUCE_TABLE = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "spruce_table"),
            new BlockItem(ModBlocks.SPRUCE_TABLE, new FabricItemSettings().maxCount(TABLE_STACK_AMOUNT))
    );

    public static final Item WARPED_TABLE = Registry.register(
            Registries.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "warped_table"),
            new BlockItem(ModBlocks.WARPED_TABLE, new FabricItemSettings().maxCount(TABLE_STACK_AMOUNT))
    );

    private ModItems() {}

    public static List<Item> items() {

        return Arrays.asList(
                ACACIA_CHAIR,
                BAMBOO_CHAIR,
                BIRCH_CHAIR,
                CHERRY_CHAIR,
                CRIMSON_CHAIR,
                DARK_OAK_CHAIR,
                JUNGLE_CHAIR,
                MANGROVE_CHAIR,
                OAK_CHAIR,
                SPRUCE_CHAIR,
                WARPED_CHAIR,
                ACACIA_TABLE,
                BAMBOO_TABLE,
                BIRCH_TABLE,
                CHERRY_TABLE,
                CRIMSON_TABLE,
                DARK_OAK_TABLE,
                JUNGLE_TABLE,
                MANGROVE_TABLE,
                OAK_TABLE,
                SPRUCE_TABLE,
                WARPED_TABLE
        );
    }

    @SuppressWarnings("UnstableApiUsage")
    public static void addToInventory() {

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(content -> {

            content.addAfter(() -> Items.ACACIA_BUTTON, () -> ACACIA_CHAIR);
            content.addAfter(() -> Items.BAMBOO_BUTTON, () -> BAMBOO_CHAIR);
            content.addAfter(() -> Items.BIRCH_BUTTON, () -> BIRCH_CHAIR);
            content.addAfter(() -> Items.CHERRY_BUTTON, () -> CHERRY_CHAIR);
            content.addAfter(() -> Items.CRIMSON_BUTTON, () -> CRIMSON_CHAIR);
            content.addAfter(() -> Items.DARK_OAK_BUTTON, () -> DARK_OAK_CHAIR);
            content.addAfter(() -> Items.JUNGLE_BUTTON, () -> JUNGLE_CHAIR);
            content.addAfter(() -> Items.MANGROVE_BUTTON, () -> MANGROVE_CHAIR);
            content.addAfter(() -> Items.OAK_BUTTON, () -> OAK_CHAIR);
            content.addAfter(() -> Items.SPRUCE_BUTTON, () -> SPRUCE_CHAIR);
            content.addAfter(() -> Items.WARPED_BUTTON, () -> WARPED_CHAIR);
            content.addAfter(() -> ACACIA_CHAIR, () -> ACACIA_TABLE);
            content.addAfter(() -> BAMBOO_CHAIR, () -> BAMBOO_TABLE);
            content.addAfter(() -> BIRCH_CHAIR, () -> BIRCH_TABLE);
            content.addAfter(() -> CHERRY_CHAIR, () -> CHERRY_TABLE);
            content.addAfter(() -> CRIMSON_CHAIR, () -> CRIMSON_TABLE);
            content.addAfter(() -> DARK_OAK_CHAIR, () -> DARK_OAK_TABLE);
            content.addAfter(() -> JUNGLE_CHAIR, () -> JUNGLE_TABLE);
            content.addAfter(() -> MANGROVE_CHAIR, () -> MANGROVE_TABLE);
            content.addAfter(() -> OAK_CHAIR, () -> OAK_TABLE);
            content.addAfter(() -> SPRUCE_CHAIR, () -> SPRUCE_TABLE);
            content.addAfter(() -> WARPED_CHAIR, () -> WARPED_TABLE);
        });
    }

}
