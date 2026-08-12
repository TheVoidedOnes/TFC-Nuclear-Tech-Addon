package com.voided.tfcnuclear.overwrite_contents.mixin.handler;

import com.hbm.inventory.recipes.BlastFurnaceRecipesNT;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.voided.tfcnuclear.compat.hbm.SlagAStack;
import com.voided.tfcnuclear.inventory.items.ItemSlagBase;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BlastFurnaceRecipesNT.class)
public class MixinHBMBlastFurnaceRecipes {

    /**
     * @author Void
     * @reason Nbt consumption
     */
    @Overwrite(remap = false)
    public GenericRecipe getRecipe(ItemStack s0, ItemStack s1) {
        BlastFurnaceRecipesNT instance = (BlastFurnaceRecipesNT) (Object) this;

        ItemStack slagStack = null;
        ItemStack otherStack = null;

        if (!s0.isEmpty() && s0.getItem() instanceof ItemSlagBase) {
            slagStack = s0;
            otherStack = s1;
        } else if (!s1.isEmpty() && s1.getItem() instanceof ItemSlagBase) {
            slagStack = s1;
            otherStack = s0;
        }

        if (slagStack != null) {
            int amount = ItemSlagBase.getAmount(slagStack);

            GenericRecipe bestRecipe = null;
            int bestRequiredAmount = 0;

            for (GenericRecipe recipe : instance.recipeOrderedList) {
                if (recipe.inputItem != null && recipe.inputItem.length > 0) {
                    if (recipe.inputItem[0] instanceof SlagAStack) {
                        SlagAStack slagRecipe = (SlagAStack) recipe.inputItem[0];
                        if (slagStack.getItem() == slagRecipe.slagItem && amount >= slagRecipe.requiredAmount) {
                            if (recipe.inputItem.length == 2) {
                                if (otherStack != null && recipe.inputItem[1].matchesRecipe(otherStack, false)) {
                                    if (slagRecipe.requiredAmount > bestRequiredAmount) {
                                        bestRecipe = recipe;
                                        bestRequiredAmount = slagRecipe.requiredAmount;
                                    }
                                }
                            } else {
                                if (slagRecipe.requiredAmount > bestRequiredAmount) {
                                    bestRecipe = recipe;
                                    bestRequiredAmount = slagRecipe.requiredAmount;
                                }
                            }
                        }
                    }
                }
            }

            if (bestRecipe != null) {
                return bestRecipe;
            }
        }

        for (GenericRecipe recipe : instance.recipeOrderedList) {
            if (recipe.inputItem.length == 1) {
                if (!s0.isEmpty() && s1.isEmpty() && recipe.inputItem[0].matchesRecipe(s0, false)) return recipe;
                if (s0.isEmpty() && !s1.isEmpty() && recipe.inputItem[0].matchesRecipe(s1, false)) return recipe;
            }
            if (recipe.inputItem.length == 2 && !s0.isEmpty() && !s1.isEmpty()) {
                if (recipe.inputItem[0].matchesRecipe(s0, true) && recipe.inputItem[1].matchesRecipe(s1, false)) return recipe;
                if (recipe.inputItem[1].matchesRecipe(s0, true) && recipe.inputItem[0].matchesRecipe(s1, false)) return recipe;
            }
        }

        return null;
    }
}