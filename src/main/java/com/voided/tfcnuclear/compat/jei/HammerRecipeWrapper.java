package com.voided.tfcnuclear.compat.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class HammerRecipeWrapper implements IRecipeWrapper {

    private final List<ItemStack> hammers;
    private final List<ItemStack> inputs;
    private final ItemStack output;

    public HammerRecipeWrapper(String inputOreDict, ItemStack output) {
        this.output = output;

        this.hammers = new ArrayList<>();
        List<ItemStack> hammersFromDict = OreDictionary.getOres("hammer");
        for (ItemStack hammer : hammersFromDict) {
            if (!hammer.isEmpty()) {
                this.hammers.add(hammer.copy());
            }
        }

        this.inputs = new ArrayList<>();
        List<ItemStack> inputItems = OreDictionary.getOres(inputOreDict);
        for (ItemStack item : inputItems) {
            if (!item.isEmpty()) {
                this.inputs.add(item.copy());
            }
        }
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<List<ItemStack>> inputsList = new ArrayList<>();
        inputsList.add(hammers);
        inputsList.add(inputs);

        ingredients.setInputLists(ItemStack.class, inputsList);
        ingredients.setOutput(ItemStack.class, output);
    }
}