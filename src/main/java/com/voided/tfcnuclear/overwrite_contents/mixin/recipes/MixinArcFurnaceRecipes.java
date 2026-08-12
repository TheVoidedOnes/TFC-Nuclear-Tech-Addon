package com.voided.tfcnuclear.overwrite_contents.mixin.recipes;

import com.hbm.inventory.RecipesCommon;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.recipes.ArcFurnaceRecipes;
import com.hbm.inventory.recipes.ArcFurnaceRecipes.ArcFurnaceRecipe;
import com.hbm.util.Tuple;
import com.voided.tfcnuclear.compat.hbm.BloomArcFurnaceStack;
import com.voided.tfcnuclear.inventory.items.ModItems;
import com.voided.tfcnuclear.inventory.material.TFCNuclearMats;
import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.capability.forge.IForgeableMeasurableMetal;
import net.dries007.tfc.api.types.Metal;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

@Mixin(value = ArcFurnaceRecipes.class, remap = false)
public abstract class MixinArcFurnaceRecipes {

    @Shadow
    private static List<Tuple.Pair<RecipesCommon.AStack, ArcFurnaceRecipe>> recipeList;

    @Shadow
    private static HashSet<RecipesCommon.ComparableStack> occupiedSolid;

    @Shadow
    private static HashSet<RecipesCommon.ComparableStack> occupiedLiquid;

    @Shadow
    private static void register(RecipesCommon.AStack input, ArcFurnaceRecipe output) {}

    @Inject(method = "registerDefaults", at = @At("TAIL"), remap = false)
    private void onRegisterDefaults(CallbackInfo ci) {
        removeByItemStack(new ItemStack(Items.BRICK));
        removeByOreDict("oreGold");
        removeByOreDict("oreIron");
        removeByOreDict("oreCopper");
        removeByOreDict("oreLead");
        removeByOreDict("oreZinc");
        removeByOreDict("oreCobalt");
        removeByOreDict("ingotPhosphateCharge");

        addCustomRecipes();
        addBloomRecipes();
    }

    private void addCustomRecipes() {
        RecipesCommon.ComparableStack phosphateCheck = new RecipesCommon.ComparableStack(ModItems.INGOT_PHOSPHATE_CHARGE, 1);
        if (occupiedLiquid != null && occupiedLiquid.contains(phosphateCheck)) {
            occupiedLiquid.remove(phosphateCheck);
        }

        register(
                new RecipesCommon.ComparableStack(Item.getByNameOrId("tfc:ceramics/unfired/clay_brick")),
                new ArcFurnaceRecipe().solid(new ItemStack(Items.BRICK)));

        register(
                new RecipesCommon.ComparableStack(Item.getByNameOrId("tfc:ceramics/unfired/fire_brick")),
                new ArcFurnaceRecipe().solid(new ItemStack(Item.getByNameOrId("hbm:ingot_firebrick"))));

        register(
                new RecipesCommon.ComparableStack(Item.getByNameOrId("minecraft:clay_ball")),
                new ArcFurnaceRecipe().solid(new ItemStack(Item.getByNameOrId("hbm:ball_fireclay"))));

        register(
                new RecipesCommon.ComparableStack(ModItems.INGOT_PHOSPHATE_CHARGE, 1),
                new ArcFurnaceRecipe().fluid(new Mats.MaterialStack(TFCNuclearMats.MAT_PHOSPHORUS_TFC, MaterialShapes.INGOT.q(1))));
    }

