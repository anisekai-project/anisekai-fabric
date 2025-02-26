package me.anisekai.enums;

import me.anisekai.interfaces.Nameable;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public enum GlassEnum implements Nameable {

    GLASS,
    WHITE_STAINED_GLASS,
    LIGHT_GRAY_STAINED_GLASS,
    GRAY_STAINED_GLASS,
    BLACK_STAINED_GLASS,
    BROWN_STAINED_GLASS,
    RED_STAINED_GLASS,
    ORANGE_STAINED_GLASS,
    YELLOW_STAINED_GLASS,
    LIME_STAINED_GLASS,
    GREEN_STAINED_GLASS,
    CYAN_STAINED_GLASS,
    LIGHT_BLUE_STAINED_GLASS,
    BLUE_STAINED_GLASS,
    PURPLE_STAINED_GLASS,
    MAGENTA_STAINED_GLASS,
    PINK_STAINED_GLASS;

    @Override
    public String getName() {

        return this.name().toLowerCase();
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
