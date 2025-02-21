package me.anisekai;

import me.anisekai.entities.seat.InvisibleSeatEntity;
import me.anisekai.interfaces.packets.ServerPacket;
import me.anisekai.packets.CondenserRecipeQueryPacket;
import me.anisekai.packets.CondenserRecipeSelectedPacket;
import me.anisekai.packets.CondenserRecipeUpdatedPacket;
import me.anisekai.registries.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnisekaiMod implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger(AnisekaiMod.class);
    public static final String MOD_ID = "anisekai";

    public static Identifier id(String path) {

        return Identifier.of(MOD_ID, path);
    }


    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {

        LOGGER.info("Registered {} blocks", ModBlocks.blocks().size());
        LOGGER.info("Registered {} items", ModItems.items().size());
        ModItems.addToInventory();
        LOGGER.info("Registered {} entities", ModEntities.entities().size());
        LOGGER.info("Registered {} block entities", ModBlockEntities.blockEntities().size());
        LOGGER.info("Registered {} screen handlers", ModScreenHandler.screenHandlers().size());
        LOGGER.info("Loaded {} item tags", ModTags.itemTags().size());
        LOGGER.info("Registered {} modded recipes", ModRecipes.recipes());

        PayloadTypeRegistry.playC2S().register(CondenserRecipeQueryPacket.PACKET_ID, CondenserRecipeQueryPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(CondenserRecipeSelectedPacket.PACKET_ID, CondenserRecipeSelectedPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(CondenserRecipeUpdatedPacket.PACKET_ID, CondenserRecipeUpdatedPacket.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(CondenserRecipeQueryPacket.PACKET_ID, ServerPacket::handle);
        ServerPlayNetworking.registerGlobalReceiver(CondenserRecipeSelectedPacket.PACKET_ID, ServerPacket::handle);

        FabricDefaultAttributeRegistry.register(
                ModEntities.INVISIBLE_SEAT_ENTITY,
                InvisibleSeatEntity.createMobAttributes()
        );
    }


}
