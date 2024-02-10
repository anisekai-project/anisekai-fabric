package me.anisekai.blocks;

import me.anisekai.blockentities.NightstandBlockEntity;
import me.anisekai.blocks.composed.OrientableStorageBlock;
import me.anisekai.utils.VoxelUtils;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public class NightstandBlock extends OrientableStorageBlock<NightstandBlockEntity> implements BlockEntityProvider {


    // <editor-fold desc="Model Properties">
    // </editor-fold>

    // <editor-fold desc="Voxels">

    public static final VoxelShape VOXEL_NORTH = VoxelUtils.make(Arrays.asList(
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

    public static final VoxelShape VOXEL_EAST = VoxelUtils.make(Arrays.asList(
            VoxelShapes.cuboid(0, 0.125, 0, 1, 0.1875, 1),
            VoxelShapes.cuboid(0, 0.1875, 0, 1, 1, 0.0625),
            VoxelShapes.cuboid(0, 0.1875, 0.9375, 1, 1, 1),
            VoxelShapes.cuboid(0.0625, 0.1875, 0.0625, 0.125, 0.9375, 0.9375),
            VoxelShapes.cuboid(0, 0.9375, 0.0625, 1, 1, 0.9375),
            VoxelShapes.cuboid(0.125, 0.5, 0.0625, 1, 0.625, 0.9375),
            VoxelShapes.cuboid(0.8125, 0.1875, 0.0625, 0.9375, 0.5, 0.9375),
            VoxelShapes.cuboid(0.8125, 0.625, 0.0625, 0.9375, 0.9375, 0.9375),
            VoxelShapes.cuboid(0.9375, 0.3125, 0.3125, 1, 0.375, 0.6875),
            VoxelShapes.cuboid(0.9375, 0.75, 0.3125, 1, 0.8125, 0.6875)
    ));

    public static final VoxelShape VOXEL_SOUTH = VoxelUtils.make(Arrays.asList(
            VoxelShapes.cuboid(0, 0.125, 0, 1, 0.1875, 1),
            VoxelShapes.cuboid(0.9375, 0.1875, 0, 1, 1, 1),
            VoxelShapes.cuboid(0, 0.1875, 0, 0.0625, 1, 1),
            VoxelShapes.cuboid(0.0625, 0.1875, 0.0625, 0.9375, 0.9375, 0.125),
            VoxelShapes.cuboid(0.0625, 0.9375, 0, 0.9375, 1, 1),
            VoxelShapes.cuboid(0.0625, 0.5, 0.125, 0.9375, 0.625, 1),
            VoxelShapes.cuboid(0.0625, 0.1875, 0.8125, 0.9375, 0.5, 0.9375),
            VoxelShapes.cuboid(0.0625, 0.625, 0.8125, 0.9375, 0.9375, 0.9375),
            VoxelShapes.cuboid(0.3125, 0.3125, 0.9375, 0.6875, 0.375, 1),
            VoxelShapes.cuboid(0.3125, 0.75, 0.9375, 0.6875, 0.8125, 1)
    ));

    public static final VoxelShape VOXEL_WEST = VoxelUtils.make(Arrays.asList(
            VoxelShapes.cuboid(0, 0.125, 0, 1, 0.1875, 1),
            VoxelShapes.cuboid(0, 0.1875, 0.9375, 1, 1, 1),
            VoxelShapes.cuboid(0, 0.1875, 0, 1, 1, 0.0625),
            VoxelShapes.cuboid(0.875, 0.1875, 0.0625, 0.9375, 0.9375, 0.9375),
            VoxelShapes.cuboid(0, 0.9375, 0.0625, 1, 1, 0.9375),
            VoxelShapes.cuboid(0, 0.5, 0.0625, 0.875, 0.625, 0.9375),
            VoxelShapes.cuboid(0.0625, 0.1875, 0.0625, 0.1875, 0.5, 0.9375),
            VoxelShapes.cuboid(0.0625, 0.625, 0.0625, 0.1875, 0.9375, 0.9375),
            VoxelShapes.cuboid(0, 0.3125, 0.3125, 0.0625, 0.375, 0.6875),
            VoxelShapes.cuboid(0, 0.75, 0.3125, 0.0625, 0.8125, 0.6875)
    ));
    // </editor-fold>

    protected static final Map<Direction, BooleanProperty> FACING_PROPERTIES =
            ConnectingBlock.FACING_PROPERTIES.entrySet()
                                             .stream()
                                             .filter(entry -> entry.getKey().getAxis().isHorizontal())
                                             .collect(Util.toMap());

    public NightstandBlock(Block block) {

        super(block, VOXEL_NORTH, VOXEL_EAST, VOXEL_SOUTH, VOXEL_WEST);

        this.setDefaultState(
                this.getDefaultState()
                    .with(Properties.NORTH, false)
                    .with(Properties.EAST, false)
                    .with(Properties.SOUTH, false)
                    .with(Properties.WEST, false)
        );
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

        World          world        = ctx.getWorld();
        BlockPos       pos          = ctx.getBlockPos();
        BlockState     currentState = super.getPlacementState(ctx);
        Direction.Axis axis         = currentState.get(Properties.HORIZONTAL_FACING).getAxis();

        if (axis == Direction.Axis.X) {
            return currentState
                    .with(Properties.NORTH, this.canConnectTo(currentState, world.getBlockState(pos.north())))
                    .with(Properties.SOUTH, this.canConnectTo(currentState, world.getBlockState(pos.south())))
                    .with(Properties.EAST, false)
                    .with(Properties.WEST, false);
        } else if (axis == Direction.Axis.Z) {
            return currentState
                    .with(Properties.EAST, this.canConnectTo(currentState, world.getBlockState(pos.east())))
                    .with(Properties.WEST, this.canConnectTo(currentState, world.getBlockState(pos.west())))
                    .with(Properties.NORTH, false)
                    .with(Properties.SOUTH, false);
        }

        return currentState;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {

        Direction facingDirection = state.get(Properties.HORIZONTAL_FACING);

        if (facingDirection.getAxis() != direction.getAxis() && FACING_PROPERTIES.containsKey(direction)) {
            return state.with(FACING_PROPERTIES.get(direction), this.canConnectTo(state, neighborState));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {

        builder.add(
                Properties.HORIZONTAL_FACING,
                Properties.WATERLOGGED,
                Properties.NORTH,
                Properties.EAST,
                Properties.WEST,
                Properties.SOUTH
        );
    }

}
