package net.therealcucumber.immersiveprojectiles.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class ReinforcedBowItem extends BowItem {
    public ReinforcedBowItem(Settings settings) {
        super(settings);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTime) {
        if (!(user instanceof PlayerEntity playerEntity)) return;

        int i = this.getMaxUseTime(stack, user) - remainingUseTime;
        float f = getPullProgress(i);

        if (f >= 0.1f) {
            if (!world.isClient) {
                // Manually creating the arrow to bypass vanilla speed limits
                ArrowItem arrowItem = (ArrowItem) Items.ARROW;
                PersistentProjectileEntity projectile = arrowItem.createArrow(world, stack, playerEntity, null);

                // SPEED: 6.0f (Twice as fast as vanilla, travels very flat)
                // INACCURACY: 0.0f (Perfectly straight)
                projectile.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, f * 4.0F, 0.0F);

                // DAMAGE: Forces it to stay at vanilla damage (approx 1-4 hearts depending on charge)
                projectile.setDamage(2.0);

                if (f == 1.0F) {
                    projectile.setCritical(true);
                }

                stack.damage(1, playerEntity, LivingEntity.getSlotForHand(user.getActiveHand()));
                world.spawnEntity(projectile);
            }
        }
    }
}