package me.anisekai.screen.common;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class ConstrainedSlot extends Slot {

    public ConstrainedSlot(Inventory inventory, int index, int x, int y) {

        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {

        return this.inventory.isValid(this.getIndex(), stack);
    }

}
