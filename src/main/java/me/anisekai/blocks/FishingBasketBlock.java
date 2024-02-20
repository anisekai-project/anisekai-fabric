package me.anisekai.blocks;

import me.anisekai.blockentities.FishingBasketBlockEntity;
import me.anisekai.blocks.feature.SeatBlock;
import me.anisekai.entities.seat.InvisibleSeatEntity;
import me.anisekai.interfaces.Orientable;
import me.anisekai.interfaces.StorageContainer;
import me.anisekai.registries.ModBlockEntities;
import me.anisekai.utils.RotatableShape;
import me.anisekai.utils.VoxelUtils;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FishingBasketBlock extends SeatBlock implements Orientable, StorageContainer<FishingBasketBlockEntity>, BlockEntityProvider {

    private static RotatableShape SHAPE = new RotatableShape(VoxelUtils.make(Arrays.asList(
            VoxelShapes.cuboid(0.125, 0, 0.125, 0.875, 0.75, 0.875),
            VoxelShapes.cuboid(0.0625, 0.625, 0.0625, 0.9375, 0.75, 0.9375),
            VoxelShapes.cuboid(0.0625, 0, 0.0625, 0.9375, 0.625, 0.9375),
            VoxelShapes.cuboid(0.6875, 0.5625, 0.9375, 0.8125, 0.6875, 1),
            VoxelShapes.cuboid(0.1875, 0.5625, 0.9375, 0.3125, 0.6875, 1)
    )));

    public FishingBasketBlock(Block block) {

        super(FabricBlockSettings.copy(block));

        this.setDefaultState(
                super.getDefaultState()
                     .with(Properties.HORIZONTAL_FACING, Direction.NORTH)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {

        super.appendProperties(builder);
        builder.add(
                Properties.HORIZONTAL_FACING
        );
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {

        return this.getOrientedShapes().getShape(state.get(Properties.HORIZONTAL_FACING));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {

        return super.getPlacementState(ctx)
                    .with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {

        return BlockRenderType.MODEL;
    }

    @Override
    public RotatableShape getOrientedShapes() {

        return SHAPE;
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
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {

        return true;
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {

        return true;
    }

    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {

        return VoxelShapes.empty();
    }

    @Override
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {

        if (stateFrom.isOf(this)) {
            return true;
        }
        return super.isSideInvisible(state, stateFrom, direction);
    }

    @Override
    public ActionResult onRightClick(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        double y = this.getLocalHitPos(pos, hit.getPos()).y;

        Vec3d vec   = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
        Vec3d sitAt = this.getSitOffsetFrom(vec);

        double distance = player.getPos().distanceTo(sitAt);

        if (y < 0.625 || (player.getVehicle() instanceof InvisibleSeatEntity && distance < 0.5)) {
            this.getBlockEntityInstance(world.getBlockEntity(pos)).ifPresent(player::openHandledScreen);
            return ActionResult.SUCCESS;
        }

        return super.onRightClick(state, world, pos, player, hand, hit);
    }

    @Override
    public Block asBlock() {

        return this;
    }

    @Override
    public Optional<FishingBasketBlockEntity> getBlockEntityInstance(BlockEntity entity) {

        if (entity instanceof FishingBasketBlockEntity fishing) {
            return Optional.of(fishing);
        }
        return Optional.empty();
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {

        return new FishingBasketBlockEntity(pos, state);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof FishingBasketBlockEntity) {

            if (!world.isClient) {
                ItemStack itemStack = new ItemStack(this.asItem());
                blockEntity.setStackNbt(itemStack);
                ItemEntity itemEntity = new ItemEntity(
                        world,
                        (double) pos.getX() + 0.5,
                        (double) pos.getY() + 0.5,
                        (double) pos.getZ() + 0.5,
                        itemStack
                );
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            }
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {

        BlockEntity blockEntity = builder.getOptional(LootContextParameters.BLOCK_ENTITY);
        if (blockEntity instanceof FishingBasketBlockEntity fishingBasket) {
            builder.addDynamicDrop(ShulkerBoxBlock.CONTENTS_DYNAMIC_DROP_ID, lootConsumer -> {
                for (int i = 0; i < fishingBasket.size(); ++i) {
                    lootConsumer.accept(fishingBasket.getStack(i));
                }
            });
        }
        return super.getDroppedStacks(state, builder);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {

        super.appendTooltip(stack, world, tooltip, options);
        NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(stack);
        if (nbtCompound != null) {
            if (nbtCompound.contains("LootTable", NbtElement.STRING_TYPE)) {
                tooltip.add(Text.literal("???????"));
            }
            if (nbtCompound.contains("Items", NbtElement.LIST_TYPE)) {
                DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(27, ItemStack.EMPTY);
                Inventories.readNbt(nbtCompound, defaultedList);
                int i = 0;
                int j = 0;
                for (ItemStack itemStack : defaultedList) {
                    if (itemStack.isEmpty()) {
                        continue;
                    }
                    ++j;
                    if (i > 4) {
                        continue;
                    }
                    ++i;
                    MutableText mutableText = itemStack.getName().copy();
                    mutableText.append(" x").append(String.valueOf(itemStack.getCount()));
                    tooltip.add(mutableText);
                }
                if (j - i > 0) {
                    tooltip.add(Text.translatable("container.shulkerBox.more", j - i).formatted(Formatting.ITALIC));
                }
            }
        }
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {

        ItemStack itemStack = super.getPickStack(world, pos, state);
        world.getBlockEntity(pos, ModBlockEntities.FISHING_BASKET)
             .ifPresent(blockEntity -> blockEntity.setStackNbt(itemStack));
        return itemStack;
    }


}
