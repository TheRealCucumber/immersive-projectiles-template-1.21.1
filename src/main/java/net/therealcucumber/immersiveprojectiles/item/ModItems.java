package net.therealcucumber.immersiveprojectiles.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.therealcucumber.immersiveprojectiles.ImmersiveProjectiles;

public class ModItems {
    public static final Item IRON_SHAFT = registerItem("iron_shaft", new Item(new Item.Settings()));
    public static final Item PEBBLE = registerItem("pebble", new Item(new Item.Settings()));
    public static final Item RAW_SILVER_ORE = registerItem("raw_silver_ore", new Item(new Item.Settings()));
    public static final Item SILVER_INGOT = registerItem("silver_ingot", new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(ImmersiveProjectiles.MOD_ID, name), item);
    }

    public static void registerModItems() {
        ImmersiveProjectiles.LOGGER.info("Registering Mod Items for " + ImmersiveProjectiles.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(IRON_SHAFT);
            fabricItemGroupEntries.add(PEBBLE);
            fabricItemGroupEntries.add(SILVER_INGOT);
            fabricItemGroupEntries.add(RAW_SILVER_ORE);

        });
    }
}