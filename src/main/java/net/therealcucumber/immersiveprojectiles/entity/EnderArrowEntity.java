package net.therealcucumber.immersiveprojectiles.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EnderArrowEntity extends PersistentProjectileEntity {

    public EnderArrowEntity(EntityType<? extends EnderArrowEntity> entityType, World world) {
        super(entityType, world);
    }

    public EnderArrowEntity(World world, LivingEntity owner, ItemStack stack, @Nullable ItemStack weapon) {
        super(ModEntities.ENDER_ARROW_ENTITY, owner, world, stack, weapon);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient) {
            this.teleportOwner();
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (!this.getWorld().isClient) {
            this.teleportOwner();
        }
    }

    private void teleportOwner() {
        if (this.getOwner() instanceof ServerPlayerEntity player) {
            if (player.getWorld() == this.getWorld() && !player.isSleeping()) {
                this.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);

                player.teleport(this.getX(), this.getY(), this.getZ(), false);

                this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                        SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);

                this.discard();
            }
        }
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return new ItemStack(Items.ARROW);
    }

    // This helps the Item set up the entity data safely
    public void initFromStack(ItemStack stack) {
        // In 1.21.1, the internal stack is usually set via this protected method
        // If 'setStack' is still red, try 'setItemStack' or just leave it blank
        // for now to test the teleportation logic!
        super.setStack(stack);
    }
}