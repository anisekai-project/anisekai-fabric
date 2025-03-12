package me.anisekai.blocks;

import me.anisekai.blocks.feature.QuarterBlock;
import me.anisekai.utils.BlockUtils;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

import java.util.List;

public class HalfSlabBlock extends Block implements Waterloggable, QuarterBlock {

    public HalfSlabBlock(AbstractBlock.Settings settings) {

        super(settings);

        this.setDefaultState(
                this.getDefaultState()
                    .with(Properties.WATERLOGGED, false)
                    .with(LAYER_0, false)
                    .with(LAYER_1, false)
                    .with(LAYER_2, false)
                    .with(LAYER_3, false)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {

        super.appendProperties(builder);
        builder.add(
                Properties.HORIZONTAL_FACING,
                Properties.WATERLOGGED,
                LAYER_0,
                LAYER_1,
                LAYER_2,
                LAYER_3
        );
    }

    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {

        if (type == NavigationType.WATER) {
            return state.get(Properties.WATERLOGGED);
        }
        return false;
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {

        return !this.isFull(state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {

        return this.getOrientableShape(state).getShape(Direction.NORTH);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {

        BlockPos   position = ctx.getBlockPos();
        BlockState state    = ctx.getWorld().getBlockState(position);

        BooleanProperty layer = this.getPlacementLayer(
                this,
                ctx.getWorld(),
                state,
                position,
                ctx.getHitPos(),
                ctx.getPlayer().getBlockPos()
        );

        BlockState placementState;
        if (state.isOf(this)) {
            placementState = state.with(layer, true);
        } else {
            BlockState blockState = super.getPlacementState(ctx);
            if (blockState == null) {
                return null;
            }
            placementState = blockState.with(layer, true);
        }

        return placementState
                .with(Properties.WATERLOGGED, BlockUtils.isContextWater(ctx) && !this.isFull(placementState));
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


    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {

        ItemStack itemStack = context.getStack();

        if (this.isFull(state) || !itemStack.isOf(this.asItem())) {
            return false;
        }

        if (context.canReplaceExisting() || (itemStack.isOf(this.asItem()) && state.isOf(this))) {
            double          y     = context.getHitPos().y - context.getBlockPos().getY();
            BooleanProperty layer = this.getAffectedLayerAt(state, y);
            return !state.get(layer);
        }
        return false;
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {

        if (state.isOf(this)) {
            Item      item  = this.asItem();
            ItemStack stack = new ItemStack(item, this.getLayerCount(state));
            return List.of(stack);
        }

        return super.getDroppedStacks(state, builder);
    }


}
