package me.anisekai.blocks;

import me.anisekai.AnisekaiMod;
import me.anisekai.utils.BlockUtils;
import me.anisekai.utils.OrientableShape;
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
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HalfSlabBlock extends Block implements Waterloggable {

    public static final Identifier L0_ID = AnisekaiMod.id("half_slab_l0");
    public static final Identifier L1_ID = AnisekaiMod.id("half_slab_l1");
    public static final Identifier L2_ID = AnisekaiMod.id("half_slab_l2");
    public static final Identifier L3_ID = AnisekaiMod.id("half_slab_l3");

    public static final BooleanProperty LAYER_3 = BooleanProperty.of("layer3");
    public static final BooleanProperty LAYER_2 = BooleanProperty.of("layer2");
    public static final BooleanProperty LAYER_1 = BooleanProperty.of("layer1");
    public static final BooleanProperty LAYER_0 = BooleanProperty.of("layer0");

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

        boolean layer0 = state.get(LAYER_0);
        boolean layer1 = state.get(LAYER_1);
        boolean layer2 = state.get(LAYER_2);
        boolean layer3 = state.get(LAYER_3);

        Collection<Identifier> layerIds = new ArrayList<>();
        if (layer0) layerIds.add(L0_ID);
        if (layer1) layerIds.add(L1_ID);
        if (layer2) layerIds.add(L2_ID);
        if (layer3) layerIds.add(L3_ID);
        return OrientableShape.of(layerIds).getShape(Direction.NORTH);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {

        BlockPos        pos   = ctx.getBlockPos();
        BlockState      state = ctx.getWorld().getBlockState(pos);
        double          y     = ctx.getHitPos().y - ctx.getBlockPos().getY();
        BooleanProperty layer = this.getAffectedLayerAt(state, y);

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

    // <editor-fold desc="Layers">

    public boolean isFull(BlockState state) {

        boolean layer0 = state.get(LAYER_0);
        boolean layer1 = state.get(LAYER_1);
        boolean layer2 = state.get(LAYER_2);
        boolean layer3 = state.get(LAYER_3);

        return layer0 && layer1 && layer2 && layer3;
    }

    public int getLayerCount(BlockState state) {

        int layer0 = state.get(LAYER_0) ? 1 : 0;
        int layer1 = state.get(LAYER_1) ? 1 : 0;
        int layer2 = state.get(LAYER_2) ? 1 : 0;
        int layer3 = state.get(LAYER_3) ? 1 : 0;

        return layer0 + layer1 + layer2 + layer3;
    }

    public boolean isAffectingLayer0(double y) {

        return y < 0.25;
    }

    public boolean isAffectingLayer1(double y) {

        return y > 0.25 && y < 0.5;
    }

    public boolean isAffectingLayer2(double y) {

        return y > 0.5 && y < 0.75;
    }

    public boolean isAffectingLayer3(double y) {

        return y > 0.75;
    }

    public BooleanProperty getAffectedLayerAt(BlockState state, double y) {

        if (y == 0.75) {
            return state.get(LAYER_2) ? LAYER_3 : LAYER_2;
        } else if (y == 0.5) {
            return state.get(LAYER_1) ? LAYER_2 : LAYER_1;
        } else if (y == 0.25) {
            return state.get(LAYER_0) ? LAYER_1 : LAYER_0;
        } else if (this.isAffectingLayer0(y)) {
            return LAYER_0;
        } else if (this.isAffectingLayer1(y)) {
            return LAYER_1;
        } else if (this.isAffectingLayer2(y)) {
            return LAYER_2;
        } else if (this.isAffectingLayer3(y)) {
            return LAYER_3;
        }
        return LAYER_0; // Default to layer 0 if y value is invalid – should not happen.
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
