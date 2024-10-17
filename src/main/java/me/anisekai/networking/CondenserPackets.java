package me.anisekai.networking;

import me.anisekai.AnisekaiMod;
import me.anisekai.blockentities.CondenserBlockEntity;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public final class CondenserPackets {

    public static final Identifier SET_RECIPE = Identifier.of(AnisekaiMod.MOD_ID, "condenser/set_recipe");

    public record CondenserRecipePacket(BlockPos pos, int idx) implements CustomPayload {

        public static final CustomPayload.Id<CondenserRecipePacket> ID =
                new CustomPayload.Id<>(CondenserPackets.SET_RECIPE);

        public static final PacketCodec<RegistryByteBuf, CondenserRecipePacket> CODEC =
                PacketCodec.tuple(
                        BlockPos.PACKET_CODEC, CondenserRecipePacket::pos,
                        PacketCodecs.INTEGER, CondenserRecipePacket::idx,
                        CondenserRecipePacket::new
                );


        @Override
        public Id<? extends CustomPayload> getId() {

            return ID;
        }

    }


    private CondenserPackets() {}


    public static int register() {

        PayloadTypeRegistry.playC2S().register(CondenserRecipePacket.ID, CondenserRecipePacket.CODEC);
        PayloadTypeRegistry.playS2C().register(CondenserRecipePacket.ID, CondenserRecipePacket.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(
                CondenserRecipePacket.ID,
                (payload, context) -> context.server().execute(() -> {
                    ServerWorld world       = context.player().getServerWorld();
                    BlockEntity blockEntity = world.getBlockEntity(payload.pos);

                    if (blockEntity instanceof CondenserBlockEntity condenser) {
                        condenser.getDelegate().set(CondenserBlockEntity.DELEGATE_VALUE_SELECTED_RECIPE, payload.idx);
                    }
                })
        );

        return 1;
    }

    public static void sendSetRecipe(BlockPos pos, int index) {

        CustomPayload payload = new CondenserRecipePacket(pos, index);
        ClientPlayNetworking.send(payload);
    }

}
