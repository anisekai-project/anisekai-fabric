package me.anisekai.utils;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.WorldView;

/**
 * Helper class for enchants because seems to love making things overcomplicated.
 */
public final class AnisekaiEnchantmentHelper {

    private AnisekaiEnchantmentHelper() {}

    /**
     * Retrieve the enchantment level for a given enchantment and stack.
     *
     * @param world
     *         WorldView in which the enchantment registry will be checked
     * @param stack
     *         Stack of which the enchantment presence should be checked
     * @param enchantment
     *         Enchantment from which the level should be checked.
     *
     * @return The enchant level, or 0 is the enchantment was not found
     */
    public static int getEnchantmentLevel(WorldView world, ItemStack stack, RegistryKey<Enchantment> enchantment) {

        Registry<Enchantment> enchantments = world.getRegistryManager().get(RegistryKeys.ENCHANTMENT);

        return enchantments.getEntry(enchantment.getValue())
                           .map(enchantmentReference -> EnchantmentHelper.getLevel(enchantmentReference, stack))
                           .orElse(0);
    }

    /**
     * Check the presence of a given enchantment on a stack.
     *
     * @param world
     *         WorldView in which the enchantment registry will be checked
     * @param stack
     *         Stack of which the enchantment presence should be checked
     * @param enchantment
     *         Enchantment that should be checked.
     *
     * @return True if the enchant was present with a level above 0, false otherwise
     */
    public static boolean hasEnchant(WorldView world, ItemStack stack, RegistryKey<Enchantment> enchantment) {

        return getEnchantmentLevel(world, stack, enchantment) > 0;
    }

}
