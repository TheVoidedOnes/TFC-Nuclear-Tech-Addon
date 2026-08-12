package com.voided.tfcnuclear.compat.hbm;

import com.hbm.inventory.RecipesCommon;
import com.hbm.inventory.RecipesCommon.NbtComparableStack;
import com.voided.tfcnuclear.inventory.items.ItemSlagBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class SlagAStack extends NbtComparableStack {

    public final int requiredAmount;
    public final Item slagItem;

    public SlagAStack(ItemStack stack, int requiredAmount) {
        super(stack);
        this.requiredAmount = requiredAmount;
        this.slagItem = stack.getItem();
        this.stacksize = 1;
    }

    public SlagAStack(Item slagItem, int requiredAmount) {
        super(new ItemStack(slagItem));
        this.requiredAmount = requiredAmount;
        this.slagItem = slagItem;
        this.stacksize = 1;
    }

    @Override
    public boolean matchesRecipe(ItemStack stack, boolean ignoreSize) {
        if (stack.isEmpty() || !(stack.getItem() instanceof ItemSlagBase)) {
            return false;
        }

        if (stack.getItem() != this.slagItem) {
            return false;
        }

        int amount = ItemSlagBase.getAmount(stack);
        // Используем >= для поддержки частичного расходования
        return amount >= requiredAmount;
    }

    @Override
    public ItemStack getStack() {
        ItemStack displayStack = new ItemStack(this.slagItem);
        ItemSlagBase.setAmount(displayStack, requiredAmount);
        return displayStack;
    }

    @Override
    public List<ItemStack> getStackList() {
        return Collections.singletonList(getStack());
    }

    @Override
    public List<ItemStack> extractForJEI() {
        return Collections.singletonList(getStack());
    }

    @Override
    public RecipesCommon.AStack copy() {
        return new SlagAStack(this.slagItem, requiredAmount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof SlagAStack)) return false;
        SlagAStack other = (SlagAStack) obj;
        return this.slagItem == other.slagItem && this.requiredAmount == other.requiredAmount;
    }

    @Override
    public int hashCode() {
        return this.slagItem.hashCode() * 31 + this.requiredAmount;
    }

    @Override
    public String toString() {
        return "SlagStack: " + requiredAmount + " x " + slagItem.getRegistryName();
    }
}