package me.anisekai.recipes;

import me.anisekai.blockentities.CondenserBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.function.Predicate;

public class ConsumingCondenserRecipe extends CondenserRecipe {

    private final int ingredientOneConsumption;
    private final int ingredientTwoConsumption;

    public ConsumingCondenserRecipe(Identifier id, ItemStack condensedStack, Predicate<ItemStack> ingredientOne, Predicate<ItemStack> ingredientTwo, Predicate<ItemStack> booster, Predicate<ItemStack> tool, ItemStack output, float workRate, float boosterBonus, SoundEvent workingSound, SoundEvent generateSound, int ingredientOneConsumption, int ingredientTwoConsumption) {

        super(
                id,
                condensedStack,
                ingredientOne,
                ingredientTwo,
                booster,
                tool,
                output,
                workRate,
                boosterBonus,
                workingSound,
                generateSound
        );

        this.ingredientOneConsumption = ingredientOneConsumption;
        this.ingredientTwoConsumption = ingredientTwoConsumption;
    }

    @Override
    public int doCraft(CondenserBlockEntity condenser) {

        condenser.getStack(CondenserBlockEntity.INGREDIENT_ONE_SLOT).decrement(this.ingredientOneConsumption);
        condenser.getStack(CondenserBlockEntity.INGREDIENT_TWO_SLOT).decrement(this.ingredientTwoConsumption);
        condenser.getStack(CondenserBlockEntity.BOOSTER_SLOT).decrement(1);
        ItemStack outputSlot = condenser.getStack(CondenserBlockEntity.OUTPUT_SLOT);

        if (outputSlot.isEmpty()) {
            condenser.setStack(CondenserBlockEntity.OUTPUT_SLOT, this.getOutput().copy());
        } else {
            condenser.getStack(CondenserBlockEntity.OUTPUT_SLOT).increment(this.getOutput().getCount());
        }
        return this.getOutput().getCount();
    }

}
