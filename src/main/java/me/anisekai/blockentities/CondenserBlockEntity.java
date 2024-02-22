package me.anisekai.blockentities;

import me.anisekai.blocks.CondenserBlock;
import me.anisekai.inventories.ImplementedInventory;
import me.anisekai.recipes.CondenserRecipe;
import me.anisekai.registries.ModBlockEntities;
import me.anisekai.screen.condenser.CondenserScreenHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MiningToolItem;
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
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Predicate;

public class CondenserBlockEntity extends BlockEntity implements BlockEntityTicker<CondenserBlockEntity>, ImplementedInventory {

    public static final int CONDENSED_BLOCK_SLOT = 0;
    public static final int INGREDIENT_ONE_SLOT  = 1;
    public static final int INGREDIENT_TWO_SLOT  = 2;
    public static final int BOOSTER_SLOT         = 3;
    public static final int TOOL_SLOT            = 4;
    public static final int OUTPUT_SLOT          = 5;

    public static final int SPEED_DELEGATE    = 0;
    public static final int PROGRESS_DELEGATE = 1;

    private int   currentTick;
    private float progress;
    private float speedMultiplier;

    private Identifier      lastActiveRecipe;
    private CondenserRecipe activeRecipe;
    private float           progressPerTick;

    protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {

        final int[] data = new int[2];

        @Override
        public int get(int index) {

            if (index < this.data.length) {
                return this.data[index];
            }
            return 0;
        }

        @Override
        public void set(int index, int value) {

            if (index < this.data.length) {
                this.data[index] = value;
            }
        }

        @Override
        public int size() {

            return this.data.length;
        }
    };

    public CondenserBlockEntity(BlockPos pos, BlockState state) {

        super(ModBlockEntities.CONDENSER, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {

        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.items);
        nbt.putFloat("progress", this.progress);
        nbt.putFloat("speedMultiplier", this.speedMultiplier);
    }

    @Override
    public void readNbt(NbtCompound nbt) {

        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.items);
        this.progress        = nbt.getFloat("progress");
        this.speedMultiplier = nbt.getFloat("speedMultiplier");
    }

    // <editor-fold desc="Inventory Implementation">

    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(6, ItemStack.EMPTY);

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
    // </editor-fold>

    // <editor-fold desc="Condenser Specific Private Methods">
    private void reset() {

        this.lastActiveRecipe = null;
        this.activeRecipe     = null;
        this.progressPerTick  = 0;
        this.progress         = 0;
    }

    private void syncRecipeStatus() {

        ItemStack condensedStack = this.getStack(CONDENSED_BLOCK_SLOT);
        if (condensedStack.isEmpty()) {
            this.reset();
            return;
        } else {
            CondenserBlock.RECIPES.values().stream()
                                  .filter(recipe -> recipe.getCondensedStack().isOf(condensedStack.getItem()))
                                  .findAny()
                                  .ifPresent(recipe -> this.lastActiveRecipe = recipe.getId());
        }

        if (this.lastActiveRecipe != null) {
            CondenserRecipe recipe = CondenserBlock.RECIPES.get(this.lastActiveRecipe);
            if (recipe == null || !recipe.getCondensedStack().isOf(condensedStack.getItem())) {
                this.reset();
            } else {
                this.activeRecipe    = recipe;
                this.progressPerTick = this.processProgressPerTick();
            }
        }
    }

    private float processProgressPerTick() {

        return (this.getMiningSpeedMultiplier() + this.getEfficiency() + this.getBoosterBonus()) * (this.speedMultiplier + 1);
    }

    private float getMiningSpeedMultiplier() {

        return this.getStack(TOOL_SLOT).getItem() instanceof MiningToolItem mining ?
                mining.getMaterial().getMiningSpeedMultiplier() : 1.0f;
    }

    private float getEfficiency() {

        int efficiency = EnchantmentHelper.getLevel(Enchantments.EFFICIENCY, this.getStack(TOOL_SLOT));
        return (efficiency * efficiency + 1);
    }

    private float getBoosterBonus() {

        return this.activeRecipe.getBooster().test(this.getStack(BOOSTER_SLOT)) ?
                this.activeRecipe.getBoosterBonus() : 0;
    }
    // </editor-fold>

