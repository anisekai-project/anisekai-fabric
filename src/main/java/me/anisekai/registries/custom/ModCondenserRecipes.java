package me.anisekai.registries.custom;

import me.anisekai.AnisekaiMod;
import me.anisekai.blocks.CondenserBlock;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class ModCondenserRecipes {

    private static void createLogRecipe(String name, Item log, Item sapling) {

        CondenserBlock.registerConsuming(
                new Identifier(AnisekaiMod.MOD_ID, "condenser/log/" + name),
                new ItemStack(log),
                stack -> stack.isOf(sapling),
                stack -> stack.isOf(Items.DIRT),
                stack -> stack.isOf(Items.BONE_MEAL),
                stack -> stack.isIn(ItemTags.AXES),
                new ItemStack(log, 6),
                Blocks.OAK_LOG.getHardness() * CondenserBlock.CONDENSER_LIMITER_FACTOR,
                10,
                SoundEvents.BLOCK_WOOD_HIT,
                SoundEvents.BLOCK_WOOD_BREAK,
                1,
                0
        );
    }

    private static void createLeavesRecipe(String name, Item leaves, Item sapling) {

        CondenserBlock.registerConsuming(
                new Identifier(AnisekaiMod.MOD_ID, "condenser/leaves/" + name),
                new ItemStack(leaves),
                stack -> stack.isOf(sapling),
                stack -> stack.isOf(Items.DIRT),
                stack -> stack.isOf(Items.BONE_MEAL),
                stack -> stack.isOf(Items.SHEARS),
                new ItemStack(leaves, 12),
                Blocks.DARK_OAK_LEAVES.getHardness() * CondenserBlock.CONDENSER_LIMITER_FACTOR,
                5,
                SoundEvents.BLOCK_AZALEA_LEAVES_HIT,
                SoundEvents.BLOCK_AZALEA_LEAVES_BREAK,
                1,
                0
        );
    }


    public static void init() {

        CondenserBlock.registerGenerative(
                new Identifier(AnisekaiMod.MOD_ID, "condenser/cobblestone"),
                new ItemStack(Items.COBBLESTONE),
                stack -> stack.isOf(Items.LAVA_BUCKET),
                stack -> stack.isOf(Items.WATER_BUCKET),
                stack -> false,
                stack -> stack.isIn(ItemTags.PICKAXES),
                new ItemStack(Items.COBBLESTONE),
                Blocks.COBBLESTONE.getHardness() * CondenserBlock.CONDENSER_LIMITER_FACTOR,
                0,
                SoundEvents.BLOCK_STONE_HIT,
                SoundEvents.BLOCK_STONE_BREAK
        );

        CondenserBlock.registerGenerative(
                new Identifier(AnisekaiMod.MOD_ID, "condenser/obsidian"),
                new ItemStack(Items.OBSIDIAN),
                stack -> stack.isOf(Items.LAVA_BUCKET),
                stack -> stack.isOf(Items.WATER_BUCKET),
                stack -> false,
                stack -> stack.isOf(Items.DIAMOND_PICKAXE) || stack.isOf(Items.NETHERITE_PICKAXE),
                new ItemStack(Items.OBSIDIAN),
                Blocks.OBSIDIAN.getHardness() * CondenserBlock.CONDENSER_LIMITER_FACTOR,
                0,
                SoundEvents.BLOCK_STONE_HIT,
                SoundEvents.BLOCK_STONE_BREAK
        );

        createLogRecipe("acacia", Items.ACACIA_LOG, Items.ACACIA_SAPLING);
        createLogRecipe("birch", Items.BIRCH_LOG, Items.BIRCH_SAPLING);
        createLogRecipe("cherry", Items.CHERRY_LOG, Items.CHERRY_SAPLING);
        createLogRecipe("dark_oak", Items.DARK_OAK_LOG, Items.DARK_OAK_SAPLING);
        createLogRecipe("jungle", Items.JUNGLE_LOG, Items.JUNGLE_SAPLING);
        createLogRecipe("mangrove", Items.MANGROVE_LOG, Items.MANGROVE_PROPAGULE);
        createLogRecipe("oak", Items.OAK_LOG, Items.OAK_SAPLING);
        createLogRecipe("spruce", Items.SPRUCE_LOG, Items.SPRUCE_SAPLING);

        createLeavesRecipe("acacia", Items.ACACIA_LEAVES, Items.ACACIA_SAPLING);
        createLeavesRecipe("birch", Items.BIRCH_LEAVES, Items.BIRCH_SAPLING);
        createLeavesRecipe("cherry", Items.CHERRY_LEAVES, Items.CHERRY_SAPLING);
        createLeavesRecipe("dark_oak", Items.DARK_OAK_LEAVES, Items.DARK_OAK_SAPLING);
        createLeavesRecipe("jungle", Items.JUNGLE_LEAVES, Items.JUNGLE_SAPLING);
        createLeavesRecipe("mangrove", Items.MANGROVE_LEAVES, Items.MANGROVE_PROPAGULE);
        createLeavesRecipe("oak", Items.OAK_LEAVES, Items.OAK_SAPLING);
        createLeavesRecipe("spruce", Items.SPRUCE_LEAVES, Items.SPRUCE_SAPLING);


        CondenserBlock.registerConsuming(
                new Identifier(AnisekaiMod.MOD_ID, "condenser/dirt"),
                new ItemStack(Items.DIRT),
                stack -> stack.isOf(Items.DIRT),
                stack -> stack.isOf(Items.GRAVEL),
                stack -> false,
                stack -> stack.isIn(ItemTags.SHOVELS),
                new ItemStack(Items.DIRT, 2),
                Blocks.DIRT.getHardness() * CondenserBlock.CONDENSER_LIMITER_FACTOR,
                0,
                SoundEvents.BLOCK_ROOTED_DIRT_HIT,
                SoundEvents.BLOCK_ROOTED_DIRT_BREAK,
                1,
                1
        );
    }

}
