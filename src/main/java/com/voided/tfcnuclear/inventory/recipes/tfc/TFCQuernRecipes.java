package com.voided.tfcnuclear.inventory.recipes.tfc;

import net.dries007.tfc.api.recipes.quern.QuernRecipe;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.ForgeRegistry;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TFCQuernRecipes {

    public static void addQuernRecipe() {
        removeOldRecipes();
        registerNewRecipes();
    }

    private static void registerNewRecipes() {
        addItemRecipe("tfc:ore/cinnabar", new ItemStack(Items.REDSTONE, 2), "tfcnuclear:ore_cinnabar_quern");
        addItemRecipe("hbm:cinnabar", new ItemStack(Items.REDSTONE, 1), "tfcnuclear:cinnabar_quern");
        addItemRecipe("tfc:ore/lapis_lazuli", new ItemStack(Item.getByNameOrId("hbm:powder_lapis"), 2), "tfcnuclear:ore_lapis_quern");
        addItemRecipe("tfc:ore/borax", new ItemStack(Item.getByNameOrId("hbm:powder_borax"), 1), "tfcnuclear:ore_borax_quern");
        addItemRecipe("tfc:ore/saltpeter", new ItemStack(Item.getByNameOrId("hbm:niter"), 1), "tfcnuclear:ore_niter_quern");
        addItemRecipe("tfc:ore/lignite", new ItemStack(Item.getByNameOrId("hbm:powder_lignite"), 1), "tfcnuclear:ore_lignite_quern");
        addItemRecipe("tfc:ore/kimberlite", new ItemStack(Item.getByNameOrId("tfc:gem/diamond"), 1, 2), "tfcnuclear:ore_diamond_quern");
        addItemRecipe("tfc:ore/graphite", new ItemStack(Item.getByNameOrId("tfc:powder/graphite"), 2), "tfcnuclear:ore_graphite_quern");
        addItemRecipe("tfc:ore/bituminous_coal", new ItemStack(Item.getByNameOrId("hbm:powder_coal"), 1), "tfcnuclear:ore_coal_quern");
        addOreDictRecipe("rockFlux", new ItemStack(Item.getByNameOrId("hbm:powder_flux")), "tfcnuclear:rock_flux");
    }

    private static void removeOldRecipes() {
        List<ResourceLocation> toRemove = new ArrayList<>();

        for (QuernRecipe recipe : TFCRegistries.QUERN.getValuesCollection()) {
            ResourceLocation name = recipe.getRegistryName();
            if (name == null) continue;

            String path = name.getPath().toLowerCase();
            if (path.contains("cinnabar") || path.contains("cryolite") || path.contains("lapis")
                    || path.contains("borax") || path.contains("flux") || path.contains("saltpeter")
                    || path.contains("sulfur") || path.contains("diamond") || path.contains("charcoal")
                    || path.contains("graphite")) {
                toRemove.add(name);
            }
        }

        if (!toRemove.isEmpty()) {
            try {
                ForgeRegistry<QuernRecipe> registry = (ForgeRegistry<QuernRecipe>) TFCRegistries.QUERN;
                for (ResourceLocation name : toRemove) {
                    registry.remove(name);
                }
            } catch (Exception e) {
                removeViaReflection(toRemove);
            }
        }
    }

    private static void removeViaReflection(List<ResourceLocation> toRemove) {
        try {
            Field registryMapField = ForgeRegistry.class.getDeclaredField("registryMap");
            registryMapField.setAccessible(true);

            ForgeRegistry<QuernRecipe> registry = (ForgeRegistry<QuernRecipe>) TFCRegistries.QUERN;
            Map<ResourceLocation, QuernRecipe> registryMap = (Map<ResourceLocation, QuernRecipe>) registryMapField.get(registry);

            for (ResourceLocation name : toRemove) {
                registryMap.remove(name);
            }
        } catch (Exception ignored) {}
    }

    private static void addOreDictRecipe(String oreDictName, ItemStack output, String registryName) {
        List<ItemStack> ores = OreDictionary.getOres(oreDictName);
        if (ores.isEmpty()) return;

        IIngredient<ItemStack> input = createOreDictIngredient(oreDictName);
        registerRecipe(input, output, registryName);
    }

    private static void addItemRecipe(String inputId, ItemStack output, String registryName) {
        Item inputItem = Item.getByNameOrId(inputId);
        if (inputItem == null) return;

        IIngredient<ItemStack> input = createItemIngredient(inputItem);
        registerRecipe(input, output, registryName);
    }

    private static void registerRecipe(IIngredient<ItemStack> input, ItemStack output, String registryName) {
        QuernRecipe recipe = new QuernRecipe(input, output);
        recipe.setRegistryName(new ResourceLocation(registryName));
        TFCRegistries.QUERN.register(recipe);
    }

    private static IIngredient<ItemStack> createOreDictIngredient(String oreDictName) {
        int targetId = OreDictionary.getOreID(oreDictName);

        return new IIngredient<ItemStack>() {
            @Override
            public boolean test(ItemStack stack) {
                if (stack.isEmpty()) return false;
                int[] oreIds = OreDictionary.getOreIDs(stack);
                for (int id : oreIds) {
                    if (id == targetId) return true;
                }
                return false;
            }

            @Override
            public ItemStack consume(ItemStack stack) {
                stack.shrink(1);
                return stack;
            }

            @Override
            public int getAmount() {
                return 1;
            }

            @Override
            public NonNullList<ItemStack> getValidIngredients() {
                NonNullList<ItemStack> validItems = NonNullList.create();
                for (ItemStack ore : OreDictionary.getOres(oreDictName)) {
                    validItems.add(ore.copy());
                }
                return validItems;
            }
        };
    }

    private static IIngredient<ItemStack> createItemIngredient(Item inputItem) {
        ItemStack required = new ItemStack(inputItem);

        return new IIngredient<ItemStack>() {
            @Override
            public boolean test(ItemStack stack) {
                return !stack.isEmpty() && stack.getItem() == required.getItem() && stack.getCount() >= required.getCount();
            }

            @Override
            public ItemStack consume(ItemStack stack) {
                stack.shrink(required.getCount());
                return stack;
            }

            @Override
            public int getAmount() {
                return required.getCount();
            }

            @Override
            public NonNullList<ItemStack> getValidIngredients() {
                return NonNullList.withSize(1, required.copy());
            }
        };
    }
}