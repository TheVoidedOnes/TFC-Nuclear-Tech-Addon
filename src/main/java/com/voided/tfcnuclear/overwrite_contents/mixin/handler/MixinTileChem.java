package com.voided.tfcnuclear.overwrite_contents.mixin.handler;

import com.hbm.tileentity.machine.TileEntityMachineChemicalPlant;
import com.voided.tfcnuclear.inventory.items.ItemSlagBase;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(TileEntityMachineChemicalPlant.class)
public class MixinTileChem {

    /**
     * @author Void
     * @reason Nbt consumption
     */
    @Overwrite(remap = false)
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        TileEntityMachineChemicalPlant plant = (TileEntityMachineChemicalPlant) (Object) this;

        if (slot == 0) return true;
        if (slot == 1 && stack.getItem() == com.hbm.items.ModItems.blueprints) return true;
        if (slot >= 2 && slot <= 3 && stack.getItem() instanceof com.hbm.items.machine.ItemMachineUpgrade) return true;
        if (slot >= 10 && slot <= 12) return true;
        if (slot >= 16 && slot <= 18) return true;

        if (slot >= 4 && slot <= 6 && stack.getItem() instanceof ItemSlagBase) {
            return true;
        }

        return plant.chemplantModule.isItemValid(slot, stack);
    }
}
