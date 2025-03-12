package me.anisekai.blocks.feature;

import me.anisekai.AnisekaiMod;
import me.anisekai.utils.OrientableShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.WorldAccess;

import java.util.ArrayList;
import java.util.Collection;

public interface QuarterBlock {

    Identifier L0_ID = AnisekaiMod.id("half_slab_l0");
    Identifier L1_ID = AnisekaiMod.id("half_slab_l1");
    Identifier L2_ID = AnisekaiMod.id("half_slab_l2");
    Identifier L3_ID = AnisekaiMod.id("half_slab_l3");

    BooleanProperty LAYER_3 = BooleanProperty.of("layer3");
    BooleanProperty LAYER_2 = BooleanProperty.of("layer2");
    BooleanProperty LAYER_1 = BooleanProperty.of("layer1");
    BooleanProperty LAYER_0 = BooleanProperty.of("layer0");

    BooleanProperty[] LAYERS = {LAYER_0, LAYER_1, LAYER_2, LAYER_3};

    default OrientableShape getOrientableShape(BlockState state) {

        Collection<Identifier> layerIds = new ArrayList<>();
        if (state.get(LAYER_0)) layerIds.add(L0_ID);
        if (state.get(LAYER_1)) layerIds.add(L1_ID);
        if (state.get(LAYER_2)) layerIds.add(L2_ID);
        if (state.get(LAYER_3)) layerIds.add(L3_ID);
        return OrientableShape.of(layerIds);
    }

    default BooleanProperty getPlacementLayer(Block block, WorldAccess world, BlockState state, BlockPos position, Vec3d hitPosition, BlockPos playerPosition) {

        double y = hitPosition.y - position.getY();
        double x = hitPosition.x - position.getX();

        BooleanProperty layer;

        if (state.isOf(block)) {
            // We already are in a half-slab context, let's use that to "complete" our block.
            return this.getAffectedLayerAt(state, y);
        }

        if (x == 0.5 && y == 0.5) {
            // We are most probably dealing with the bridging mod here
            // Let's find the nearest block from the hitpos in the player direction
            BlockPos positionDiff = playerPosition.subtract(position);

            int dx = Integer.compare(positionDiff.getX(), 0);
            int dz = Integer.compare(positionDiff.getZ(), 0);

            BlockPos neighborDirection = new BlockPos(dx, 0, dz);
            BlockPos neighborPosition  = position.add(neighborDirection);

            BlockState neighborState = world.getBlockState(neighborPosition);

            if (neighborState.isOf(block)) {
                // The neighbor is a half-slab, let's just use its highest layer.
                return this.getHighestLayer(block, neighborState);
            }

            // It's not a half-slab, let's use the highest Y value from the block collision shape instead
            VoxelShape shape = neighborState.getCollisionShape(world, neighborPosition);
            if (shape.isEmpty()) {
                // Why are we still here, just to suffer... what fucking value I'm supposed to use there ??
                return this.getAffectedLayerAt(1);
            }

            return this.getAffectedLayerAt(shape.getBoundingBox().maxY);

        }
        // Just use the hit position to detect which layer should be set.
        return this.getAffectedLayerAt(y);

    }

    default BooleanProperty getHighestLayer(Block block, BlockState state) {

        if (!state.isOf(block)) throw new IllegalArgumentException("State must be half-slab");
        if (state.get(LAYER_3)) return LAYER_3;
        if (state.get(LAYER_2)) return LAYER_2;
        if (state.get(LAYER_1)) return LAYER_1;
        if (state.get(LAYER_0)) return LAYER_0;
        throw new IllegalStateException("Half-Slab state without any layers (??)");
    }

    default boolean isFull(BlockState state) {

        boolean layer0 = state.get(LAYER_0);
        boolean layer1 = state.get(LAYER_1);
        boolean layer2 = state.get(LAYER_2);
        boolean layer3 = state.get(LAYER_3);

        return layer0 && layer1 && layer2 && layer3;
    }

    default int getLayerCount(BlockState state) {

        int layer0 = state.get(LAYER_0) ? 1 : 0;
        int layer1 = state.get(LAYER_1) ? 1 : 0;
        int layer2 = state.get(LAYER_2) ? 1 : 0;
        int layer3 = state.get(LAYER_3) ? 1 : 0;

        return layer0 + layer1 + layer2 + layer3;
    }

    default boolean isAffectingLayer0(double y) {

        return y <= 0.25;
    }

    default boolean isAffectingLayer1(double y) {

        return y >= 0.25 && y <= 0.5;
    }

    default boolean isAffectingLayer2(double y) {

        return y >= 0.5 && y <= 0.75;
    }

    default boolean isAffectingLayer3(double y) {

        return y >= 0.75;
    }

    default BooleanProperty getAffectedLayerAt(double y) {

        if (this.isAffectingLayer0(y)) {
            return LAYER_0;
        } else if (this.isAffectingLayer1(y)) {
            return LAYER_1;
        } else if (this.isAffectingLayer2(y)) {
            return LAYER_2;
        } else if (this.isAffectingLayer3(y)) {
            return LAYER_3;
        }
        return LAYER_0; // Default to layer 0 if y value is invalid – should not happen under normal circumstances.
    }

    default BooleanProperty getAffectedLayerAt(BlockState state, double y) {

        if (y == 0.75) {
            return state.get(LAYER_2) ? LAYER_3 : LAYER_2;
        } else if (y == 0.5) {
            return state.get(LAYER_1) ? LAYER_2 : LAYER_1;
        } else if (y == 0.25) {
            return state.get(LAYER_0) ? LAYER_1 : LAYER_0;
        } else {
            return this.getAffectedLayerAt(y);
        }
    }

}
