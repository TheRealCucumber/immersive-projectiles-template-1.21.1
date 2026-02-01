package net.therealcucumber.immersiveprojectiles.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.therealcucumber.immersiveprojectiles.ImmersiveProjectiles;
import org.apache.http.config.Registry;

public class ModItemGroups {
    public static final ItemGroup IMMERSIVE_PROJECTILES_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(ImmersiveProjectiles.MOD_ID), ("immersive_projectiles_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.SILVER_INGOT))
                    .displayName(Text.translatable("immersiveprojectiles.immersive_projectiles_items"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.SILVER_INGOT);
                        entries.add(ModItems.PEBBLE);
                        entries.add(ModItems.RAW_SILVER_ORE);
                        entries.add(ModItems.IRON_SHAFT);

                    }).build());


    public static void registerItemGroups() {
        ImmersiveProjectiles.LOGGER.info("Registering Item Groups for " + ImmersiveProjectiles.MOD_ID);
    }
}

