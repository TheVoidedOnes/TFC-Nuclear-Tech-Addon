package com.voided.tfcnuclear.overwrite_contents.mixin.handler;

import com.hbm.inventory.fluid.tank.FluidTankNTM;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.modules.machine.ModuleMachineBase;
import com.voided.tfcnuclear.compat.hbm.SlagChemicalAStack;
import com.voided.tfcnuclear.inventory.items.ItemSlagBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ModuleMachineBase.class)
public abstract class MixinModuleMachineBase {

    @Shadow(remap = false)
    protected ItemStackHandler inventory;

    @Shadow(remap = false)
    protected int[] inputSlots;

    @Shadow(remap = false)
    protected int[] outputSlots;

    @Shadow(remap = false)
    protected FluidTankNTM[] inputTanks;

    @Shadow(remap = false)
    protected FluidTankNTM[] outputTanks;


    @Overwrite(remap = false)
    protected boolean hasInput(GenericRecipe recipe) {
        if (recipe.inputItem != null) {
            for (int i = 0; i < Math.min(recipe.inputItem.length, inputSlots.length); i++) {
                ItemStack slotStack = inventory.getStackInSlot(inputSlots[i]);

                if (recipe.inputItem[i] instanceof SlagChemicalAStack) {
                    SlagChemicalAStack consume = (SlagChemicalAStack) recipe.inputItem[i];

                    if (slotStack.isEmpty() || !(slotStack.getItem() instanceof ItemSlagBase)) {
                        return false;
                    }

                    if (slotStack.getItem() != consume.chemicalItem) {
                        return false;
                    }

                    int amount = ItemSlagBase.getAmount(slotStack);
                    if (amount < consume.requiredAmount) {
                        return false;
                    }
                } else {
                    if (!recipe.inputItem[i].matchesRecipe(slotStack, false)) {
                        return false;
                    }
                }
            }
        }

        if (recipe.inputFluid != null) {
            for (int i = 0; i < Math.min(recipe.inputFluid.length, inputTanks.length); i++) {
                if (inputTanks[i].getFill() < recipe.inputFluid[i].fill) {
                    return false;
                }
            }
        }

        return true;
    }

    @Overwrite(remap = false)
    protected void consumeInput(GenericRecipe recipe) {
        if (recipe.inputItem != null) {
            for (int i = 0; i < Math.min(recipe.inputItem.length, inputSlots.length); i++) {
                int slotIndex = inputSlots[i];
                ItemStack slotStack = inventory.getStackInSlot(slotIndex);

                if (slotStack.isEmpty()) continue;

                if (recipe.inputItem[i] instanceof SlagChemicalAStack) {
                    SlagChemicalAStack consume = (SlagChemicalAStack) recipe.inputItem[i];

                    if (slotStack.getItem() instanceof ItemSlagBase) {
                        int currentAmount = ItemSlagBase.getAmount(slotStack);
                        int remaining = currentAmount - consume.requiredAmount;

                        if (remaining <= 0) {
                            inventory.setStackInSlot(slotIndex, ItemStack.EMPTY);
                        } else {
                            ItemSlagBase.setAmount(slotStack, remaining);
                            inventory.setStackInSlot(slotIndex, slotStack);
                        }
                    }
                } else {
                    int amountToShrink = recipe.inputItem[i].stacksize;
                    slotStack.shrink(amountToShrink);
                    if (slotStack.getCount() <= 0) {
                        inventory.setStackInSlot(slotIndex, ItemStack.EMPTY);
                    } else {
                        inventory.setStackInSlot(slotIndex, slotStack);
                    }
                }
            }
        }

        if (recipe.inputFluid != null) {
            for (int i = 0; i < Math.min(recipe.inputFluid.length, inputTanks.length); i++) {
                inputTanks[i].setFill(inputTanks[i].getFill() - recipe.inputFluid[i].fill);
            }
        }
    }

    @Overwrite(remap = false)
    protected boolean canFitOutput(GenericRecipe recipe) {
        if (recipe.outputItem != null) {
            for (int i = 0; i < Math.min(recipe.outputItem.length, outputSlots.length); i++) {
                ItemStack slotStack = inventory.getStackInSlot(outputSlots[i]);
                if (slotStack.isEmpty()) continue;

                if (!(recipe.outputItem[i] instanceof com.hbm.inventory.recipes.loader.GenericRecipes.IOutput)) {
                    continue;
                }

                com.hbm.inventory.recipes.loader.GenericRecipes.IOutput output = recipe.outputItem[i];
                if (output.possibleMultiOutput()) return false;

                ItemStack single = output.getSingle();
                if (single == null || single.isEmpty()) return false;

                if (slotStack.getItem() != single.getItem()) return false;
                if (slotStack.getItemDamage() != single.getItemDamage()) return false;
                if (slotStack.getCount() + single.getCount() > slotStack.getMaxStackSize()) return false;
            }
        }

        if (recipe.outputFluid != null) {
            for (int i = 0; i < Math.min(recipe.outputFluid.length, outputTanks.length); i++) {
                if (recipe.outputFluid[i].fill + outputTanks[i].getFill() > outputTanks[i].getMaxFill()) {
                    return false;
                }
            }
        }

        return true;
    }
}