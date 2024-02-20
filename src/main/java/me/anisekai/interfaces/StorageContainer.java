package me.anisekai.interfaces;

import me.anisekai.inventories.ImplementedInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public interface StorageContainer<T extends BlockEntity & ImplementedInventory> extends AnisekaiBlock {

    Optional<T> getBlockEntityInstance(BlockEntity entity);

    default boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity == null) {
            return false;
        }
        return blockEntity.onSyncedBlockEvent(type, data);
    }

    default NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {

        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity instanceof NamedScreenHandlerFactory ? (NamedScreenHandlerFactory) blockEntity : null;
    }

    default void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {

        if (state.isOf(newState.getBlock())) {
            return;
        }

        this.getBlockEntityInstance(world.getBlockEntity(pos)).ifPresent(inv -> {
            ItemScatterer.spawn(world, pos, inv);
            world.updateComparators(pos, this.asBlock());
        });

        if (state.hasBlockEntity() && !state.isOf(newState.getBlock())) {
            world.removeBlockEntity(pos);
        }
    }

    default boolean hasComparatorOutput(BlockState state) {

        return true;
    }

    default int getComparatorOutput(BlockState state, World world, BlockPos pos) {

        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

}