    @Override
    public void tick(World world, BlockPos pos, BlockState state, CondenserBlockEntity blockEntity) {

        this.syncRecipeStatus();
        if (this.activeRecipe == null) {
            return;
        }

        if (!this.activeRecipe.canContinueProcessing(this)) {
            this.progress        = 0;
            this.progressPerTick = 0;

            this.propertyDelegate.set(SPEED_DELEGATE, 0);
            this.propertyDelegate.set(PROGRESS_DELEGATE, 0);

            world.setBlockState(
                    pos,
                    state.with(Properties.LIT, false),
                    Block.NOTIFY_ALL
            );

            return;
        }

        world.setBlockState(
                pos,
                state.with(Properties.LIT, true),
                Block.NOTIFY_ALL
        );

        this.progress += this.progressPerTick;

        float progressPerSecond = this.progressPerTick * 20;
        float speed             = progressPerSecond / this.activeRecipe.getBaseWorkRate();

        this.propertyDelegate.set(SPEED_DELEGATE, Math.round(speed * 100));
        this.propertyDelegate.set(
                PROGRESS_DELEGATE,
                Math.round(Math.min((this.progress / this.activeRecipe.getWorkRate()), 1) * 100)
        );

        if (this.progress >= this.activeRecipe.getWorkRate()) {

            boolean hadBooster = !this.getStack(BOOSTER_SLOT).isEmpty();

            int damage = this.activeRecipe.doCraft(this);

            if (this.getStack(INGREDIENT_ONE_SLOT).isEmpty() || this.getStack(INGREDIENT_TWO_SLOT).isEmpty()) {
                world.playSound(
                        null,
                        pos,
                        SoundEvents.BLOCK_NOTE_BLOCK_CHIME.value(),
                        SoundCategory.BLOCKS,
                        0.2f,
                        1.0f
                );
            }

            if (this.getStack(BOOSTER_SLOT).isEmpty() && hadBooster) {
                world.playSound(
                        null,
                        pos,
                        SoundEvents.BLOCK_NOTE_BLOCK_BANJO.value(),
                        SoundCategory.BLOCKS,
                        0.2f,
                        1.0f
                );
            }

            this.progress    = 0;
            this.currentTick = 0;

            ItemStack tool = this.getStack(CondenserBlockEntity.TOOL_SLOT);

            if (tool.damage(damage, this.getWorld().random, null)) {
                this.setStack(TOOL_SLOT, new ItemStack(Items.AIR));
                world.playSound(null, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1f, 1.0f);
            }

            if (this.activeRecipe.getGenerateSound() != null) {
                world.playSound(null, pos, this.activeRecipe.getGenerateSound(), SoundCategory.BLOCKS, 1f, 1.0f);
            }

            this.markDirty();
            return;
        }

        this.currentTick = (this.currentTick + 1) % 5;

        if (this.currentTick == 0 && this.activeRecipe.getWorkingSound() != null) {
            world.playSound(null, pos, this.activeRecipe.getWorkingSound(), SoundCategory.BLOCKS, 0.1f, 1.0f);
        }
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {

        return new CondenserScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    public Text getDisplayName() {

        return Text.translatable("inventory.anisekai.condenser");
    }

    // <editor-fold desc="Hopper logic">

    @Override
    public int[] getAvailableSlots(Direction side) {

        if (side == Direction.DOWN) {
            return new int[]{OUTPUT_SLOT};
        }
        return new int[]{INGREDIENT_ONE_SLOT, INGREDIENT_TWO_SLOT, BOOSTER_SLOT, TOOL_SLOT};
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {

        return switch (slot) {
            case INGREDIENT_ONE_SLOT -> this.isCompatible(CondenserRecipe::getIngredientOne, stack);
            case INGREDIENT_TWO_SLOT -> this.isCompatible(CondenserRecipe::getIngredientTwo, stack);
            case BOOSTER_SLOT -> this.isCompatible(CondenserRecipe::getBooster, stack);
            case TOOL_SLOT -> this.isCompatible(CondenserRecipe::getTool, stack);
            default -> false;
        };
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {

        return slot == OUTPUT_SLOT;
    }

    private boolean isCompatible(Function<CondenserRecipe, Predicate<ItemStack>> predicateFunction, ItemStack stack) {

        if (this.activeRecipe == null) {
            return false;
        }

        return predicateFunction.apply(this.activeRecipe).test(stack);
    }

    // </editor-fold>

}
