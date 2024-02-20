package me.anisekai.interfaces;

import net.minecraft.block.BlockState;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Map;

public interface Connectable {

    Map<Direction, BooleanProperty> FACING_PROPERTIES =
            ConnectingBlock.FACING_PROPERTIES.entrySet()
                                             .stream()
                                             .filter(entry -> entry.getKey().getAxis().isHorizontal())
                                             .collect(Util.toMap());

    boolean canConnectTo(BlockState current, BlockState other);

    default BlockState applyPlacementConnection(World world, BlockPos pos, BlockState current) {

        Direction.Axis axis = current.get(Properties.HORIZONTAL_FACING).getAxis();

        if (axis == Direction.Axis.X) {
            return current
                    .with(Properties.NORTH, this.canConnectTo(current, world.getBlockState(pos.north())))
                    .with(Properties.SOUTH, this.canConnectTo(current, world.getBlockState(pos.south())))
                    .with(Properties.EAST, false)
                    .with(Properties.WEST, false);
        } else if (axis == Direction.Axis.Z) {
            return current
                    .with(Properties.EAST, this.canConnectTo(current, world.getBlockState(pos.east())))
                    .with(Properties.WEST, this.canConnectTo(current, world.getBlockState(pos.west())))
                    .with(Properties.NORTH, false)
                    .with(Properties.SOUTH, false);
        }

        return current;
    }

    default BlockState tryConnect(BlockState state, Direction direction, BlockState neighborState) {

        Direction facingDirection = state.get(Properties.HORIZONTAL_FACING);

        if (facingDirection.getAxis() != direction.getAxis() && FACING_PROPERTIES.containsKey(direction)) {
            return state.with(FACING_PROPERTIES.get(direction), this.canConnectTo(state, neighborState));
        }

        return state;
    }

}
