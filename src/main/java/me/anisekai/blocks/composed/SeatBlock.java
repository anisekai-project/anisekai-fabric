package me.anisekai.blocks.composed;

import me.anisekai.entities.chair.ChairEntity;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public abstract class SeatBlock extends OrientableBlock implements Seatable {

    public SeatBlock(Block block, VoxelShape north, VoxelShape east, VoxelShape south, VoxelShape west) {

        super(block, north, east, south, west);
    }

    public SeatBlock(Block block, VoxelShape shape) {

        super(block, shape);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        if (world.isClient) {
            return ActionResult.CONSUME;
        }

        BlockPos   above      = pos.offset(Direction.UP);
        BlockState aboveState = world.getBlockState(above);

        if (player.isSpectator() || player.isSneaking() || HandUtils.getHandStack(player, hand).isOf(Items.DEBUG_STICK)) {
            return ActionResult.FAIL;
        }

        if (aboveState.isSolidBlock(world, above) && player instanceof ServerPlayerEntity serverPlayer) {
            Packet<ClientPlayPacketListener> p = new OverlayMessageS2CPacket(Text.translatable("action.chair.blocked"));
            serverPlayer.networkHandler.sendPacket(p);
            return ActionResult.FAIL;
        }

        List<ChairEntity> active = world.getEntitiesByClass(
                ChairEntity.class,
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
        } else if (ChairEntity.sitEntity(world, pos, state, this, player) == ActionResult.SUCCESS) {
            return ActionResult.SUCCESS;
        }
        return ActionResult.CONSUME;
    }

}
