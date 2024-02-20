package me.anisekai.blocks;

import me.anisekai.blocks.feature.SeatBlock;
import me.anisekai.interfaces.Orientable;
import me.anisekai.interfaces.Seatable;
import me.anisekai.utils.BlockUtils;
import me.anisekai.utils.RotatableShape;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

import java.util.Arrays;

public class ChairBlock extends SeatBlock implements Waterloggable, Seatable, Orientable {

    private static final RotatableShape SHAPE = new RotatableShape(Arrays.asList(
            VoxelShapes.cuboid(0.125, 0, 0.125, 0.25, 0.5, 0.25),
            VoxelShapes.cuboid(0.75, 0, 0.125, 0.875, 0.5, 0.25),
            VoxelShapes.cuboid(0.125, 0, 0.75, 0.25, 0.5, 0.875),
            VoxelShapes.cuboid(0.75, 0, 0.75, 0.875, 0.5, 0.875),
            VoxelShapes.cuboid(0.125, 0.625, 0.75, 0.25, 1.375, 0.875),
            VoxelShapes.cuboid(0.75, 0.625, 0.75, 0.875, 1.375, 0.875),
            VoxelShapes.cuboid(0.25, 1.0625, 0.75, 0.75, 1.3125, 0.875),
            VoxelShapes.cuboid(0.125, 0.5, 0.125, 0.875, 0.625, 0.875)
    ));

    public ChairBlock(Block block) {

        super(FabricBlockSettings.copy(block));

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

        return super.getPlacementState(ctx)
                    .with(Properties.WATERLOGGED, BlockUtils.isContextWater(ctx))
                    .with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

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

    @Override
    public BlockRenderType getRenderType(BlockState state) {

        return BlockRenderType.MODEL;
    }

    @Override
    public Vec3d getSitOffsetFrom(Vec3d pos) {

        return pos.add(0.5, 0.4, 0.5);
    }

    @Override
    public float getSitYaw(BlockState state) {

        return state.get(Properties.HORIZONTAL_FACING).asRotation();
    }

    @Override
    public RotatableShape getOrientedShapes() {

        return SHAPE;
    }

}
