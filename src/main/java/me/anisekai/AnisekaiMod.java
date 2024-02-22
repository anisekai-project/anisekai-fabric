package me.anisekai;

import me.anisekai.entities.seat.InvisibleSeatEntity;
import me.anisekai.registries.*;
import me.anisekai.registries.custom.ModCondenserRecipes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnisekaiMod implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger(AnisekaiMod.class);
    public static final String MOD_ID = "anisekai";


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

        FabricDefaultAttributeRegistry.register(
                ModEntities.INVISIBLE_SEAT_ENTITY,
                InvisibleSeatEntity.createMobAttributes()
        );

        ModCondenserRecipes.init();

    }


}
