package me.anisekai.entities.chair;

import me.anisekai.entities.EmptyModel;
import me.anisekai.registries.ModModels;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class ChairRenderer extends MobEntityRenderer<ChairEntity, EmptyModel> {

    private static final Identifier EMPTY_TEXTURE = new Identifier("minecraft", "textures/block/stone.png");

    public ChairRenderer(EntityRendererFactory.Context context) {

        super(context, new EmptyModel(context.getPart(ModModels.EMPTY_MODEL)), 0);
    }

    @Override
    public Identifier getTexture(ChairEntity entity) {

        return EMPTY_TEXTURE;
    }

}
