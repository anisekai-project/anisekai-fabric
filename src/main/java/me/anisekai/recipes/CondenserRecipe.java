package me.anisekai.recipes;

import me.anisekai.AnisekaiMod;
import me.anisekai.recipes.inputs.CondenserRecipeInput;
import me.anisekai.recipes.types.ConsumableIngredient;
import me.anisekai.registries.ModRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public record CondenserRecipe(
        ConsumableIngredient apply,
        ConsumableIngredient onto,
        Ingredient booster,
        Ingredient tool,
        int time,
        ItemStack result
) implements Recipe<CondenserRecipeInput> {

    @SuppressWarnings("Singleton")
    public static final class Type implements RecipeType<CondenserRecipe> {

        private Type() {}

        public static final Type       INSTANCE = new Type();
        public static final Identifier ID       = AnisekaiMod.id("condenser");

        @Override
        public String toString() {

            return ID.toString();
        }

    }

    public boolean test(ItemStack apply, ItemStack onto, ItemStack tool) {

        return this.apply.test(apply) && this.onto.test(onto) && this.tool.test(tool);
    }

    @Override
    public boolean matches(CondenserRecipeInput input, World world) {

        boolean canConsume          = this.apply.test(input.consumableOne()) && this.onto.test(input.consumableTwo());
        boolean isBoosterCompatible = input.booster().isEmpty() || this.booster.test(input.booster());
        boolean canUseTool          = this.tool.test(input.tool());

        return canConsume && isBoosterCompatible && canUseTool;
    }

    @Override
    public ItemStack craft(CondenserRecipeInput input, RegistryWrapper.WrapperLookup lookup) {

        return this.result.copy();
    }

    @Override
    public boolean fits(int width, int height) {

        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {

        return this.result;
    }

    /**
     * {@return the serializer associated with this recipe}
     */
    @Override
    public RecipeSerializer<?> getSerializer() {

        return ModRecipes.CONDENSER_RECIPE_SERIALIZER;
    }

    /**
     * {@return the type of this recipe}
     *
     * <p>The {@code type} in the recipe JSON format is the {@linkplain
     * #getSerializer() serializer} instead.
     */
    @Override
    public RecipeType<?> getType() {

        return ModRecipes.CONDENSER_RECIPE_TYPE;
    }

}
