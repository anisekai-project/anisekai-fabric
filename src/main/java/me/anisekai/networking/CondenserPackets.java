package me.anisekai.networking;

import me.anisekai.AnisekaiMod;
import me.anisekai.blockentities.CondenserBlockEntity;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class CondenserPackets {

    public static final Identifier SET_RECIPE = new Identifier(AnisekaiMod.MOD_ID, "condenser/set_recipe");

    public static int register() {

        ServerPlayNetworking.registerGlobalReceiver(SET_RECIPE, CondenserPackets::receiveSetRecipe);

        return 1;
    }


    public static void sendSetRecipe(BlockPos pos, int index) {

        PacketByteBuf packet = PacketByteBufs.create();
        packet.writeBlockPos(pos);
        packet.writeInt(index);

        ClientPlayNetworking.send(SET_RECIPE, packet);
    }

    private static void receiveSetRecipe(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {

        BlockPos pos   = buf.readBlockPos();
        int      index = buf.readInt();

        server.execute(() -> {
            ServerWorld world       = player.getServerWorld();
            BlockEntity blockEntity = world.getBlockEntity(pos);

            if (blockEntity instanceof CondenserBlockEntity condenser) {
                condenser.getDelegate().set(CondenserBlockEntity.DELEGATE_VALUE_SELECTED_RECIPE, index);
            }
        });

    }

}
