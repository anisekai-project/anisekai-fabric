package me.anisekai.screen.condenser;

import me.anisekai.inventories.slots.OutputSlot;
import me.anisekai.registries.ModScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

import static me.anisekai.blockentities.CondenserBlockEntity.*;

public class CondenserScreenHandler extends ScreenHandler {

    public static CondenserScreenHandler create(int syncId, PlayerInventory playerInventory) {

        return create(syncId, playerInventory, new SimpleInventory(6), new ArrayPropertyDelegate(3));
    }

    public static CondenserScreenHandler create(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {

        return new CondenserScreenHandler(
                syncId,
                playerInventory,
                inventory,
                new ArrayPropertyDelegate(3)
        );
    }

    private final PropertyDelegate propertyDelegate;
    private final Inventory        inventory;

    private final Slot condensedSlot;
    private final Slot ingredientOneSlot;
    private final Slot ingredientTwoSlot;
    private final Slot boosterSlot;
    private final Slot toolSlot;
    private final Slot outputSlot;

    public CondenserScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {

        super(ModScreenHandler.CONDENSER, syncId);
        this.inventory        = inventory;
        this.propertyDelegate = propertyDelegate;

        this.condensedSlot     = this.addSlot(new Slot(this.inventory, CONDENSED_BLOCK_SLOT, 13, 25));
        this.ingredientOneSlot = this.addSlot(new Slot(this.inventory, INGREDIENT_ONE_SLOT, 39, 17));
        this.ingredientTwoSlot = this.addSlot(new Slot(this.inventory, INGREDIENT_TWO_SLOT, 59, 17));
        this.boosterSlot       = this.addSlot(new Slot(this.inventory, BOOSTER_SLOT, 49, 36));
        this.toolSlot          = this.addSlot(new Slot(this.inventory, TOOL_SLOT, 85, 25));
        this.outputSlot        = this.addSlot(new OutputSlot(this.inventory, OUTPUT_SLOT, 144, 25));

        // <editor-fold desc="Draw player inventory">
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 67 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 125));
        }
        // </editor-fold>

        this.addProperties(propertyDelegate);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {

        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {

        return true;
    }

    @Override
    public void onClosed(PlayerEntity player) {

        super.onClosed(player);
    }

    public Inventory getInventory() {

        return this.inventory;
    }

    public float getCondensingSpeed() {

        return this.propertyDelegate.get(SPEED_DELEGATE) / 100.0f;
    }

    public float getCondensingProgress() {

        return this.propertyDelegate.get(PROGRESS_DELEGATE) / 100.0f;
    }

    public boolean isDoingThings() {

        return this.propertyDelegate.get(SPEED_DELEGATE) > 0;
    }

}
