package me.anisekai.blocks;

import com.mojang.serialization.MapCodec;
import me.anisekai.blockentities.CondenserBlockEntity;
import me.anisekai.interfaces.Orientable;
import me.anisekai.interfaces.StorageContainer;
import me.anisekai.registries.ModBlockEntities;
import me.anisekai.utils.RotatableShape;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CondenserBlock extends BlockWithEntity implements Orientable, StorageContainer<CondenserBlockEntity> {

    public static final MapCodec<CondenserBlock> CODEC = createCodec(CondenserBlock::new);

    // Because it allows passive farming, the rate should be limited.
    public static final int             CONDENSER_LIMITER_FACTOR = 200;
    public static final BooleanProperty JAMMED                   = BooleanProperty.of("jammed");

    private static final RotatableShape SHAPE = new RotatableShape(
            VoxelShapes.cuboid(0, 0, 0, 1, 1, 1)
    );

    public CondenserBlock(AbstractBlock.Settings settings) {

        super(settings);

        this.setDefaultState(
                super.getDefaultState()
                     .with(Properties.HORIZONTAL_FACING, Direction.NORTH)
                     .with(Properties.LIT, false)
                     .with(JAMMED, false)
        );
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> checkType(World world, BlockEntityType<T> givenType) {

        return world.isClient ? null : checkType(
                givenType,
                ModBlockEntities.CONDENSER,
                (world1, pos, state, blockEntity) ->
                        blockEntity.tick(
                                world,
                                pos,
                                state,
                                blockEntity
                        )
        );
    }

    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {

        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {

        super.appendProperties(builder);
        builder.add(Properties.HORIZONTAL_FACING, Properties.LIT, JAMMED);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {

        return this.getOrientedShapes().getShape(state.get(Properties.HORIZONTAL_FACING));
    }

    @Override
    public RotatableShape getOrientedShapes() {

        return SHAPE;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {

        return super.getPlacementState(ctx)
                    .with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite())
                    .with(Properties.LIT, false)
                    .with(JAMMED, false);
    }


    @Override
    public BlockRenderType getRenderType(BlockState state) {

        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {

        return new CondenserBlockEntity(pos, state);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {

        return checkType(world, type);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        Optional<CondenserBlockEntity> blockEntityInstance = this.getBlockEntityInstance(world.getBlockEntity(pos));

        if (blockEntityInstance.isPresent()) {
            CondenserBlockEntity blockEntity = blockEntityInstance.get();
            player.openHandledScreen(blockEntity);
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }

    @Override
    public Block asBlock() {

        return this;
    }

    @Override
    public Optional<CondenserBlockEntity> getBlockEntityInstance(BlockEntity entity) {

        if (entity instanceof CondenserBlockEntity fishing) {
            return Optional.of(fishing);
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
    protected MapCodec<? extends BlockWithEntity> getCodec() {

        return CODEC;
    }

}
