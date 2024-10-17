package me.anisekai.interfaces;

import me.anisekai.inventories.ImplementedInventory;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface StorageContainer<T extends BlockEntity & ImplementedInventory> extends AnisekaiBlock {

    Optional<T> getBlockEntityInstance(BlockEntity entity);

    @Nullable
    static <T extends BlockEntity, E extends BlockEntity & BlockEntityTicker<E>> BlockEntityTicker<T> checkType(World world, BlockEntityType<T> givenType, BlockEntityType<E> requiredType) {

        return world.isClient ? null : checkType(
                givenType,
                requiredType,
                (world1, pos, state, blockEntity) ->
                        blockEntity.tick(
                                world,
                                pos,
                                state,
                                blockEntity
                        )
        );
    }

    @Nullable
    static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {

        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }

}
