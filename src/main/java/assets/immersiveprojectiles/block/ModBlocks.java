package assets.immersiveprojectiles.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.therealcucumber.immersiveprojectiles.ImmersiveProjectiles;

public class ModBlocks {
    public static final Block SILVER_ORE = registerBlock("silver_ore",
            new Block(AbstractBlock.Settings.create().strength(2f)
                    .requiresTool().sounds(BlockSoundGroup.STONE)));

    public static final Block DEEPSLATE_SILVER_ORE = registerBlock("deepslate_silver_ore",
            new Block(AbstractBlock.Settings.create().strength(3f).
                    requiresTool().sounds(BlockSoundGroup.DEEPSLATE)));

    public static final Block SILVER_BLOCK = registerBlock("silver_block",
            new Block(AbstractBlock.Settings.create().strength(4f).requiresTool().sounds(BlockSoundGroup.METAL)));

    public static final Block RAW_SILVER_BLOCK = registerBlock("raw_silver_block",
            new Block(AbstractBlock.Settings.create().strength(3f).requiresTool().sounds(BlockSoundGroup.STONE)));



    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(ImmersiveProjectiles.MOD_ID, name),block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(ImmersiveProjectiles.MOD_ID, name),
            new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        ImmersiveProjectiles.LOGGER.info("Registering Mod Blocks for " + ImmersiveProjectiles.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(SILVER_ORE);
            fabricItemGroupEntries.add(DEEPSLATE_SILVER_ORE);
            fabricItemGroupEntries.add(SILVER_BLOCK);
            fabricItemGroupEntries.add(RAW_SILVER_BLOCK);
        });
    }
}
