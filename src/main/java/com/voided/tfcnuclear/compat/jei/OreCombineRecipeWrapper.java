package com.voided.tfcnuclear.compat.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OreCombineRecipeWrapper implements IRecipeWrapper {

    private final List<ItemStack> hammers;
    private final ItemStack ore;
    private final ItemStack result;

    public OreCombineRecipeWrapper(ItemStack ore, ItemStack result) {
        this.hammers = new ArrayList<>();
        List<ItemStack> hammersFromDict = OreDictionary.getOres("hammer");
        for (ItemStack hammer : hammersFromDict) {
            if (!hammer.isEmpty()) {
                this.hammers.add(hammer.copy());
            }
        }
        this.ore = ore;
        this.result = result;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<List<ItemStack>> inputs = new ArrayList<>();
        inputs.add(hammers);
        inputs.add(Collections.singletonList(ore));

        ingredients.setInputLists(ItemStack.class, inputs);
        ingredients.setOutput(ItemStack.class, result);
    }
}