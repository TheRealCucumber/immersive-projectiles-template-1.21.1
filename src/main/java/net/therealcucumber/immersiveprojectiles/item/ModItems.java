package net.therealcucumber.immersiveprojectiles.item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.therealcucumber.immersiveprojectiles.ImmersiveProjectiles;

public class ModItems {


private static Item registerItem(String name, Item item){
    return Registry.register(Registries.ITEM, Identifier.of(ImmersiveProjectiles.MOD_ID, name), item)
}

    public static void registerModItems() {
        ImmersiveProjectiles.LOGGER.info("Registering Mod Items for " + ImmersiveProjectiles.MOD_ID);
    }

}