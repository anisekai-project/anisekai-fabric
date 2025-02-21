package me.anisekai.packets;

import me.anisekai.AnisekaiMod;
import me.anisekai.blockentities.CondenserBlockEntity;
import me.anisekai.interfaces.packets.ServerPacket;
import me.anisekai.packets.abstracts.CondenserRecipePacket;
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

public class CondenserRecipeSelectedPacket extends CondenserRecipePacket implements ServerPacket {

    private static final Logger LOGGER = LoggerFactory.getLogger(CondenserRecipeSelectedPacket.class);

    public static final Identifier                     ID        = AnisekaiMod.id("packets/condenser/recipe_selected");
    public static final Id<CondenserRecipeSelectedPacket> PACKET_ID = new Id<>(ID);

    public static final PacketCodec<RegistryByteBuf, CondenserRecipeSelectedPacket> CODEC = createPacketCodec(
            CondenserRecipeSelectedPacket::new);

    public CondenserRecipeSelectedPacket(BlockPos position, Identifier identifier) {

        super(position, identifier);
    }

    @Override
    public Id<? extends CustomPayload> getId() {

        return PACKET_ID;
    }

    @Override
    public void handle(ServerPlayNetworking.Context context) {

        LOGGER.info(
                "[CondenserRecipeSelected] Received recipe selection request (pos={}, identifier={})",
                this.getPosition(),
                this.getIdentifier()
        );

        context.server().execute(() -> {
            ServerWorld world       = context.player().getServerWorld();
            BlockEntity blockEntity = world.getBlockEntity(this.getPosition());

            if (blockEntity instanceof CondenserBlockEntity condenser) {
                condenser.setSelectedRecipe(this.getIdentifier());

                LOGGER.info(
                        "[CondenserRecipeSelected] Sending recipe update notification (pos={}, identifier={})",
                        this.getPosition(),
                        this.getIdentifier()
                );

                CustomPayload payload = new CondenserRecipeUpdatedPacket(this.getPosition(), this.getIdentifier());
                world.getPlayers().forEach(player -> {
                    ServerPlayNetworking.send(player, payload);
                });
            }
        });
    }

}
