package me.anisekai.utils;

import net.minecraft.util.Identifier;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.*;

import static me.anisekai.AnisekaiMod.MOD_ID;

/**
 * Class allowing to easily rotate a {@link VoxelShape} around the X axis for hitboxes.
 */
public class OrientableShape {

    private static final Map<Identifier, VoxelShape> SHAPES = new HashMap<>() {{

        this.put(
                Identifier.of(MOD_ID, "chair"), OrientableShape.makeShape(Arrays.asList(
                        VoxelShapes.cuboid(0.125, 0.625, 0.75, 0.25, 1.375, 0.875),
                        VoxelShapes.cuboid(0.75, 0.625, 0.75, 0.875, 1.375, 0.875),
                        VoxelShapes.cuboid(0.125, 0.0, 0.75, 0.25, 0.5, 0.875),
                        VoxelShapes.cuboid(0.75, 0.0, 0.75, 0.875, 0.5, 0.875),
                        VoxelShapes.cuboid(0.125, 0.0, 0.125, 0.25, 0.5, 0.25),
                        VoxelShapes.cuboid(0.75, 0.0, 0.125, 0.875, 0.5, 0.25),
                        VoxelShapes.cuboid(0.25, 1.0625, 0.75, 0.75, 1.3125, 0.875),
                        VoxelShapes.cuboid(0.125, 0.5, 0.125, 0.875, 0.625, 0.875)
                ))
        );

        this.put(
                Identifier.of(MOD_ID, "condenser"), OrientableShape.makeShape(Arrays.asList(
                        VoxelShapes.cuboid(0.0, 0.0, 0.125, 1.0, 1.0, 1.0),
                        VoxelShapes.cuboid(0.6875, 0.0, 0.0, 1.0, 1.0, 0.125),
                        VoxelShapes.cuboid(0.0, 0.0, 0.0, 0.3125, 1.0, 0.125),
                        VoxelShapes.cuboid(0.3125, 0.6875, 0.0, 0.6875, 1.0, 0.125),
                        VoxelShapes.cuboid(0.3125, 0.0, 0.0, 0.6875, 0.3125, 0.125)
                ))
        );

        this.put(
                Identifier.of(MOD_ID, "condenser_activity_sensor"), OrientableShape.makeShape(Collections.singletonList(
                        VoxelShapes.cuboid(0.3125, 0.3125, -0.0625, 0.6875, 0.6875, 0.0625)
                ))
        );

        this.put(
                Identifier.of(MOD_ID, "fishing_basket"), OrientableShape.makeShape(Arrays.asList(
                        VoxelShapes.cuboid(0.0625, 0.0, 0.0625, 0.9375, 0.625, 0.9375),
                        VoxelShapes.cuboid(0.0625, 0.625, 0.0625, 0.9375, 0.75, 0.9375),
                        VoxelShapes.cuboid(0.6875, 0.5625, 0.9375, 0.8125, 0.6875, 1.0),
                        VoxelShapes.cuboid(0.1875, 0.5625, 0.9375, 0.3125, 0.6875, 1.0)
                ))
        );

        this.put(
                Identifier.of(MOD_ID, "half_slab_l0"), OrientableShape.makeShape(Collections.singletonList(
                        VoxelShapes.cuboid(0.0, 0.0, 0.0, 1.0, 0.25, 1.0)
                ))
        );

        this.put(
                Identifier.of(MOD_ID, "half_slab_l1"), OrientableShape.makeShape(Collections.singletonList(
                        VoxelShapes.cuboid(0.0, 0.25, 0.0, 1.0, 0.5, 1.0)
                ))
        );

        this.put(
                Identifier.of(MOD_ID, "half_slab_l2"), OrientableShape.makeShape(Collections.singletonList(
                        VoxelShapes.cuboid(0.0, 0.5, 0.0, 1.0, 0.75, 1.0)
                ))
        );

        this.put(
                Identifier.of(MOD_ID, "half_slab_l3"), OrientableShape.makeShape(Collections.singletonList(
                        VoxelShapes.cuboid(0.0, 0.75, 0.0, 1.0, 1.0, 1.0)
                ))
        );

        this.put(
                Identifier.of(MOD_ID, "nightstand"), OrientableShape.makeShape(Arrays.asList(
                        VoxelShapes.cuboid(0.0, 0.125, 0.0, 1.0, 0.1875, 1.0),
                        VoxelShapes.cuboid(0.0, 0.1875, 0.0, 0.0625, 1.0, 1.0),
                        VoxelShapes.cuboid(0.9375, 0.1875, 0.0, 1.0, 1.0, 1.0),
                        VoxelShapes.cuboid(0.0625, 0.1875, 0.875, 0.9375, 0.9375, 0.9375),
                        VoxelShapes.cuboid(0.0625, 0.9375, 0.0, 0.9375, 1.0, 1.0),
                        VoxelShapes.cuboid(0.0625, 0.5, 0.0, 0.9375, 0.625, 0.875),
                        VoxelShapes.cuboid(0.3125, 0.3125, 0.0, 0.6875, 0.375, 0.0625),
                        VoxelShapes.cuboid(0.0625, 0.1875, 0.0625, 0.9375, 0.5, 0.1875),
                        VoxelShapes.cuboid(0.3125, 0.75, 0.0, 0.6875, 0.8125, 0.0625),
                        VoxelShapes.cuboid(0.0625, 0.625, 0.0625, 0.9375, 0.9375, 0.1875)
                ))
        );

        this.put(
                Identifier.of(MOD_ID, "nightstand_feet_full"), OrientableShape.makeShape(Arrays.asList(
                        VoxelShapes.cuboid(0.0625, 0.0, 0.0625, 0.1875, 0.125, 0.1875),
                        VoxelShapes.cuboid(0.8125, 0.0, 0.0625, 0.9375, 0.125, 0.1875),
                        VoxelShapes.cuboid(0.0625, 0.0, 0.8125, 0.1875, 0.125, 0.9375),
                        VoxelShapes.cuboid(0.8125, 0.0, 0.8125, 0.9375, 0.125, 0.9375)
                ))
        );

        this.put(
                Identifier.of(MOD_ID, "nightstand_feet_side"), OrientableShape.makeShape(Arrays.asList(
                        VoxelShapes.cuboid(0.0625, 0.0, 0.0625, 0.1875, 0.125, 0.1875),
                        VoxelShapes.cuboid(0.8125, 0.0, 0.0625, 0.9375, 0.125, 0.1875)
                ))
        );

        this.put(
                Identifier.of(MOD_ID, "staircase"), OrientableShape.makeShape(Arrays.asList(
                        VoxelShapes.cuboid(0.0625, 0.0625, 0.0, 0.9375, 0.125, 0.25),
                        VoxelShapes.cuboid(0.0625, 0.5625, 0.5, 0.9375, 0.625, 0.75),
                        VoxelShapes.cuboid(0.0625, 0.3125, 0.25, 0.9375, 0.375, 0.5),
                        VoxelShapes.cuboid(0.0625, 0.8125, 0.75, 0.9375, 0.875, 1.0),
                        VoxelShapes.cuboid(0.9375, 0.0, 0.0, 1.0, 0.25, 0.25),
                        VoxelShapes.cuboid(0.9375, 0.25, 0.25, 1.0, 0.5, 0.5),
                        VoxelShapes.cuboid(0.9375, 0.75, 0.75, 1.0, 1.0, 1.0),
                        VoxelShapes.cuboid(0.9375, 0.5, 0.5, 1.0, 0.75, 0.75),
                        VoxelShapes.cuboid(0.0, 0.0, 0.0, 0.0625, 0.25, 0.25),
                        VoxelShapes.cuboid(0.0, 0.25, 0.25, 0.0625, 0.5, 0.5),
                        VoxelShapes.cuboid(0.0, 0.75, 0.75, 0.0625, 1.0, 1.0),
                        VoxelShapes.cuboid(0.0, 0.5, 0.5, 0.0625, 0.75, 0.75)
                ))
        );

        this.put(
                Identifier.of(MOD_ID, "staircase_connect_all"), OrientableShape.makeShape(Arrays.asList(
                        VoxelShapes.cuboid(0.0, 0.0625, 0.0, 1.0, 0.125, 0.25),
                        VoxelShapes.cuboid(0.0, 0.5625, 0.5, 1.0, 0.625, 0.75),
                        VoxelShapes.cuboid(0.0, 0.3125, 0.25, 1.0, 0.375, 0.5),
                        VoxelShapes.cuboid(0.0, 0.8125, 0.75, 1.0, 0.875, 1.0)
                ))
        );

        this.put(
                Identifier.of(MOD_ID, "staircase_connect_left"), OrientableShape.makeShape(Arrays.asList(
                        VoxelShapes.cuboid(0.0625, 0.0625, 0.0, 1.0, 0.125, 0.25),
                        VoxelShapes.cuboid(0.0625, 0.5625, 0.5, 1.0, 0.625, 0.75),
                        VoxelShapes.cuboid(0.0625, 0.3125, 0.25, 1.0, 0.375, 0.5),
                        VoxelShapes.cuboid(0.0625, 0.8125, 0.75, 1.0, 0.875, 1.0),
                        VoxelShapes.cuboid(0.0, 0.0, 0.0, 0.0625, 0.25, 0.25),
                        VoxelShapes.cuboid(0.0, 0.25, 0.25, 0.0625, 0.5, 0.5),
                        VoxelShapes.cuboid(0.0, 0.75, 0.75, 0.0625, 1.0, 1.0),
                        VoxelShapes.cuboid(0.0, 0.5, 0.5, 0.0625, 0.75, 0.75)
                ))
        );

        this.put(
                Identifier.of(MOD_ID, "staircase_connect_right"), OrientableShape.makeShape(Arrays.asList(
                        VoxelShapes.cuboid(0.0, 0.0625, 0.0, 0.9375, 0.125, 0.25),
                        VoxelShapes.cuboid(0.0, 0.5625, 0.5, 0.9375, 0.625, 0.75),
                        VoxelShapes.cuboid(0.0, 0.3125, 0.25, 0.9375, 0.375, 0.5),
                        VoxelShapes.cuboid(0.0, 0.8125, 0.75, 0.9375, 0.875, 1.0),
                        VoxelShapes.cuboid(0.9375, 0.0, 0.0, 1.0, 0.25, 0.25),
                        VoxelShapes.cuboid(0.9375, 0.25, 0.25, 1.0, 0.5, 0.5),
                        VoxelShapes.cuboid(0.9375, 0.75, 0.75, 1.0, 1.0, 1.0),
                        VoxelShapes.cuboid(0.9375, 0.5, 0.5, 1.0, 0.75, 0.75)
                ))
        );

        this.put(
                Identifier.of(MOD_ID, "stool"), OrientableShape.makeShape(Arrays.asList(
                        VoxelShapes.cuboid(0.125, 0.0, 0.75, 0.25, 0.5, 0.875),
                        VoxelShapes.cuboid(0.75, 0.0, 0.75, 0.875, 0.5, 0.875),
                        VoxelShapes.cuboid(0.125, 0.0, 0.125, 0.25, 0.5, 0.25),
                        VoxelShapes.cuboid(0.75, 0.0, 0.125, 0.875, 0.5, 0.25),
                        VoxelShapes.cuboid(0.125, 0.5, 0.125, 0.875, 0.625, 0.875)
                ))
        );

        this.put(
                Identifier.of(MOD_ID, "table"), OrientableShape.makeShape(Arrays.asList(
                        VoxelShapes.cuboid(0.3125, 0.0, 0.3125, 0.6875, 0.0625, 0.6875),
                        VoxelShapes.cuboid(0.4375, 0.0625, 0.4375, 0.5625, 0.875, 0.5625),
                        VoxelShapes.cuboid(0.0, 0.875, 0.0, 1.0, 1.0, 1.0)
                ))
        );
    }};

