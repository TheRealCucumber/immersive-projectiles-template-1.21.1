package net.therealcucumber.immersiveprojectiles.entity;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.therealcucumber.immersiveprojectiles.ImmersiveProjectiles;

public class ModEntities {

    public static final EntityType<EnderArrowEntity> ENDER_ARROW_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(ImmersiveProjectiles.MOD_ID, "ender_arrow"),
            EntityType.Builder.<EnderArrowEntity>create(EnderArrowEntity::new, SpawnGroup.MISC)
                    .dimensions(0.5f, 0.5f) // Set the size of the arrow
                    .build()
    );

    public static void registerModEntities() {
        ImmersiveProjectiles.LOGGER.info("Registering Entities for " + ImmersiveProjectiles.MOD_ID);
    }
}