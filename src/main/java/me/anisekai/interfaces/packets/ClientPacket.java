package me.anisekai.interfaces.packets;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public interface ClientPacket extends CustomPayload {

    void handle(ClientPlayNetworking.Context context);

    static <T extends ClientPacket> void registerPacket(CustomPayload.Id<T> id, PacketCodec<RegistryByteBuf, T> packetCodec) {

        PayloadTypeRegistry.playS2C().register(id, packetCodec);
        ClientPlayNetworking.registerGlobalReceiver(id, ClientPacket::handle);
    }
}
