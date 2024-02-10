package me.anisekai.blocks;

import me.anisekai.blocks.composed.OrientableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.SlabType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.Arrays;
import java.util.List;

public class HalfSlabBlock extends OrientableBlock {

    public static final BooleanProperty LAYER_3 = BooleanProperty.of("layer3");
    public static final BooleanProperty LAYER_2 = BooleanProperty.of("layer2");
    public static final BooleanProperty LAYER_1 = BooleanProperty.of("layer1");
    public static final BooleanProperty LAYER_0 = BooleanProperty.of("layer0");

    private static final VoxelShape SHAPE_LAYER_3 = VoxelShapes.cuboid(0, 0.75, 0, 1, 1, 1);
    private static final VoxelShape SHAPE_LAYER_2 = VoxelShapes.cuboid(0, 0.5, 0, 1, 0.75, 1);
    private static final VoxelShape SHAPE_LAYER_1 = VoxelShapes.cuboid(0, 0.25, 0, 1, 0.5, 1);
    private static final VoxelShape SHAPE_LAYER_0 = VoxelShapes.cuboid(0, 0, 0, 1, 0.25, 1);

    public HalfSlabBlock(Block block) {

        super(block);

        this.setDefaultState(
                this.getDefaultState()
                    .with(LAYER_0, false)
                    .with(LAYER_1, false)
                    .with(LAYER_2, false)
                    .with(LAYER_3, false)
        );
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {

        boolean layer0 = state.get(LAYER_0);
        boolean layer1 = state.get(LAYER_1);
        boolean layer2 = state.get(LAYER_2);
        boolean layer3 = state.get(LAYER_3);
        return !layer0 || !layer1 || !layer2 || !layer3;
    }


    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {

        VoxelShape shape = VoxelShapes.empty();

        if (state.get(LAYER_3)) {
            shape = VoxelShapes.combineAndSimplify(shape, SHAPE_LAYER_3, BooleanBiFunction.OR);
        }
        if (state.get(LAYER_2)) {
            shape = VoxelShapes.combineAndSimplify(shape, SHAPE_LAYER_2, BooleanBiFunction.OR);
        }
        if (state.get(LAYER_1)) {
            shape = VoxelShapes.combineAndSimplify(shape, SHAPE_LAYER_1, BooleanBiFunction.OR);
        }
        if (state.get(LAYER_0)) {
            shape = VoxelShapes.combineAndSimplify(shape, SHAPE_LAYER_0, BooleanBiFunction.OR);
        }

        return shape;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {

        super.appendProperties(builder);
        builder.add(LAYER_3, LAYER_2, LAYER_1, LAYER_0);
    }

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

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {

        BlockPos        pos   = ctx.getBlockPos();
        BlockState      state = ctx.getWorld().getBlockState(pos);
        double          y     = ctx.getHitPos().y - ctx.getBlockPos().getY();
        BooleanProperty layer = this.getAffectedLayerAt(state, y);

        if (state.isOf(this)) {
            return state.with(layer, true);
        }

        return super.getPlacementState(ctx).with(layer, true);
    }

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
            Item item = this.asItem();
            ItemStack stack = new ItemStack(item, this.getLayerCount(state));
            return List.of(stack);
        }

        return super.getDroppedStacks(state, builder);
    }

}
