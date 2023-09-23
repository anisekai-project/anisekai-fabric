package me.anisekai.blocks;

import me.anisekai.utils.VoxelUtils;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

import java.util.Arrays;

public class StairsBlock extends HorizontalFacingBlock implements Waterloggable {

    // <editor-fold desc="Voxels">

    public static final VoxelShape VOXEL_NORTH = VoxelUtils.make(Arrays.asList(
            VoxelShapes.cuboid(0.0625, 0.0625, 0, 0.9375, 0.125, 0.25),
            VoxelShapes.cuboid(0.0625, 0.5625, 0.5, 0.9375, 0.625, 0.75),
            VoxelShapes.cuboid(0.0625, 0.3125, 0.25, 0.9375, 0.375, 0.5),
            VoxelShapes.cuboid(0.0625, 0.8125, 0.75, 0.9375, 0.875, 1),
            VoxelShapes.cuboid(0.9375, 0, 0, 1, 0.25, 0.25),
            VoxelShapes.cuboid(0.9375, 0.25, 0.25, 1, 0.5, 0.5),
            VoxelShapes.cuboid(0.9375, 0.75, 0.75, 1, 1, 1),
            VoxelShapes.cuboid(0.9375, 0.5, 0.5, 1, 0.75, 0.75),
            VoxelShapes.cuboid(0, 0, 0, 0.0625, 0.25, 0.25),
            VoxelShapes.cuboid(0, 0.25, 0.25, 0.0625, 0.5, 0.5),
            VoxelShapes.cuboid(0, 0.75, 0.75, 0.0625, 1, 1),
            VoxelShapes.cuboid(0, 0.5, 0.5, 0.0625, 0.75, 0.75)
    ));

    public static final VoxelShape VOXEL_EAST = VoxelUtils.make(Arrays.asList(
            VoxelShapes.cuboid(0.75, 0.0625, 0.0625, 1, 0.125, 0.9375),
            VoxelShapes.cuboid(0.25, 0.5625, 0.0625, 0.5, 0.625, 0.9375),
            VoxelShapes.cuboid(0.5, 0.3125, 0.0625, 0.75, 0.375, 0.9375),
            VoxelShapes.cuboid(0, 0.8125, 0.0625, 0.25, 0.875, 0.9375),
            VoxelShapes.cuboid(0.75, 0, 0.9375, 1, 0.25, 1),
            VoxelShapes.cuboid(0.5, 0.25, 0.9375, 0.75, 0.5, 1),
            VoxelShapes.cuboid(0, 0.75, 0.9375, 0.25, 1, 1),
            VoxelShapes.cuboid(0.25, 0.5, 0.9375, 0.5, 0.75, 1),
            VoxelShapes.cuboid(0.75, 0, 0, 1, 0.25, 0.0625),
            VoxelShapes.cuboid(0.5, 0.25, 0, 0.75, 0.5, 0.0625),
            VoxelShapes.cuboid(0, 0.75, 0, 0.25, 1, 0.0625),
            VoxelShapes.cuboid(0.25, 0.5, 0, 0.5, 0.75, 0.0625)
    ));

    public static final VoxelShape VOXEL_SOUTH = VoxelUtils.make(Arrays.asList(
            VoxelShapes.cuboid(0.0625, 0.0625, 0.75, 0.9375, 0.125, 1),
            VoxelShapes.cuboid(0.0625, 0.5625, 0.25, 0.9375, 0.625, 0.5),
            VoxelShapes.cuboid(0.0625, 0.3125, 0.5, 0.9375, 0.375, 0.75),
            VoxelShapes.cuboid(0.0625, 0.8125, 0, 0.9375, 0.875, 0.25),
            VoxelShapes.cuboid(0, 0, 0.75, 0.0625, 0.25, 1),
            VoxelShapes.cuboid(0, 0.25, 0.5, 0.0625, 0.5, 0.75),
            VoxelShapes.cuboid(0, 0.75, 0, 0.0625, 1, 0.25),
            VoxelShapes.cuboid(0, 0.5, 0.25, 0.0625, 0.75, 0.5),
            VoxelShapes.cuboid(0.9375, 0, 0.75, 1, 0.25, 1),
            VoxelShapes.cuboid(0.9375, 0.25, 0.5, 1, 0.5, 0.75),
            VoxelShapes.cuboid(0.9375, 0.75, 0, 1, 1, 0.25),
            VoxelShapes.cuboid(0.9375, 0.5, 0.25, 1, 0.75, 0.5)
    ));

    public static final VoxelShape VOXEL_WEST = VoxelUtils.make(Arrays.asList(
            VoxelShapes.cuboid(0, 0.0625, 0.0625, 0.25, 0.125, 0.9375),
            VoxelShapes.cuboid(0.5, 0.5625, 0.0625, 0.75, 0.625, 0.9375),
            VoxelShapes.cuboid(0.25, 0.3125, 0.0625, 0.5, 0.375, 0.9375),
            VoxelShapes.cuboid(0.75, 0.8125, 0.0625, 1, 0.875, 0.9375),
            VoxelShapes.cuboid(0, 0, 0, 0.25, 0.25, 0.0625),
            VoxelShapes.cuboid(0.25, 0.25, 0, 0.5, 0.5, 0.0625),
            VoxelShapes.cuboid(0.75, 0.75, 0, 1, 1, 0.0625),
            VoxelShapes.cuboid(0.5, 0.5, 0, 0.75, 0.75, 0.0625),
            VoxelShapes.cuboid(0, 0, 0.9375, 0.25, 0.25, 1),
            VoxelShapes.cuboid(0.25, 0.25, 0.9375, 0.5, 0.5, 1),
            VoxelShapes.cuboid(0.75, 0.75, 0.9375, 1, 1, 1),
            VoxelShapes.cuboid(0.5, 0.5, 0.9375, 0.75, 0.75, 1)
    ));
    // </editor-fold>


    public StairsBlock(Block block) {

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

}
