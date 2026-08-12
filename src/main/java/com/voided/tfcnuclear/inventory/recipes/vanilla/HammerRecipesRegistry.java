package com.voided.tfcnuclear.inventory.recipes.vanilla;

import com.hbm.items.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static com.voided.tfcnuclear.inventory.recipes.vanilla.RecipeHelper.registerHammerRecipe;

public class HammerRecipesRegistry {

    private static boolean registered = false;

    public static void register() {
        if (registered) {
            return;
        }

        registerHammerRecipe("hammer_flux", new ItemStack(ModItems.powder_flux, 1), "rockFlux", 1);
        registerHammerRecipe("hammer_lignite", new ItemStack(ModItems.lignite, 1), "oreLigniteTFC", 1);
        registerHammerRecipe("hammer_coal", new ItemStack(Items.COAL, 1), "oreBituminousCoal", 1);
        registerHammerRecipe("hammer_lapis", new ItemStack(Items.DYE, 1, 4), "oreLapisLazuliTFC", 1);

        registered = true;
    }
}