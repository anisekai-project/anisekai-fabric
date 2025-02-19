package me.anisekai.recipes.inputs;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

public record CondenserRecipeInput(
        ItemStack consumableOne,
        ItemStack consumableTwo,
        ItemStack booster,
        ItemStack tool
) implements RecipeInput {

    public static final int CONSUMABLE_ONE = 0;
    public static final int CONSUMABLE_TWO = 1;
    public static final int BOOSTER        = 2;
    public static final int TOOL           = 3;

    @Override
    public ItemStack getStackInSlot(int slot) {

        return switch (slot) {
            case CONSUMABLE_ONE -> this.consumableOne;
            case CONSUMABLE_TWO -> this.consumableTwo;
            case BOOSTER -> this.booster;
            case TOOL -> this.tool;
            default -> throw new IllegalArgumentException("Recipe does not contain slot " + slot);
        };
    }

    @Override
    public int getSize() {

        return 4;
    }

}
