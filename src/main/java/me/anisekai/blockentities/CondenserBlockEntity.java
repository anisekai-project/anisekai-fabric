package me.anisekai.blockentities;

import me.anisekai.blocks.CondenserBlock;
import me.anisekai.inventories.ImplementedInventory;
import me.anisekai.recipes.CondenserRecipe;
import me.anisekai.registries.ModBlockEntities;
import me.anisekai.screen.condenser.CondenserScreenHandler;
import me.anisekai.utils.AnisekaiEnchantmentHelper;
import me.anisekai.utils.HandUtils;
import me.anisekai.utils.ReadWritePropertyDelegate;
import me.anisekai.utils.delegates.DelegatedBoolean;
import me.anisekai.utils.delegates.DelegatedInteger;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class CondenserBlockEntity extends BlockEntity implements BlockEntityTicker<CondenserBlockEntity>, ImplementedInventory {

    public static final int INVENTORY_SIZE = 5;
    public static final int DELEGATE_SIZE  = 9;

    public static final int INV_APPLY   = 0;
    public static final int INV_ONTO    = 1;
    public static final int INV_BOOSTER = 2;
    public static final int INV_TOOL    = 3;
    public static final int INV_OUTPUT  = 4;

    public static final int DELEGATE_OPTIONS_PROTECT_TOOL    = 0;
    public static final int DELEGATE_OPTIONS_SILENT_OVERFLOW = 1;
    public static final int DELEGATE_VALUE_IPS               = 2;
    public static final int DELEGATE_VALUE_PROGRESS_PERCENT  = 3;
    public static final int DELEGATE_POS_X                   = 4;
    public static final int DELEGATE_POS_Y                   = 5;
    public static final int DELEGATE_POS_Z                   = 6;

    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);

    private final DelegatedBoolean protectTool    = new DelegatedBoolean(false);
    private final DelegatedBoolean silentOverflow = new DelegatedBoolean(false);
    private final DelegatedBoolean jammed         = new DelegatedBoolean(false);

    private final DelegatedInteger progressPerTick    = new DelegatedInteger(0);
    private final DelegatedInteger currentWorkedTicks = new DelegatedInteger(0);
    private final DelegatedInteger itemPerSeconds     = new DelegatedInteger(0);
    private final DelegatedInteger progressPercent    = new DelegatedInteger(0);

    private final DelegatedInteger posX = new DelegatedInteger(0);
    private final DelegatedInteger posY = new DelegatedInteger(0);
    private final DelegatedInteger posZ = new DelegatedInteger(0);

    private int                          internalTick   = 0;
    private RecipeEntry<CondenserRecipe> activeRecipe   = null;
    private Identifier                   selectedRecipe = null;

    private final PropertyDelegate delegate = new ReadWritePropertyDelegate(
            this.protectTool,
            this.silentOverflow,
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

    public List<RecipeEntry<CondenserRecipe>> getAvailableRecipes() {

        if (this.world == null) {
            return List.of(); // Avoid crashes if world is not loaded
        }

        return this.world.getRecipeManager().listAllOfType(CondenserRecipe.Type.INSTANCE);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {

        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, this.items, registryLookup);

        nbt.putBoolean("ProtectTool", this.protectTool.isTrue());
        nbt.putBoolean("SilentOverflow", this.silentOverflow.isTrue());
        nbt.putBoolean("Jammed", this.jammed.isTrue());
        nbt.putInt("ProgressPerTick", this.progressPerTick.get());
        nbt.putInt("CurrentWorkedTick", this.currentWorkedTicks.get());

        if (this.selectedRecipe != null) {
            nbt.putString("SelectedRecipe", this.selectedRecipe.toString());
        }
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {

        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, this.items, registryLookup);

        this.protectTool.set(nbt.getBoolean("ProtectTool"));
        this.silentOverflow.set(nbt.getBoolean("SilentOverflow"));
        this.jammed.set(nbt.getBoolean("Jammed"));
        this.progressPerTick.set(nbt.getInt("ProgressPerTick"));
        this.currentWorkedTicks.set(nbt.getInt("CurrentWorkedTick"));

        if (nbt.contains("SelectedRecipe")) {
            this.selectedRecipe = Identifier.of(nbt.getString("SelectedRecipe"));
        }
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
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {

        return this.createNbt(registryLookup);
    }

    private void decrementSlot(int slot) {

        ItemStack stack = this.getStack(slot);
        stack.decrement(1);
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
     * Find the {@link RecipeEntry} matching the player-selected {@link Identifier}.
     *
     * @return An optional {@link RecipeEntry}
     */
    public Optional<RecipeEntry<CondenserRecipe>> findSelectedRecipe() {

        if (this.selectedRecipe == null) return Optional.empty();
        return this.getAvailableRecipes()
                   .stream()
                   .filter(recipe -> recipe.id().equals(this.selectedRecipe))
                   .findFirst();
    }

    /**
     * Check if the provided {@link RecipeEntry} can be processed with the current {@link CondenserBlockEntity}'s
     * {@link Inventory}. The booster {@link ItemStack} will not be checked against.
     *
     * @param recipe
     *         The {@link RecipeEntry} to check
     *
     * @return True if the {@link Inventory} is compatible with the {@link RecipeEntry}, false otherwise.
     */
    private boolean canProcessRecipe(RecipeEntry<CondenserRecipe> recipe) {

        if (recipe == null) return false;
        ItemStack apply = this.getStack(INV_APPLY);
        ItemStack onto  = this.getStack(INV_ONTO);
        ItemStack tool  = this.getStack(INV_TOOL);
        return recipe.value().test(apply, onto, tool);
    }

    /**
     * Reset this {@link CondenserBlockEntity} state. This will cause to cancel any recipe that was currently being worked on. Any
     * used item as booster will not be refunded (same as when you break a {@link FurnaceBlock} with fuel time left)
     */
    private void resetState() {

        this.activeRecipe = null;
        this.currentWorkedTicks.set(0);
        this.itemPerSeconds.set(0);
        this.progressPercent.set(0);
    }

    /**
     * Start processing the provided recipe.
     *
     * @param recipe
     *         The {@link RecipeEntry} to condense.
     */
    private void startProcessing(RecipeEntry<CondenserRecipe> recipe) {

        this.internalTick = 0;
        this.activeRecipe = recipe;
        this.currentWorkedTicks.set(0);
        this.processSpeed();
    }

    private void processSpeed() {

        ItemStack booster = this.getStack(INV_BOOSTER);
        ItemStack tool    = this.getStack(INV_TOOL);

        float progressPerTick = HandUtils.getEfficiencyMiningValue(this.world, tool) + HandUtils.getMiningMultiplier(tool);

        if (this.activeRecipe.value().booster().test(booster)) {
            progressPerTick *= 1.2f;
        }

        int outputCount  = this.activeRecipe.value().result().getCount();
        int timeRequired = this.activeRecipe.value().time();

        float itemPerTick   = (progressPerTick / timeRequired) * outputCount;
        float itemPerSecond = itemPerTick * 20;

        // Used for 2 digits precision on player screen.
        int itemPerSecond100 = (int) (itemPerSecond * 100);

        this.itemPerSeconds.set(itemPerSecond100);
        this.progressPerTick.set((int) progressPerTick);
    }

    /**
     * Add the {@link CondenserRecipe} output to this {@link CondenserBlockEntity} output.
     */
    private int doCrafting() {

        ItemStack blockOutput  = this.getStack(INV_OUTPUT);
        ItemStack recipeOutput = this.activeRecipe.value().result();

        if (this.activeRecipe.value().apply().consume()) this.decrementSlot(INV_APPLY);
        if (this.activeRecipe.value().onto().consume()) this.decrementSlot(INV_ONTO);

        if (this.activeRecipe.value().booster().test(this.getStack(INV_BOOSTER))) {
            this.decrementSlot(INV_BOOSTER);
        }

        if (blockOutput.isEmpty()) {
            this.setStack(INV_OUTPUT, recipeOutput.copy());
        } else {
            blockOutput.increment(recipeOutput.getCount());
        }

        if (this.activeRecipe.id().equals(this.selectedRecipe) && this.canProcessRecipe(this.activeRecipe)) {
            this.startProcessing(this.activeRecipe); // Immediate restart if we can
        } else {
            this.resetState();
        }

        return recipeOutput.getCount();
    }

    /**
     * Check if the provided {@link CondenserRecipe} can put its output in this {@link CondenserBlockEntity} output.
     *
     * @return True if the output can fit, both with the item type and item amount
     */
    private boolean canOutput() {

        ItemStack output = this.getStack(INV_OUTPUT);

        if (output.isEmpty()) {
            return true;
        }

        boolean areItemsCompatible = ItemStack.areItemsAndComponentsEqual(output, this.activeRecipe.value().result());
        boolean canOutputCount     = output.getMaxCount() - output.getCount() >= this.activeRecipe.value().result().getCount();

        return areItemsCompatible && canOutputCount;
    }

    /**
     * Method called each tick when there is an active recipe within this {@link CondenserBlockEntity}. This does not ensure any
     * progress will be made on this tick.
     */
    private void doRecipeTick(WorldAccess world, BlockPos pos) {

        this.processSpeed();
        this.internalTick = (this.internalTick + 1) % 5;

        ItemStack tool          = this.getStack(INV_TOOL);
        boolean   toolMayBreak  = tool.getDamage() + this.activeRecipe.value().result().getCount() >= tool.getMaxDamage();
        boolean   isMending     = AnisekaiEnchantmentHelper.hasEnchant(this.world, tool, Enchantments.MENDING);
        boolean   shouldProtect = this.protectTool.isTrue() || isMending;

        if ((toolMayBreak && shouldProtect) || !this.canOutput()) {
            this.currentWorkedTicks.set(0);
            this.progressPercent.set(0);
            this.jammed.set(true);

            if (this.internalTick == 0) {
                world.playSound(null, pos, SoundEvents.BLOCK_NOTE_BLOCK_BASS.value(), SoundCategory.BLOCKS, 0.1f, 1.0f);
            }
        } else {
            float workedTick   = (float) this.currentWorkedTicks.get();
            float timeRequired = (float) this.activeRecipe.value().time();

            this.jammed.set(false);
            this.currentWorkedTicks.increment(this.progressPerTick.get());
            this.progressPercent.set(Math.round((workedTick / timeRequired) * 100));

            if (workedTick >= timeRequired) {
                int damage = this.doCrafting();

                tool.damage(
                        damage,
                        (ServerWorld) world, null, item -> {
                            this.removeStack(INV_TOOL);
                            world.playSound(null, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1f, 1.0f);
                        }
                );

                world.playSound(null, pos, SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 0.1f, 1.0f);
                return;
            }

            if (this.internalTick == 0) {
                world.playSound(null, pos, SoundEvents.BLOCK_STONE_HIT, SoundCategory.BLOCKS, 0.1f, 1.0f);
            }

        }

    }

    // </editor-fold>

    @Override
    public void tick(World world, BlockPos pos, BlockState state, CondenserBlockEntity blockEntity) {

        if (this.activeRecipe == null) {
            Optional<RecipeEntry<CondenserRecipe>> optionalRecipe = this.findSelectedRecipe();

            if (optionalRecipe.isPresent()) {
                RecipeEntry<CondenserRecipe> recipe = optionalRecipe.get();
                if (this.canProcessRecipe(recipe)) {
                    this.startProcessing(optionalRecipe.get());
                    this.doRecipeTick(world, pos);
                }
            } else {
                this.currentWorkedTicks.set(0);
                this.progressPercent.set(0);
                this.jammed.set(false);
            }
        } else {
            if (this.canProcessRecipe(this.activeRecipe)) {
                this.doRecipeTick(world, pos);
            } else {
                this.resetState();
            }
        }

        // Synchronize state with the world
        BlockState updatedState = state
                .with(Properties.LIT, this.activeRecipe != null && !this.jammed.isTrue())
                .with(CondenserBlock.JAMMED, this.activeRecipe != null && this.jammed.isTrue());

        world.setBlockState(pos, updatedState, Block.NOTIFY_ALL);

        this.markDirty();
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {

        return new CondenserScreenHandler(syncId, playerInventory, this, this.delegate, this::getAvailableRecipes);
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
        return new int[]{INV_APPLY, INV_ONTO, INV_BOOSTER, INV_TOOL};
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {

        Optional<RecipeEntry<CondenserRecipe>> optionalRecipe = this.findSelectedRecipe();
        if (optionalRecipe.isEmpty()) return false;
        RecipeEntry<CondenserRecipe> recipe = optionalRecipe.get();

        boolean isApplyEmpty = this.getStack(INV_APPLY).isEmpty();
        boolean isOntoEmpty  = this.getStack(INV_ONTO).isEmpty();

        return switch (slot) {
            case INV_APPLY -> recipe.value().apply().test(stack) && (isApplyEmpty || !isOntoEmpty);
            case INV_ONTO -> recipe.value().onto().test(stack) && (isOntoEmpty || !isApplyEmpty);
            case INV_BOOSTER -> recipe.value().booster().test(stack);
            case INV_TOOL -> recipe.value().tool().test(stack);
            default -> false;
        };
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {

        return slot == INV_OUTPUT;
    }

    // </editor-fold>

    // <editor-fold desc="Packet Bound">
    public Identifier getSelectedRecipe() {

        return this.selectedRecipe;
    }

    public void setSelectedRecipe(Identifier selectedRecipe) {

        this.selectedRecipe = selectedRecipe;
    }
    // </editor-fold>

}
