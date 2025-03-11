package me.anisekai.devtools.tools;

import me.anisekai.devtools.DevUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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

    public List<Voxel> getVoxels() {

        return this.voxels;
    }

    @Override
    public int compareTo(@NotNull Shape o) {

        return this.getName().compareTo(o.getName());
    }

    @Override
    public String toString() {

        if (this.voxels.isEmpty()) {
            return DevUtils.Java.put(
                    DevUtils.Java.id(this.name),
                    DevUtils.Java.makeShape(DevUtils.Java.emptyList())
            );
        } else if (this.voxels.size() == 1) {
            return DevUtils.Java.put(
                    DevUtils.Java.id(this.name),
                    DevUtils.Java.makeShape(DevUtils.Java.singletonList(this.voxels.getFirst()))
            );
        } else {
            return DevUtils.Java.put(
                    DevUtils.Java.id(this.name),
                    DevUtils.Java.makeShape(DevUtils.Java.listOf(this.voxels))
            );
        }
    }

}
