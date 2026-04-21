package net.therealcucumber.immersiveprojectiles.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.therealcucumber.immersiveprojectiles.item.QuiverItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Mixin(PlayerEntity.class)
public class QuiverAmmoMixin {

    @Inject(method = "getProjectileType", at = @At("HEAD"), cancellable = true)
    private void checkQuiverForAmmo(ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        Predicate<ItemStack> predicate = ((net.minecraft.item.BowItem) stack.getItem()).getHeldProjectiles();

        // Loop through player's inventory to find a Quiver
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack invStack = player.getInventory().getStack(i);

            if (invStack.getItem() instanceof QuiverItem) {
                ContainerComponent container = invStack.get(DataComponentTypes.CONTAINER);

                if (container != null) {
                    // Check stacks inside the quiver
                    for (ItemStack arrowStack : container.iterateNonEmpty()) {
                        if (predicate.test(arrowStack)) {
                            // Found an arrow! Tell the bow to use this one.
                            cir.setReturnValue(arrowStack);
                            return;
                        }
                    }
                }
            }
        }
    }
}
