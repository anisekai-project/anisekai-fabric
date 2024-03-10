package me.anisekai.screen.condenser;

import me.anisekai.inventories.slots.OutputSlot;
import me.anisekai.recipes.CondenserRecipe;
import me.anisekai.registries.ModScreenHandler;
import me.anisekai.screen.common.ConstrainedSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

import java.util.List;

import static me.anisekai.blockentities.CondenserBlockEntity.*;

public class CondenserScreenHandler extends ScreenHandler {

    public static CondenserScreenHandler create(int syncId, PlayerInventory playerInventory) {

        return create(
                syncId,
                playerInventory,
                new SimpleInventory(INVENTORY_SIZE),
                new ArrayPropertyDelegate(DELEGATE_SIZE)
        );
    }

    public static CondenserScreenHandler create(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {

        return new CondenserScreenHandler(
                syncId,
                playerInventory,
                inventory,
                propertyDelegate
        );
    }

    private final PropertyDelegate propertyDelegate;
    private final Inventory        inventory;

    private final List<CondenserRecipeRenderer> recipes = CondenserRecipe.RECIPES.values()
                                                                                 .stream()
                                                                                 .map(CondenserRecipeRenderer::new)
                                                                                 .toList();

    public CondenserScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {

        super(ModScreenHandler.CONDENSER, syncId);
        this.inventory        = inventory;
        this.propertyDelegate = propertyDelegate;

        this.addSlot(new ConstrainedSlot(this.inventory, INV_INGREDIENT_A, 125, 21));
        this.addSlot(new ConstrainedSlot(this.inventory, INV_INGREDIENT_B, 155, 21));
        this.addSlot(new ConstrainedSlot(this.inventory, INV_BOOSTER, 140, 42));
        this.addSlot(new ConstrainedSlot(this.inventory, INV_TOOL, 179, 37));
        this.addSlot(new OutputSlot(this.inventory, INV_OUTPUT, 234, 38));

        // <editor-fold desc="Draw player inventory">
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 108 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 108 + i * 18, 142));
        }
        // </editor-fold>

        this.addProperties(propertyDelegate);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {

        Slot      clickedSlot  = this.getSlot(slot);
        ItemStack clickedStack = clickedSlot.getStack();
        ItemStack stack        = clickedStack.copy();

        if (slot == INV_OUTPUT) {

            if (!this.insertItem(clickedStack, 6, 40, true)) {
                return ItemStack.EMPTY;
            }
            clickedSlot.onQuickTransfer(clickedStack, stack);
        } else if (slot >= INV_INGREDIENT_A && slot <= INV_TOOL) {
            if (!this.insertItem(clickedStack, 6, 40, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (!this.insertItem(clickedStack, INV_INGREDIENT_A, INV_OUTPUT, true)) {
                return ItemStack.EMPTY;
            }
        }

        if (clickedStack.isEmpty()) {
            clickedSlot.setStack(ItemStack.EMPTY);
        } else {
            clickedSlot.markDirty();
        }

        if (clickedStack.getCount() == stack.getCount()) {
            return ItemStack.EMPTY;
        }

        clickedSlot.onTakeItem(player, clickedStack);
        return stack;
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

    // <editor-fold desc="Delegated Data">
    public float getWorkingSpeed() {

        return this.propertyDelegate.get(DELEGATE_VALUE_IPS) / 100.0f;
    }

    public float getProgress() {

        return this.propertyDelegate.get(DELEGATE_VALUE_PROGRESS_PERCENT) / 100.0f;
    }

    public boolean isWorking() {

        return this.propertyDelegate.get(DELEGATE_VALUE_ACTIVE_RECIPE) > -1;
    }

    public int getSelectedRecipe() {

        return this.propertyDelegate.get(DELEGATE_VALUE_SELECTED_RECIPE);
    }

    public BlockPos getBlockPos() {

        int x = this.propertyDelegate.get(DELEGATE_POS_X);
        int y = this.propertyDelegate.get(DELEGATE_POS_Y);
        int z = this.propertyDelegate.get(DELEGATE_POS_Z);

        return new BlockPos(x, y, z);
    }

    public List<CondenserRecipeRenderer> getRecipes() {

        return this.recipes;
    }

    public void tick() {

        this.recipes.forEach(CondenserRecipeRenderer::tick);
    }

    // </editor-fold>

}
