package com.voided.tfcnuclear.compat.hbm;

import com.hbm.inventory.RecipesCommon;
import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.capability.forge.IForgeableMeasurableMetal;
import net.dries007.tfc.api.types.Metal;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class BloomPressAStack extends RecipesCommon.AStack {

    public final Item item;
    public final int metalAmount;
    public final Metal metal;

    public BloomPressAStack(Item item, int metalAmount) {
        this(item, metalAmount, Metal.WROUGHT_IRON);
    }

    public BloomPressAStack(Item item, int metalAmount, Metal metal) {
        this.item = item;
        this.metalAmount = metalAmount;
        this.metal = metal;
        this.stacksize = 1;
    }

    @Override
    public int compareTo(RecipesCommon.AStack o) {
        if (o instanceof BloomPressAStack) {
            BloomPressAStack other = (BloomPressAStack) o;
            int itemComp = this.item.getRegistryName().toString()
                    .compareTo(other.item.getRegistryName().toString());
            if (itemComp != 0) {
                return itemComp;
            }
            return Integer.compare(this.metalAmount, other.metalAmount);
        }
        return 0;
    }

    @Override
    public RecipesCommon.AStack copy() {
        return new BloomPressAStack(item, metalAmount, metal);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BloomPressAStack that = (BloomPressAStack) obj;
        return metalAmount == that.metalAmount &&
                Objects.equals(item, that.item) &&
                metal == that.metal;
    }

    @Override
    public List<ItemStack> extractForJEI() {
        return Collections.singletonList(getStack());
    }

    @Override
    public ItemStack getStack() {
        ItemStack stack = new ItemStack(item, stacksize);
        IForgeableMeasurableMetal cap = (IForgeableMeasurableMetal)
                stack.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
        if (cap != null) {
            cap.setMetal(metal);
            cap.setMetalAmount(metalAmount);
        }
        return stack;
    }

    @Override
    public List<ItemStack> getStackList() {
        return Collections.singletonList(getStack());
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, metalAmount, metal);
    }

    @Override
    public boolean matchesRecipe(ItemStack stack, boolean ignoreSize) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        if (stack.getItem() != this.item) {
            return false;
        }
        if (!ignoreSize && stack.getCount() < this.stacksize) {
            return false;
        }

        IForgeableMeasurableMetal cap = (IForgeableMeasurableMetal)
                stack.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
        if (cap == null) {
            return false;
        }

        return cap.getMetalAmount() == this.metalAmount &&
                cap.getMetal() == this.metal;
    }
}