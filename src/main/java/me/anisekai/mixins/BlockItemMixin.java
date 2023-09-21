package me.anisekai.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CommandBlockBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockItemMixin {

    /**
     * This mixin removes the need for {@link PlayerEntity} to be in {@link GameMode#CREATIVE} and
     * {@link ServerPlayerEntity} to have its {@link MinecraftServer#getPermissionLevel(GameProfile)} to 2 or above to
     * place an item which will write {@link NbtCompound} to a {@link BlockEntity}.
     * <p>
     * For few {@link BlockEntity}, is also removes the need of {@link ServerPlayerEntity} having its
     * {@link MinecraftServer#getPermissionLevel(GameProfile)} to 4.
     * <p>
     * It is important to note that any non-op {@link PlayerEntity} are now able to write NBT to
     * {@link CommandBlockBlockEntity}, but the real question in this situation would be how they obtained one in the
     * first place ?
     * <p>
     * Removed checks:
     *
     * @see BlockEntity#copyItemDataRequiresOperator
     * @see PlayerEntity#isCreativeLevelTwoOp
     */
    @Inject(method = "writeNbtToBlockEntity", at = {@At("HEAD")}, cancellable = true)
    private static void writeNbtToBlockEntity(World world, @Nullable PlayerEntity player, BlockPos pos, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {

        NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(stack);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (nbtCompound != null && blockEntity != null) {
            if (world.isClient) { // ← IF modified by mixin – see original method to see the changes
                cir.setReturnValue(false);
                return;
            }
            NbtCompound blockEntityNbt         = blockEntity.createNbt();
            NbtCompound originalBlockEntityNbt = blockEntityNbt.copy();
            blockEntityNbt.copyFrom(nbtCompound);
            if (!blockEntityNbt.equals(originalBlockEntityNbt)) {
                blockEntity.readNbt(blockEntityNbt);
                blockEntity.markDirty();
                cir.setReturnValue(true);
                return;
            }
        }
        cir.setReturnValue(false);
    }

}
