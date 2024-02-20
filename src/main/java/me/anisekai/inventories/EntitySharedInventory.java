package me.anisekai.inventories;

import net.minecraft.entity.vehicle.VehicleInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public interface EntitySharedInventory extends VehicleInventory {

    BlockPos getSharedPos();

    void setSharedPos(BlockPos storageContainerPos);

    default Optional<ImplementedInventory> getSharedInventory(World world) {

        return Optional.ofNullable(this.getSharedPos())
                       .map(world::getBlockEntity)
                       .filter(ImplementedInventory.class::isInstance)
                       .map(ImplementedInventory.class::cast);
    }

}
