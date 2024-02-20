package me.anisekai.entities.renderer;

import me.anisekai.entities.model.InvisibleSeatModel;
import me.anisekai.entities.seat.InvisibleSeatEntity;
import me.anisekai.registries.ModModels;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class InvisibleSeatRenderer extends MobEntityRenderer<InvisibleSeatEntity, InvisibleSeatModel> {

    private static final Identifier EMPTY_TEXTURE = new Identifier("minecraft", "textures/block/stone.png");

    public InvisibleSeatRenderer(EntityRendererFactory.Context context) {

        super(context, new InvisibleSeatModel(context.getPart(ModModels.EMPTY_MODEL)), 0);
    }

    @Override
    public Identifier getTexture(InvisibleSeatEntity entity) {

        return EMPTY_TEXTURE;
    }

}
