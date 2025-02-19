package me.anisekai.screen.condenser;

import me.anisekai.recipes.CondenserRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeEntry;

import java.util.function.Consumer;

public class CondenserRecipeRenderer {

    private final RecipeEntry<CondenserRecipe> recipe;
    private       int                          applyIdx = 0;
    private       int                          ontoIdx  = 0;
    private       int                          toolIdx  = 0;

    private long tick = System.currentTimeMillis();

    public CondenserRecipeRenderer(RecipeEntry<CondenserRecipe> recipe) {

        this.recipe = recipe;
    }

    public RecipeEntry<CondenserRecipe> getRecipe() {

        return this.recipe;
    }

    public void tick() {

        if (this.tick + 1000 > System.currentTimeMillis()) {
            return;
        }
        this.tick = System.currentTimeMillis();


        ItemStack[] apply = this.recipe.value().apply().use().getMatchingStacks();
        ItemStack[] onto  = this.recipe.value().onto().use().getMatchingStacks();
        ItemStack[] tool  = this.recipe.value().tool().getMatchingStacks();

        this.inc(this.applyIdx, apply.length, v -> this.applyIdx = v);
        this.inc(this.ontoIdx, onto.length, v -> this.ontoIdx = v);
        this.inc(this.toolIdx, tool.length, v -> this.toolIdx = v);
    }

    private void inc(int current, int max, Consumer<Integer> applier) {

        applier.accept(current == max - 1 ? 0 : current + 1);
    }

    public ItemStack getApply() {

        ItemStack[] stacks = this.recipe.value().apply().use().getMatchingStacks();
        if (stacks.length == 0) return new ItemStack(Items.LIGHT);
        return stacks[this.applyIdx];
    }

    public ItemStack getOnto() {

        ItemStack[] stacks = this.recipe.value().onto().use().getMatchingStacks();
        if (stacks.length == 0) return new ItemStack(Items.LIGHT);
        return stacks[this.ontoIdx];
    }

    public ItemStack getTool() {

        ItemStack[] stacks = this.recipe.value().tool().getMatchingStacks();
        if (stacks.length == 0) return new ItemStack(Items.LIGHT);
        return stacks[this.toolIdx];
    }


}
