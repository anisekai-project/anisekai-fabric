package me.anisekai.recipes;

import me.anisekai.blockentities.CondenserBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.function.Predicate;

public abstract class CondenserRecipe {

    private final Identifier           id;
    private final ItemStack            condensedStack;
    private final Predicate<ItemStack> ingredientOne;
    private final Predicate<ItemStack> ingredientTwo;
    private final Predicate<ItemStack> booster;
    private final Predicate<ItemStack> tool;
    private final ItemStack            output;
    private final float                baseWorkRate;
    private final float                boosterBonus;

    private final SoundEvent workingSound;
    private final SoundEvent generateSound;

    public CondenserRecipe(Identifier id, ItemStack condensedStack, Predicate<ItemStack> ingredientOne, Predicate<ItemStack> ingredientTwo, Predicate<ItemStack> booster, Predicate<ItemStack> tool, ItemStack output, float baseWorkRate, float boosterBonus, SoundEvent workingSound, SoundEvent generateSound) {

        this.id = id;

        this.condensedStack = condensedStack;
        this.ingredientOne  = ingredientOne;
        this.ingredientTwo  = ingredientTwo;
        this.booster        = booster;
        this.tool           = tool;
        this.output         = output;
        this.baseWorkRate   = baseWorkRate;
        this.boosterBonus   = boosterBonus;
        this.workingSound   = workingSound;
        this.generateSound  = generateSound;
    }

    public Identifier getId() {

        return this.id;
    }

    public ItemStack getCondensedStack() {

        return this.condensedStack;
    }

    public Predicate<ItemStack> getIngredientOne() {

        return this.ingredientOne;
    }

    public Predicate<ItemStack> getIngredientTwo() {

        return this.ingredientTwo;
    }

    public Predicate<ItemStack> getBooster() {

        return this.booster;
    }

    public Predicate<ItemStack> getTool() {

        return this.tool;
    }

    public ItemStack getOutput() {

        return this.output;
    }

    public float getBaseWorkRate() {

        return this.baseWorkRate;
    }

    public float getWorkRate() {

        return this.baseWorkRate * this.getOutput().getCount();
    }

    public float getBoosterBonus() {

        return this.boosterBonus;
    }

    public SoundEvent getWorkingSound() {

        return this.workingSound;
    }

    public SoundEvent getGenerateSound() {

        return this.generateSound;
    }

    public boolean canContinueProcessing(CondenserBlockEntity condenser) {

        return this.areIngredientsCompatible(condenser) &&
                this.canOutput(condenser) &&
                this.getTool().test(condenser.getStack(CondenserBlockEntity.TOOL_SLOT));
    }

    public boolean isJammed(CondenserBlockEntity condenser) {

        return this.areIngredientsCompatible(condenser) &&
                !this.canOutput(condenser) &&
                this.getTool().test(condenser.getStack(CondenserBlockEntity.TOOL_SLOT));
    }

    public boolean areIngredientsCompatible(CondenserBlockEntity condenser) {

        ItemStack ingredientOne = condenser.getStack(CondenserBlockEntity.INGREDIENT_ONE_SLOT);
        ItemStack ingredientTwo = condenser.getStack(CondenserBlockEntity.INGREDIENT_TWO_SLOT);
        return (this.ingredientOne.test(ingredientOne) && this.ingredientTwo.test(ingredientTwo));
    }

    public boolean canOutput(CondenserBlockEntity condenser) {

        ItemStack currentOutput = condenser.getStack(CondenserBlockEntity.OUTPUT_SLOT);
        ItemStack craftOutput   = this.output;

        return currentOutput.isEmpty() || (currentOutput.isOf(craftOutput.getItem()) && currentOutput.getCount() + craftOutput.getCount() <= currentOutput.getMaxCount());
    }

    public abstract int doCraft(CondenserBlockEntity condenser);

}
