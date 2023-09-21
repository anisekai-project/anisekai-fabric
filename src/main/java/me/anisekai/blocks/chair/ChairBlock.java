package me.anisekai.blocks.chair;

import me.anisekai.entities.chair.ChairEntity;
import me.anisekai.registries.ModEntities;
import me.anisekai.utils.VoxelUtils;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.OverlayMessageS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ChairBlock extends HorizontalFacingBlock implements Waterloggable {

    // <editor-fold desc="Voxels">

    private static final VoxelShape VOXEL_NORTH = VoxelUtils.make(Arrays.asList(
            VoxelShapes.cuboid(0.125, 0, 0.125, 0.25, 0.5, 0.25),
            VoxelShapes.cuboid(0.75, 0, 0.125, 0.875, 0.5, 0.25),
            VoxelShapes.cuboid(0.125, 0, 0.75, 0.25, 0.5, 0.875),
            VoxelShapes.cuboid(0.75, 0, 0.75, 0.875, 0.5, 0.875),
            VoxelShapes.cuboid(0.125, 0.625, 0.75, 0.25, 1.375, 0.875),
            VoxelShapes.cuboid(0.75, 0.625, 0.75, 0.875, 1.375, 0.875),
            VoxelShapes.cuboid(0.25, 1.0625, 0.75, 0.75, 1.3125, 0.875),
            VoxelShapes.cuboid(0.125, 0.5, 0.125, 0.875, 0.625, 0.875)
    ));

    private static final VoxelShape VOXEL_EAST = VoxelUtils.make(Arrays.asList(
            VoxelShapes.cuboid(0.75, 0, 0.125, 0.875, 0.5, 0.25),
            VoxelShapes.cuboid(0.75, 0, 0.75, 0.875, 0.5, 0.875),
            VoxelShapes.cuboid(0.125, 0, 0.125, 0.25, 0.5, 0.25),
            VoxelShapes.cuboid(0.125, 0, 0.75, 0.25, 0.5, 0.875),
            VoxelShapes.cuboid(0.125, 0.625, 0.125, 0.25, 1.375, 0.25),
            VoxelShapes.cuboid(0.125, 0.625, 0.75, 0.25, 1.375, 0.875),
            VoxelShapes.cuboid(0.125, 1.0625, 0.25, 0.25, 1.3125, 0.75),
            VoxelShapes.cuboid(0.125, 0.5, 0.125, 0.875, 0.625, 0.875)
    ));

    private static final VoxelShape VOXEL_SOUTH = VoxelUtils.make(Arrays.asList(
            VoxelShapes.cuboid(0.75, 0, 0.75, 0.875, 0.5, 0.875),
            VoxelShapes.cuboid(0.125, 0, 0.75, 0.25, 0.5, 0.875),
            VoxelShapes.cuboid(0.75, 0, 0.125, 0.875, 0.5, 0.25),
            VoxelShapes.cuboid(0.125, 0, 0.125, 0.25, 0.5, 0.25),
            VoxelShapes.cuboid(0.75, 0.625, 0.125, 0.875, 1.375, 0.25),
            VoxelShapes.cuboid(0.125, 0.625, 0.125, 0.25, 1.375, 0.25),
            VoxelShapes.cuboid(0.25, 1.0625, 0.125, 0.75, 1.3125, 0.25),
            VoxelShapes.cuboid(0.125, 0.5, 0.125, 0.875, 0.625, 0.875)
    ));

    private static final VoxelShape VOXEL_WEST = VoxelUtils.make(Arrays.asList(
            VoxelShapes.cuboid(0.125, 0, 0.75, 0.25, 0.5, 0.875),
            VoxelShapes.cuboid(0.125, 0, 0.125, 0.25, 0.5, 0.25),
            VoxelShapes.cuboid(0.75, 0, 0.75, 0.875, 0.5, 0.875),
            VoxelShapes.cuboid(0.75, 0, 0.125, 0.875, 0.5, 0.25),
            VoxelShapes.cuboid(0.75, 0.625, 0.75, 0.875, 1.375, 0.875),
            VoxelShapes.cuboid(0.75, 0.625, 0.125, 0.875, 1.375, 0.25),
            VoxelShapes.cuboid(0.75, 1.0625, 0.25, 0.875, 1.3125, 0.75),
            VoxelShapes.cuboid(0.125, 0.5, 0.125, 0.875, 0.625, 0.875)
    ));

    // </editor-fold>

    private final float height = 0.4f;

    public ChairBlock(Block block) {

        super(FabricBlockSettings.copy(block));
        this.setDefaultState(
                this.getDefaultState()
                    .with(Properties.HORIZONTAL_FACING, Direction.NORTH)
                    .with(Properties.WATERLOGGED, false)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {

        builder.add(Properties.HORIZONTAL_FACING, Properties.WATERLOGGED);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {

        Direction direction = state.get(FACING);

        return switch (direction) {
            case NORTH -> VOXEL_NORTH;
            case EAST -> VOXEL_EAST;
            case SOUTH -> VOXEL_SOUTH;
            case WEST -> VOXEL_WEST;
            default -> VoxelShapes.fullCube();
        };
    }

    @Override
    public FluidState getFluidState(BlockState state) {

        return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {

        if (state.get(Properties.WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {

        return super.getPlacementState(ctx)
                    .with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite())
                    .with(Properties.WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).isOf(Fluids.WATER));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        if (world.isClient) {
            return ActionResult.CONSUME;
        }

        BlockPos   above      = pos.offset(Direction.UP);
        BlockState aboveState = world.getBlockState(above);

        if (player.isSpectator() || player.isSneaking()) {
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
        } else if (this.sitEntity(world, pos, state, player) == ActionResult.SUCCESS) {
            return ActionResult.SUCCESS;
        }
        return ActionResult.CONSUME;
    }


    public ActionResult sitEntity(World world, BlockPos pos, BlockState state, Entity entityToSit) {

        double px = pos.getX() + 0.5;
        double py = pos.getY() + this.height;
        double pz = pos.getZ() + 0.5;

        float       yaw         = state.get(FACING).asRotation();
        ChairEntity chairEntity = Objects.requireNonNull(ModEntities.CHAIR_ENTITY.create(world));
        chairEntity.refreshPositionAndAngles(px, py, pz, yaw, 0);
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
            entityToSit.startRiding(chairEntity, true);
            entityToSit.setYaw(yaw);
            entityToSit.setHeadYaw(yaw);
            chairEntity.setYaw(yaw);
            chairEntity.setBodyYaw(yaw);
            chairEntity.setHeadYaw(yaw);

            return ActionResult.SUCCESS;
        }
        return ActionResult.CONSUME;
    }

}
