package com.voided.tfcnuclear.inventory.recipes.vanilla;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryModifiable;

import javax.annotation.Nullable;
import java.util.List;

public class RecipeHelper {

    public static final String MOD_ID = "tfcnuclear";

    @Nullable
    public static ItemStack getItemStack(String id) {
        Item item = Item.getByNameOrId(id);
        return item != null ? new ItemStack(item) : ItemStack.EMPTY;
    }

    @Nullable
    public static ItemStack getItemStack(String id, int count) {
        Item item = Item.getByNameOrId(id);
        return item != null ? new ItemStack(item, count) : ItemStack.EMPTY;
    }

    @Nullable
    public static ItemStack getItemStack(String id, int count, int meta) {
        Item item = Item.getByNameOrId(id);
        return item != null ? new ItemStack(item, count, meta) : ItemStack.EMPTY;
    }

    public static String oreDict(String name) {
        return name;
    }

    public static void removeRecipes(List<ResourceLocation> recipesToRemove) {
        IForgeRegistryModifiable registry = (IForgeRegistryModifiable) ForgeRegistries.RECIPES;
        for (ResourceLocation location : recipesToRemove) {
            if (registry.containsKey(location)) {
                registry.remove(location);
            }
        }
    }

    public static void registerShapedRecipe(String name, ItemStack result, Object... ingredients) {
        if (result == null || result.isEmpty() || ingredients.length == 0) {
            return;
        }
        GameRegistry.addShapedRecipe(
                new ResourceLocation(MOD_ID, name),
                new ResourceLocation(MOD_ID, name),
                result,
                ingredients
        );
    }

    public static void registerHammerRecipe(String name, ItemStack result, String inputOreDict, int multiplier) {
        HammerRecipe recipe = new HammerRecipe(result, inputOreDict, multiplier);
        recipe.setRegistryName(new ResourceLocation(MOD_ID, name));
        ForgeRegistries.RECIPES.register(recipe);
    }

    public static void registerHammerRecipe(String name, ItemStack result, String inputOreDict) {
        registerHammerRecipe(name, result, inputOreDict, 2);
    }

    public static void registerFurnaceRecipeOreDict(String inputOreDict, String outputId, int count, float experience) {
        registerFurnaceRecipeOreDict(inputOreDict, outputId, count, 0, experience);
    }

    public static void registerFurnaceRecipeOreDict(String inputOreDict, String outputId, int count, int meta, float experience) {
        List<ItemStack> ores = OreDictionary.getOres(inputOreDict);
        if (ores.isEmpty()) {
            return;
        }

        Item outputItem = Item.getByNameOrId(outputId);
        if (outputItem == null) {
            return;
        }

        ItemStack output = new ItemStack(outputItem, count, meta);
        if (output.isEmpty()) {
            return;
        }

        for (ItemStack input : ores) {
            if (!input.isEmpty()) {
                GameRegistry.addSmelting(input.copy(), output.copy(), experience);
            }
        }
    }

    public static void registerFurnaceRecipeItem(String inputId, String outputId, int count, float experience) {
        registerFurnaceRecipeItem(inputId, outputId, count, 0, experience);
    }

    public static void registerFurnaceRecipeItem(String inputId, String outputId, int count, int meta, float experience) {
        Item inputItem = Item.getByNameOrId(inputId);
        Item outputItem = Item.getByNameOrId(outputId);

        if (inputItem == null || outputItem == null) {
            return;
        }

        ItemStack input = new ItemStack(inputItem);
        ItemStack output = new ItemStack(outputItem, count, meta);

        if (!input.isEmpty() && !output.isEmpty()) {
            GameRegistry.addSmelting(input, output, experience);
        }
    }
}