    /**
     * Retrieve an {@link OrientableShape} created using a {@link VoxelShape} identified by the provided {@link Identifier}.
     *
     * @param id
     *         {@link Identifier} of the {@link OrientableShape}
     *
     * @return An {@link OrientableShape}, or {@code null}.
     */
    public static OrientableShape of(Identifier id) {

        return new OrientableShape(SHAPES.get(id));
    }

    /**
     * Retrieve an {@link OrientableShape} combining multiple {@link VoxelShape} identified by a provided set of
     * {@link Identifier}.
     *
     * @param ids
     *         Group of {@link Identifier} identifying {@link VoxelShape}
     *
     * @return An {@link OrientableShape} combining all matching {@link VoxelShape}
     */
    public static OrientableShape of(Identifier... ids) {

        VoxelShape shape = Arrays
                .stream(ids)
                .map(SHAPES::get)
                .filter(Objects::nonNull)
                .collect(
                        VoxelShapes::empty,
                        (a, b) -> VoxelShapes.combine(a, b, BooleanBiFunction.OR),
                        (a, b) -> VoxelShapes.combine(a, b, BooleanBiFunction.OR)
                );

        return new OrientableShape(shape);
    }

    /**
     * Retrieve an {@link OrientableShape} combining multiple {@link VoxelShape} identified by a provided set of
     * {@link Identifier}.
     *
     * @param ids
     *         Group of {@link Identifier} identifying {@link VoxelShape}
     *
     * @return An {@link OrientableShape} combining all matching {@link VoxelShape}
     */
    public static OrientableShape of(Collection<Identifier> ids) {

        VoxelShape shape = ids
                .stream()
                .map(SHAPES::get)
                .filter(Objects::nonNull)
                .collect(
                        VoxelShapes::empty,
                        (a, b) -> VoxelShapes.combine(a, b, BooleanBiFunction.OR),
                        (a, b) -> VoxelShapes.combine(a, b, BooleanBiFunction.OR)
                );

        return new OrientableShape(shape);
    }

