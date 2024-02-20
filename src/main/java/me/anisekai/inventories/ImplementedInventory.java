package me.anisekai.inventories;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public interface ImplementedInventory extends SidedInventory, NamedScreenHandlerFactory {

    /**
     * Retrieves the item list of this inventory. Must return the same instance every time it's called.
     */
    DefaultedList<ItemStack> getItems();

    /**
     * Returns the inventory size.
     */
    @Override
    default int size() {

        return this.getItems().size();
    }

    /**
     * Checks if the inventory is empty.
     *
     * @return true if this inventory has only empty stacks, false otherwise.
     */
    @Override
    default boolean isEmpty() {

        for (int i = 0; i < this.size(); i++) {
            ItemStack stack = this.getStack(i);
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retrieves the item in the slot.
     */
    @Override
    default ItemStack getStack(int slot) {

        return this.getItems().get(slot);
    }

    /**
     * Removes items from an inventory slot.
     *
     * @param slot
     *         The slot to remove from.
     * @param count
     *         How many items to remove. If there are fewer items in the slot than what are requested, takes all items
     *         in that slot.
     */
    @Override
    default ItemStack removeStack(int slot, int count) {

        ItemStack result = Inventories.splitStack(this.getItems(), slot, count);
        if (!result.isEmpty()) {
            this.markDirty();
        }
        return result;
    }

    /**
     * Removes all items from an inventory slot.
     *
     * @param slot
     *         The slot to remove from.
     */
    @Override
    default ItemStack removeStack(int slot) {

        return Inventories.removeStack(this.getItems(), slot);
    }

    /**
     * Replaces the current stack in an inventory slot with the provided stack.
     *
     * @param slot
     *         The inventory slot of which to replace the itemstack.
     * @param stack
     *         The replacing itemstack. If the stack is too big for this inventory
     *         ({@link Inventory#getMaxCountPerStack()}), it gets resized to this inventory's maximum amount.
     */
    @Override
    default void setStack(int slot, ItemStack stack) {

        this.getItems().set(slot, stack);
        if (stack.getCount() > stack.getMaxCount()) {
            stack.setCount(stack.getMaxCount());
        }
    }

    /**
     * Clears the inventory.
     */
    @Override
    default void clear() {

        this.getItems().clear();
    }

    /**
     * Marks the state as dirty. Must be called after changes in the inventory, so that the game can properly save the
     * inventory contents and notify neighboring blocks of inventory changes.
     */
    @Override
    default void markDirty() {
        // Override if you want behavior.
    }

    /**
     * @return true if the player can use the inventory, false otherwise.
     */
    @Override
    default boolean canPlayerUse(PlayerEntity player) {

        return true;
    }

    /**
     * Try to add the provided stack into this inventory.
     *
     * @param stack
     *         The stack to add in the inventory.
     *
     * @return Stack representing the items that couldn't be added to this inventory due to missing space. It is the
     *         same instance as the one provided input.
     */
    default ItemStack insert(ItemStack stack) {

        for (int i = 0; i < this.size(); i++) {
            ItemStack invStack = this.getStack(i);

            if (ItemStack.canCombine(invStack, stack)) {

                if (invStack.getCount() + stack.getCount() <= invStack.getMaxCount()) {
                    invStack.setCount(invStack.getCount() + stack.getCount());
                    stack.setCount(0);
                    return stack;
                }

                int slotSpaceLeft = invStack.getMaxCount() - invStack.getCount();

                invStack.setCount(invStack.getMaxCount());
                stack.decrement(slotSpaceLeft);
            } else if (invStack.getItem() == Items.AIR) {
                this.setStack(i, stack.copy());
                stack.setCount(0);
                return stack;
            }
        }

        return stack;
    }

    /**
     * Check if the provided stack can FULLY fit within this inventory.
     *
     * @param stack
     *         The stack to check
     *
     * @return True if, and only if, all items in the stack can fit in this inventory. Returns false if after storing,
     *         some or all original items in the stack are left
     */
    default boolean canFit(ItemStack stack) {

        ItemStack copy = stack.copy();

        for (int i = 0; i < this.size(); i++) {
            ItemStack invStack = this.getStack(i);

            if (ItemStack.canCombine(invStack, copy)) {

                if (invStack.getCount() + stack.getCount() <= invStack.getMaxCount()) {
                    return true;
                }

                int slotSpaceLeft = invStack.getMaxCount() - invStack.getCount();
                copy.decrement(slotSpaceLeft);
            } else if (invStack.getItem() == Items.AIR) {
                return true;
            }
        }

        return false;
    }

    @Override
    default int[] getAvailableSlots(Direction side) {
        int[] arr = new int[this.size()];
        for (int i = 0; i < this.size(); i++) {
            arr[i] = i;
        }
        return arr;
    }

    @Override
    default boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {

        return false;
    }

    @Override
    default boolean canExtract(int slot, ItemStack stack, Direction dir) {

        return false;
    }

}
