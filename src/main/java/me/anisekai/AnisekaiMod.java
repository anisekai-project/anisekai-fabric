package me.anisekai;

import me.anisekai.entities.seat.InvisibleSeatEntity;
import me.anisekai.interfaces.packets.ClientPacket;
import me.anisekai.interfaces.packets.ServerPacket;
import me.anisekai.packets.CondenserQueryPacket;
import me.anisekai.packets.CondenserRecipePacket;
import me.anisekai.registries.*;
import net.fabricmc.api.ModInitializer;
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

        ClientPacket.registerPacket(CondenserRecipePacket.PACKET_ID, CondenserRecipePacket.CODEC);

        ServerPacket.registerPacket(CondenserQueryPacket.PACKET_ID, CondenserQueryPacket.CODEC);
        ServerPacket.registerPacket(CondenserRecipePacket.PACKET_ID, CondenserRecipePacket.CODEC);

        FabricDefaultAttributeRegistry.register(
                ModEntities.INVISIBLE_SEAT_ENTITY,
                InvisibleSeatEntity.createMobAttributes()
        );
    }


}
