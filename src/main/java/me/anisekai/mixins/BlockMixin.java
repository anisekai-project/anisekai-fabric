package me.anisekai.mixins;

import me.anisekai.mixins.accessors.BlockEntityMixin;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.List;

@Mixin({Block.class})
public abstract class BlockMixin {

    @Inject(
            method = "dropStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)V",
            at = {@At("HEAD")},
            cancellable = true)
    private static void dropStacks(BlockState state, World world, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack tool, CallbackInfo ci) {

        if (world instanceof ServerWorld) {
            List<ItemStack> droppedStacks = getDroppedStacks(
                    state,
                    (ServerWorld) world,
                    pos,
                    blockEntity,
                    entity,
                    tool
            );

            droppedStacks.forEach(stack -> Block.dropStack(world, pos, stack));
            state.onStacksDropped((ServerWorld) world, pos, tool, mayDropExp(blockEntity, tool));
            ci.cancel();
        }
    }

    @Unique
    private static List<ItemStack> getDroppedStacks(BlockState state, ServerWorld world, BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity entity, ItemStack stack) {

        if (blockEntity instanceof MobSpawnerBlockEntity spawner) {
            return overrideDrops(ItemTags.PICKAXES, stack, Items.SPAWNER, spawner);
        }
        return Block.getDroppedStacks(state, world, pos, blockEntity, entity, stack);
    }

    @Unique
    private static List<ItemStack> overrideDrops(TagKey<Item> requireTool, ItemStack tool, Item resultItem, BlockEntity blockEntity) {
        // Is the item a silk touch pickaxe ?
        if (!tool.isIn(requireTool) || !EnchantmentHelper.hasSilkTouch(tool)) {
            // Nope, let's exit there
            return Collections.emptyList();
        }

        BlockEntityMixin accessor = (BlockEntityMixin) blockEntity;
        NbtCompound      nbt      = new NbtCompound();
        accessor.invokeWriteNbt(nbt);

        ItemStack stack = new ItemStack(resultItem);
        stack.getOrCreateNbt().put(BlockItem.BLOCK_ENTITY_TAG_KEY, nbt);
        return Collections.singletonList(stack);
    }

    @Unique
    private static boolean mayDropExp(BlockEntity blockEntity, ItemStack tool) {

        if (blockEntity instanceof MobSpawnerBlockEntity) {
            return !tool.isIn(ItemTags.PICKAXES) || !EnchantmentHelper.hasSilkTouch(tool);
        }
        return true;
    }

}
