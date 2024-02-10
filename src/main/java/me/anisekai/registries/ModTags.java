package me.anisekai.registries;

import me.anisekai.AnisekaiMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public final class ModTags {

    public static TagKey<Item> of(String name) {

        return TagKey.of(
                RegistryKeys.ITEM,
                new Identifier(AnisekaiMod.MOD_ID, name)
        );
    }


    public static final TagKey<Item> CHAIRS     = of("chairs");
    public static final TagKey<Item> HALF_SLABS = of("half_slabs");
    public static final TagKey<Item> STAIRS     = of("stairs");
    public static final TagKey<Item> TABLES     = of("tables");
    public static final TagKey<Item> STOOLS     = of("stools");
    public static final TagKey<Item> NIGHTSTAND = of("nightstand");

    private ModTags() {}

    public static List<TagKey<Item>> itemTags() {

        return Arrays.asList(CHAIRS, HALF_SLABS, STAIRS, TABLES, STOOLS,NIGHTSTAND);
    }

}
