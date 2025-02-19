package me.anisekai.recipes.types;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;

public record ConsumableIngredient(Ingredient use, boolean consume) {

    public static final Codec<ConsumableIngredient>                        CODEC        = createCodec();
    public static final PacketCodec<RegistryByteBuf, ConsumableIngredient> PACKET_CODEC = createPacketCodec();

    public boolean test(ItemStack stack) {

        return this.use.test(stack);
    }

    private static Codec<ConsumableIngredient> createCodec() {

        return RecordCodecBuilder.<ConsumableIngredient>create(instance -> instance.group(
                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("use").forGetter(ConsumableIngredient::use),
                Codec.BOOL.optionalFieldOf("consume", false).forGetter(ConsumableIngredient::consume)
        ).apply(instance, ConsumableIngredient::new));
    }

    private static PacketCodec<RegistryByteBuf, ConsumableIngredient> createPacketCodec() {

        return new PacketCodec<>() {
            @Override
            public ConsumableIngredient decode(RegistryByteBuf buf) {

                Ingredient use     = Ingredient.PACKET_CODEC.decode(buf);
                boolean    consume = buf.readBoolean();

                return new ConsumableIngredient(use, consume);
            }

            @Override
            public void encode(RegistryByteBuf buf, ConsumableIngredient value) {

                Ingredient.PACKET_CODEC.encode(buf, value.use);
                buf.writeBoolean(value.consume);
            }
        };
    }

}
