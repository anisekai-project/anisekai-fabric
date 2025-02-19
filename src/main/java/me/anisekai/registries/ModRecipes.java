package me.anisekai.registries;

import me.anisekai.AnisekaiMod;
import me.anisekai.recipes.CondenserRecipe;
import me.anisekai.recipes.serializers.CondenserRecipeSerializer;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;


public final class ModRecipes {

    public static final RecipeType<CondenserRecipe> CONDENSER_RECIPE_TYPE = Registry.register(
            Registries.RECIPE_TYPE,
            AnisekaiMod.id("condenser"),
            CondenserRecipe.Type.INSTANCE
    );

    public static final RecipeSerializer<CondenserRecipe> CONDENSER_RECIPE_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER,
            AnisekaiMod.id("condenser"),
            new CondenserRecipeSerializer()
    );

    private ModRecipes() {}

    public static int recipes() {

        RecipeSerializer.register(CONDENSER_RECIPE_TYPE.toString(), CONDENSER_RECIPE_SERIALIZER);
        return 1;
    }

}
