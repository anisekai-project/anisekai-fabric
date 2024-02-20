package me.anisekai;

import me.anisekai.entities.seat.InvisibleSeatEntity;
import me.anisekai.inventories.constrained.ConstrainedContainerScreenHandler;
import me.anisekai.registries.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnisekaiMod implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger(AnisekaiMod.class);
    public static final String MOD_ID = "anisekai";


    public static final ScreenHandlerType<ConstrainedContainerScreenHandler> CONSTRAINED_INVENTORY = Registry.register(
            Registries.SCREEN_HANDLER,
            new Identifier(MOD_ID, "constrained_inventory"),
            new ScreenHandlerType<>(ConstrainedContainerScreenHandler::createConstrained, FeatureFlags.VANILLA_FEATURES)
    );

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
        LOGGER.info("Loaded {} item tags", ModTags.itemTags().size());

        FabricDefaultAttributeRegistry.register(
                ModEntities.INVISIBLE_SEAT_ENTITY,
                InvisibleSeatEntity.createMobAttributes()
        );
    }

}
