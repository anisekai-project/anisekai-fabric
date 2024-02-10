package me.anisekai.entities.chair;

import me.anisekai.blocks.composed.SeatBlock;
import me.anisekai.interfaces.Seatable;
import me.anisekai.registries.ModEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Objects;

public class ChairEntity extends MobEntity {

    public static ActionResult sitEntity(World world, BlockPos pos, BlockState state, Seatable seatable, Entity entity) {

        Vec3d vec   = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
        Vec3d sitAt = seatable.getSitOffsetFrom(vec);
        float yaw   = seatable.getSitYaw(state);

        ChairEntity chairEntity = Objects.requireNonNull(ModEntities.CHAIR_ENTITY.create(world));
        chairEntity.refreshPositionAndAngles(sitAt.getX(), sitAt.getY(), sitAt.getZ(), yaw, 0);

        chairEntity.setNoGravity(true);
        chairEntity.setSilent(true);
        chairEntity.setInvisible(false);
        chairEntity.setInvulnerable(true);
        chairEntity.setAiDisabled(true);
        chairEntity.setNoDrag(true);
        chairEntity.setHeadYaw(yaw);
        chairEntity.setYaw(yaw);
        chairEntity.setBodyYaw(yaw);

        if (world.spawnEntity(chairEntity)) {
            entity.startRiding(chairEntity, true);
            entity.setYaw(yaw);
            entity.setHeadYaw(yaw);

            chairEntity.setYaw(yaw);
            chairEntity.setBodyYaw(yaw);
            chairEntity.setHeadYaw(yaw);

            return ActionResult.SUCCESS;
        }
        return ActionResult.CONSUME;

    }


    public ChairEntity(EntityType<? extends ChairEntity> type, World world) {

        super(type, world);
        this.noClip = true;
    }

    @Override
    public void tick() {

        BlockState state = this.getWorld().getBlockState(this.getBlockPos());
        Block      block = state.getBlock();

        if ((!this.hasPassengers() && !this.getWorld().isClient) || !(block instanceof SeatBlock)) {
            this.removeAllPassengers();
            this.discard();
        }
    }

    @Override
    public void tickMovement() {

        super.tickMovement();
        this.setVelocity(Vec3d.ZERO);
    }

    @Override
    public boolean isAlive() {

        return !this.isRemoved();
    }

    public ActionResult interactAt(PlayerEntity player, Vec3d hitPos, Hand hand) {

        return super.interactAt(player, hitPos, hand);
    }

    @Override
    public boolean isPushedByFluids() {

        return false;
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {

        Direction direction = this.getMovementDirection();
        if (direction.getAxis() != Direction.Axis.Y) {
            int[][]          is       = Dismounting.getDismountOffsets(direction);
            BlockPos         blockPos = this.getBlockPos();
            BlockPos.Mutable mutable  = new BlockPos.Mutable();

            for (EntityPose entityPose : passenger.getPoses()) {
                Box box = passenger.getBoundingBox(entityPose);

                for (int[] js : is) {
                    mutable.set(blockPos.getX() + js[1], blockPos.getY() + 0.3, blockPos.getZ() - js[0]);
                    double d = this.getWorld().getDismountHeight(mutable);
                    if (Dismounting.canDismountInBlock(d)) {
                        Vec3d vec3d = Vec3d.ofCenter(mutable, d);
                        if (Dismounting.canPlaceEntityAt(this.getWorld(), passenger, box.offset(vec3d))) {
                            passenger.setPose(entityPose);
                            return vec3d;
                        }
                    }
                }
            }
        }
        return super.updatePassengerForDismount(passenger);
    }

    public static DefaultAttributeContainer.Builder createMobAttributes() {

        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 0);
    }

}
