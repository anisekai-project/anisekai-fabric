package me.anisekai.screen.condenser;

import me.anisekai.recipes.CondenserRecipe;
import net.minecraft.item.ItemStack;

import java.util.function.Consumer;

public class CondenserRecipeRenderer {

    private final CondenserRecipe recipe;
    private       int             ingredientOneIdx = 0;
    private       int             ingredientTwoIdx = 0;
    private       int             toolIdx          = 0;

    private long tick = System.currentTimeMillis();

    public CondenserRecipeRenderer(CondenserRecipe recipe) {

        this.recipe = recipe;
    }

    public CondenserRecipe getRecipe() {

        return this.recipe;
    }

    public void tick() {

        if (this.tick + 1000 > System.currentTimeMillis()) {
            return;
        }
        this.tick = System.currentTimeMillis();

        ItemStack[] ingredientA = this.recipe.getOn()[0].getMatchingStacks();
        ItemStack[] ingredientB = this.recipe.getOn()[1].getMatchingStacks();
        ItemStack[] tool        = this.recipe.getUsing().getMatchingStacks();

        this.inc(this.ingredientOneIdx, ingredientA.length, v -> this.ingredientOneIdx = v);
        this.inc(this.ingredientTwoIdx, ingredientB.length, v -> this.ingredientTwoIdx = v);
        this.inc(this.toolIdx, tool.length, v -> this.toolIdx = v);
    }

    private void inc(int current, int max, Consumer<Integer> applier) {

        applier.accept(current == max - 1 ? 0 : current + 1);
    }

    public ItemStack getIngredientA() {

        return this.recipe.getOn()[0].getMatchingStacks()[this.ingredientOneIdx];
    }

    public ItemStack getIngredientB() {

        return this.recipe.getOn()[1].getMatchingStacks()[this.ingredientTwoIdx];
    }

    public ItemStack getTool() {

        return this.recipe.getUsing().getMatchingStacks()[this.toolIdx];
    }


}
