package net.therealcucumber.immersiveprojectiles.screen;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.therealcucumber.immersiveprojectiles.item.QuiverItem;
import java.util.ArrayList;
import java.util.List;

public class QuiverScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final ItemStack quiverStack;

    // Small Constructor for Client-side registration
    public QuiverScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(5), ItemStack.EMPTY);
    }

    // Big Constructor for Server-side logic
    public QuiverScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, ItemStack stack) {
        super(ModScreenHandlers.QUIVER_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        this.quiverStack = stack;
        inventory.onOpen(playerInventory.player);

        // 1. Quiver Slots (Aligned to your texture's middle row)
        for (int i = 0; i < 5; ++i) {
            this.addSlot(new Slot(inventory, i, 44 + i * 18, 45) {
                @Override
                public boolean canInsert(ItemStack stack) {
                    return stack.isIn(ItemTags.ARROWS) && !(stack.getItem() instanceof QuiverItem);
                }
            });
        }

        // 2. Player Inventory (3x9 grid)
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int index = col + row * 9 + 9;
                this.addSlot(new Slot(playerInventory, index, 8 + col * 18, 84 + row * 18) {
                    public boolean canTakeElement(PlayerEntity player) {
                        return getStack() != quiverStack;
                    }
                });
            }
        }

        // 3. Player Hotbar
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142) {
                public boolean canTakeElement(PlayerEntity player) {
                    return getStack() != quiverStack;
                }
            });
        }
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        if (!player.getWorld().isClient && !quiverStack.isEmpty()) {
            List<ItemStack> stacks = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                stacks.add(inventory.getStack(i));
            }
            quiverStack.set(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(stacks));
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < 5) {
                if (!this.insertItem(originalStack, 5, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (originalStack.isIn(ItemTags.ARROWS)) {
                    if (!this.insertItem(originalStack, 0, 5, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    return ItemStack.EMPTY;
                }
            }
            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}