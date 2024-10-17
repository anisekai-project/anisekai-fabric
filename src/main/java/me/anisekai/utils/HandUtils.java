package me.anisekai.utils;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Hand;
import net.minecraft.world.WorldView;

import java.util.Optional;

public final class HandUtils {

    private HandUtils() {}

    public record HandContent(Hand hand, ItemStack stack) {

        public EquipmentSlot getSlot() {

            return switch (this.hand) {
                case MAIN_HAND -> EquipmentSlot.MAINHAND;
                case OFF_HAND -> EquipmentSlot.OFFHAND;
            };
        }

    }

    public static ItemStack getHandStack(PlayerEntity entity, Hand hand) {

        return switch (hand) {
            case MAIN_HAND -> entity.getMainHandStack();
            case OFF_HAND -> entity.getOffHandStack();
        };
    }

    public static Optional<HandContent> getHandContent(TagKey<Item> tag, PlayerEntity player, Hand currentHand) {

        ItemStack mainHandStack = player.getMainHandStack();
        ItemStack offHandStack  = player.getOffHandStack();

        if (!mainHandStack.isIn(tag) && !offHandStack.isIn(tag)) {
            return Optional.empty();
        }


        if (mainHandStack.isIn(tag) && offHandStack.isIn(tag) && currentHand == Hand.OFF_HAND) { // Priority on main hand
            return Optional.empty();
        }

        if (mainHandStack.isIn(tag) && currentHand == Hand.MAIN_HAND) {
            return Optional.of(new HandContent(Hand.MAIN_HAND, mainHandStack));
        }

        if (offHandStack.isIn(tag) && currentHand == Hand.OFF_HAND) {
            return Optional.of(new HandContent(Hand.OFF_HAND, offHandStack));
        }

        return Optional.empty();
    }

    public static float getMiningMultiplier(ItemStack stack) {

        return stack.getItem() instanceof MiningToolItem miningItem ?
                miningItem.getMaterial().getMiningSpeedMultiplier() :
                1.0f;
    }

    public static int getEfficiencyMiningValue(WorldView world, ItemStack stack) {

        int efficiency = AnisekaiEnchantmentHelper.getEnchantmentLevel(world, stack, Enchantments.EFFICIENCY);
        return efficiency * efficiency + 1;
    }

}
