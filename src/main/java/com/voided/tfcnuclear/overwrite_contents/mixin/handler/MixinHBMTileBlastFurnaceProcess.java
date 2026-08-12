package com.voided.tfcnuclear.overwrite_contents.mixin.handler;

import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.tileentity.machine.TileEntityMachineBlastFurnace;
import com.voided.tfcnuclear.compat.hbm.SlagAStack;
import com.voided.tfcnuclear.inventory.items.ItemSlagBase;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(TileEntityMachineBlastFurnace.class)
public class MixinHBMTileBlastFurnaceProcess {

    /**
     * @author Void
     * @reason Nbt consumption
     */
    @Overwrite(remap = false)
    public void process(GenericRecipe recipe) {
        TileEntityMachineBlastFurnace furnace = (TileEntityMachineBlastFurnace) (Object) this;

        int slagSlot = -1;
        int requiredAmount = 0;
        ItemStack slagStack = null;

        for (int i = 0; i < recipe.inputItem.length; i++) {
            if (recipe.inputItem[i] instanceof SlagAStack) {
                SlagAStack slagRecipe = (SlagAStack) recipe.inputItem[i];
                requiredAmount = slagRecipe.requiredAmount;

                if (!furnace.inventory.getStackInSlot(1).isEmpty() &&
                        furnace.inventory.getStackInSlot(1).getItem() instanceof ItemSlagBase) {
                    if (furnace.inventory.getStackInSlot(1).getItem() == slagRecipe.slagItem) {
                        int amount = ItemSlagBase.getAmount(furnace.inventory.getStackInSlot(1));
                        if (amount >= requiredAmount) { // >= для частичного расходования
                            slagSlot = 1;
                            slagStack = furnace.inventory.getStackInSlot(1);
                            break;
                        }
                    }
                }

                if (!furnace.inventory.getStackInSlot(2).isEmpty() &&
                        furnace.inventory.getStackInSlot(2).getItem() instanceof ItemSlagBase) {
                    if (furnace.inventory.getStackInSlot(2).getItem() == slagRecipe.slagItem) {
                        int amount = ItemSlagBase.getAmount(furnace.inventory.getStackInSlot(2));
                        if (amount >= requiredAmount) { // >= для частичного расходования
                            slagSlot = 2;
                            slagStack = furnace.inventory.getStackInSlot(2);
                            break;
                        }
                    }
                }
                break;
            }
        }

        if (slagSlot != -1 && slagStack != null) {
            int currentAmount = ItemSlagBase.getAmount(slagStack);
            int newAmount = currentAmount - requiredAmount;

            if (newAmount <= 0) {
                furnace.inventory.setStackInSlot(slagSlot, ItemStack.EMPTY);
            } else {
                ItemSlagBase.setAmount(slagStack, newAmount);
                furnace.inventory.setStackInSlot(slagSlot, slagStack);
            }
        }

        for (int i = 0; i < recipe.inputItem.length; i++) {
            if (!(recipe.inputItem[i] instanceof SlagAStack)) {
                if (recipe.inputItem[i].matchesRecipe(furnace.inventory.getStackInSlot(1), false)) {
                    furnace.inventory.getStackInSlot(1).shrink(recipe.inputItem[i].stacksize);
                }
                else if (recipe.inputItem[i].matchesRecipe(furnace.inventory.getStackInSlot(2), false)) {
                    furnace.inventory.getStackInSlot(2).shrink(recipe.inputItem[i].stacksize);
                }
            }
        }

        for (int i = 0; i < Math.min(recipe.outputItem.length, 2); i++) {
            ItemStack output = recipe.outputItem[i].collapse();
            int slot = 3 + i;
            if (!furnace.inventory.getStackInSlot(slot).isEmpty()) {
                furnace.inventory.getStackInSlot(slot).grow(output.getCount());
            } else {
                furnace.inventory.setStackInSlot(slot, output);
            }
        }
    }
}