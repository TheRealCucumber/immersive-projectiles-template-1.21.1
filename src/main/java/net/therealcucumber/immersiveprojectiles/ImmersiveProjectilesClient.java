package net.therealcucumber.immersiveprojectiles;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import net.therealcucumber.immersiveprojectiles.item.ModItems;

public class ImmersiveProjectilesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Tells the game how far the string is pulled (0.0 to 1.0)
        ModelPredicateProviderRegistry.register(ModItems.REINFORCED_BOW, Identifier.ofVanilla("pull"), (stack, world, entity, seed) -> {
            if (entity == null) return 0.0f;
            if (entity.getActiveItem() != stack) return 0.0f;
            return (float)(stack.getMaxUseTime(entity) - entity.getItemUseTimeLeft()) / 20.0f;
        });

        // Tells the game if the player is currently pulling
        ModelPredicateProviderRegistry.register(ModItems.REINFORCED_BOW, Identifier.ofVanilla("pulling"), (stack, world, entity, seed) -> {
            return entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f;
        });
    }
}