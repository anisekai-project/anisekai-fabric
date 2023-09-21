package me.anisekai.enums;

import me.anisekai.AnisekaiMod;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;

public enum WoodType {

    OAK("oak", Blocks.OAK_LOG, Blocks.OAK_BUTTON),
    SPRUCE("spruce", Blocks.SPRUCE_LOG, Blocks.SPRUCE_BUTTON),
    BIRCH("birch", Blocks.BIRCH_LOG, Blocks.BIRCH_BUTTON),
    JUNGLE("jungle", Blocks.JUNGLE_LOG, Blocks.JUNGLE_BUTTON),
    ACACIA("acacia", Blocks.ACACIA_LOG, Blocks.ACACIA_BUTTON),
    DARK_OAK("dark_oak", Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_BUTTON),
    MANGROVE("mangrove", Blocks.MANGROVE_LOG, Blocks.MANGROVE_BUTTON),
    CHERRY("cherry", Blocks.CHERRY_LOG, Blocks.CHERRY_BUTTON),
    BAMBOO("bamboo", Blocks.BAMBOO_BLOCK, Blocks.BAMBOO_BUTTON),
    CRIMSON("crimson", Blocks.CRIMSON_STEM, Blocks.CRIMSON_BUTTON),
    WARPED("warped", Blocks.WARPED_STEM, Blocks.WARPED_BUTTON);

    private final String id;
    private final Block  log;
    private final Block  button;

    WoodType(String id, Block log, Block button) {

        this.id  = id;
        this.log = log;
        this.button = button;
    }

    public Block getLog() {

        return this.log;
    }

    public Block getButton() {

        return this.button;
    }

    public Identifier getChairIdentifier() {

        return new Identifier(AnisekaiMod.MOD_ID, String.format("%s_chair", this.id));
    }
}
