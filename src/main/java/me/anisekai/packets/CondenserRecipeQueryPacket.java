package me.anisekai.packets;

import me.anisekai.AnisekaiMod;
import me.anisekai.blockentities.CondenserBlockEntity;
import me.anisekai.interfaces.packets.ServerPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record CondenserRecipeQueryPacket(BlockPos position) implements ServerPacket {

    public static final Identifier                     ID        = AnisekaiMod.id("packets/condenser/query_recipe");
    public static final Id<CondenserRecipeQueryPacket> PACKET_ID = new Id<>(ID);

    public static final PacketCodec<RegistryByteBuf, CondenserRecipeQueryPacket> CODEC = createPacketCodec();

    public static void clientSend(BlockPos pos) {

        CustomPayload payload = new CondenserRecipeQueryPacket(pos);
        ClientPlayNetworking.send(payload);
    }

    @Override
    public void handle(ServerPlayNetworking.Context context) {

        context.server().execute(() -> {
            ServerWorld world       = context.player().getServerWorld();
            BlockEntity blockEntity = world.getBlockEntity(this.position);

            if (blockEntity instanceof CondenserBlockEntity condenser) {
                CustomPayload payload = new CondenserRecipeUpdatedPacket(this.position, condenser.getSelectedRecipe());
                ServerPlayNetworking.send(context.player(), payload);
            }
        });
    }

    @Override
    public Id<? extends CustomPayload> getId() {

        return PACKET_ID;
    }

    public static PacketCodec<RegistryByteBuf, CondenserRecipeQueryPacket> createPacketCodec() {

        return PacketCodec.tuple(
                BlockPos.PACKET_CODEC, CondenserRecipeQueryPacket::position,
                CondenserRecipeQueryPacket::new
        );
    }

}
