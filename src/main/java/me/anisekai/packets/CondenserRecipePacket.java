package me.anisekai.packets;

import me.anisekai.AnisekaiMod;
import me.anisekai.blockentities.CondenserBlockEntity;
import me.anisekai.interfaces.packets.ClientPacket;
import me.anisekai.interfaces.packets.ServerPacket;
import me.anisekai.screen.condenser.CondenserScreenHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record CondenserRecipePacket(BlockPos position, Identifier recipe) implements ClientPacket, ServerPacket {

    private static final Logger LOGGER = LoggerFactory.getLogger(CondenserRecipePacket.class);

    public static final Identifier                              ID        = AnisekaiMod.id("packets/condenser/set_recipe");
    public static final CustomPayload.Id<CondenserRecipePacket> PACKET_ID = new CustomPayload.Id<>(ID);

    public static final PacketCodec<RegistryByteBuf, CondenserRecipePacket> CODEC = createPacketCodec();

    public static void clientSend(BlockPos pos, Identifier recipe) {

        CustomPayload payload = new CondenserRecipePacket(pos, recipe);
        ClientPlayNetworking.send(payload);
    }

    public static void serverSend(ServerWorld world, BlockPos pos, Identifier recipe) {

        CustomPayload payload = new CondenserRecipePacket(pos, recipe);

        for (ServerPlayerEntity player : world.getPlayers()) {
            if (player.currentScreenHandler instanceof CondenserScreenHandler handler) {
                if (handler.getBlockPos().equals(pos)) {
                    LOGGER.info("[Server] > Sending to {}", player.getName().getString());
                    ServerPlayNetworking.send(player, payload);
                }
            }
        }
    }


    @Override
    public void handle(ClientPlayNetworking.Context context) {

        context.client().execute(() -> {
            ClientPlayerEntity player = context.player();
            if (player.currentScreenHandler instanceof CondenserScreenHandler handler) {
                handler.setSelectedRecipe(this.recipe());
            }
        });
    }

    @Override
    public void handle(ServerPlayNetworking.Context context) {

        context.server().execute(() -> {
            ServerWorld world       = context.player().getServerWorld();
            BlockEntity blockEntity = world.getBlockEntity(this.position);

            if (blockEntity instanceof CondenserBlockEntity condenser) {
                condenser.setSelectedRecipe(this.recipe());
                serverSend(world, this.position, this.recipe);
            }
        });
    }

    @Override
    public Id<? extends CustomPayload> getId() {

        return PACKET_ID;
    }

    public static PacketCodec<RegistryByteBuf, CondenserRecipePacket> createPacketCodec() {

        return PacketCodec.tuple(
                BlockPos.PACKET_CODEC, CondenserRecipePacket::position,
                Identifier.PACKET_CODEC, CondenserRecipePacket::recipe,
                CondenserRecipePacket::new
        );
    }

}