    /**
     * Create a {@link VoxelShape} by combining multiple other {@link VoxelShape}.
     *
     * @param voxels
     *         Group of {@link VoxelShape} to combine.
     *
     * @return A {@link VoxelShape} combining all input {@link VoxelShape}.
     */
    public static VoxelShape makeShape(Iterable<VoxelShape> voxels) {

        VoxelShape shape = VoxelShapes.empty();

        for (VoxelShape voxel : voxels) {
            shape = VoxelShapes.combine(shape, voxel, BooleanBiFunction.OR);
        }

        return shape;
    }

    /**
     * Create a copy of the provided {@link VoxelShape}.
     *
     * @param shape
     *         The {@link VoxelShape} to copy
     *
     * @return A copy of the {@link VoxelShape}.
     */
    private static VoxelShape createCopy(VoxelShape shape) {

        return makeShape(shape.getBoundingBoxes().stream()
                              .map(VoxelShapes::cuboid)
                              .toList());
    }

    /**
     * Rotate the provided {@link VoxelShape} clockwise.
     *
     * @param shape
     *         The {@link VoxelShape} to rotate.
     *
     * @return The rotated {@link VoxelShape}.
     */
    private static VoxelShape rotate(VoxelShape shape) {

        Collection<VoxelShape> shapes = new ArrayList<>();

        for (Box box : shape.getBoundingBoxes()) {
            Vec3d min = new Vec3d(1 - box.minZ, box.minY, box.minX);
            Vec3d max = new Vec3d(1 - box.maxZ, box.maxY, box.maxX);

            shapes.add(VoxelShapes.cuboid(new Box(min, max)));
        }

        return makeShape(shapes);
    }

