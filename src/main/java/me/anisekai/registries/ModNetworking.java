package me.anisekai.registries;

import me.anisekai.networking.CondenserPackets;

public final class ModNetworking {

    private ModNetworking() {}

    public static int packets() {

        int count = 0;
        count += CondenserPackets.register();
        return count;
    }

}
