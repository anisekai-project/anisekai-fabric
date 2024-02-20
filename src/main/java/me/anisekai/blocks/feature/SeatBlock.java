package me.anisekai.blocks.feature;

import me.anisekai.entities.seat.InvisibleSeatEntity;
import me.anisekai.interfaces.Seatable;
import me.anisekai.utils.HandUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.OverlayMessageS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public abstract class SeatBlock extends Block implements Seatable {

    public SeatBlock(Settings settings) {

        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        if (world.isClient) {
            return ActionResult.CONSUME;
        }

        boolean isSpectator = player.isSpectator();
        boolean isSneaking = player.isSneaking();
        boolean hasDebugStick = HandUtils.getHandStack(player, hand).isOf(Items.DEBUG_STICK);

        if (isSpectator || isSneaking || hasDebugStick) {
            return ActionResult.FAIL;
        }

        return this.onRightClick(state, world, pos, player, hand, hit);
    }

    public final Vec3d getLocalHitPos(BlockPos pos, Position hit) {

        return new Vec3d(
                hit.getX() - pos.getX(),
                hit.getY() - pos.getY(),
                hit.getZ() - pos.getZ()
        );
    }

    public ActionResult onRightClick(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        BlockPos   above      = pos.offset(Direction.UP);
        BlockState aboveState = world.getBlockState(above);

        if (aboveState.isSolidBlock(world, above) && player instanceof ServerPlayerEntity serverPlayer) {
            Packet<ClientPlayPacketListener> p = new OverlayMessageS2CPacket(Text.translatable("action.chair.blocked"));
            serverPlayer.networkHandler.sendPacket(p);
            return ActionResult.FAIL;
        }

        List<InvisibleSeatEntity> active = world.getEntitiesByClass(
                InvisibleSeatEntity.class,
                new Box(pos),
                Entity::hasPassengers
        );

        List<Entity> hasPassenger = new ArrayList<>();
        active.forEach(chairEntity -> hasPassenger.add(chairEntity.getFirstPassenger()));
        if (!active.isEmpty() && hasPassenger.stream().anyMatch(Entity::isPlayer)) {
            return ActionResult.FAIL;
        } else if (!active.isEmpty()) {
            hasPassenger.forEach(Entity::stopRiding);
            return ActionResult.SUCCESS;
        } else if (Seatable.sit(world, pos, state, this, player) == ActionResult.SUCCESS) {
            return ActionResult.SUCCESS;
        }
        return ActionResult.CONSUME;
    }

}
