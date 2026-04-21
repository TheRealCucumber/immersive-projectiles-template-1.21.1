package net.therealcucumber.immersiveprojectiles.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.therealcucumber.immersiveprojectiles.screen.QuiverScreenHandler;

public class QuiverItem extends Item {
    public QuiverItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient) {
            SimpleInventory inventory = new SimpleInventory(5);
            ContainerComponent container = stack.get(DataComponentTypes.CONTAINER);
            if (container != null) {
                container.copyTo(inventory.getHeldStacks());
            }
            user.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, playerInventory, player) ->
                    new QuiverScreenHandler(syncId, playerInventory, inventory, stack),
                    Text.translatable("item.immersiveprojectiles.quiver")));
        }
        return TypedActionResult.success(stack);
    }
}