package me.anisekai.client;

import me.anisekai.AnisekaiMod;
import me.anisekai.entities.model.InvisibleSeatModel;
import me.anisekai.entities.renderer.InvisibleSeatRenderer;
import me.anisekai.inventories.constrained.ConstrainedContainerScreen;
import me.anisekai.registries.ModEntities;
import me.anisekai.registries.ModModels;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class AnisekaiModClient implements ClientModInitializer {

    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {

        AnisekaiMod.LOGGER.info("Registered {} models", ModModels.models().size());
        EntityModelLayerRegistry.registerModelLayer(ModModels.EMPTY_MODEL, InvisibleSeatModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.INVISIBLE_SEAT_ENTITY, InvisibleSeatRenderer::new);
        HandledScreens.register(AnisekaiMod.CONSTRAINED_INVENTORY, ConstrainedContainerScreen::new);
    }

}
