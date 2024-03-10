package me.anisekai.utils;

import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.*;

public class RotatableShape {

    private static VoxelShape createCopy(VoxelShape shape) {

        return VoxelUtils.make(shape.getBoundingBoxes().stream()
                                    .map(VoxelShapes::cuboid)
                                    .toList());

    }

    private static VoxelShape rotate(VoxelShape shape) {

        List<VoxelShape> shapes = new ArrayList<>();

        for (Box box : shape.getBoundingBoxes()) {
            Vec3d min = new Vec3d(1 - box.minZ, box.minY, box.minX);
            Vec3d max = new Vec3d(1 - box.maxZ, box.maxY, box.maxX);

            shapes.add(VoxelShapes.cuboid(new Box(min, max)));
        }

        return VoxelUtils.make(shapes);
    }

    private static VoxelShape rotate(int count, VoxelShape shape) {

        VoxelShape finalShape = createCopy(shape);

        for (int i = 0; i < count; i++) {
            finalShape = rotate(finalShape);
        }
        return finalShape;
    }

    private final Map<Direction, VoxelShape> shapes = new HashMap<>();

    public RotatableShape(Collection<VoxelShape> shape) {

        this(VoxelUtils.make(shape));
    }

    public RotatableShape(VoxelShape north, VoxelShape east, VoxelShape south, VoxelShape west) {

        this.shapes.put(Direction.NORTH, north);
        this.shapes.put(Direction.EAST, east);
        this.shapes.put(Direction.SOUTH, south);
        this.shapes.put(Direction.WEST, west);
    }

    public RotatableShape() {

        this.shapes.put(Direction.NORTH, VoxelShapes.fullCube());
        this.shapes.put(Direction.EAST, VoxelShapes.fullCube());
        this.shapes.put(Direction.SOUTH, VoxelShapes.fullCube());
        this.shapes.put(Direction.WEST, VoxelShapes.fullCube());
    }

    public RotatableShape(VoxelShape shape) {

        this.shapes.put(Direction.NORTH, shape);
        this.shapes.put(Direction.EAST, rotate(1, shape));
        this.shapes.put(Direction.SOUTH, rotate(2, shape));
        this.shapes.put(Direction.WEST, rotate(3, shape));
    }

    public VoxelShape getShape(Direction direction) {

        if (direction.getAxis() == Direction.Axis.Y) {
            throw new IllegalStateException("Invalid direction: Y axis not supported");
        }
        return this.shapes.get(direction);
    }

}
