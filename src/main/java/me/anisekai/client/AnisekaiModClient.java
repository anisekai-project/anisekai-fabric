package me.anisekai.client;

import me.anisekai.AnisekaiMod;
import me.anisekai.entities.EmptyModel;
import me.anisekai.entities.chair.ChairRenderer;
import me.anisekai.registries.ModEntities;
import me.anisekai.registries.ModModels;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class AnisekaiModClient implements ClientModInitializer {

    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {

        AnisekaiMod.LOGGER.info("Registered {} models", ModModels.models().size());
        EntityModelLayerRegistry.registerModelLayer(ModModels.EMPTY_MODEL, EmptyModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.CHAIR_ENTITY, ChairRenderer::new);
    }

}
