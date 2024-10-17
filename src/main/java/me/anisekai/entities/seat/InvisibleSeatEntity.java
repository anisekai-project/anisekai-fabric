package me.anisekai.entities.seat;

import me.anisekai.interfaces.Seatable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Dismounting;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@SuppressWarnings("AssignmentToSuperclassField")
public class InvisibleSeatEntity extends MobEntity {

    public InvisibleSeatEntity(EntityType<? extends InvisibleSeatEntity> type, World world) {

        super(type, world);
        this.noClip = true;
    }

    @Override
    public void tick() {

        BlockState state = this.getWorld().getBlockState(this.getBlockPos());
        Block      block = state.getBlock();

        if ((!this.hasPassengers() && !this.getWorld().isClient) || !(block instanceof Seatable)) {
            this.removeAllPassengers();
            this.discard();
        }
    }

    @Override
    public void tickMovement() {

        this.setVelocity(Vec3d.ZERO);
    }

    @Override
    public boolean isAlive() {

        return !this.isRemoved();
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
