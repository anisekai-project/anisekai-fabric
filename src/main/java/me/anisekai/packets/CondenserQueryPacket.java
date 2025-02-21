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

public record CondenserQueryPacket(BlockPos position) implements ServerPacket {

    private static final Logger LOGGER = LoggerFactory.getLogger(CondenserQueryPacket.class);

    public static final Identifier               ID        = AnisekaiMod.id("packets/condenser/query_recipe");
    public static final Id<CondenserQueryPacket> PACKET_ID = new Id<>(ID);

    public static final PacketCodec<RegistryByteBuf, CondenserQueryPacket> CODEC = createPacketCodec();

    public static void clientSend(BlockPos pos) {

        LOGGER.info("[Client] Sending condenser query packet for position {}", pos);
        CustomPayload payload = new CondenserQueryPacket(pos);
        ClientPlayNetworking.send(payload);
    }

    @Override
    public void handle(ServerPlayNetworking.Context context) {

        LOGGER.info("[Server] Received condenser query packet for position {}", this.position);
        context.server().execute(() -> {
            ServerWorld world       = context.player().getServerWorld();
            BlockEntity blockEntity = world.getBlockEntity(this.position);

            if (blockEntity instanceof CondenserBlockEntity condenser) {
                CondenserRecipePacket.serverSend(world, this.position, condenser.getSelectedRecipe());
            }
        });
    }

    @Override
    public Id<? extends CustomPayload> getId() {

        return PACKET_ID;
    }

    public static PacketCodec<RegistryByteBuf, CondenserQueryPacket> createPacketCodec() {

        return PacketCodec.tuple(
                BlockPos.PACKET_CODEC, CondenserQueryPacket::position,
                CondenserQueryPacket::new
        );
    }

}
