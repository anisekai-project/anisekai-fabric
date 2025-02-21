package me.anisekai.interfaces.packets;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public interface ServerPacket extends CustomPayload {

    void handle(ServerPlayNetworking.Context context);

    static <T extends ServerPacket> void registerPacket(CustomPayload.Id<T> id, PacketCodec<RegistryByteBuf, T> packetCodec) {

        PayloadTypeRegistry.playC2S().register(id, packetCodec);
        ServerPlayNetworking.registerGlobalReceiver(id, ServerPacket::handle);
    }

}
