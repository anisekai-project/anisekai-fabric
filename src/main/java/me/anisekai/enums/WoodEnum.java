package me.anisekai.enums;

import me.anisekai.interfaces.Nameable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public enum WoodEnum implements Nameable {

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
    WARPED;

    public String getName() {

        return this.name().toLowerCase();
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

}
