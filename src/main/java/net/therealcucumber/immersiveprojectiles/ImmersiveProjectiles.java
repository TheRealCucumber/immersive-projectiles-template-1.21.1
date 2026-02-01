package net.therealcucumber.immersiveprojectiles;

import assets.immersiveprojectiles.block.ModBlocks;
import net.fabricmc.api.ModInitializer;

import net.therealcucumber.immersiveprojectiles.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImmersiveProjectiles implements ModInitializer {
	public static final String MOD_ID = "immersiveprojectiles";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
	}
}