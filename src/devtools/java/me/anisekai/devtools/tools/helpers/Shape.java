package me.anisekai.devtools.tools.helpers;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Shape implements Comparable<Shape> {

    private final String      name;
    private final List<Voxel> voxels;

    public Shape(String name) {

        this.name   = name;
        this.voxels = new ArrayList<>();
    }

    public String getName() {

        return this.name;
    }

    public void add(Voxel voxel) {

        this.voxels.add(voxel);
    }

    @Override
    public int compareTo(@NotNull Shape o) {

        return this.getName().compareTo(o.getName());
    }

    @Override
    public String toString() {

        if (this.voxels.isEmpty()) {
            return String.format(
                    "this.put(Identifier.of(MOD_ID, \"%s\"), OrientableShape.makeShape(Collections.emptyList())",
                    this.name
            );
        }

        if (this.voxels.size() == 1) {
            return String.format(
                    "this.put(Identifier.of(MOD_ID, \"%s\"), OrientableShape.makeShape(Collections.singletonList(\n%s\n)))",
                    this.name,
                    this.voxels
                            .stream()
                            .map(Voxel::toString)
                            .collect(Collectors.joining(",\n"))
            );
        }

        return String.format(
                "this.put(Identifier.of(MOD_ID, \"%s\"), OrientableShape.makeShape(Arrays.asList(\n%s\n)))",
                this.name,
                this.voxels
                        .stream()
                        .map(Voxel::toString)
                        .collect(Collectors.joining(",\n"))
        );
    }

}
