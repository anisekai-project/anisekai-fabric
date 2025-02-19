package me.anisekai.recipes.serializers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.anisekai.recipes.CondenserRecipe;
import me.anisekai.recipes.types.ConsumableIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;

public class CondenserRecipeSerializer implements RecipeSerializer<CondenserRecipe> {

    private final MapCodec<CondenserRecipe>                     codec;
    private final PacketCodec<RegistryByteBuf, CondenserRecipe> packetCodec;

    public CondenserRecipeSerializer() {

        this.codec = RecordCodecBuilder.mapCodec(instance -> instance.group(
                ConsumableIngredient.CODEC.fieldOf("apply").forGetter(CondenserRecipe::apply),
                ConsumableIngredient.CODEC.fieldOf("onto").forGetter(CondenserRecipe::onto),
                Ingredient.ALLOW_EMPTY_CODEC.optionalFieldOf("booster", Ingredient.EMPTY).forGetter(CondenserRecipe::booster),
                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("tool").forGetter(CondenserRecipe::tool),
                Codec.INT.fieldOf("time").forGetter(CondenserRecipe::time),
                ItemStack.CODEC.fieldOf("result").forGetter(CondenserRecipe::result)
        ).apply(instance, CondenserRecipe::new));

        this.packetCodec = PacketCodec.ofStatic(this::write, this::read);
    }

    @Override
    public MapCodec<CondenserRecipe> codec() {

        return this.codec;
    }

    @Override
    public PacketCodec<RegistryByteBuf, CondenserRecipe> packetCodec() {

        return this.packetCodec;
    }

    private CondenserRecipe read(RegistryByteBuf buf) {

        ConsumableIngredient apply   = ConsumableIngredient.PACKET_CODEC.decode(buf);
        ConsumableIngredient onto    = ConsumableIngredient.PACKET_CODEC.decode(buf);
        Ingredient           booster = Ingredient.PACKET_CODEC.decode(buf);
        Ingredient           tool    = Ingredient.PACKET_CODEC.decode(buf);
        int                  time    = buf.readInt();
        ItemStack            result  = ItemStack.PACKET_CODEC.decode(buf);
        return new CondenserRecipe(apply, onto, booster, tool, time, result);
    }

    private void write(RegistryByteBuf buf, CondenserRecipe recipe) {

        ConsumableIngredient.PACKET_CODEC.encode(buf, recipe.apply());
        ConsumableIngredient.PACKET_CODEC.encode(buf, recipe.onto());
        Ingredient.PACKET_CODEC.encode(buf, recipe.booster());
        Ingredient.PACKET_CODEC.encode(buf, recipe.tool());
        buf.writeInt(recipe.time());
        ItemStack.PACKET_CODEC.encode(buf, recipe.result());
    }

}
