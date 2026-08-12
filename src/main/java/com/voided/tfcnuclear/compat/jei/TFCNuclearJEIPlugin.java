package com.voided.tfcnuclear.compat.jei;

import com.voided.tfcnuclear.inventory.items.ItemSlagBase;
import com.voided.tfcnuclear.inventory.items.ModItems;
import com.voided.tfcnuclear.inventory.recipes.vanilla.HammerRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@JEIPlugin
public class TFCNuclearJEIPlugin implements IModPlugin {

    private static final Set<String> HIDDEN_ITEM_IDS = new HashSet<>();
    private static final Map<String, OreData> ORE_MAPPING = new HashMap<>();

    static {
        ORE_MAPPING.put("tfc:ore/hematite", new OreData(ModItems.HEMATITE_SLAG, new int[]{0, 1, 2}, new int[]{25, 15, 35}));
        ORE_MAPPING.put("tfc:ore/small/hematite", new OreData(ModItems.HEMATITE_SLAG, new int[]{0}, new int[]{10}));

        ORE_MAPPING.put("tfc:ore/limonite", new OreData(ModItems.LIMONITE_SLAG, new int[]{0, 1, 2}, new int[]{25, 15, 35}));
        ORE_MAPPING.put("tfc:ore/small/limonite", new OreData(ModItems.LIMONITE_SLAG, new int[]{0}, new int[]{10}));

        ORE_MAPPING.put("tfc:ore/magnetite", new OreData(ModItems.MAGNETITE_SLAG, new int[]{0, 1, 2}, new int[]{25, 15, 35}));
        ORE_MAPPING.put("tfc:ore/small/magnetite", new OreData(ModItems.MAGNETITE_SLAG, new int[]{0}, new int[]{10}));

        ORE_MAPPING.put("tfc:ore/native_gold", new OreData(ModItems.GOLD_SLAG, new int[]{0, 1, 2}, new int[]{25, 15, 35}));
        ORE_MAPPING.put("tfc:ore/small/native_gold", new OreData(ModItems.GOLD_SLAG, new int[]{0}, new int[]{10}));

        ORE_MAPPING.put("tfc:ore/galena", new OreData(ModItems.GALENA_SLAG, new int[]{0, 1, 2}, new int[]{25, 15, 35}));
        ORE_MAPPING.put("tfc:ore/small/galena", new OreData(ModItems.GALENA_SLAG, new int[]{0}, new int[]{10}));

        ORE_MAPPING.put("tfc:ore/native_copper", new OreData(ModItems.COPPER_SLAG, new int[]{0, 1, 2}, new int[]{25, 15, 35}));
        ORE_MAPPING.put("tfc:ore/small/native_copper", new OreData(ModItems.COPPER_SLAG, new int[]{0}, new int[]{10}));

        ORE_MAPPING.put("tfc:ore/garnierite", new OreData(ModItems.CHROME_SLAG, new int[]{0, 1, 2}, new int[]{25, 15, 35}));
        ORE_MAPPING.put("tfc:ore/small/garnierite", new OreData(ModItems.CHROME_SLAG, new int[]{0}, new int[]{10}));

        ORE_MAPPING.put("tfc:ore/sphalerite", new OreData(ModItems.ZINC_SLAG, new int[]{0, 1, 2}, new int[]{25, 15, 35}));
        ORE_MAPPING.put("tfc:ore/small/sphalerite", new OreData(ModItems.ZINC_SLAG, new int[]{0}, new int[]{10}));
    }

    private ItemStack getOreStack(String oreId, int meta) {
        Item item = Item.getByNameOrId(oreId);
        if (item != null) {
            return new ItemStack(item, 1, meta);
        }

        for (String oreName : OreDictionary.getOreNames()) {
            List<ItemStack> ores = OreDictionary.getOres(oreName);
            for (ItemStack ore : ores) {
                if (ore.getItem().getRegistryName() != null) {
                    String id = ore.getItem().getRegistryName().toString();
                    if (oreId.equals(id) && ore.getMetadata() == meta) {
                        return ore.copy();
                    }
                }
            }
        }

        return ItemStack.EMPTY;
    }

    private void hideItems(IModRegistry registry) {
        IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();

        for (String itemId : HIDDEN_ITEM_IDS) {
            Item item = Item.getByNameOrId(itemId);
            if (item != null) {
                ItemStack stack = new ItemStack(item);
                if (!stack.isEmpty()) {
                    blacklist.addIngredientToBlacklist(stack);
                }
            }
        }
    }

    @Override
    public void register(IModRegistry registry) {
        hideItems(registry);
        registerOreCombineRecipes(registry);
        registerHammerRecipes(registry);
    }

    private void registerHammerRecipes(IModRegistry registry) {
        List<HammerRecipe> hammerRecipes = new ArrayList<>();

        for (IRecipe recipe : ForgeRegistries.RECIPES) {
            if (recipe instanceof HammerRecipe) {
                hammerRecipes.add((HammerRecipe) recipe);
            }
        }

        if (!hammerRecipes.isEmpty()) {
            List<IRecipeWrapper> wrappers = new ArrayList<>();
            for (HammerRecipe recipe : hammerRecipes) {
                wrappers.add(new HammerRecipeWrapper(recipe.getInputOreDict(), recipe.getRecipeOutput()));
            }

            registry.addRecipes(wrappers, VanillaRecipeCategoryUid.CRAFTING);
        }
    }

    private void registerOreCombineRecipes(IModRegistry registry) {
        List<OreCombineRecipeWrapper> recipes = new ArrayList<>();

        for (Map.Entry<String, OreData> entry : ORE_MAPPING.entrySet()) {
            String oreId = entry.getKey();
            OreData oreData = entry.getValue();

            for (int i = 0; i < oreData.metas.length; i++) {
                int meta = oreData.metas[i];
                int amount = oreData.amounts[i];

                ItemStack oreStack = getOreStack(oreId, meta);
                if (oreStack == null || oreStack.isEmpty()) {
                    continue;
                }

                ItemStack slagResult = new ItemStack(oreData.slagItem);
                ItemSlagBase.setAmount(slagResult, amount);

                recipes.add(new OreCombineRecipeWrapper(oreStack, slagResult));
            }
        }

        if (!recipes.isEmpty()) {
            registry.addRecipes(recipes, VanillaRecipeCategoryUid.CRAFTING);
        }
    }

    private static class OreData {
        final Item slagItem;
        final int[] metas;
        final int[] amounts;

        OreData(Item slagItem, int[] metas, int[] amounts) {
            this.slagItem = slagItem;
            this.metas = metas;
            this.amounts = amounts;
        }
    }
}