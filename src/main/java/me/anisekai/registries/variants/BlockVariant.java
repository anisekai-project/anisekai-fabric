package me.anisekai.registries.variants;

import me.anisekai.AnisekaiMod;
import me.anisekai.enums.CoinEnum;
import me.anisekai.enums.WoodEnum;
import me.anisekai.interfaces.Nameable;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class BlockVariant<T extends Nameable> implements Nameable {

    public static final BlockVariant<WoodEnum> WOODS = new BlockVariant<>("woods", WoodEnum.values());
    public static final BlockVariant<CoinEnum> COINS = new BlockVariant<>("coins", CoinEnum.values());

    private final String name;
    private final T[]    variants;

    public BlockVariant(String name, T[] variants) {

        this.name     = name;
        this.variants = variants;
    }

    @Override
    public String getName() {

        return this.name;
    }

    public T[] getVariants() {

        return this.variants;
    }

    public Map<T, Block> createBlocks(String format, Function<T, Block> creator) {

        Map<T, Block> blockMap = new HashMap<>();
        for (T variant : this.variants) {
            String name = format.formatted(variant.getName());
            Block block = Registry.register(
                    Registries.BLOCK,
                    AnisekaiMod.id(name),
                    creator.apply(variant)
            );
            blockMap.put(variant, block);
        }
        return blockMap;
    }

    public Map<T, BlockItem> createBlockItems(Map<T, Block> blocks, String format, int stackSize) {

        Map<T, BlockItem> itemMap = new HashMap<>();
        for (T variant : this.variants) {
            String name  = format.formatted(variant.getName());
            Block  block = blocks.get(variant);
            BlockItem item = Registry.register(
                    Registries.ITEM,
                    AnisekaiMod.id(name),
                    new BlockItem(block, new Item.Settings().maxCount(stackSize))
            );
            itemMap.put(variant, item);
        }
        return itemMap;
    }

    public Map<T, Item> createItems(String format, Function<T, Item> creator) {

        Map<T, Item> itemMap = new HashMap<>();
        for (T variant : this.variants) {
            String name = format.formatted(variant.getName());
            Item item = Registry.register(
                    Registries.ITEM,
                    AnisekaiMod.id(name),
                    creator.apply(variant)
            );
            itemMap.put(variant, item);
        }
        return itemMap;
    }

}
