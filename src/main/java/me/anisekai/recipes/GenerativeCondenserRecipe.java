package me.anisekai.recipes;

import me.anisekai.blockentities.CondenserBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.function.Predicate;

public class GenerativeCondenserRecipe extends CondenserRecipe {


    public GenerativeCondenserRecipe(Identifier id, ItemStack condensedStack, Predicate<ItemStack> ingredientOne, Predicate<ItemStack> ingredientTwo, Predicate<ItemStack> booster, Predicate<ItemStack> tool, ItemStack output, float workRate, float boosterBonus, SoundEvent workingSound, SoundEvent generateSound) {

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
    }

    @Override
    public int doCraft(CondenserBlockEntity condenser) {

        ItemStack outputSlot = condenser.getStack(CondenserBlockEntity.OUTPUT_SLOT);

        if (outputSlot.isEmpty()) {
            condenser.setStack(CondenserBlockEntity.OUTPUT_SLOT, this.getOutput().copy());
        } else {
            condenser.getStack(CondenserBlockEntity.OUTPUT_SLOT).increment(this.getOutput().getCount());
        }


        return this.getOutput().getCount();
    }

}
