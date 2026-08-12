package com.voided.tfcnuclear.inventory.recipes.vanilla;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

public class HammerRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    private final ItemStack result;
    private final String inputOreDict;
    private final int outputMultiplier;

    public HammerRecipe(ItemStack result, String inputOreDict, int outputMultiplier) {
        this.result = result.copy();
        this.inputOreDict = inputOreDict;
        this.outputMultiplier = outputMultiplier;
    }

    public HammerRecipe(ItemStack result, String inputOreDict) {
        this(result, inputOreDict, 2);
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    @Override
    @Nonnull
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {
        int inputCount = 0;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty() && isInputItem(stack)) {
                inputCount++;
            }
        }

        ItemStack resultStack = result.copy();
        resultStack.setCount(inputCount * outputMultiplier);
        return resultStack;
    }

    @Override
    @Nonnull
    public ItemStack getRecipeOutput() {
        return result;
    }

    @Override
    @Nonnull
    public NonNullList<ItemStack> getRemainingItems(@Nonnull InventoryCrafting inv) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack.isEmpty()) {
                continue;
            }

            if (isHammer(stack)) {
                ItemStack damaged = stack.copy();
                int newDamage = damaged.getItemDamage() + 1;
                if (newDamage < damaged.getMaxDamage()) {
                    damaged.setItemDamage(newDamage);
                    remaining.set(i, damaged);
                }
            }
        }

        return remaining;
    }

    public String getInputOreDict() {
        return inputOreDict;
    }

    public int getOutputMultiplier() {
        return outputMultiplier;
    }

    private boolean isHammer(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        int[] oreIds = OreDictionary.getOreIDs(stack);
        for (int id : oreIds) {
            if ("hammer".equals(OreDictionary.getOreName(id))) {
                return true;
            }
        }
        return false;
    }

    private boolean isInputItem(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        int[] oreIds = OreDictionary.getOreIDs(stack);
        for (int id : oreIds) {
            if (inputOreDict.equals(OreDictionary.getOreName(id))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World world) {
        boolean hasHammer = false;
        boolean hasInput = false;
        int hammerCount = 0;
        int inputCount = 0;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack.isEmpty()) {
                continue;
            }

            if (isHammer(stack)) {
                hammerCount++;
                if (hammerCount > 1) {
                    return false;
                }
                hasHammer = true;
            } else if (isInputItem(stack)) {
                inputCount++;
                hasInput = true;
            } else {
                return false;
            }
        }

        return hasHammer && hasInput;
    }
}
