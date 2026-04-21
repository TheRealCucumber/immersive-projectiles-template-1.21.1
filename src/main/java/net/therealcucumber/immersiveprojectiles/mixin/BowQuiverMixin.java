package net.therealcucumber.immersiveprojectiles.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ProjectileItem;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.therealcucumber.immersiveprojectiles.item.QuiverItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(BowItem.class)
public class BowQuiverMixin {

    @Inject(method = "onStoppedUsing", at = @At("HEAD"), cancellable = true)
    private void fireFromQuiver(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo ci) {
        if (!(user instanceof PlayerEntity player)) return;

        // Check if player has ammo in main inventory (if so, vanilla handles it)
        ItemStack vanillaAmmo = player.getProjectileType(stack);
        if (!vanillaAmmo.isEmpty() || player.getAbilities().creativeMode) return;

        // Search for the Quiver in the inventory
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack quiver = player.getInventory().getStack(i);

            if (quiver.getItem() instanceof QuiverItem) {
                ContainerComponent container = quiver.get(DataComponentTypes.CONTAINER);
                if (container == null) continue;

                // Copy contents to a list so we can modify and save back
                List<ItemStack> contents = new ArrayList<>();
                for (ItemStack s : container.iterateNonEmpty()) {
                    contents.add(s.copy());
                }

                for (ItemStack arrowStack : contents) {
                    if (!arrowStack.isEmpty() && arrowStack.getItem() instanceof ProjectileItem projectileItem) {
                        int useDuration = stack.getMaxUseTime(user) - remainingUseTicks;
                        float pullProgress = BowItem.getPullProgress(useDuration);

                        if (pullProgress >= 0.1f) {
                            if (!world.isClient) {
                                // Match your mapping: World, Position, ItemStack, Direction
                                Direction facing = Direction.getEntityFacingOrder(user)[0];
                                ProjectileEntity projectileEntity = projectileItem.createEntity(world, user.getPos(), arrowStack, facing);

                                // Manually set owner and velocity since we used the Position constructor
                                projectileEntity.setOwner(user);
                                projectileEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, pullProgress * 3.0F, 1.0F);

                                world.spawnEntity(projectileEntity);

                                // Consume the arrow and update Quiver components
                                arrowStack.decrement(1);
                                quiver.set(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(contents));
                            }
                            ci.cancel(); // Stop the bow from looking for other ammo
                            return;
                        }
                    }
                }
            }
        }
    }
}