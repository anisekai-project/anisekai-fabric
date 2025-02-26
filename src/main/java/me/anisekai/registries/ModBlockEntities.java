package me.anisekai.registries;

import me.anisekai.AnisekaiMod;
import me.anisekai.blockentities.CondenserBlockEntity;
import me.anisekai.blockentities.FishingBasketBlockEntity;
import me.anisekai.blockentities.NightstandBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public final class ModBlockEntities {

    public static final BlockEntityType<NightstandBlockEntity> NIGHTSTAND = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(AnisekaiMod.MOD_ID, "nightstand"),
            BlockEntityType.Builder.create(
                    NightstandBlockEntity::new,
                    ModBlocks.NIGHTSTANDS.values().toArray(new Block[0])
            ).build()
    );

    public static final BlockEntityType<FishingBasketBlockEntity> FISHING_BASKET = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(AnisekaiMod.MOD_ID, "fishing_basket"),
            BlockEntityType.Builder.create(
                    FishingBasketBlockEntity::new,
                    ModBlocks.FISHING_BASKET
            ).build()
    );

    public static final BlockEntityType<CondenserBlockEntity> CONDENSER = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(AnisekaiMod.MOD_ID, "condenser"),
            BlockEntityType.Builder.create(
                    CondenserBlockEntity::new,
                    ModBlocks.CONDENSER
            ).build()
    );

    private ModBlockEntities() {}

    public static List<BlockEntityType<?>> blockEntities() {

        return Arrays.asList(
                NIGHTSTAND,
                FISHING_BASKET,
                CONDENSER
        );
    }

}
