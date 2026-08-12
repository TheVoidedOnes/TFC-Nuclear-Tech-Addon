package com.voided.tfcnuclear.compat.hbm;

import com.hbm.inventory.RecipesCommon;
import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.capability.forge.IForgeable;
import net.dries007.tfc.api.capability.forge.IForgeableMeasurableMetal;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.items.ItemBloom;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class BloomCrucibleAStack extends RecipesCommon.AStack {

    private static final int[] VALID_AMOUNTS = {100, 200, 300, 400};
    private final int displayAmount;

    public BloomCrucibleAStack() {
        this(100);
    }

    public BloomCrucibleAStack(int displayAmount) {
        this.displayAmount = displayAmount;
        this.stacksize = 1;
    }

    public static int[] getValidAmounts() {
        return VALID_AMOUNTS.clone();
    }

    @Override
    public int compareTo(@NotNull RecipesCommon.AStack o) {
        if (o instanceof BloomCrucibleAStack) {
            return Integer.compare(this.displayAmount, ((BloomCrucibleAStack) o).displayAmount);
        }
        return 1;
    }

    @Override
    public RecipesCommon.AStack copy() {
        return new BloomCrucibleAStack(this.displayAmount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        BloomCrucibleAStack other = (BloomCrucibleAStack) obj;
        return this.displayAmount == other.displayAmount;
    }

    @Override
    public List<ItemStack> extractForJEI() {
        return Collections.emptyList();
    }

    @Override
    public ItemStack getStack() {
        return ItemStack.EMPTY;
    }

    @Override
    public List<ItemStack> getStackList() {
        return Collections.emptyList();
    }

    @Override
    public int hashCode() {
        return ("TFCBloomWroughtIron".hashCode() * 31) + displayAmount;
    }

    @Override
    public boolean matchesRecipe(ItemStack stack, boolean ignoreSize) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }

        if (!(stack.getItem() instanceof ItemBloom)) {
            return false;
        }

        IForgeable cap = stack.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
        if (!(cap instanceof IForgeableMeasurableMetal)) {
            return false;
        }

        IForgeableMeasurableMetal metalCap = (IForgeableMeasurableMetal) cap;

        if (metalCap.getMetal() != Metal.WROUGHT_IRON) {
            return false;
        }

        int amount = metalCap.getMetalAmount();
        if (amount <= 0) {
            return false;
        }

        ItemBloom bloom = (ItemBloom) stack.getItem();
        return bloom.canMelt(stack);
    }
}