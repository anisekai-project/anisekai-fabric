package me.anisekai.interfaces;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.Vec3d;

public interface Seatable {

    Vec3d getSitOffsetFrom(Vec3d pos);

    float getSitYaw(BlockState state);

}
