package me.anisekai.packets;

import me.anisekai.AnisekaiMod;
import me.anisekai.interfaces.packets.ClientPacket;
import me.anisekai.packets.abstracts.CondenserRecipePacket;
import me.anisekai.screen.condenser.CondenserScreenHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CondenserRecipeUpdatedPacket extends CondenserRecipePacket implements ClientPacket {

    private static final Logger LOGGER = LoggerFactory.getLogger(CondenserRecipeUpdatedPacket.class);

    public static final Identifier                     ID        = AnisekaiMod.id("packets/condenser/recipe_updated");
    public static final Id<CondenserRecipeUpdatedPacket> PACKET_ID = new Id<>(ID);

    public static final PacketCodec<RegistryByteBuf, CondenserRecipeUpdatedPacket> CODEC = createPacketCodec(
            CondenserRecipeUpdatedPacket::new);

    public CondenserRecipeUpdatedPacket(BlockPos position, Identifier identifier) {

        super(position, identifier);
    }

    @Override
    public Id<? extends CustomPayload> getId() {

        return PACKET_ID;
    }

    @Override
    public void handle(ClientPlayNetworking.Context context) {

        LOGGER.info(
                "[CondenserUpdateRecipe] Received recipe update notification (pos={}, identifier={})",
                this.getPosition(),
                this.getIdentifier()
        );

        context.client().execute(() -> {
            ClientPlayerEntity player = context.player();
            if (player.currentScreenHandler instanceof CondenserScreenHandler handler) {
                if (handler.getBlockPos().equals(this.getPosition())) {
                    LOGGER.info("[CondenserUpdateRecipe] Updating UI...");
                    handler.setSelectedRecipe(this.getIdentifier());
                }
            }
        });
    }

}
