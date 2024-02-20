package me.anisekai.interfaces;

import me.anisekai.entities.seat.InvisibleSeatEntity;
import me.anisekai.registries.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Objects;

public interface Seatable {

    static ActionResult sit(World world, BlockPos pos, BlockState state, Seatable seatable, Entity entity) {

        Vec3d vec   = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
        Vec3d sitAt = seatable.getSitOffsetFrom(vec);
        float yaw   = seatable.getSitYaw(state);

        InvisibleSeatEntity invisibleSeatEntity = Objects.requireNonNull(ModEntities.INVISIBLE_SEAT_ENTITY.create(world));

        invisibleSeatEntity.refreshPositionAndAngles(sitAt.getX(), sitAt.getY(), sitAt.getZ(), yaw, 0);

        invisibleSeatEntity.setNoGravity(true);
        invisibleSeatEntity.setSilent(true);
        invisibleSeatEntity.setInvisible(false);
        invisibleSeatEntity.setInvulnerable(true);
        invisibleSeatEntity.setAiDisabled(true);
        invisibleSeatEntity.setNoDrag(true);
        invisibleSeatEntity.setHeadYaw(yaw);
        invisibleSeatEntity.setYaw(yaw);
        invisibleSeatEntity.setBodyYaw(yaw);

        if (world.spawnEntity(invisibleSeatEntity)) {
            entity.startRiding(invisibleSeatEntity, true);
            return ActionResult.SUCCESS;
        }
        return ActionResult.CONSUME;
    }

    Vec3d getSitOffsetFrom(Vec3d pos);

    float getSitYaw(BlockState state);

}
