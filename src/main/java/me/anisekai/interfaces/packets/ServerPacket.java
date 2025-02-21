package me.anisekai.interfaces.packets;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.packet.CustomPayload;

public interface ServerPacket extends CustomPayload {

    void handle(ServerPlayNetworking.Context context);

}
