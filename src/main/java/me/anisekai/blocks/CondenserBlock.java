package me.anisekai.blocks;

import com.mojang.serialization.MapCodec;
import me.anisekai.AnisekaiMod;
import me.anisekai.blockentities.CondenserBlockEntity;
import me.anisekai.interfaces.Orientable;
import me.anisekai.interfaces.StorageContainer;
import me.anisekai.registries.ModBlockEntities;
import me.anisekai.utils.OrientableShape;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CondenserBlock extends BlockWithEntity implements Orientable, StorageContainer<CondenserBlockEntity> {

    public static final Identifier               ID    = AnisekaiMod.id("condenser");
    public static final MapCodec<CondenserBlock> CODEC = createCodec(CondenserBlock::new);

    // Because it allows passive farming, the rate should be limited.
    public static final int             CONDENSER_LIMITER_FACTOR = 200;
    public static final BooleanProperty JAMMED                   = BooleanProperty.of("jammed");

    private static final OrientableShape SHAPE = OrientableShape.of(ID);

    public CondenserBlock(AbstractBlock.Settings settings) {

        super(settings);

        this.setDefaultState(
                super.getDefaultState()
                     .with(Properties.HORIZONTAL_FACING, Direction.NORTH)
                     .with(Properties.LIT, false)
                     .with(JAMMED, false)
        );
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
    public BlockState getPlacementState(ItemPlacementContext ctx) {

        BlockState state = super.getPlacementState(ctx);
        if (state != null) {
            return state.with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite())
                        .with(Properties.LIT, false)
                        .with(JAMMED, false);
        }

        return null;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {

        return BlockRenderType.MODEL;
    }

    // <editor-fold desc="Orientation">

    @Override
    public OrientableShape getOrientedShapes() {

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
    public Optional<CondenserBlockEntity> getBlockEntityInstance(BlockEntity entity) {

        if (entity instanceof CondenserBlockEntity condenser) {
            return Optional.of(condenser);
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

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {

        return new CondenserBlockEntity(pos, state);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {

        return StorageContainer.checkType(world, type, ModBlockEntities.CONDENSER);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        if (!world.isClient) {
            return ActionResult.PASS;
        }

        Optional<CondenserBlockEntity> blockEntityInstance = this.getBlockEntityInstance(world.getBlockEntity(pos));

        if (blockEntityInstance.isPresent()) {
            CondenserBlockEntity blockEntity = blockEntityInstance.get();

            if (blockEntity.getAvailableRecipes().isEmpty()) {
                player.sendMessage(Text.translatable("anisekai.message.condenser.no_recipe"), false);
                return ActionResult.FAIL;
            }

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
