package net.therealcucumber.immersiveprojectiles.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.therealcucumber.immersiveprojectiles.ImmersiveProjectiles;

public class ModItems {
    // Other items
    public static final Item IRON_SHAFT = registerItem("iron_shaft", new Item(new Item.Settings()));
    public static final Item PEBBLE = registerItem("pebble", new Item(new Item.Settings()));
    public static final Item RAW_SILVER_ORE = registerItem("raw_silver_ore", new Item(new Item.Settings()));
    public static final Item SILVER_INGOT = registerItem("silver_ingot", new Item(new Item.Settings()));

    // We stay with BowItem for now, but we'll use a trick for damage later
    public static final Item REINFORCED_BOW = registerItem("reinforced_bow",
            new ReinforcedBowItem(new Item.Settings().maxDamage(900).fireproof().rarity(Rarity.UNCOMMON)));

    public static final Item QUIVER = registerItem("quiver",
            new QuiverItem(new Item.Settings().maxCount(1)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(ImmersiveProjectiles.MOD_ID, name), item);
    }

    public static final Item ENDER_ARROW = registerItem("ender_arrow",
            new EnderArrowItem(new Item.Settings()));

    public static void registerModItems() {
        // Add Ingots/Ores to Ingredients Tab
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(IRON_SHAFT);
            entries.add(PEBBLE);
            entries.add(SILVER_INGOT);
            entries.add(RAW_SILVER_ORE);
        });

        // Add Bow to Combat Tab
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(REINFORCED_BOW);
            entries.add(QUIVER);
            entries.add(ENDER_ARROW);
        });
    }
}