    /**
     * Return a copy of the provided {@link VoxelShape} rotated a provided amount of time.
     *
     * @param count
     *         The amount of time to rotate the {@link VoxelShape}.
     * @param shape
     *         The {@link VoxelShape} to rotate.
     *
     * @return The rotated {@link VoxelShape}.
     */
    private static VoxelShape rotate(int count, VoxelShape shape) {

        VoxelShape finalShape = createCopy(shape);

        for (int i = 0; i < count; i++) {
            finalShape = rotate(finalShape);
        }
        return finalShape;
    }

    private final Map<Direction, VoxelShape> shapes = new HashMap<>();

    /**
     * Create a {@link OrientableShape} from the provided group of {@link VoxelShape}.
     *
     * @param shapes
     *         Group of {@link VoxelShape} to combine.
     */
    public OrientableShape(Iterable<VoxelShape> shapes) {

        this(makeShape(shapes));
    }

    /**
     * Create a {@link OrientableShape} from the four provided {@link VoxelShape}, each representing a direction.
     *
     * @param north
     *         The {@link VoxelShape} for the north direction
     * @param east
     *         The {@link VoxelShape} for the east direction
     * @param south
     *         The {@link VoxelShape} for the south direction
     * @param west
     *         The {@link VoxelShape} for the west direction
     */
    public OrientableShape(VoxelShape north, VoxelShape east, VoxelShape south, VoxelShape west) {

        this.shapes.put(Direction.NORTH, north);
        this.shapes.put(Direction.EAST, east);
        this.shapes.put(Direction.SOUTH, south);
        this.shapes.put(Direction.WEST, west);
    }

    /**
     * Create a {@link OrientableShape} using {@link VoxelShapes#fullCube()} for each direction.
     */
    public OrientableShape() {

        this.shapes.put(Direction.NORTH, VoxelShapes.fullCube());
        this.shapes.put(Direction.EAST, VoxelShapes.fullCube());
        this.shapes.put(Direction.SOUTH, VoxelShapes.fullCube());
        this.shapes.put(Direction.WEST, VoxelShapes.fullCube());
    }

    /**
     * Create a {@link OrientableShape} using the provided {@link VoxelShape} that will be rotated to generate all directions.
     *
     * @param shape
     *         A {@link VoxelShape} to create the {@link OrientableShape} from.
     */
    public OrientableShape(VoxelShape shape) {

        this.shapes.put(Direction.NORTH, shape);
        this.shapes.put(Direction.EAST, rotate(1, shape));
        this.shapes.put(Direction.SOUTH, rotate(2, shape));
        this.shapes.put(Direction.WEST, rotate(3, shape));
    }

    /**
     * Retrieve the {@link VoxelShape} matching the provided direction.
     *
     * @param direction
     *         The {@link Direction} into which the {@link VoxelShape} should be.
     *
     * @return A {@link VoxelShape}.
     */
    public VoxelShape getShape(Direction direction) {

        if (direction.getAxis() == Direction.Axis.Y) {
            throw new IllegalStateException("Invalid direction: Y axis not supported");
        }
        return this.shapes.get(direction);
    }

}
