package me.anisekai.blocks;

import com.mojang.serialization.MapCodec;
import me.anisekai.AnisekaiMod;
import me.anisekai.blockentities.NightstandBlockEntity;
import me.anisekai.interfaces.Connectable;
import me.anisekai.interfaces.Orientable;
import me.anisekai.interfaces.StorageContainer;
import me.anisekai.utils.BlockUtils;
import me.anisekai.utils.OrientableShape;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class NightstandBlock extends Block implements BlockEntityProvider, StorageContainer<NightstandBlockEntity>, Connectable, Orientable, Waterloggable {

    public static final Identifier                ID    = AnisekaiMod.id("nightstand");
    public static final MapCodec<NightstandBlock> CODEC = createCodec(NightstandBlock::new);

    public static final OrientableShape SHAPE = OrientableShape.of(ID);

    public NightstandBlock(AbstractBlock.Settings settings) {

        super(settings);

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

    @Override
    public OrientableShape getOrientedShapes() {

        return SHAPE;
    }

    // <editor-fold desc="Waterlogged + Connected Block">

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

    public boolean canConnectTo(BlockState current, BlockState other) {

        return other.isOf(this) && other.get(Properties.HORIZONTAL_FACING) == current.get(Properties.HORIZONTAL_FACING);
    }

    // </editor-fold>

    // <editor-fold desc="Storage">

    @Override
    public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity == null) {
            return false;
        }
        return blockEntity.onSyncedBlockEvent(type, data);
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {

        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {

        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {

        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity instanceof NamedScreenHandlerFactory ? (NamedScreenHandlerFactory) blockEntity : null;
    }

    @Override
    public Optional<NightstandBlockEntity> getBlockEntityInstance(BlockEntity entity) {

        if (entity instanceof NightstandBlockEntity nightstand) {
            return Optional.of(nightstand);
        }
        return Optional.empty();
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {

        if (state.isOf(newState.getBlock())) {
            return;
        }

        this.getBlockEntityInstance(world.getBlockEntity(pos)).ifPresent(inv -> {
            ItemScatterer.spawn(world, pos, inv);
            world.updateComparators(pos, this.asBlock());
        });

        if (state.hasBlockEntity() && !state.isOf(newState.getBlock())) {
            world.removeBlockEntity(pos);
        }
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {

        Block     block = state.getBlock();
        Item      item  = block.asItem();
        ItemStack stack = new ItemStack(item);
        return Collections.singletonList(stack);
    }

    @Override
    protected MapCodec<? extends Block> getCodec() {

        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {

        return new NightstandBlockEntity(pos, state);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        Optional<NightstandBlockEntity> blockEntityInstance = this.getBlockEntityInstance(world.getBlockEntity(pos));

        if (blockEntityInstance.isPresent()) {
            NightstandBlockEntity blockEntity = blockEntityInstance.get();
            player.openHandledScreen(blockEntity);
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }

    // </editor-fold>

    @Override
    public Block asBlock() {

        return this;
    }

}
