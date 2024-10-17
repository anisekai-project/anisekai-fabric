package me.anisekai.enums;

import me.anisekai.AnisekaiMod;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public enum WoodType {

    ACACIA("acacia"),
    BAMBOO("bamboo"),
    BIRCH("birch"),
    CHERRY("cherry"),
    CRIMSON("crimson"),
    DARK_OAK("dark_oak"),
    JUNGLE("jungle"),
    MANGROVE("mangrove"),
    OAK("oak"),
    SPRUCE("spruce"),
    WARPED("warped");

    public static <T> Map<WoodType, T> generate(Function<WoodType, T> factory) {

        Map<WoodType, T> map = new HashMap<>();
        for (WoodType value : WoodType.values()) {
            map.put(value, factory.apply(value));
        }
        return map;
    }

    private final String id;

    WoodType(String id) {

        this.id = id;
    }

    public String itemNameOf(String name) {

        return "%s_%s".formatted(this.id, name);
    }

    public Identifier identifierOf(String name) {

        return Identifier.of(AnisekaiMod.MOD_ID, this.itemNameOf(name));
    }

    public Block asLog() {

        return switch (this) {
            case ACACIA -> Blocks.ACACIA_LOG;
            case BAMBOO -> Blocks.BAMBOO_BLOCK;
            case BIRCH -> Blocks.BIRCH_LOG;
            case CHERRY -> Blocks.CHERRY_LOG;
            case CRIMSON -> Blocks.CRIMSON_STEM;
            case DARK_OAK -> Blocks.DARK_OAK_LOG;
            case JUNGLE -> Blocks.JUNGLE_LOG;
            case MANGROVE -> Blocks.MANGROVE_LOG;
            case OAK -> Blocks.OAK_LOG;
            case SPRUCE -> Blocks.SPRUCE_LOG;
            case WARPED -> Blocks.WARPED_STEM;
        };
    }

    public Block asLeaves() {

        return switch (this) {
            case ACACIA -> Blocks.ACACIA_LEAVES;
            case BAMBOO -> null;
            case BIRCH -> Blocks.BIRCH_LEAVES;
            case CHERRY -> Blocks.CHERRY_LEAVES;
            case CRIMSON -> Blocks.CRIMSON_FUNGUS;
            case DARK_OAK -> Blocks.NETHER_WART_BLOCK;
            case JUNGLE -> Blocks.JUNGLE_LEAVES;
            case MANGROVE -> Blocks.MANGROVE_LEAVES;
            case OAK -> Blocks.OAK_LEAVES;
            case SPRUCE -> Blocks.SPRUCE_LEAVES;
            case WARPED -> Blocks.WARPED_WART_BLOCK;
        };
    }

    public Block asWood() {

        return switch (this) {
            case ACACIA -> Blocks.ACACIA_WOOD;
            case BAMBOO -> null;
            case BIRCH -> Blocks.BIRCH_WOOD;
            case CHERRY -> Blocks.CHERRY_WOOD;
            case CRIMSON -> Blocks.CRIMSON_HYPHAE;
            case DARK_OAK -> Blocks.DARK_OAK_WOOD;
            case JUNGLE -> Blocks.JUNGLE_WOOD;
            case MANGROVE -> Blocks.MANGROVE_WOOD;
            case OAK -> Blocks.OAK_WOOD;
            case SPRUCE -> Blocks.SPRUCE_WOOD;
            case WARPED -> Blocks.WARPED_HYPHAE;
        };
    }

    public Block asStrippedLog() {

        return switch (this) {
            case ACACIA -> Blocks.STRIPPED_ACACIA_LOG;
            case BAMBOO -> null;
            case BIRCH -> Blocks.STRIPPED_BIRCH_LOG;
            case CHERRY -> Blocks.STRIPPED_CHERRY_LOG;
            case CRIMSON -> Blocks.STRIPPED_CRIMSON_STEM;
            case DARK_OAK -> Blocks.STRIPPED_DARK_OAK_LOG;
            case JUNGLE -> Blocks.STRIPPED_JUNGLE_LOG;
            case MANGROVE -> Blocks.STRIPPED_MANGROVE_LOG;
            case OAK -> Blocks.STRIPPED_OAK_LOG;
            case SPRUCE -> Blocks.STRIPPED_SPRUCE_LOG;
            case WARPED -> Blocks.STRIPPED_WARPED_STEM;
        };
    }

    public Block asStrippedWood() {

        return switch (this) {
            case ACACIA -> Blocks.STRIPPED_ACACIA_WOOD;
            case BAMBOO -> null;
            case BIRCH -> Blocks.STRIPPED_BIRCH_WOOD;
            case CHERRY -> Blocks.STRIPPED_CHERRY_WOOD;
            case CRIMSON -> Blocks.STRIPPED_CRIMSON_HYPHAE;
            case DARK_OAK -> Blocks.STRIPPED_DARK_OAK_WOOD;
            case JUNGLE -> Blocks.STRIPPED_JUNGLE_WOOD;
            case MANGROVE -> Blocks.STRIPPED_MANGROVE_WOOD;
            case OAK -> Blocks.STRIPPED_OAK_WOOD;
            case SPRUCE -> Blocks.STRIPPED_SPRUCE_WOOD;
            case WARPED -> Blocks.STRIPPED_WARPED_HYPHAE;
        };
    }

    public Block asPlanks() {

        return switch (this) {
            case ACACIA -> Blocks.ACACIA_PLANKS;
            case BAMBOO -> Blocks.BAMBOO_PLANKS;
            case BIRCH -> Blocks.BIRCH_PLANKS;
            case CHERRY -> Blocks.CHERRY_PLANKS;
            case CRIMSON -> Blocks.CRIMSON_PLANKS;
            case DARK_OAK -> Blocks.DARK_OAK_PLANKS;
            case JUNGLE -> Blocks.JUNGLE_PLANKS;
            case MANGROVE -> Blocks.MANGROVE_PLANKS;
            case OAK -> Blocks.OAK_PLANKS;
            case SPRUCE -> Blocks.SPRUCE_PLANKS;
            case WARPED -> Blocks.WARPED_PLANKS;
        };
    }

    public Block asStairs() {

        return switch (this) {
            case ACACIA -> Blocks.ACACIA_STAIRS;
            case BAMBOO -> Blocks.BAMBOO_STAIRS;
            case BIRCH -> Blocks.BIRCH_STAIRS;
            case CHERRY -> Blocks.CHERRY_STAIRS;
            case CRIMSON -> Blocks.CRIMSON_STAIRS;
            case DARK_OAK -> Blocks.DARK_OAK_STAIRS;
            case JUNGLE -> Blocks.JUNGLE_STAIRS;
            case MANGROVE -> Blocks.MANGROVE_STAIRS;
            case OAK -> Blocks.OAK_STAIRS;
            case SPRUCE -> Blocks.SPRUCE_STAIRS;
            case WARPED -> Blocks.WARPED_STAIRS;
        };
    }

    public Block asSlab() {

        return switch (this) {
            case ACACIA -> Blocks.ACACIA_SLAB;
            case BAMBOO -> Blocks.BAMBOO_SLAB;
            case BIRCH -> Blocks.BIRCH_SLAB;
            case CHERRY -> Blocks.CHERRY_SLAB;
            case CRIMSON -> Blocks.CRIMSON_SLAB;
            case DARK_OAK -> Blocks.DARK_OAK_SLAB;
            case JUNGLE -> Blocks.JUNGLE_SLAB;
            case MANGROVE -> Blocks.MANGROVE_SLAB;
            case OAK -> Blocks.OAK_SLAB;
            case SPRUCE -> Blocks.SPRUCE_SLAB;
            case WARPED -> Blocks.WARPED_SLAB;
        };
    }

    public Block asFence() {

        return switch (this) {
            case ACACIA -> Blocks.ACACIA_FENCE;
            case BAMBOO -> Blocks.BAMBOO_FENCE;
            case BIRCH -> Blocks.BIRCH_FENCE;
            case CHERRY -> Blocks.CHERRY_FENCE;
            case CRIMSON -> Blocks.CRIMSON_FENCE;
            case DARK_OAK -> Blocks.DARK_OAK_FENCE;
            case JUNGLE -> Blocks.JUNGLE_FENCE;
            case MANGROVE -> Blocks.MANGROVE_FENCE;
            case OAK -> Blocks.OAK_FENCE;
            case SPRUCE -> Blocks.SPRUCE_FENCE;
            case WARPED -> Blocks.WARPED_FENCE;
        };
    }

    public Block asFenceGate() {

        return switch (this) {
            case ACACIA -> Blocks.ACACIA_FENCE_GATE;
            case BAMBOO -> Blocks.BAMBOO_FENCE_GATE;
            case BIRCH -> Blocks.BIRCH_FENCE_GATE;
            case CHERRY -> Blocks.CHERRY_FENCE_GATE;
            case CRIMSON -> Blocks.CRIMSON_FENCE_GATE;
            case DARK_OAK -> Blocks.DARK_OAK_FENCE_GATE;
            case JUNGLE -> Blocks.JUNGLE_FENCE_GATE;
            case MANGROVE -> Blocks.MANGROVE_FENCE_GATE;
            case OAK -> Blocks.OAK_FENCE_GATE;
            case SPRUCE -> Blocks.SPRUCE_FENCE_GATE;
            case WARPED -> Blocks.WARPED_FENCE_GATE;
        };
    }

    public Block asDoor() {

        return switch (this) {
            case ACACIA -> Blocks.ACACIA_DOOR;
            case BAMBOO -> Blocks.BAMBOO_DOOR;
            case BIRCH -> Blocks.BIRCH_DOOR;
            case CHERRY -> Blocks.CHERRY_DOOR;
            case CRIMSON -> Blocks.CRIMSON_DOOR;
            case DARK_OAK -> Blocks.DARK_OAK_DOOR;
            case JUNGLE -> Blocks.JUNGLE_DOOR;
            case MANGROVE -> Blocks.MANGROVE_DOOR;
            case OAK -> Blocks.OAK_DOOR;
            case SPRUCE -> Blocks.SPRUCE_DOOR;
            case WARPED -> Blocks.WARPED_DOOR;
        };
    }

    public Block asTrapdoor() {

        return switch (this) {
            case ACACIA -> Blocks.ACACIA_TRAPDOOR;
            case BAMBOO -> Blocks.BAMBOO_TRAPDOOR;
            case BIRCH -> Blocks.BIRCH_TRAPDOOR;
            case CHERRY -> Blocks.CHERRY_TRAPDOOR;
            case CRIMSON -> Blocks.CRIMSON_TRAPDOOR;
            case DARK_OAK -> Blocks.DARK_OAK_TRAPDOOR;
            case JUNGLE -> Blocks.JUNGLE_TRAPDOOR;
            case MANGROVE -> Blocks.MANGROVE_TRAPDOOR;
            case OAK -> Blocks.OAK_TRAPDOOR;
            case SPRUCE -> Blocks.SPRUCE_TRAPDOOR;
            case WARPED -> Blocks.WARPED_TRAPDOOR;
        };
    }

    public Block asPressurePlate() {

        return switch (this) {
            case ACACIA -> Blocks.ACACIA_PRESSURE_PLATE;
            case BAMBOO -> Blocks.BAMBOO_PRESSURE_PLATE;
            case BIRCH -> Blocks.BIRCH_PRESSURE_PLATE;
            case CHERRY -> Blocks.CHERRY_PRESSURE_PLATE;
            case CRIMSON -> Blocks.CRIMSON_PRESSURE_PLATE;
            case DARK_OAK -> Blocks.DARK_OAK_PRESSURE_PLATE;
            case JUNGLE -> Blocks.JUNGLE_PRESSURE_PLATE;
            case MANGROVE -> Blocks.MANGROVE_PRESSURE_PLATE;
            case OAK -> Blocks.OAK_PRESSURE_PLATE;
            case SPRUCE -> Blocks.SPRUCE_PRESSURE_PLATE;
            case WARPED -> Blocks.WARPED_PRESSURE_PLATE;
        };
    }

    public Block asButton() {

        return switch (this) {
            case ACACIA -> Blocks.ACACIA_BUTTON;
            case BAMBOO -> Blocks.BAMBOO_BUTTON;
            case BIRCH -> Blocks.BIRCH_BUTTON;
            case CHERRY -> Blocks.CHERRY_BUTTON;
            case CRIMSON -> Blocks.CRIMSON_BUTTON;
            case DARK_OAK -> Blocks.DARK_OAK_BUTTON;
            case JUNGLE -> Blocks.JUNGLE_BUTTON;
            case MANGROVE -> Blocks.MANGROVE_BUTTON;
            case OAK -> Blocks.OAK_BUTTON;
            case SPRUCE -> Blocks.SPRUCE_BUTTON;
            case WARPED -> Blocks.WARPED_BUTTON;
        };
    }

    public Item asSapling() {

        return switch (this) {
            case ACACIA -> Items.ACACIA_SAPLING;
            case BAMBOO -> Items.BAMBOO;
            case BIRCH -> Items.BIRCH_SAPLING;
            case CHERRY -> Items.CHERRY_SAPLING;
            case CRIMSON -> Items.CRIMSON_FUNGUS;
            case DARK_OAK -> Items.DARK_OAK_SAPLING;
            case JUNGLE -> Items.JUNGLE_SAPLING;
            case MANGROVE -> Items.MANGROVE_PROPAGULE;
            case OAK -> Items.OAK_SAPLING;
            case SPRUCE -> Items.SPRUCE_SAPLING;
            case WARPED -> Items.WARPED_FUNGUS;
        };
    }

}
