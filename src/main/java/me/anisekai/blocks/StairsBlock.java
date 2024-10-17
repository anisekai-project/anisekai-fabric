package me.anisekai.blocks;

import me.anisekai.interfaces.Orientable;
import me.anisekai.utils.BlockUtils;
import me.anisekai.utils.RotatableShape;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

import java.util.Arrays;

public class StairsBlock extends Block implements Waterloggable, Orientable {

    private static final RotatableShape SHAPE = new RotatableShape(Arrays.asList(
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

    public StairsBlock(AbstractBlock.Settings settings) {

        super(settings);

        this.setDefaultState(
                super.getDefaultState()
                     .with(Properties.HORIZONTAL_FACING, Direction.NORTH)
                     .with(Properties.WATERLOGGED, false)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {

        super.appendProperties(builder);
        builder.add(
                Properties.HORIZONTAL_FACING,
                Properties.WATERLOGGED
        );
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {

        return this.getOrientedShapes().getShape(state.get(Properties.HORIZONTAL_FACING));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {

        BlockState state = super.getPlacementState(ctx);
        if (state != null) {
            return state.with(Properties.WATERLOGGED, BlockUtils.isContextWater(ctx))
                        .with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
        }

        return null;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {

        return BlockRenderType.MODEL;
    }

    // <editor-fold desc="Waterlogged">

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {

        if (state.get(Properties.WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {

        if (state.get(Properties.WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    // </editor-fold>

    // <editor-fold desc="Orientation">

    @Override
    public RotatableShape getOrientedShapes() {

        return SHAPE;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {

        return state.with(Properties.HORIZONTAL_FACING, rotation.rotate(state.get(Properties.HORIZONTAL_FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {

        return state.rotate(mirror.getRotation(state.get(Properties.HORIZONTAL_FACING)));
    }

    // </editor-fold>

}
