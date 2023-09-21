package me.anisekai.blocks.chair;

import me.anisekai.utils.VoxelUtils;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
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

public class TableBlock extends Block implements Waterloggable {

    private static final VoxelShape SHAPE = VoxelUtils.make(Arrays.asList(
            VoxelShapes.cuboid(0.3125, 0, 0.3125, 0.6875, 0.0625, 0.6875),
            VoxelShapes.cuboid(0.4375, 0.0625, 0.4375, 0.5625, 0.875, 0.5625),
            VoxelShapes.cuboid(0, 0.875, 0, 1, 1, 1)
    ));

    public TableBlock(Block block) {

        super(FabricBlockSettings.copy(block));
        this.setDefaultState(this.getDefaultState().with(Properties.WATERLOGGED, false));
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {

        builder.add(Properties.WATERLOGGED);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {

        return SHAPE;
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
                    .with(Properties.WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).isOf(Fluids.WATER));
    }


}
