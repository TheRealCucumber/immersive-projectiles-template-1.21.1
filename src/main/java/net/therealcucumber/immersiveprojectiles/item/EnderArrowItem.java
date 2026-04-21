package net.therealcucumber.immersiveprojectiles.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import net.therealcucumber.immersiveprojectiles.entity.EnderArrowEntity;
import net.therealcucumber.immersiveprojectiles.entity.ModEntities;
import org.jetbrains.annotations.Nullable;

public class EnderArrowItem extends ArrowItem {
    public EnderArrowItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        // Use .create() instead of 'new' to avoid the Registry crash
        EnderArrowEntity entity = ModEntities.ENDER_ARROW_ENTITY.create(world);
        if (entity != null) {
            entity.updatePosition(pos.getX(), pos.getY(), pos.getZ());
        }
        return entity;
    }

    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter, @Nullable ItemStack weapon) {
        // Use .create() here as well for standard bow firing
        EnderArrowEntity entity = ModEntities.ENDER_ARROW_ENTITY.create(world);
        if (entity != null) {
            entity.setOwner(shooter);
            // We use the copy of the stack to ensure the entity "is" an Ender Arrow
            entity.initFromStack(stack.copy());
        }
        return entity;
    }
}