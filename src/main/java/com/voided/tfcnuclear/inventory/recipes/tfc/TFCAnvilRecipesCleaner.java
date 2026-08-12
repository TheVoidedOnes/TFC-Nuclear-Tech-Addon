package com.voided.tfcnuclear.inventory.recipes.tfc;

import net.dries007.tfc.api.recipes.WeldingRecipe;
import net.dries007.tfc.api.recipes.anvil.AnvilRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistryModifiable;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class TFCAnvilRecipesCleaner {

    private static final String MOD_ID = "tfcnuclear";

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onAnvilRegistry(RegistryEvent.Register<AnvilRecipe> event) {
        IForgeRegistryModifiable registry = (IForgeRegistryModifiable) event.getRegistry();

        List<ResourceLocation> toRemove = new ArrayList<>();

        for (AnvilRecipe recipe : (Iterable<AnvilRecipe>) registry.getValuesCollection()) {
            ResourceLocation name = recipe.getRegistryName();
            if (name != null && MOD_ID.equals(name.getNamespace())) {
                continue;
            }

            if (isBismuthAnvilRecipe(recipe)) {
                toRemove.add(name);
            }
            if (isChromeAnvilRecipe(recipe)) {
                toRemove.add(name);
            }
            if (isSteelRodAnvilRecipe(recipe)) {
                toRemove.add(name);
            }
            if (isSteelIngotAnvilRecipe(recipe)) {
                toRemove.add(name);
            }
        }

        for (ResourceLocation name : toRemove) {
            registry.remove(name);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onWeldingRegistry(RegistryEvent.Register<WeldingRecipe> event) {
        IForgeRegistryModifiable registry = (IForgeRegistryModifiable) event.getRegistry();

        List<ResourceLocation> toRemove = new ArrayList<>();

        for (WeldingRecipe recipe : (Iterable<WeldingRecipe>) registry.getValuesCollection()) {
            ResourceLocation name = recipe.getRegistryName();
            if (name == null || MOD_ID.equals(name.getNamespace())) {
                continue;
            }

            if (isBismuthWeldingRecipe(recipe) || isChromeWeldingRecipe(recipe)) {
                toRemove.add(name);
            }
        }

        for (ResourceLocation name : toRemove) {
            registry.remove(name);
        }
    }

    private static boolean isBismuthAnvilRecipe(AnvilRecipe recipe) {
        return containsOutput(recipe, "bismuth");
    }

    private static boolean isBismuthWeldingRecipe(WeldingRecipe recipe) {
        return containsOutput(recipe, "bismuth");
    }

    private static boolean isChromeAnvilRecipe(AnvilRecipe recipe) {
        return containsOutput(recipe, "nickel");
    }

    private static boolean isChromeWeldingRecipe(WeldingRecipe recipe) {
        return containsOutput(recipe, "nickel");
    }

    private static boolean isSteelRodAnvilRecipe(AnvilRecipe recipe) {
        return containsOutput(recipe, "steel") && containsOutput(recipe, "rod");
    }

    private static boolean isSteelIngotAnvilRecipe(AnvilRecipe recipe) {
        String outputName = getOutputRegistryName(recipe);
        if (outputName == null) return false;

        return outputName.contains("steel")
                && outputName.contains("ingot")
                && !outputName.contains("blue")
                && !outputName.contains("red")
                && !outputName.contains("black")
                && !outputName.contains("carbon");
    }

    private static boolean containsOutput(AnvilRecipe recipe, String substring) {
        String outputName = getOutputRegistryName(recipe);
        return outputName != null && outputName.contains(substring);
    }

    private static boolean containsOutput(WeldingRecipe recipe, String substring) {
        String outputName = getOutputRegistryName(recipe);
        return outputName != null && outputName.contains(substring);
    }

    private static String getOutputRegistryName(AnvilRecipe recipe) {
        if (recipe == null || recipe.getOutputs().isEmpty()) return null;
        ItemStack output = recipe.getOutputs().get(0);
        if (output.isEmpty() || output.getItem().getRegistryName() == null) return null;
        return output.getItem().getRegistryName().toString();
    }

    private static String getOutputRegistryName(WeldingRecipe recipe) {
        if (recipe == null || recipe.getOutputs().isEmpty()) return null;
        ItemStack output = recipe.getOutputs().get(0);
        if (output.isEmpty() || output.getItem().getRegistryName() == null) return null;
        return output.getItem().getRegistryName().toString();
    }
}