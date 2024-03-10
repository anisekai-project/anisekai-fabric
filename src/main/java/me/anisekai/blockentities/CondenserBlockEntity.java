package me.anisekai.blockentities;

import me.anisekai.blocks.CondenserBlock;
import me.anisekai.inventories.ImplementedInventory;
import me.anisekai.recipes.CondenserRecipe;
import me.anisekai.registries.ModBlockEntities;
import me.anisekai.screen.condenser.CondenserScreenHandler;
import me.anisekai.utils.HandUtils;
import me.anisekai.utils.ReadWritePropertyDelegate;
import me.anisekai.utils.delegates.DelegatedBoolean;
import me.anisekai.utils.delegates.DelegatedIdentifier;
import me.anisekai.utils.delegates.DelegatedInteger;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class CondenserBlockEntity extends BlockEntity implements BlockEntityTicker<CondenserBlockEntity>, ImplementedInventory {

    public static final int INVENTORY_SIZE = 5;
    public static final int DELEGATE_SIZE  = 9;

    public static final int INV_INGREDIENT_A = 0;
    public static final int INV_INGREDIENT_B = 1;
    public static final int INV_BOOSTER      = 2;
    public static final int INV_TOOL         = 3;
    public static final int INV_OUTPUT       = 4;

    public static final int DELEGATE_OPTIONS_PROTECT_TOOL    = 0;
    public static final int DELEGATE_OPTIONS_SILENT_OVERFLOW = 1;
    public static final int DELEGATE_VALUE_ACTIVE_RECIPE     = 2;
    public static final int DELEGATE_VALUE_SELECTED_RECIPE   = 3;
    public static final int DELEGATE_VALUE_IPS               = 4;
    public static final int DELEGATE_VALUE_PROGRESS_PERCENT  = 5;
    public static final int DELEGATE_POS_X                   = 6;
    public static final int DELEGATE_POS_Y                   = 7;
    public static final int DELEGATE_POS_Z                   = 8;

    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);

    private final DelegatedBoolean    protectTool    = new DelegatedBoolean(false);
    private final DelegatedBoolean    silentOverflow = new DelegatedBoolean(false);
    private final DelegatedBoolean    jammed         = new DelegatedBoolean(false);
    private final DelegatedIdentifier activeRecipe   = DelegatedIdentifier.of(CondenserRecipe.RECIPES);
    private final DelegatedIdentifier selectedRecipe = DelegatedIdentifier.of(CondenserRecipe.RECIPES);

    private final DelegatedInteger progressPerTick    = new DelegatedInteger(0);
    private final DelegatedInteger currentWorkedTicks = new DelegatedInteger(0);
    private final DelegatedInteger itemPerSeconds     = new DelegatedInteger(0);
    private final DelegatedInteger progressPercent    = new DelegatedInteger(0);

    private final DelegatedInteger posX = new DelegatedInteger(0);
    private final DelegatedInteger posY = new DelegatedInteger(0);
    private final DelegatedInteger posZ = new DelegatedInteger(0);

    private int internalTick = 0;

    private final PropertyDelegate delegate = new ReadWritePropertyDelegate(
            this.protectTool,
            this.silentOverflow,
            this.activeRecipe,
            this.selectedRecipe,
            this.itemPerSeconds,
            this.progressPercent,
            this.posX,
            this.posY,
            this.posZ
    );

    public CondenserBlockEntity(BlockPos pos, BlockState state) {

        super(ModBlockEntities.CONDENSER, pos, state);
        this.posX.set(pos.getX());
        this.posY.set(pos.getY());
        this.posZ.set(pos.getZ());
    }

    public PropertyDelegate getDelegate() {

        return this.delegate;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {

        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.items);

        nbt.putBoolean("ProtectTool", this.protectTool.isTrue());
        nbt.putBoolean("SilentOverflow", this.silentOverflow.isTrue());
        nbt.putBoolean("Jammed", this.jammed.isTrue());
        nbt.putString("ActiveRecipe", this.activeRecipe.toString());
        nbt.putString("SelectedRecipe", this.selectedRecipe.toString());
        nbt.putInt("ProgressPerTick", this.progressPerTick.get());
        nbt.putInt("CurrentWorkedTick", this.currentWorkedTicks.get());
    }

    @Override
    public void readNbt(NbtCompound nbt) {

        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.items);

        this.protectTool.set(nbt.getBoolean("ProtectTool"));
        this.silentOverflow.set(nbt.getBoolean("SilentOverflow"));
        this.jammed.set(nbt.getBoolean("Jammed"));
        this.activeRecipe.setFrom(nbt, "ActiveRecipe");
        this.selectedRecipe.setFrom(nbt, "SelectedRecipe");
        this.progressPerTick.set(nbt.getInt("ProgressPerTick"));
        this.currentWorkedTicks.set(nbt.getInt("CurrentWorkedTick"));
    }

    // <editor-fold desc="Inventory Implementation">
    @Override
    public DefaultedList<ItemStack> getItems() {

        return this.items;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {

        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {

        return this.createNbt();
    }

    private void decrementSlot(int slot, int amount) {

        ItemStack stack = this.getStack(slot);
        stack.decrement(amount);
        if (stack.getCount() <= 0) {
            this.removeStack(slot);
        }
    }

    @Override
    public void setStack(int slot, ItemStack stack) {

        if (slot == INV_BOOSTER) {
            this.currentWorkedTicks.set(0);
        }
        ImplementedInventory.super.setStack(slot, stack);
    }

    // </editor-fold>

    // <editor-fold desc="Condenser Specific Private Methods">

    /**
     * Retrieve the active {@link CondenserRecipe} of this {@link CondenserBlockEntity}. An active recipe is a recipe
     * that is currently being used to create an {@link ItemStack}.
     *
     * @return An optional {@link CondenserRecipe}. The value will be empty if no task is being worked on by this
     *         {@link CondenserBlockEntity}.
     */
    private Optional<CondenserRecipe> getActiveRecipe() {

        if (!this.activeRecipe.isSet()) {
            return Optional.empty();
        }
        return Optional.ofNullable(CondenserRecipe.RECIPES.get(this.activeRecipe.getIdentifier()));
    }

    /**
     * Retrieve the selected {@link CondenserRecipe} of this {@link CondenserBlockEntity}. A selected recipe is a recipe
     * that has been chosen by a player
     *
     * @return An optional {@link CondenserRecipe}. While usually not empty, this will be empty if the block has just
     *         been placed in the world and the user did not select any recipe yet. Once the player selected a recipe,
     *         it is impossible to unselect it.
     */
    private Optional<CondenserRecipe> getSelectedRecipe() {

        if (this.selectedRecipe.isSet()) {
            return Optional.of(CondenserRecipe.RECIPES.get(this.selectedRecipe.getIdentifier()));
        }
        return Optional.empty();
    }

    /**
     * Reset this {@link CondenserRecipe} state. This will cause to cancel any recipe that was currently being worked
     * on. Any used item as booster will not be refunded (same as when you break a {@link FurnaceBlock} with fuel time
     * left)
     */
    private void resetState() {

        this.activeRecipe.set(null);
        this.progressPerTick.set(0);
        this.currentWorkedTicks.set(0);
    }

    /**
     * Check if the provided {@link CondenserRecipe} can be processed by this {@link CondenserBlockEntity} depending on
     * its inventory content. This will not check the booster neither if anything can be generated if using this
     * recipe.
     *
     * @param recipe
     *         The {@link CondenserRecipe} to check
     *
     * @return True if both ingredients and tool matched the provided {@link CondenserRecipe}, false otherwise.
     */
    private boolean canProcessRecipe(CondenserRecipe recipe) {

        ItemStack ingredientA = this.getStack(INV_INGREDIENT_A);
        ItemStack ingredientB = this.getStack(INV_INGREDIENT_B);
        ItemStack tool        = this.getStack(INV_TOOL);

        int consumesA = recipe.getConsumes()[0];
        int consumesB = recipe.getConsumes()[1];

        boolean isRightTool        = recipe.canUseWith(tool);
        boolean isRightIngredients = recipe.canUseOn(ingredientA, ingredientB);
        boolean hasRightAmountOfA  = ingredientA.getCount() >= consumesA;
        boolean hasRightAmountOfB  = ingredientB.getCount() >= consumesB;

        return isRightTool && isRightIngredients && hasRightAmountOfA && hasRightAmountOfB;
    }

    /**
     * Start processing the provided recipe.
     *
     * @param recipe
     *         The {@link CondenserRecipe} to condense.
     */
    private void startProcessing(CondenserRecipe recipe) {

        this.processSpeed(recipe);
        this.currentWorkedTicks.set(0);
        this.activeRecipe.set(recipe.getId());
    }

    private void processSpeed(CondenserRecipe recipe) {

        ItemStack booster = this.getStack(INV_BOOSTER);
        ItemStack tool    = this.getStack(INV_TOOL);

        float rawSpeed = HandUtils.getEfficiencyMiningValue(tool) + HandUtils.getMiningMultiplier(tool);

        if (recipe.canBoost(booster)) {
            rawSpeed *= recipe.getBoosterMultiplier();
        }

        int progressPerTick = Math.round(rawSpeed);
        int itemSecond100 = (int) (((double) progressPerTick / recipe.getRequiredTickTime()) * 2000 * recipe.getOutput()
                                                                                                            .getCount());
        this.itemPerSeconds.set(itemSecond100);
        this.progressPerTick.set(progressPerTick);
    }

    /**
     * Add the {@link CondenserRecipe} output to this {@link CondenserBlockEntity} output.
     *
     * @param recipe
     *         The {@link CondenserRecipe} to condense.
     */
    private void doCrafting(CondenserRecipe recipe) {

        ItemStack blockOutput  = this.getStack(INV_OUTPUT);
        ItemStack recipeOutput = recipe.getOutput();

        this.decrementSlot(INV_INGREDIENT_A, recipe.getConsumes()[0]);
        this.decrementSlot(INV_INGREDIENT_B, recipe.getConsumes()[1]);
        this.decrementSlot(INV_BOOSTER, 1);

        if (blockOutput.isEmpty()) {
            this.setStack(INV_OUTPUT, recipeOutput.copy());
        } else {
            blockOutput.increment(recipeOutput.getCount());
        }

        if (this.canProcessRecipe(recipe) && Objects.equals(this.selectedRecipe.getIdentifier(), recipe.getId())) {
            this.startProcessing(recipe); // Immediate restart if we can
        } else {
            this.resetState();
        }
    }

    /**
     * Check if the provided {@link CondenserRecipe} can put its output in this {@link CondenserBlockEntity} output.
     *
     * @param recipe
     *         The {@link CondenserRecipe} to check
     *
     * @return True if the output can fit, both with the item type and item amount
     */
    private boolean canOutput(CondenserRecipe recipe) {

        ItemStack currentOutput = this.getStack(INV_OUTPUT);
        int       left          = currentOutput.getMaxCount() - currentOutput.getCount();

        if (currentOutput.isEmpty()) {
            return true;
        }

        return ItemStack.canCombine(currentOutput, recipe.getOutput()) && left >= recipe.getOutput().getCount();
    }

    /**
     * Method called each tick when there is no active recipe within this {@link CondenserBlockEntity}. Depending on the
     * inventory content, this method may or may not set an active recipe.
     */
    private void tickNoActiveRecipe() {

        this.getSelectedRecipe().ifPresent(recipe -> {
            if (this.canProcessRecipe(recipe)) {
                this.startProcessing(recipe);
            }
        });
    }

    /**
     * Method called each tick when there is an active recipe within this {@link CondenserBlockEntity}. This does not
     * ensure any progress will be made on this tick.
     */
    private void tickActiveRecipe(World world, BlockPos pos) {
        // Do we have any active recipe ?
        if (this.getActiveRecipe().isEmpty()) {
            this.resetState();
            return; // No, so we can exit early.
        }

        CondenserRecipe recipe = this.getActiveRecipe().get();
        this.processSpeed(recipe);

        if (!this.canProcessRecipe(recipe)) {
            this.resetState();
            return;
        }

        // Tool checks
        ItemStack tool = this.getStack(INV_TOOL);

        boolean shouldWait = this.protectTool.isTrue() &&
                tool.getDamage() + recipe.getOutput().getCount() >= tool.getMaxDamage();

        // If we can output or the tool could potentially break on next condensing...
        if (!this.canOutput(recipe) || shouldWait) {
            this.currentWorkedTicks.set(0);
            this.progressPercent.set(0);
            this.jammed.set(true); // Jam the block...

            if (!this.silentOverflow.isTrue() && this.internalTick == 0) {
                // ...and play a sound, if the block's settings allow it
                world.playSound(null, pos, SoundEvents.BLOCK_NOTE_BLOCK_BASS.value(), SoundCategory.BLOCKS, 0.1f, 1.0f);
            }
            return;
        }

        // Increment progress
        this.jammed.set(false);
        this.currentWorkedTicks.increment(this.progressPerTick.get());
        int progress = Math.round((this.currentWorkedTicks.get() / (float) recipe.getRequiredTickTime()) * 100);
        this.progressPercent.set(progress);

        // If the progress is over what is required to condense our block
        if (this.currentWorkedTicks.get() >= recipe.getRequiredTickTime()) {
            this.doCrafting(recipe); // Craft it

            // Damage our tool
            if (tool.damage(recipe.getOutput().getCount(), world.random, null)) {
                this.removeStack(INV_TOOL); // Oh no, it broke :(
                world.playSound(null, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1f, 1.0f);
            }

            world.playSound(null, pos, recipe.getCondensedSound(), SoundCategory.BLOCKS, 0.1f, 1.0f);
            return;
        }

        if (this.internalTick == 0 && recipe.getWorkingSound() != null) {
            // Play the working sound if we have one
            world.playSound(null, pos, recipe.getWorkingSound(), SoundCategory.BLOCKS, 0.1f, 1.0f);
        }
    }

    // </editor-fold>

    @Override
    public void tick(World world, BlockPos pos, BlockState state, CondenserBlockEntity blockEntity) {

        if (!this.selectedRecipe.isSet()) {
            return;
        }

        this.internalTick = (this.internalTick + 1) % 5;

        if (this.activeRecipe.isSet()) {
            this.tickActiveRecipe(world, pos);
        } else {
            this.tickNoActiveRecipe();
        }

        // Synchronize state with the world
        BlockState updatedState = state
                .with(Properties.LIT, this.activeRecipe.isSet() && !this.jammed.isTrue())
                .with(CondenserBlock.JAMMED, this.activeRecipe.isSet() && this.jammed.isTrue());

        world.setBlockState(pos, updatedState, Block.NOTIFY_ALL);

        this.markDirty();
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {

        return new CondenserScreenHandler(syncId, playerInventory, this, this.delegate);
    }

    @Override
    public Text getDisplayName() {

        return Text.translatable("inventory.anisekai.condenser");
    }

    // <editor-fold desc="Hopper logic">

    @Override
    public int[] getAvailableSlots(Direction side) {

        if (side == Direction.DOWN) {
            return new int[]{INV_OUTPUT};
        }
        return new int[]{INV_INGREDIENT_A, INV_INGREDIENT_B, INV_BOOSTER, INV_TOOL};
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {

        return slot != INV_OUTPUT && this.isValid(slot, stack);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {

        return slot == INV_OUTPUT;
    }

    // </editor-fold>


    @Override
    public boolean isValid(int slot, ItemStack stack) {

        return switch (slot) {
            case INV_INGREDIENT_A -> this.getSelectedRecipe().map(recipe -> recipe.canUseOnA(stack)).orElse(false);
            case INV_INGREDIENT_B -> this.getSelectedRecipe().map(recipe -> recipe.canUseOnB(stack)).orElse(false);
            case INV_TOOL -> this.getSelectedRecipe().map(recipe -> recipe.canUseWith(stack)).orElse(false);
            case INV_BOOSTER -> this.getSelectedRecipe().map(recipe -> recipe.canBoost(stack)).orElse(false);
            default -> true;
        };
    }

}
