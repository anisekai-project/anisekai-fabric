package me.anisekai.blocks;

import me.anisekai.blockentities.NightstandBlockEntity;
import me.anisekai.interfaces.Connectable;
import me.anisekai.interfaces.Orientable;
import me.anisekai.interfaces.StorageContainer;
import me.anisekai.utils.BlockUtils;
import me.anisekai.utils.RotatableShape;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Optional;

public class NightstandBlock extends Block implements BlockEntityProvider, StorageContainer<NightstandBlockEntity>, Connectable, Orientable, Waterloggable {

    public static final RotatableShape SHAPE = new RotatableShape(Arrays.asList(
            VoxelShapes.cuboid(0, 0.125, 0, 1, 0.1875, 1),
            VoxelShapes.cuboid(0, 0.1875, 0, 0.0625, 1, 1),
            VoxelShapes.cuboid(0.9375, 0.1875, 0, 1, 1, 1),
            VoxelShapes.cuboid(0.0625, 0.1875, 0.875, 0.9375, 0.9375, 0.9375),
            VoxelShapes.cuboid(0.0625, 0.9375, 0, 0.9375, 1, 1),
            VoxelShapes.cuboid(0.0625, 0.5, 0, 0.9375, 0.625, 0.875),
            VoxelShapes.cuboid(0.0625, 0.1875, 0.0625, 0.9375, 0.5, 0.1875),
            VoxelShapes.cuboid(0.0625, 0.625, 0.0625, 0.9375, 0.9375, 0.1875),
            VoxelShapes.cuboid(0.3125, 0.3125, 0, 0.6875, 0.375, 0.0625),
            VoxelShapes.cuboid(0.3125, 0.75, 0, 0.6875, 0.8125, 0.0625)
    ));

    public NightstandBlock(Block block) {

        super(FabricBlockSettings.copy(block));

        this.setDefaultState(
                this.getDefaultState()
                    .with(Properties.HORIZONTAL_FACING, Direction.NORTH)
                    .with(Properties.WATERLOGGED, false)
                    .with(Properties.NORTH, false)
                    .with(Properties.EAST, false)
                    .with(Properties.SOUTH, false)
                    .with(Properties.WEST, false)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {

        super.appendProperties(builder);
        builder.add(
                Properties.HORIZONTAL_FACING,
                Properties.WATERLOGGED,
                Properties.NORTH,
                Properties.EAST,
                Properties.WEST,
                Properties.SOUTH
        );
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {

        return this.getOrientedShapes().getShape(state.get(Properties.HORIZONTAL_FACING));
    }

    @Override
    public Optional<NightstandBlockEntity> getBlockEntityInstance(BlockEntity entity) {

        if (entity instanceof NightstandBlockEntity nightstand) {
            return Optional.of(nightstand);
        }
        return Optional.empty();
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {

        return new NightstandBlockEntity(pos, state);
    }

    public boolean canConnectTo(BlockState current, BlockState other) {

        return other.isOf(this) && other.get(Properties.HORIZONTAL_FACING) == current.get(Properties.HORIZONTAL_FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {

        BlockState state = super.getPlacementState(ctx)
                                .with(Properties.WATERLOGGED, BlockUtils.isContextWater(ctx))
                                .with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());

        return this.applyPlacementConnection(ctx.getWorld(), ctx.getBlockPos(), state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {

        if (state.get(Properties.WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        BlockState blockState = this.tryConnect(state, direction, neighborState);
        return super.getStateForNeighborUpdate(blockState, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {

        if (state.get(Properties.WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    @Override
    public Block asBlock() {

        return this;
    }

    @Override
    public RotatableShape getOrientedShapes() {

        return SHAPE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        this.getBlockEntityInstance(world.getBlockEntity(pos)).ifPresent(player::openHandledScreen);
        return ActionResult.CONSUME;
    }

}
