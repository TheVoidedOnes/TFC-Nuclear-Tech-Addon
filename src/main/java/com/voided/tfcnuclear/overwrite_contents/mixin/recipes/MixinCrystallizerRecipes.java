package com.voided.tfcnuclear.overwrite_contents.mixin.recipes;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.fluid.FluidStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.CrystallizerRecipes;
import com.hbm.inventory.recipes.CrystallizerRecipes.CrystallizerRecipe;
import com.hbm.items.ModItems;
import com.hbm.util.Tuple;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(value = CrystallizerRecipes.class, remap = false)
public abstract class MixinCrystallizerRecipes {

    @Shadow
    protected static void registerRecipe(Object input, CrystallizerRecipe recipe, FluidStack stack) {}

    @Shadow
    protected static void registerRecipe(Object input, CrystallizerRecipe recipe) {}

    @Shadow
    private static HashMap<Tuple.Pair<Object, FluidType>, CrystallizerRecipe> recipes;

    @Shadow
    private static HashMap<Object, Integer> amounts;

    @Inject(method = "registerDefaults", at = @At("TAIL"), remap = false)
    private void onRegisterDefaults(CallbackInfo ci) {
        removeVanillaRecipes();
        addCustomRecipes();
    }

    private void removeVanillaRecipes() {
        String[] vanillaOres = {
                "oreCoal", "oreIron", "oreGold", "oreRedstone", "oreLapis",
                "oreDiamond", "oreEmerald", "cobblestone"
        };

        String[] hbmOres = {
                "oreSaltpeter", "oreSulfur", "oreCopper", "oreLead", "oreAluminum", "oreTrixite"
        };

        for (String ore : vanillaOres) {
            removeByOreDict(ore);
        }
        for (String ore : hbmOres) {
            removeByOreDict(ore);
        }

        removeByOutput("minecraft:diamond");
        removeByOutput("hbm:crystal_trixite");
        removeByOutput("minecraft:leather");
        removeByOutput("hbm:crystal_phosphorus");
        removeByOutput("hbm:block_graphite");
        removeByItem(Items.ROTTEN_FLESH);
        removeByItem(ModItems.crystal_copper);
    }

    private void addCustomRecipes() {
        final int baseTime = 600;

        registerRecipe("oreRedstoneTFC", new CrystallizerRecipe(ModItems.crystal_redstone, baseTime).prod(0.05F));
        registerRecipe("oreLapisLazuliTFC", new CrystallizerRecipe(ModItems.crystal_lapis, baseTime).prod(0.05F));
        registerRecipe("oreSaltpeterTFC", new CrystallizerRecipe(ModItems.crystal_niter, baseTime).prod(0.05F));
        registerRecipe("oreCryoliteTFC", new CrystallizerRecipe(ModItems.crystal_aluminium, baseTime).prod(0.05F));
        registerRecipe("oreSulfurTFC", new CrystallizerRecipe(ModItems.crystal_sulfur, baseTime).prod(0.05F));
        registerRecipe("oreBituminousCoal", new CrystallizerRecipe(ModItems.crystal_coal, baseTime).prod(0.05F));
        registerRecipe("cobblestone", new CrystallizerRecipe(ModBlocks.reinforced_stone, baseTime).prod(0.05F));
        registerRecipe("oreApatite", new CrystallizerRecipe(com.voided.tfcnuclear.inventory.items.ModItems.CRYSTAL_APATITE, baseTime).prod(0.05F), new FluidStack(Fluids.SULFURIC_ACID, 500));
    }

    private void removeByOreDict(String dictKey) {
        HashMap<Tuple.Pair<Object, FluidType>, CrystallizerRecipe> toRemove = new HashMap<>();

        for (Map.Entry<Tuple.Pair<Object, FluidType>, CrystallizerRecipe> entry : recipes.entrySet()) {
            Object key = entry.getKey().getKey();
            if (key instanceof String && ((String) key).equals(dictKey)) {
                toRemove.put(entry.getKey(), entry.getValue());
            }
        }

        for (Tuple.Pair<Object, FluidType> key : toRemove.keySet()) {
            recipes.remove(key);
            amounts.remove(key.getKey());
        }
    }

    private void removeByItem(Item item) {
        removeByItem(item, -1);
    }

    private void removeByItem(Item item, int meta) {
        String itemName = Item.REGISTRY.getNameForObject(item).toString();
        removeByItemId(itemName, meta);
    }

    private void removeByItemId(String itemId, int meta) {
        HashMap<Tuple.Pair<Object, FluidType>, CrystallizerRecipe> toRemove = new HashMap<>();

        for (Map.Entry<Tuple.Pair<Object, FluidType>, CrystallizerRecipe> entry : recipes.entrySet()) {
            Object key = entry.getKey().getKey();
            if (key instanceof ComparableStack) {
                ComparableStack stack = (ComparableStack) key;
                String id = Item.REGISTRY.getNameForObject(stack.item).toString();
                if (id.equals(itemId) && (meta == -1 || stack.meta == meta)) {
                    toRemove.put(entry.getKey(), entry.getValue());
                }
            }
        }

        for (Tuple.Pair<Object, FluidType> key : toRemove.keySet()) {
            recipes.remove(key);
            amounts.remove(key.getKey());
        }
    }

    private void removeByOutput(String outputId) {
        HashMap<Tuple.Pair<Object, FluidType>, CrystallizerRecipe> toRemove = new HashMap<>();

        for (Map.Entry<Tuple.Pair<Object, FluidType>, CrystallizerRecipe> entry : recipes.entrySet()) {
            ItemStack output = entry.getValue().output;
            if (output != null && !output.isEmpty()) {
                String id = Item.REGISTRY.getNameForObject(output.getItem()).toString();
                if (id.equals(outputId)) {
                    toRemove.put(entry.getKey(), entry.getValue());
                }
            }
        }

        for (Tuple.Pair<Object, FluidType> key : toRemove.keySet()) {
            recipes.remove(key);
            amounts.remove(key.getKey());
        }
    }
}