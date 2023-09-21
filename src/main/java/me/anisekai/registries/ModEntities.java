package me.anisekai.registries;

import me.anisekai.AnisekaiMod;
import me.anisekai.entities.chair.ChairEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.List;

public class ModEntities {

    public static final EntityType<ChairEntity> CHAIR_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(AnisekaiMod.MOD_ID, "chair"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ChairEntity::new)
                                   .dimensions(EntityDimensions.fixed(0f, 0f))
                                   .build()
    );

    public static List<EntityType<? extends Entity>> entities() {

        return Collections.singletonList(CHAIR_ENTITY);
    }

}
