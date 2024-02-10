package me.anisekai.registries;

import me.anisekai.registries.blocks.*;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public final class ModBlocks {

    private ModBlocks() {}

    public static List<Block> blocks() {

        List<Block> blocks = new ArrayList<>();
        blocks.addAll(ModBlockChairs.blocks());
        blocks.addAll(ModBlockHalfSlabs.blocks());
        blocks.addAll(ModBlockStairs.blocks());
        blocks.addAll(ModBlockTables.blocks());
        blocks.addAll(ModBlockStools.blocks());
        blocks.addAll(ModBlockNightstands.blocks());
        return blocks;
    }

}
