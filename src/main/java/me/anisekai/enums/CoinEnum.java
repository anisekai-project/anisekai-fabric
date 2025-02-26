package me.anisekai.enums;

import me.anisekai.interfaces.Nameable;

public enum CoinEnum implements Nameable {

    COPPER,
    IRON,
    GOLD,
    DIAMOND,
    EMERALD;

    public String getName() {

        return this.name().toLowerCase();
    }
}
