package me.anisekai.registries;

import me.anisekai.AnisekaiMod;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.List;

public final class ModModels {

    public static final EntityModelLayer EMPTY_MODEL = new EntityModelLayer(Identifier.of(
            AnisekaiMod.MOD_ID,
            "null"
    ), "null");

    private ModModels() {}

    public static List<EntityModelLayer> models() {

        return Collections.singletonList(EMPTY_MODEL);
    }

}
