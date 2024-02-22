package me.anisekai.registries;

import me.anisekai.AnisekaiMod;
import me.anisekai.inventories.constrained.ConstrainedContainerScreenHandler;
import me.anisekai.screen.condenser.CondenserScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public final class ModScreenHandler {

    public static final ScreenHandlerType<ConstrainedContainerScreenHandler> CONSTRAINED_INVENTORY = Registry.register(
            Registries.SCREEN_HANDLER,
            new Identifier(AnisekaiMod.MOD_ID, "constrained_inventory"),
            new ScreenHandlerType<>(ConstrainedContainerScreenHandler::createConstrained, FeatureFlags.VANILLA_FEATURES)
    );

    public static final ScreenHandlerType<CondenserScreenHandler> CONDENSER = Registry.register(
            Registries.SCREEN_HANDLER,
            new Identifier(AnisekaiMod.MOD_ID, "condenser"),
            new ScreenHandlerType<>(CondenserScreenHandler::create, FeatureFlags.VANILLA_FEATURES)
    );

    private ModScreenHandler() {}

    public static List<ScreenHandlerType<?>> screenHandlers() {

        return Arrays.asList(CONSTRAINED_INVENTORY, CONDENSER);
    }

}
