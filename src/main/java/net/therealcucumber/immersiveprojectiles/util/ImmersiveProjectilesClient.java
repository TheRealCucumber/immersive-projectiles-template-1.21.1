package net.therealcucumber.immersiveprojectiles.util; // Ensure this matches your folder structure!

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import net.therealcucumber.immersiveprojectiles.item.ModItems;
import net.therealcucumber.immersiveprojectiles.screen.ModScreenHandlers;
import net.therealcucumber.immersiveprojectiles.screen.QuiverScreen;

public class ImmersiveProjectilesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // --- Screen Registration ---
        // This is the line that fixes "Failed to create screen"
        HandledScreens.register(ModScreenHandlers.QUIVER_SCREEN_HANDLER, QuiverScreen::new);

        System.out.println("Immersive Projectiles Client: Registered Quiver Screen!");

        // --- Bow Animations ---
        ModelPredicateProviderRegistry.register(ModItems.REINFORCED_BOW, Identifier.ofVanilla("pull"), (stack, world, entity, seed) -> {
            if (entity == null) return 0.0f;
            if (entity.getActiveItem() != stack) return 0.0f;
            return (float)(stack.getMaxUseTime(entity) - entity.getItemUseTimeLeft()) / 20.0f;
        });

        ModelPredicateProviderRegistry.register(ModItems.REINFORCED_BOW, Identifier.ofVanilla("pulling"), (stack, world, entity, seed) -> {
            return entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f;
        });
    }
}