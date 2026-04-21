package net.therealcucumber.immersiveprojectiles.screen;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.therealcucumber.immersiveprojectiles.ImmersiveProjectiles;
import net.minecraft.resource.featuretoggle.FeatureFlags; // Try this import

public class ModScreenHandlers {

    // Simplified registration that avoids the complex constructor if FeatureFlags is failing
    public static final ScreenHandlerType<QuiverScreenHandler> QUIVER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER,
                    Identifier.of("immersiveprojectiles", "quiver"), // Hardcode the string here
                    new ScreenHandlerType<>(QuiverScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static void registerScreenHandlers() {
        ImmersiveProjectiles.LOGGER.info("Registering Screen Handlers for " + ImmersiveProjectiles.MOD_ID);
    }
}