package me.anisekai.mixins;

import me.anisekai.blockentities.FishingBasketBlockEntity;
import me.anisekai.entities.seat.InvisibleSeatEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin {

    @Inject(
            method = "use(Lnet/minecraft/item/ItemStack;)I",
            at = {
                    @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/advancement/criterion/FishingRodHookedCriterion;trigger(Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/projectile/FishingBobberEntity;Ljava/util/Collection;)V",
                            ordinal = 1,
                            shift = At.Shift.AFTER
                    )
            },
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void onFished(ItemStack usedItem, CallbackInfoReturnable<Integer> cir, PlayerEntity playerEntity, int i, LootContextParameterSet lootContextParameterSet, LootTable lootTable, List<ItemStack> list) {

        if (playerEntity.getVehicle() instanceof InvisibleSeatEntity seat) {
            BlockPos    pos         = seat.getBlockPos();
            BlockEntity blockEntity = playerEntity.getWorld().getBlockEntity(pos);

            if (blockEntity instanceof FishingBasketBlockEntity fishingBasket) {
                for (ItemStack stack : list) {
                    playerEntity.sendMessage(Text.translatable(
                            "anisekai.message.fishing",
                            stack.getItem().getName(),
                            stack.getCount()
                    ));

                    if (stack.isIn(ItemTags.FISHES)) {
                        if (fishingBasket.canFit(stack)) {
                            playerEntity.increaseStat(Stats.FISH_CAUGHT, stack.getCount());
                            fishingBasket.insert(stack);
                        }
                    }
                }
            }
        }
    }

}
