package me.anisekai.blockentities;

import me.anisekai.inventories.ImplementedInventory;
import me.anisekai.inventories.constrained.ConstrainedContainerScreenHandler;
import me.anisekai.registries.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Optional;

public class FishingBasketBlockEntity extends BlockEntity implements ImplementedInventory {

    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(27, ItemStack.EMPTY);

    public FishingBasketBlockEntity(BlockPos pos, BlockState state) {

        super(ModBlockEntities.FISHING_BASKET, pos, state);
    }

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

    @Override
    protected void writeNbt(NbtCompound nbt) {

        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.items, false);
    }

    @Override
    public void readNbt(NbtCompound nbt) {

        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.items);
    }

    @Override
    public Text getDisplayName() {

        File file = null;

        Optional.ofNullable(file)
                .map(File::getName)
                .orElse(null);

        return Text.translatable("inventory.anisekai.fishing_basket");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {

        return ConstrainedContainerScreenHandler.create(syncId, playerInventory, this);
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {

        return stack.isIn(ItemTags.FISHES);
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {

        return this.isValid(slot, stack);
    }

}
