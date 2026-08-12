package com.voided.tfcnuclear.inventory.recipes.vanilla;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FurnaceRecipesRegistry {

    private static boolean recipesAdded = false;

    private static final List<ResourceLocation> RECIPES_TO_REMOVE = Arrays.asList(

            new ResourceLocation("minecraft", "gold_ingot"),
            new ResourceLocation("minecraft", "gold_nugget"),
            new ResourceLocation("minecraft", "diamond"),
            new ResourceLocation("minecraft", "emerald"),
            new ResourceLocation("minecraft", "redstone"),
            new ResourceLocation("minecraft", "dye"),
            new ResourceLocation("minecraft", "coal"),
            new ResourceLocation("minecraft", "brick"),
            new ResourceLocation("minecraft", "stonebrick"),
            new ResourceLocation("minecraft", "stone"),
            new ResourceLocation("minecraft", "cobblestone"),
            new ResourceLocation("minecraft", "iron_nugget"),

            new ResourceLocation("hbm", "ingot_lead"),
            new ResourceLocation("hbm", "ingot_titanium"),
            new ResourceLocation("hbm", "ingot_firebrick"),
            new ResourceLocation("hbmspace", "ingot_zinc"),
            new ResourceLocation("hbm", "ingot_cobalt"),
            new ResourceLocation("hbm", "powder_fire")
    );

    private static final Map<String, FurnaceRecipeEntry> RECIPES = new HashMap<>();

    static {

        RECIPES.put("hbm:powder_gold", new FurnaceRecipeEntry("tfc:metal/ingot/gold", 1, 1.0f));
        RECIPES.put("hbm:crystal_gold", new FurnaceRecipeEntry("tfc:metal/ingot/gold", 2, 0.7f));
        RECIPES.put("hbm:crystal_redstone", new FurnaceRecipeEntry("minecraft:redstone", 6, 0.5f));
        RECIPES.put("hbm:powder_lapis", new FurnaceRecipeEntry("minecraft:dye", 1, 4, 0.5f));
        RECIPES.put("tfc:ore/cryolite", new FurnaceRecipeEntry("hbm:chunk_ore", 1, 2, 0.5f));
        RECIPES.put("tfc:ceramics/unfired/clay_brick", new FurnaceRecipeEntry("minecraft:brick", 1, 0, 0.5f));
        RECIPES.put("minecraft:clay_ball", new FurnaceRecipeEntry("hbm:ball_fireclay", 1, 0, 0.5f));
        RECIPES.put("tfc:ceramics/unfired/fire_brick", new FurnaceRecipeEntry("hbm:ingot_firebrick", 1, 0, 0.5f));
        RECIPES.put("tfc:glass_shard", new FurnaceRecipeEntry("minecraft:glass", 1, 0.7f));
        RECIPES.put("hbm:catalyst_clay", new FurnaceRecipeEntry("tfcnuclear:fired_catalyst_clay", 1, 0.7f));
        RECIPES.put("hbm:crystal_phosphorus", new FurnaceRecipeEntry("hbm:powder_fire", 2, 0.7f));
        RECIPES.put("hbm:ore_meteor@0", new FurnaceRecipeEntry("minecraft:iron_ingot", 16, 0.7f));
        RECIPES.put("hbm:ore_meteor@1", new FurnaceRecipeEntry("hbm:ingot_copper", 16, 0.7f));
        RECIPES.put("hbm:ore_meteor@4", new FurnaceRecipeEntry("hbm:ingot_cobalt", 16, 0.7f));

        RECIPES.put("tfc:gravel/granite", new FurnaceRecipeEntry("tfc:cobble/granite", 1, 0.7f));
        RECIPES.put("tfc:gravel/diorite", new FurnaceRecipeEntry("tfc:cobble/diorite", 1, 0.7f));
        RECIPES.put("tfc:gravel/gabbro", new FurnaceRecipeEntry("tfc:cobble/gabbro", 1, 0.7f));
        RECIPES.put("tfc:gravel/shale", new FurnaceRecipeEntry("tfc:cobble/shale", 1, 0.7f));
        RECIPES.put("tfc:gravel/claystone", new FurnaceRecipeEntry("tfc:cobble/claystone", 1, 0.7f));
        RECIPES.put("tfc:gravel/rocksalt", new FurnaceRecipeEntry("tfc:cobble/rocksalt", 1, 0.7f));
        RECIPES.put("tfc:gravel/limestone", new FurnaceRecipeEntry("tfc:cobble/limestone", 1, 0.7f));
        RECIPES.put("tfc:gravel/conglomerate", new FurnaceRecipeEntry("tfc:cobble/conglomerate", 1, 0.7f));
        RECIPES.put("tfc:gravel/dolomite", new FurnaceRecipeEntry("tfc:cobble/dolomite", 1, 0.7f));
        RECIPES.put("tfc:gravel/chert", new FurnaceRecipeEntry("tfc:cobble/chert", 1, 0.7f));
        RECIPES.put("tfc:gravel/chalk", new FurnaceRecipeEntry("tfc:cobble/chalk", 1, 0.7f));
        RECIPES.put("tfc:gravel/rhyolite", new FurnaceRecipeEntry("tfc:cobble/rhyolite", 1, 0.7f));
        RECIPES.put("tfc:gravel/basalt", new FurnaceRecipeEntry("tfc:cobble/basalt", 1, 0.7f));
        RECIPES.put("tfc:gravel/andesite", new FurnaceRecipeEntry("tfc:cobble/andesite", 1, 0.7f));
        RECIPES.put("tfc:gravel/dacite", new FurnaceRecipeEntry("tfc:cobble/dacite", 1, 0.7f));
        RECIPES.put("tfc:gravel/quartzite", new FurnaceRecipeEntry("tfc:cobble/quartzite", 1, 0.7f));
        RECIPES.put("tfc:gravel/slate", new FurnaceRecipeEntry("tfc:cobble/slate", 1, 0.7f));
        RECIPES.put("tfc:gravel/phyllite", new FurnaceRecipeEntry("tfc:cobble/phyllite", 1, 0.7f));
        RECIPES.put("tfc:gravel/schist", new FurnaceRecipeEntry("tfc:cobble/schist", 1, 0.7f));
        RECIPES.put("tfc:gravel/gneiss", new FurnaceRecipeEntry("tfc:cobble/gneiss", 1, 0.7f));
        RECIPES.put("tfc:gravel/marble", new FurnaceRecipeEntry("tfc:cobble/marble", 1, 0.7f));

        addOreDictFurnaceRecipe("sand", "minecraft:glass", 1, 0.7f);
    }

    public static void addRecipes() {
        if (recipesAdded) return;

        removeFurnaceRecipes();
        registerFurnaceRecipes();

        recipesAdded = true;
    }

    private static void removeFurnaceRecipes() {
        Map<ItemStack, ItemStack> smeltingList = net.minecraft.item.crafting.FurnaceRecipes.instance().getSmeltingList();
        List<ItemStack> toRemove = new java.util.ArrayList<>();

        for (Map.Entry<ItemStack, ItemStack> entry : smeltingList.entrySet()) {
            ItemStack output = entry.getValue();
            ResourceLocation outputName = output.getItem().getRegistryName();

            if (outputName != null) {
                for (ResourceLocation toRemoveLoc : RECIPES_TO_REMOVE) {
                    if (outputName.toString().equals(toRemoveLoc.toString())) {
                        toRemove.add(entry.getKey());
                        break;
                    }
                }
            }
        }

        for (ItemStack input : toRemove) {
            smeltingList.remove(input);
        }
    }

    private static void registerFurnaceRecipes() {
        for (Map.Entry<String, FurnaceRecipeEntry> entry : RECIPES.entrySet()) {
            String inputId = entry.getKey();
            FurnaceRecipeEntry recipe = entry.getValue();

            if (inputId.startsWith("oreDict:")) {
                String oreDictName = inputId.substring(8);
                addOreDictFurnaceRecipe(oreDictName, recipe.outputId, recipe.count, recipe.meta, recipe.experience);
                continue;
            }

            Item inputItem = Item.getByNameOrId(inputId);
            Item outputItem = Item.getByNameOrId(recipe.outputId);

            if (inputItem != null && outputItem != null) {
                ItemStack input = new ItemStack(inputItem);
                ItemStack output = new ItemStack(outputItem, recipe.count, recipe.meta);

                if (!input.isEmpty() && !output.isEmpty()) {
                    GameRegistry.addSmelting(input, output, recipe.experience);
                }
            }
        }
    }

    private static void addOreDictFurnaceRecipe(String oreDictName, String outputId, int count, int meta, float experience) {
        List<ItemStack> ores = OreDictionary.getOres(oreDictName);

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

    private static void addOreDictFurnaceRecipe(String oreDictName, String outputId, int count, float experience) {
        addOreDictFurnaceRecipe(oreDictName, outputId, count, 0, experience);
    }

    private static class FurnaceRecipeEntry {
        final String outputId;
        final int count;
        final int meta;
        final float experience;

        FurnaceRecipeEntry(String outputId, int count, float experience) {
            this(outputId, count, 0, experience);
        }

        FurnaceRecipeEntry(String outputId, int count, int meta, float experience) {
            this.outputId = outputId;
            this.count = count;
            this.meta = meta;
            this.experience = experience;
        }
    }
}