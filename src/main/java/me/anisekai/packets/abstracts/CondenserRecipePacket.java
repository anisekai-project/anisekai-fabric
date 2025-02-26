package me.anisekai.packets.abstracts;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.function.BiFunction;

public abstract class CondenserRecipePacket implements CustomPayload {

    private final BlockPos   position;
    private final Identifier identifier;

    public CondenserRecipePacket(BlockPos position, Identifier identifier) {

        this.position   = position;
        this.identifier = identifier;
    }

    public BlockPos getPosition() {

        return this.position;
    }

    public Identifier getIdentifier() {

        return this.identifier;
    }

    public static <T extends CondenserRecipePacket> PacketCodec<RegistryByteBuf, T> createPacketCodec(BiFunction<BlockPos, Identifier, T> creator) {

        return new PacketCodec<>() {
            @Override
            public void encode(RegistryByteBuf buf, CondenserRecipePacket packet) {

                BlockPos.PACKET_CODEC.encode(buf, packet.position);

                // Encode whether the recipe is null
                boolean hasRecipe = packet.getIdentifier() != null;
                buf.writeBoolean(hasRecipe);

                if (hasRecipe) {
                    Identifier.PACKET_CODEC.encode(buf, packet.getIdentifier());
                }
            }

            @Override
            public T decode(RegistryByteBuf buf) {

                BlockPos pos = BlockPos.PACKET_CODEC.decode(buf);

                // Decode whether the recipe is present
                boolean    hasRecipe = buf.readBoolean();
                Identifier recipe    = hasRecipe ? Identifier.PACKET_CODEC.decode(buf) : null;

                return creator.apply(pos, recipe);
            }
        };
    }

}
