package me.anisekai.interfaces;

import me.anisekai.utils.RotatableShape;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.Direction;

public interface Orientable {

    default BlockState defaultOrientationState(BlockState state) {

        return state.with(Properties.HORIZONTAL_FACING, Direction.NORTH);
    }

    RotatableShape getOrientedShapes();

    default BlockState rotate(BlockState state, BlockRotation rotation) {

        return state.with(Properties.HORIZONTAL_FACING, rotation.rotate(state.get(Properties.HORIZONTAL_FACING)));
    }

    default BlockState mirror(BlockState state, BlockMirror mirror) {

        return state.rotate(mirror.getRotation(state.get(Properties.HORIZONTAL_FACING)));
    }

}
