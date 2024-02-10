package me.anisekai.blocks.composed;

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

import java.util.HashMap;
import java.util.Map;

public class OrientableBlock extends HorizontalFacingBlock implements Waterloggable {

    private final Map<Direction, VoxelShape> shapes = new HashMap<>();

    public OrientableBlock(Block block, VoxelShape north, VoxelShape east, VoxelShape south, VoxelShape west) {

        super(FabricBlockSettings.copy(block));
        this.setDefaultState(
                this.getDefaultState()
                    .with(Properties.HORIZONTAL_FACING, Direction.NORTH)
                    .with(Properties.WATERLOGGED, false)
        );

        this.shapes.put(Direction.NORTH, north);
        this.shapes.put(Direction.EAST, east);
        this.shapes.put(Direction.SOUTH, south);
        this.shapes.put(Direction.WEST, west);
    }

    public OrientableBlock(Block block, VoxelShape shape) {

        this(block, shape, shape, shape, shape);
    }

    public OrientableBlock(Block block) {

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

        return this.shapes.getOrDefault(state.get(FACING), VoxelShapes.fullCube());
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
    public BlockRenderType getRenderType(BlockState state) {

        return BlockRenderType.MODEL;
    }


}
