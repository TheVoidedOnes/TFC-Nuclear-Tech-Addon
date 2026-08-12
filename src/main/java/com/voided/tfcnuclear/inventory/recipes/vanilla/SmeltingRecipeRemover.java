package com.voided.tfcnuclear.inventory.recipes.vanilla;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import java.util.Iterator;
import java.util.Map;

public class SmeltingRecipeRemover {

    private static boolean removed = false;

    public static void remove() {
        if (removed) {
            return;
        }

        FurnaceRecipes furnaceRecipes = FurnaceRecipes.instance();
        Map<ItemStack, ItemStack> smeltingList = furnaceRecipes.getSmeltingList();

        Iterator<Map.Entry<ItemStack, ItemStack>> iterator = smeltingList.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<ItemStack, ItemStack> entry = iterator.next();
            ItemStack key = entry.getKey();
            Item item = key.getItem();

            if (shouldRemove(item)) {
                iterator.remove();
                removeExperience(furnaceRecipes, key);
            }
        }

        removed = true;
    }

    private static boolean shouldRemove(Item item) {
        if (item == null) {
            return false;
        }

        if (item == com.hbm.items.ModItems.crystal_trixite) {
            return true;
        }

        if (item == Item.getItemFromBlock(Blocks.IRON_ORE)) {
            return true;
        }

        if (item == Item.getItemFromBlock(com.hbm.blocks.ModBlocks.ore_gneiss_iron) ||
                item == Item.getItemFromBlock(com.hbm.blocks.ModBlocks.ore_gneiss_copper) ||
                item == Item.getItemFromBlock(com.hbm.blocks.ModBlocks.ore_copper) ||
                item == Item.getItemFromBlock(com.hbm.blocks.ModBlocks.ore_nether_cobalt) ||
                item == Item.getItemFromBlock(com.hbm.blocks.ModBlocks.ore_aluminium)) {
            return true;
        }

        return false;
    }

    private static void removeExperience(FurnaceRecipes furnaceRecipes, ItemStack key) {
        try {
            java.lang.reflect.Field field = FurnaceRecipes.class.getDeclaredField("experienceList");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<ItemStack, Float> experienceMap = (Map<ItemStack, Float>) field.get(furnaceRecipes);
            if (experienceMap != null) {
                experienceMap.remove(key);
            }
        } catch (Exception ignored) {
        }
    }

    public static void removeRecipeForBlock(Block block) {
        if (block == null) {
            return;
        }

        FurnaceRecipes furnaceRecipes = FurnaceRecipes.instance();
        Map<ItemStack, ItemStack> smeltingList = furnaceRecipes.getSmeltingList();

        ItemStack targetStack = new ItemStack(block);

        Iterator<Map.Entry<ItemStack, ItemStack>> iterator = smeltingList.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<ItemStack, ItemStack> entry = iterator.next();
            ItemStack key = entry.getKey();

            if (key.getItem() == Item.getItemFromBlock(block) && key.getMetadata() == targetStack.getMetadata()) {
                iterator.remove();
                removeExperience(furnaceRecipes, key);
                break;
            }
        }
    }

    public static void removeRecipeForItem(Item item) {
        if (item == null) {
            return;
        }

        FurnaceRecipes furnaceRecipes = FurnaceRecipes.instance();
        Map<ItemStack, ItemStack> smeltingList = furnaceRecipes.getSmeltingList();

        Iterator<Map.Entry<ItemStack, ItemStack>> iterator = smeltingList.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<ItemStack, ItemStack> entry = iterator.next();
            ItemStack key = entry.getKey();

            if (key.getItem() == item) {
                iterator.remove();
                removeExperience(furnaceRecipes, key);
                break;
            }
        }
    }
}