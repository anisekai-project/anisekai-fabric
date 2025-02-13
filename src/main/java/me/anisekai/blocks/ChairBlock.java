package me.anisekai.blocks;

import me.anisekai.AnisekaiMod;
import me.anisekai.interfaces.Orientable;
import me.anisekai.interfaces.Seatable;
import me.anisekai.utils.OrientableShape;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.util.Identifier;

public class ChairBlock extends StoolBlock implements Waterloggable, Seatable, Orientable {

    public static final  Identifier      ID    = AnisekaiMod.id("chair");
    private static final OrientableShape SHAPE = OrientableShape.of(ID);

    public ChairBlock(AbstractBlock.Settings settings) {

        super(settings);
    }

    @Override
    public OrientableShape getOrientedShapes() {

        return SHAPE;
    }

}
