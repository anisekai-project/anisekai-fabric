package me.anisekai.mixins;

import me.anisekai.registries.ModTags;
import me.anisekai.utils.HandUtils;
import net.fabricmc.fabric.impl.tag.convention.TagRegistration;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({AbstractBlock.class})
public abstract class CustomToolBlockInteractionMixin {

    @Inject(method = {"onUse"}, at = {@At("HEAD")}, cancellable = true)
    public void onUseBlock(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {

        Block block = state.getBlock();

        if (block instanceof CropBlock crop) {
            HandUtils.getHandContent(ItemTags.HOES, player, hand)
                     .ifPresent(content -> {
                         if (crop.isMature(state)) {

                             // The crop is harvestable
                             content.stack().damage(1, player, p -> p.sendToolBreakStatus(content.hand()));

                             if (world.isClient) {
                                 block.onBreak(world, pos, state, player);
                                 return;
                             }

                             world.setBlockState(pos, crop.withAge(0), Block.NOTIFY_LISTENERS);
                             Block.dropStacks(state, world, pos);
                             cir.setReturnValue(ActionResult.SUCCESS);
                         }
                     });
        } else if (block instanceof FarmlandBlock) {
            HandUtils.getHandContent(ItemTags.SHOVELS, player, hand)
                     .ifPresent(content -> {
                         content.stack().damage(1, player, p -> p.sendToolBreakStatus(content.hand()));
                         world.setBlockState(pos, Blocks.DIRT.getDefaultState());
                         world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0f, 1.0f);
                         cir.setReturnValue(ActionResult.SUCCESS);
                     });
        }
    }

}
