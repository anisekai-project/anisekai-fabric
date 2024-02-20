package me.anisekai.inventories.constrained;

import me.anisekai.AnisekaiMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;

public class ConstrainedContainerScreenHandler extends ScreenHandler {


    public static ConstrainedContainerScreenHandler create(int syncId, PlayerInventory playerInventory, SidedInventory inventory) {

        return new ConstrainedContainerScreenHandler(
                AnisekaiMod.CONSTRAINED_INVENTORY,
                syncId,
                playerInventory,
                inventory
        );
    }

    public static ConstrainedContainerScreenHandler createConstrained(int syncId, PlayerInventory playerInventory) {

        return new ConstrainedContainerScreenHandler(
                AnisekaiMod.CONSTRAINED_INVENTORY,
                syncId,
                playerInventory,
                new SimpleInventory(27)
        );
    }

    private final int rows = 3;

    protected ConstrainedContainerScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory) {

        super(type, syncId);
        int k;
        int j;
        GenericContainerScreenHandler.checkSize(inventory, this.rows * 9);
        inventory.onOpen(playerInventory.player);
        int i = (this.rows - 4) * 18;
        for (j = 0; j < this.rows; ++j) {
            for (k = 0; k < 9; ++k) {
                this.addSlot(new ConstrainedSlot(inventory, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }
        for (j = 0; j < 3; ++j) {
            for (k = 0; k < 9; ++k) {
                this.addSlot(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));
            }
        }
        for (j = 0; j < 9; ++j) {
            this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 161 + i));
        }

    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {

        ItemStack itemStack = ItemStack.EMPTY;
        Slot      slot2     = this.slots.get(slot);
        if (slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if (slot < this.rows * 9 ? !this.insertItem(
                    itemStack2,
                    this.rows * 9,
                    this.slots.size(),
                    true
            ) : !this.insertItem(itemStack2, 0, this.rows * 9, false)) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }
        }
        return itemStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {

        return true;
    }

}
