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
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Supplier;

import static me.anisekai.blockentities.CondenserBlockEntity.*;

public class CondenserScreenHandler extends ScreenHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CondenserScreenHandler.class);

    public static CondenserScreenHandler create(int syncId, PlayerInventory playerInventory) {

        PlayerEntity player = playerInventory.player;
        World        world  = player.getWorld();
        if (world == null) {
            throw new IllegalStateException("Tried to open a condenser screen handler but world is null");
        }

        RecipeManager manager = world.getRecipeManager();

        return create(
                syncId,
                playerInventory,
                new SimpleInventory(INVENTORY_SIZE),
                new ArrayPropertyDelegate(DELEGATE_SIZE),
                () -> manager.listAllOfType(CondenserRecipe.Type.INSTANCE)
        );
    }

    public static CondenserScreenHandler create(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate, Supplier<List<RecipeEntry<CondenserRecipe>>> recipeSupplier) {

        return new CondenserScreenHandler(
                syncId,
                playerInventory,
                inventory,
                propertyDelegate,
                recipeSupplier
        );
    }

    private final PropertyDelegate                   propertyDelegate;
    private final Inventory                          inventory;
    private final List<RecipeEntry<CondenserRecipe>> recipes;
    private final List<CondenserRecipeRenderer>      renderers;

    public CondenserScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate, Supplier<List<RecipeEntry<CondenserRecipe>>> recipeSupplier) {

        super(ModScreenHandler.CONDENSER, syncId);
        this.inventory        = inventory;
        this.propertyDelegate = propertyDelegate;
        this.recipes          = recipeSupplier.get();
        this.renderers        = this.recipes.stream().map(CondenserRecipeRenderer::new).toList();

        this.addSlot(new ConstrainedSlot(this.inventory, INV_APPLY, 125, 21));
        this.addSlot(new ConstrainedSlot(this.inventory, INV_ONTO, 155, 21));
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
        } else if (slot >= INV_APPLY && slot <= INV_TOOL) {
            if (!this.insertItem(clickedStack, 6, 40, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (!this.insertItem(clickedStack, INV_APPLY, INV_OUTPUT, true)) {
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

        return this.getWorkingSpeed() > 0;
    }

    public BlockPos getBlockPos() {

        int x = this.propertyDelegate.get(DELEGATE_POS_X);
        int y = this.propertyDelegate.get(DELEGATE_POS_Y);
        int z = this.propertyDelegate.get(DELEGATE_POS_Z);

        return new BlockPos(x, y, z);
    }

    public void tick() {

        this.renderers.forEach(CondenserRecipeRenderer::tick);
    }

    public List<CondenserRecipeRenderer> getRenderers() {

        return this.renderers;
    }

    public List<RecipeEntry<CondenserRecipe>> getRecipes() {

        return this.recipes;
    }

    // </editor-fold>

}
