package me.anisekai.utils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Hand;

import java.util.Optional;

public class HandUtils {

    public record HandContent(Hand hand, ItemStack stack) {

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

}
