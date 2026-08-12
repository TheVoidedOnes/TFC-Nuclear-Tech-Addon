package com.voided.tfcnuclear.compat.hbm;

import com.hbm.inventory.RecipesCommon;
import com.hbm.inventory.RecipesCommon.NbtComparableStack;
import com.voided.tfcnuclear.inventory.items.ItemSlagBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class SlagChemicalAStack extends NbtComparableStack {

    public final int requiredAmount;
    public final Item chemicalItem;

    public SlagChemicalAStack(ItemStack stack, int requiredAmount) {
        super(stack);
        this.requiredAmount = requiredAmount;
        this.chemicalItem = stack.getItem();
        this.stacksize = 1;
    }

    public SlagChemicalAStack(Item chemicalItem, int requiredAmount) {
        super(new ItemStack(chemicalItem));
        this.requiredAmount = requiredAmount;
        this.chemicalItem = chemicalItem;
        this.stacksize = 1;
    }

    @Override
    public RecipesCommon.AStack copy() {
        return new SlagChemicalAStack(this.chemicalItem, requiredAmount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SlagChemicalAStack)) {
            return false;
        }
        SlagChemicalAStack other = (SlagChemicalAStack) obj;
        return this.chemicalItem == other.chemicalItem && this.requiredAmount == other.requiredAmount;
    }

    @Override
    public List<ItemStack> extractForJEI() {
        return Collections.singletonList(getStack());
    }

    @Override
    public ItemStack getStack() {
        ItemStack displayStack = new ItemStack(this.chemicalItem);
        ItemSlagBase.setAmount(displayStack, requiredAmount);
        return displayStack;
    }

    @Override
    public List<ItemStack> getStackList() {
        return Collections.singletonList(getStack());
    }

    @Override
    public int hashCode() {
        return this.chemicalItem.hashCode() * 31 + this.requiredAmount;
    }

    @Override
    public boolean matchesRecipe(ItemStack stack, boolean ignoreSize) {
        if (stack.isEmpty() || !(stack.getItem() instanceof ItemSlagBase)) {
            return false;
        }

        if (stack.getItem() != this.chemicalItem) {
            return false;
        }

        int amount = ItemSlagBase.getAmount(stack);
        return amount >= requiredAmount;
    }

    @Override
    public String toString() {
        return "ChemicalConsumeStack: " + requiredAmount + " x " + chemicalItem.getRegistryName();
    }
}