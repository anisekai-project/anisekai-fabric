package me.anisekai.registries;

import me.anisekai.AnisekaiMod;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public final class ModTags {

    public static final TagKey<Item> CHAIRS = TagKey.of(
            RegistryKeys.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "chairs")
    );

    public static final TagKey<Item> HALF_SLABS = TagKey.of(
            RegistryKeys.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "half_slabs")
    );

    public static final TagKey<Item> STAIRS = TagKey.of(
            RegistryKeys.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "stairs")
    );

    public static final TagKey<Item> TABLES = TagKey.of(
            RegistryKeys.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "tables")
    );

    public static final TagKey<Item> STOOLS = TagKey.of(
            RegistryKeys.ITEM,
            new Identifier(AnisekaiMod.MOD_ID, "stools")
    );

    private ModTags() {}

    public static List<TagKey<Item>> itemTags() {

        return Arrays.asList(CHAIRS, HALF_SLABS, STAIRS, TABLES, STOOLS);
    }

}