    private void addBloomRecipes() {
        if (!Loader.isModLoaded("tfc")) {
            return;
        }

        try {
            Item rawBloom = Item.getByNameOrId("tfc:bloom/unrefined");
            Item refinedBloom = Item.getByNameOrId("tfc:bloom/refined");

            if (rawBloom == null || refinedBloom == null) {
                return;
            }

            cleanExistingBloomRecipes();

            addRawToRefinedRecipe(rawBloom, refinedBloom, 100);
            addRawToRefinedRecipe(rawBloom, refinedBloom, 200);
            addRawToRefinedRecipe(rawBloom, refinedBloom, 300);
            addRawToRefinedRecipe(rawBloom, refinedBloom, 400);

            addRefinedToIngotRecipe(refinedBloom, 400, 4);
            addRefinedToIngotRecipe(refinedBloom, 300, 3);
            addRefinedToIngotRecipe(refinedBloom, 200, 2);
            addRefinedToIngotRecipe(refinedBloom, 100, 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cleanExistingBloomRecipes() {
        Iterator<Tuple.Pair<RecipesCommon.AStack, ArcFurnaceRecipe>> iterator = recipeList.iterator();
        while (iterator.hasNext()) {
            Tuple.Pair<RecipesCommon.AStack, ArcFurnaceRecipe> entry = iterator.next();
            RecipesCommon.AStack input = entry.getKey();

            if (input instanceof BloomArcFurnaceStack) {
                iterator.remove();
            } else if (input instanceof RecipesCommon.ComparableStack) {
                RecipesCommon.ComparableStack comp = (RecipesCommon.ComparableStack) input;
                if (comp.item != null) {
                    String name = comp.item.getRegistryName().toString();
                    if (name.equals("tfc:bloom/refined") || name.equals("tfc:bloom/unrefined")) {
                        iterator.remove();
                    }
                }
            }
        }
    }

    private void addRawToRefinedRecipe(Item inputItem, Item outputItem, int amount) {
        ItemStack inputStack = new ItemStack(inputItem, 1);
        IForgeableMeasurableMetal capIn = (IForgeableMeasurableMetal)
                inputStack.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
        if (capIn != null) {
            capIn.setMetal(Metal.WROUGHT_IRON);
            capIn.setMetalAmount(amount);
        }

        ItemStack outputStack = new ItemStack(outputItem, 1);
        IForgeableMeasurableMetal capOut = (IForgeableMeasurableMetal)
                outputStack.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
        if (capOut != null) {
            capOut.setMetal(Metal.WROUGHT_IRON);
            capOut.setMetalAmount(amount);
        }

        BloomArcFurnaceStack input = new BloomArcFurnaceStack(inputStack);
        ArcFurnaceRecipe recipe = new ArcFurnaceRecipe().solid(outputStack);

        recipeList.add(new Tuple.Pair<>(input, recipe));

        if (occupiedSolid != null) {
            occupiedSolid.add(new RecipesCommon.ComparableStack(inputItem));
        }
    }

    private void addRefinedToIngotRecipe(Item inputItem, int bloomAmount, int ingotCount) {
        ItemStack inputStack = new ItemStack(inputItem, 1);
        IForgeableMeasurableMetal cap = (IForgeableMeasurableMetal)
                inputStack.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
        if (cap != null) {
            cap.setMetal(Metal.WROUGHT_IRON);
            cap.setMetalAmount(bloomAmount);
        }

        BloomArcFurnaceStack input = new BloomArcFurnaceStack(inputStack);
        ItemStack output = new ItemStack(Item.getByNameOrId("tfc:metal/ingot/wrought_iron"), ingotCount);
        ArcFurnaceRecipe recipe = new ArcFurnaceRecipe().solid(output);

        recipeList.add(new Tuple.Pair<>(input, recipe));

        if (occupiedSolid != null) {
            occupiedSolid.add(new RecipesCommon.ComparableStack(inputItem));
        }
    }

    private void removeByOreDict(String dictKey) {
        List<Tuple.Pair<RecipesCommon.AStack, ArcFurnaceRecipe>> toRemove = new ArrayList<>();

        for (Tuple.Pair<RecipesCommon.AStack, ArcFurnaceRecipe> entry : recipeList) {
            RecipesCommon.AStack key = entry.getKey();
            if (key instanceof RecipesCommon.OreDictStack) {
                RecipesCommon.OreDictStack oreDict = (RecipesCommon.OreDictStack) key;
                if (oreDict.name.equals(dictKey)) {
                    toRemove.add(entry);
                }
            }
        }

        for (Tuple.Pair<RecipesCommon.AStack, ArcFurnaceRecipe> entry : toRemove) {
            recipeList.remove(entry);
        }
    }

    private void removeByComparableStack(RecipesCommon.ComparableStack stackToRemove) {
        List<Tuple.Pair<RecipesCommon.AStack, ArcFurnaceRecipe>> toRemove = new ArrayList<>();

        for (Tuple.Pair<RecipesCommon.AStack, ArcFurnaceRecipe> entry : recipeList) {
            RecipesCommon.AStack key = entry.getKey();
            if (key instanceof RecipesCommon.ComparableStack) {
                RecipesCommon.ComparableStack stack = (RecipesCommon.ComparableStack) key;
                if (stack.item != null && stackToRemove.item != null) {
                    String itemId1 = Item.REGISTRY.getNameForObject(stack.item).toString();
                    String itemId2 = Item.REGISTRY.getNameForObject(stackToRemove.item).toString();

                    if (itemId1.equals(itemId2) && stack.meta == stackToRemove.meta) {
                        toRemove.add(entry);
                    }
                }
            }
        }

        for (Tuple.Pair<RecipesCommon.AStack, ArcFurnaceRecipe> entry : toRemove) {
            recipeList.remove(entry);
        }
    }

    private void removeByItemStack(ItemStack stackToRemove) {
        removeByComparableStack(new RecipesCommon.ComparableStack(stackToRemove));
    }
}