package me.anisekai.registries;

import me.anisekai.AnisekaiMod;
import me.anisekai.blockentities.CondenserBlockEntity;
import me.anisekai.blockentities.FishingBasketBlockEntity;
import me.anisekai.blockentities.NightstandBlockEntity;
import me.anisekai.registries.blocks.ModBlockNightstands;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public class ModBlockEntities {

    public static final BlockEntityType<NightstandBlockEntity> NIGHTSTAND = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier(AnisekaiMod.MOD_ID, "nightstand"),
            FabricBlockEntityTypeBuilder.create(
                    NightstandBlockEntity::new,
                    ModBlockNightstands.ACACIA,
                    ModBlockNightstands.BAMBOO,
                    ModBlockNightstands.BIRCH,
                    ModBlockNightstands.CHERRY,
                    ModBlockNightstands.CRIMSON,
                    ModBlockNightstands.DARK_OAK,
                    ModBlockNightstands.JUNGLE,
                    ModBlockNightstands.MANGROVE,
                    ModBlockNightstands.OAK,
                    ModBlockNightstands.SPRUCE,
                    ModBlockNightstands.WARPED
            ).build()
    );

    public static final BlockEntityType<FishingBasketBlockEntity> FISHING_BASKET = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier(AnisekaiMod.MOD_ID, "fishing_basket"),
            FabricBlockEntityTypeBuilder.create(
                    FishingBasketBlockEntity::new,
                    ModBlocks.FISHING_BASKET
            ).build()
    );

    public static final BlockEntityType<CondenserBlockEntity> CONDENSER = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier(AnisekaiMod.MOD_ID, "condenser"),
            FabricBlockEntityTypeBuilder.create(
                    CondenserBlockEntity::new,
                    ModBlocks.CONDENSER
            ).build()
    );

    public static List<BlockEntityType<?>> blockEntities() {

        return Arrays.asList(
                NIGHTSTAND,
                FISHING_BASKET,
                CONDENSER
        );
    }

}
