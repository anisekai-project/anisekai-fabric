package me.anisekai.recipes;

import me.anisekai.AnisekaiMod;
import me.anisekai.blocks.CondenserBlock;
import me.anisekai.enums.WoodType;
import me.anisekai.registries.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class CondenserRecipe {

    public static final Map<Identifier, CondenserRecipe> RECIPES = new LinkedHashMap<>();

    private static void register(CondenserRecipe recipe) {

        RECIPES.put(recipe.getId(), recipe);
    }

    public static int createRecipes() {

        for (WoodType value : WoodType.values()) {

            if (value == WoodType.BAMBOO) {
                continue; // Skip bamboo
            }

            String sourceName       = Registries.ITEM.getId(value.asSapling()).getPath();
            String outputNameLog    = Registries.ITEM.getId(value.asLog().asItem()).getPath();
            String outputNameLeaves = Registries.ITEM.getId(value.asLeaves().asItem()).getPath();

            Identifier identifierLog = Identifier.of(
                    AnisekaiMod.MOD_ID,
                    "condenser/%s_to_%s".formatted(sourceName, outputNameLog)
            );
            Identifier identifierLeaves = Identifier.of(
                    AnisekaiMod.MOD_ID,
                    "condenser/%s_to_%s".formatted(sourceName, outputNameLeaves)
            );


            Ingredient growsOn = switch (value) {
                //noinspection DataFlowIssue – Yes, bamboo is not reachable, but needs to be there for the switch case to be happy about having all cases covered.
                case ACACIA, BAMBOO, BIRCH,
                     CHERRY, DARK_OAK, JUNGLE,
                     MANGROVE, OAK, SPRUCE -> Ingredient.fromTag(ItemTags.DIRT);
                case CRIMSON -> Ingredient.ofItems(Items.CRIMSON_NYLIUM);
                case WARPED -> Ingredient.ofItems(Items.WARPED_NYLIUM);
            };


            register(create(identifierLog)
                             .using(ItemTags.AXES)
                             .on(value.asSapling(), growsOn)
                             .consumes(1, 0)
                             .requiredTickTime(value.asLog(), CondenserBlock.CONDENSER_LIMITER_FACTOR)
                             .boostingWith(Items.BONE_MEAL, 1.2f)
                             .sounds(SoundEvents.BLOCK_CHERRY_WOOD_HIT, SoundEvents.BLOCK_CHERRY_WOOD_BREAK)
                             .output(value.asLog(), 6)
                             .build()
            );

            register(create(identifierLeaves)
                             .using(Items.SHEARS)
                             .on(value.asSapling(), growsOn)
                             .consumes(1, 0)
                             .requiredTickTime(value.asLog(), CondenserBlock.CONDENSER_LIMITER_FACTOR)
                             .boostingWith(Items.BONE_MEAL, 1.2f)
                             .sounds(SoundEvents.BLOCK_CHERRY_LEAVES_HIT, SoundEvents.BLOCK_CHERRY_LEAVES_BREAK)
                             .output(value.asLeaves(), 12)
                             .build()
            );
        }

        register(create(Identifier.of(AnisekaiMod.MOD_ID, "condenser/cobblestone"))
                         .using(ItemTags.PICKAXES)
                         .on(Items.LAVA_BUCKET, Items.WATER_BUCKET)
                         .requiredTickTime(Blocks.STONE, CondenserBlock.CONDENSER_LIMITER_FACTOR)
                         .sounds(SoundEvents.BLOCK_STONE_HIT, SoundEvents.BLOCK_STONE_BREAK)
                         .output(Items.COBBLESTONE, 1)
                         .build()
        );

        register(create(Identifier.of(AnisekaiMod.MOD_ID, "condenser/obsidian"))
                         .using(ModTags.ADVANCED_PICKAXES)
                         .on(Items.LAVA_BUCKET, Items.WATER_BUCKET)
                         .requiredTickTime(Blocks.OBSIDIAN, CondenserBlock.CONDENSER_LIMITER_FACTOR)
                         .sounds(SoundEvents.BLOCK_STONE_HIT, SoundEvents.BLOCK_STONE_BREAK)
                         .output(Items.OBSIDIAN, 1)
                         .build()
        );


        return RECIPES.size();
    }

    public static Builder create(Identifier id) {

        return new Builder(id);
    }

    public static final class Builder {

        private final Identifier   id;
        private       Ingredient   using             = null;
        private       Ingredient[] on                = new Ingredient[0];
        private       int[]        consumes          = new int[]{0, 0};
        private       long         requiredTickTime  = 200;
        private       Ingredient   booster           = null;
        private       float        boosterMultiplier = 1;
        private       SoundEvent   workingSound      = null;
        private       SoundEvent   condensedSound    = null;
        private       ItemStack    output            = null;

        private Builder(Identifier identifier) {

            this.id = identifier;
        }

        public Builder using(Ingredient ingredient) {

            this.using = ingredient;
            return this;
        }

        public Builder using(Item item) {

            return this.using(Ingredient.ofItems(item));
        }

        public Builder using(TagKey<Item> tag) {

            return this.using(Ingredient.fromTag(tag));
        }

        public Builder on(Ingredient ingredientA, Ingredient ingredientB) {

            this.on = new Ingredient[]{ingredientA, ingredientB};
            return this;
        }

        public Builder on(Item itemA, Item itemB) {

            return this.on(Ingredient.ofItems(itemA), Ingredient.ofItems(itemB));
        }

        public Builder on(Item item, TagKey<Item> tag) {

            return this.on(Ingredient.ofItems(item), Ingredient.fromTag(tag));
        }

        public Builder on(Item item, Ingredient ingredient) {

            return this.on(Ingredient.ofItems(item), ingredient);
        }

        public Builder on(TagKey<Item> tag, Item item) {

            return this.on(Ingredient.fromTag(tag), Ingredient.ofItems(item));
        }

        public Builder on(TagKey<Item> tagA, TagKey<Item> tagB) {

            return this.on(Ingredient.fromTag(tagA), Ingredient.fromTag(tagB));
        }

        public Builder consumes(int first, int second) {

            this.consumes = new int[]{first, second};
            return this;
        }

        public Builder requiredTickTime(int requiredTickTime) {

            this.requiredTickTime = requiredTickTime;
            return this;
        }

        public Builder requiredTickTime(Block block, int factor) {

            return this.requiredTickTime(Math.round(block.getHardness() * factor));
        }

        public Builder boostingWith(Item item, float multiplier) {

            this.booster           = Ingredient.ofItems(item);
            this.boosterMultiplier = multiplier;
            return this;
        }

        public Builder boostingWith(TagKey<Item> tag, float multiplier) {

            this.booster           = Ingredient.fromTag(tag);
            this.boosterMultiplier = multiplier;
            return this;
        }

        public Builder sounds(SoundEvent workingSound, SoundEvent condensedSound) {

            this.workingSound   = workingSound;
            this.condensedSound = condensedSound;
            return this;
        }

        public Builder output(ItemStack stack) {

            this.output = stack;
            return this;
        }

        public Builder output(ItemConvertible item, int count) {

            return this.output(new ItemStack(item, count));
        }

        public CondenserRecipe build() {

            Objects.requireNonNull(this.output, "Recipe must have an output");
            if (this.on.length != 2) {
                throw new IllegalArgumentException("Recipe must have 2 ingredients");
            }

            return new CondenserRecipe(
                    this.id,
                    this.using,
                    this.on,
                    this.consumes,
                    this.requiredTickTime * this.output.getCount(),
                    this.booster,
                    this.boosterMultiplier,
                    this.workingSound,
                    this.condensedSound,
                    this.output
            );
        }

    }

    private final Identifier   id;
    private final Ingredient   using;
    private final Ingredient[] on;
    private final int[]        consumes;
    private final long         requiredTickTime;
    private final Ingredient   booster;
    private final float        boosterMultiplier;
    private final SoundEvent   workingSound;
    private final SoundEvent   condensedSound;
    private final ItemStack    output;

    public CondenserRecipe(Identifier id, Ingredient using, Ingredient[] on, int[] consumes, long requiredTickTime, Ingredient booster, float boosterMultiplier, SoundEvent workingSound, SoundEvent condensedSound, ItemStack output) {

        this.id                = id;
        this.using             = using;
        this.on                = on;
        this.consumes          = consumes;
        this.requiredTickTime  = requiredTickTime;
        this.booster           = booster;
        this.boosterMultiplier = boosterMultiplier;
        this.workingSound      = workingSound;
        this.condensedSound    = condensedSound;
        this.output            = output;
    }

    public Identifier getId() {

        return this.id;
    }

    public Ingredient getUsing() {

        return this.using;
    }

    public Ingredient[] getOn() {

        return this.on;
    }

    public int[] getConsumes() {

        return this.consumes;
    }

    public long getRequiredTickTime() {

        return this.requiredTickTime;
    }

    public Ingredient getBooster() {

        return this.booster;
    }

    public float getBoosterMultiplier() {

        return this.boosterMultiplier;
    }

    public SoundEvent getWorkingSound() {

        return this.workingSound;
    }

    public SoundEvent getCondensedSound() {

        return this.condensedSound;
    }

    public ItemStack getOutput() {

        return this.output;
    }

    public boolean canUseOn(ItemStack a, ItemStack b) {

        return this.canUseOnA(a) && this.canUseOnB(b);
    }

    public boolean canUseOnA(ItemStack a) {

        return this.on[0].test(a);
    }

    public boolean canUseOnB(ItemStack b) {

        return this.on[1].test(b);
    }

    public boolean canUseWith(ItemStack tool) {

        return this.using.test(tool);
    }

    public boolean canBoost(ItemStack booster) {

        return this.booster != null && this.booster.test(booster);
    }

}
