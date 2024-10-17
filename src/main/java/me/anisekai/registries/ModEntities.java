package me.anisekai.registries;

import me.anisekai.AnisekaiMod;
import me.anisekai.entities.seat.InvisibleSeatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.List;

public final class ModEntities {

    public static final EntityType<InvisibleSeatEntity> INVISIBLE_SEAT_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(AnisekaiMod.MOD_ID, "seat"),
            EntityType.Builder.create(InvisibleSeatEntity::new, SpawnGroup.MISC)
                              .dimensions(0f, 0f)
                              .build()
    );

    private ModEntities() {}

    public static List<EntityType<? extends Entity>> entities() {

        return Collections.singletonList(INVISIBLE_SEAT_ENTITY);
    }

}
