package me.anisekai.entities.model;

import com.google.common.collect.ImmutableList;
import me.anisekai.entities.seat.InvisibleSeatEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

public class InvisibleSeatModel extends AnimalModel<InvisibleSeatEntity> {

    private final ModelPart base;

    public InvisibleSeatModel(ModelPart modelPart) {

        this.base = modelPart.getChild(EntityModelPartNames.CUBE);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {

        return ImmutableList.of();
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {

        return ImmutableList.of();
    }

    @Override
    public void setAngles(InvisibleSeatEntity entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch) {

    }

    public static TexturedModelData getTexturedModelData() {

        ModelData     modelData     = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(
                EntityModelPartNames.CUBE,
                ModelPartBuilder.create().uv(0, 0).cuboid(0F, 0F, 0F, 0F, 0F, 0F),
                ModelTransform.pivot(0F, 0F, 0F)
        );
        return TexturedModelData.of(modelData, 16, 16);
    }

}
