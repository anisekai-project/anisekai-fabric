package me.anisekai.registries;

import me.anisekai.recipes.CondenserRecipe;

public final class ModRecipes {

    private ModRecipes() {}

    public static int recipes() {

        int count = 0;
        count += CondenserRecipe.createRecipes();
        return count;
    }

}
