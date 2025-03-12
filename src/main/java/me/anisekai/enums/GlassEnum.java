package me.anisekai.enums;

import me.anisekai.interfaces.Nameable;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.DyeColor;

public enum GlassEnum implements Nameable {

    GLASS(null),
    WHITE_STAINED_GLASS(DyeColor.WHITE),
    LIGHT_GRAY_STAINED_GLASS(DyeColor.LIGHT_GRAY),
    GRAY_STAINED_GLASS(DyeColor.GRAY),
    BLACK_STAINED_GLASS(DyeColor.BLACK),
    BROWN_STAINED_GLASS(DyeColor.BROWN),
    RED_STAINED_GLASS(DyeColor.RED),
    ORANGE_STAINED_GLASS(DyeColor.ORANGE),
    YELLOW_STAINED_GLASS(DyeColor.YELLOW),
    LIME_STAINED_GLASS(DyeColor.LIME),
    GREEN_STAINED_GLASS(DyeColor.GREEN),
    CYAN_STAINED_GLASS(DyeColor.CYAN),
    LIGHT_BLUE_STAINED_GLASS(DyeColor.LIGHT_BLUE),
    BLUE_STAINED_GLASS(DyeColor.BLUE),
    PURPLE_STAINED_GLASS(DyeColor.PURPLE),
    MAGENTA_STAINED_GLASS(DyeColor.MAGENTA),
    PINK_STAINED_GLASS(DyeColor.PINK);

    private final DyeColor color;

    GlassEnum(DyeColor color) {

        this.color = color;
    }

    @Override
    public String getName() {

        return this.name().toLowerCase();
    }

    public DyeColor getColor() {

        return this.color;
    }

    public boolean isStained() {

        return this.getColor() != null;
    }

    public Item asPane() {

        return switch (this) {
            case GLASS -> Items.GLASS_PANE;
            case WHITE_STAINED_GLASS -> Items.WHITE_STAINED_GLASS_PANE;
            case LIGHT_GRAY_STAINED_GLASS -> Items.LIGHT_GRAY_STAINED_GLASS_PANE;
            case GRAY_STAINED_GLASS -> Items.GRAY_STAINED_GLASS_PANE;
            case BLACK_STAINED_GLASS -> Items.BLACK_STAINED_GLASS_PANE;
            case BROWN_STAINED_GLASS -> Items.BROWN_STAINED_GLASS_PANE;
            case RED_STAINED_GLASS -> Items.RED_STAINED_GLASS_PANE;
            case ORANGE_STAINED_GLASS -> Items.ORANGE_STAINED_GLASS_PANE;
            case YELLOW_STAINED_GLASS -> Items.YELLOW_STAINED_GLASS_PANE;
            case LIME_STAINED_GLASS -> Items.LIME_STAINED_GLASS_PANE;
            case GREEN_STAINED_GLASS -> Items.GREEN_STAINED_GLASS_PANE;
            case CYAN_STAINED_GLASS -> Items.CYAN_STAINED_GLASS_PANE;
            case LIGHT_BLUE_STAINED_GLASS -> Items.LIGHT_BLUE_STAINED_GLASS_PANE;
            case BLUE_STAINED_GLASS -> Items.BLUE_STAINED_GLASS_PANE;
            case PURPLE_STAINED_GLASS -> Items.PURPLE_STAINED_GLASS_PANE;
            case MAGENTA_STAINED_GLASS -> Items.MAGENTA_STAINED_GLASS_PANE;
            case PINK_STAINED_GLASS -> Items.PINK_STAINED_GLASS_PANE;
        };
    }

}
