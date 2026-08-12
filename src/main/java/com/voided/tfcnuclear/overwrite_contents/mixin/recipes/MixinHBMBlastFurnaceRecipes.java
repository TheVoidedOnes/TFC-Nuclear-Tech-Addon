package com.voided.tfcnuclear.overwrite_contents.mixin.recipes;

import com.hbm.inventory.material.Mats;
import com.hbm.inventory.recipes.BlastFurnaceRecipesNT;
import com.hbm.inventory.recipes.BlastFurnaceRecipe;
import com.hbm.inventory.RecipesCommon;
import com.hbm.items.ModItems;
import com.voided.tfcnuclear.compat.hbm.SlagAStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlastFurnaceRecipesNT.class)
public class MixinHBMBlastFurnaceRecipes {

    @Inject(method = "registerDefaults", at = @At("TAIL"), remap = false)
    public void modifyRecipes(CallbackInfo ci) {
        removeRecipes();
        addCustomRecipes();
    }

    private void removeRecipes() {
        BlastFurnaceRecipesNT instance = BlastFurnaceRecipesNT.INSTANCE;

        String[] recipesToRemove = {
                "blast.steelFromIngot",
                "blast.steelFromDust",
                "blast.steelFromOre",
                "blast.steelWithFlux",
                "blast.mingrade",
                "blast.mingradeIngot",
                "blast.mingradeDust",
                "blast.mingradeCursed",
                "blast.mingradeOre",
                "blast.firebrick",
                "blast.firebrickLimestone"
        };

        for (String recipeName : recipesToRemove) {
            instance.removeRecipeByName(recipeName);
        }
    }

    private void addCustomRecipes() {
        BlastFurnaceRecipesNT instance = BlastFurnaceRecipesNT.INSTANCE;

        instance.register((BlastFurnaceRecipe) new BlastFurnaceRecipe("blast.bloom_hematite_1")
                .setDuration(600)
                .inputItems(
                        new SlagAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.HEMATITE_SLAG, 1), 100),
                        new RecipesCommon.OreDictStack("sand", 1)
                )
                .outputItems(createBloomWithAmount("tfc:bloom/unrefined", 100)));

        instance.register((BlastFurnaceRecipe) new BlastFurnaceRecipe("blast.bloom_magnetite_1")
                .setDuration(600)
                .inputItems(
                        new SlagAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.MAGNETITE_SLAG, 1), 100),
                        new RecipesCommon.OreDictStack("sand", 1)
                )
                .outputItems(createBloomWithAmount("tfc:bloom/unrefined", 100)));

        instance.register((BlastFurnaceRecipe) new BlastFurnaceRecipe("blast.bloom_limonite_1")
                .setDuration(600)
                .inputItems(
                        new SlagAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.LIMONITE_SLAG, 1), 100),
                        new RecipesCommon.OreDictStack("sand", 1)
                )
                .outputItems(createBloomWithAmount("tfc:bloom/unrefined", 100)));

        instance.register((BlastFurnaceRecipe) new BlastFurnaceRecipe("blast.bloom_hematite_2")
                .setDuration(1000)
                .inputItems(
                        new SlagAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.HEMATITE_SLAG, 1), 200),
                        new RecipesCommon.OreDictStack("sand", 2)
                )
                .outputItems(createBloomWithAmount("tfc:bloom/unrefined", 200)));

        instance.register((BlastFurnaceRecipe) new BlastFurnaceRecipe("blast.bloom_magnetite_2")
                .setDuration(1000)
                .inputItems(
                        new SlagAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.MAGNETITE_SLAG, 1), 200),
                        new RecipesCommon.OreDictStack("sand", 2)
                )
                .outputItems(createBloomWithAmount("tfc:bloom/unrefined", 200)));

        instance.register((BlastFurnaceRecipe) new BlastFurnaceRecipe("blast.bloom_limonite_2")
                .setDuration(1000)
                .inputItems(
                        new SlagAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.LIMONITE_SLAG, 1), 200),
                        new RecipesCommon.OreDictStack("sand", 2)
                )
                .outputItems(createBloomWithAmount("tfc:bloom/unrefined", 200)));

        instance.register((BlastFurnaceRecipe) new BlastFurnaceRecipe("blast.bloom_hematite_3")
                .setDuration(1400)
                .inputItems(
                        new SlagAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.HEMATITE_SLAG, 1), 300),
                        new RecipesCommon.OreDictStack("sand", 3)
                )
                .outputItems(createBloomWithAmount("tfc:bloom/unrefined", 300)));

        instance.register((BlastFurnaceRecipe) new BlastFurnaceRecipe("blast.bloom_magnetite_3")
                .setDuration(1400)
                .inputItems(
                        new SlagAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.MAGNETITE_SLAG, 1), 300),
                        new RecipesCommon.OreDictStack("sand", 3)
                )
                .outputItems(createBloomWithAmount("tfc:bloom/unrefined", 300)));

        instance.register((BlastFurnaceRecipe) new BlastFurnaceRecipe("blast.bloom_limonite_3")
                .setDuration(1400)
                .inputItems(
                        new SlagAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.LIMONITE_SLAG, 1), 300),
                        new RecipesCommon.OreDictStack("sand", 3)
                )
                .outputItems(createBloomWithAmount("tfc:bloom/unrefined", 300)));

        instance.register((BlastFurnaceRecipe) new BlastFurnaceRecipe("blast.bloom_hematite_4")
                .setDuration(1800)
                .inputItems(
                        new SlagAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.HEMATITE_SLAG, 1), 400),
                        new RecipesCommon.OreDictStack("sand", 4)
                )
                .outputItems(createBloomWithAmount("tfc:bloom/unrefined", 400)));

        instance.register((BlastFurnaceRecipe) new BlastFurnaceRecipe("blast.bloom_magnetite_4")
                .setDuration(1800)
                .inputItems(
                        new SlagAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.MAGNETITE_SLAG, 1), 400),
                        new RecipesCommon.OreDictStack("sand", 4)
                )
                .outputItems(createBloomWithAmount("tfc:bloom/unrefined", 400)));

        instance.register((BlastFurnaceRecipe) new BlastFurnaceRecipe("blast.bloom_limonite_4")
                .setDuration(1800)
                .inputItems(
                        new SlagAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.LIMONITE_SLAG, 1), 400),
                        new RecipesCommon.OreDictStack("sand", 4)
                )
                .outputItems(createBloomWithAmount("tfc:bloom/unrefined", 400)));

        instance.register((BlastFurnaceRecipe) new BlastFurnaceRecipe("blast.bloom_iron_ingot")
                .setDuration(500)
                .inputItems(
                        new RecipesCommon.OreDictStack("ingotIron", 1),
                        new RecipesCommon.OreDictStack("sand", 1)
                )
                .outputItems(createBloomWithAmount("tfc:bloom/unrefined", 100)));

        instance.register((BlastFurnaceRecipe) new BlastFurnaceRecipe("blast.bloom_iron_dust")
                .setDuration(500)
                .inputItems(
                        new RecipesCommon.OreDictStack("dustIron", 1),
                        new RecipesCommon.OreDictStack("sand", 1)
                )
                .outputItems(createBloomWithAmount("tfc:bloom/unrefined", 100)));

        instance.register((BlastFurnaceRecipe) new BlastFurnaceRecipe("blast.tfc_pig_iron_ingot")
                .setDuration(700)
                .inputItems(
                        new RecipesCommon.OreDictStack("ingotIron", 1),
                        new RecipesCommon.ComparableStack(Item.getByNameOrId("hbm:powder_flux"), 1)
                )
                .outputItems(
                        new ItemStack(Item.getByNameOrId("tfc:metal/ingot/pig_iron"), 1),
                        new ItemStack(ModItems.ingot_raw, 1, Mats.MAT_SLAG.id)
                ));

        instance.register((BlastFurnaceRecipe) new BlastFurnaceRecipe("blast.tfc_pig_iron_dust")
                .setDuration(700)
                .inputItems(
                        new RecipesCommon.OreDictStack("dustIron", 1),
                        new RecipesCommon.ComparableStack(Item.getByNameOrId("hbm:powder_flux"), 1)
                )
                .outputItems(
                        new ItemStack(Item.getByNameOrId("tfc:metal/ingot/pig_iron"), 1),
                        new ItemStack(ModItems.ingot_raw, 1, Mats.MAT_SLAG.id)
                ));

        instance.register((BlastFurnaceRecipe) new BlastFurnaceRecipe("blast.pig_iron_hematite_1")
                .setDuration(800)
                .inputItems(
                        new SlagAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.HEMATITE_SLAG, 1), 100),
                        new RecipesCommon.ComparableStack(Item.getByNameOrId("hbm:powder_flux"), 1)
                )
                .outputItems(
                        new ItemStack(Item.getByNameOrId("tfc:metal/ingot/pig_iron"), 1),
                        new ItemStack(ModItems.ingot_raw, 1, Mats.MAT_SLAG.id)
                ));

        instance.register((BlastFurnaceRecipe) new BlastFurnaceRecipe("blast.pig_iron_magnetite_1")
                .setDuration(800)
                .inputItems(
                        new SlagAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.MAGNETITE_SLAG, 1), 100),
                        new RecipesCommon.ComparableStack(Item.getByNameOrId("hbm:powder_flux"), 1)
                )
                .outputItems(
                        new ItemStack(Item.getByNameOrId("tfc:metal/ingot/pig_iron"), 1),
                        new ItemStack(ModItems.ingot_raw, 1, Mats.MAT_SLAG.id)
                ));

        instance.register((BlastFurnaceRecipe) new BlastFurnaceRecipe("blast.pig_iron_limonite_1")
                .setDuration(800)
                .inputItems(
                        new SlagAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.LIMONITE_SLAG, 1), 100),
                        new RecipesCommon.ComparableStack(Item.getByNameOrId("hbm:powder_flux"), 1)
                )
                .outputItems(
                        new ItemStack(Item.getByNameOrId("tfc:metal/ingot/pig_iron"), 1),
                        new ItemStack(ModItems.ingot_raw, 1, Mats.MAT_SLAG.id)
                ));

        instance.register((BlastFurnaceRecipe) new BlastFurnaceRecipe("blast.pig_iron_hematite_2")
                .setDuration(1400)
                .inputItems(
                        new SlagAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.HEMATITE_SLAG, 1), 200),
                        new RecipesCommon.ComparableStack(Item.getByNameOrId("hbm:powder_flux"), 2)
                )
                .outputItems(
                        new ItemStack(Item.getByNameOrId("tfc:metal/ingot/pig_iron"), 2),
                        new ItemStack(ModItems.ingot_raw, 2, Mats.MAT_SLAG.id)
                ));

        instance.register((BlastFurnaceRecipe) new BlastFurnaceRecipe("blast.pig_iron_magnetite_2")
                .setDuration(1400)
                .inputItems(
                        new SlagAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.MAGNETITE_SLAG, 1), 200),
                        new RecipesCommon.ComparableStack(Item.getByNameOrId("hbm:powder_flux"), 2)
                )
                .outputItems(
                        new ItemStack(Item.getByNameOrId("tfc:metal/ingot/pig_iron"), 2),
                        new ItemStack(ModItems.ingot_raw, 2, Mats.MAT_SLAG.id)
                ));

        instance.register((BlastFurnaceRecipe) new BlastFurnaceRecipe("blast.pig_iron_limonite_2")
                .setDuration(1400)
                .inputItems(
                        new SlagAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.LIMONITE_SLAG, 1), 200),
                        new RecipesCommon.ComparableStack(Item.getByNameOrId("hbm:powder_flux"), 2)
                )
                .outputItems(
                        new ItemStack(Item.getByNameOrId("tfc:metal/ingot/pig_iron"), 2),
                        new ItemStack(ModItems.ingot_raw, 2, Mats.MAT_SLAG.id)
                ));

        instance.register((BlastFurnaceRecipe) new BlastFurnaceRecipe("blast.pig_iron_hematite_3")
                .setDuration(2000)
                .inputItems(
                        new SlagAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.HEMATITE_SLAG, 1), 300),
                        new RecipesCommon.ComparableStack(Item.getByNameOrId("hbm:powder_flux"), 3)
                )
                .outputItems(
                        new ItemStack(Item.getByNameOrId("tfc:metal/ingot/pig_iron"), 3),
                        new ItemStack(ModItems.ingot_raw, 3, Mats.MAT_SLAG.id)
                ));

        instance.register((BlastFurnaceRecipe) new BlastFurnaceRecipe("blast.pig_iron_magnetite_3")
                .setDuration(2000)
                .inputItems(
                        new SlagAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.MAGNETITE_SLAG, 1), 300),
                        new RecipesCommon.ComparableStack(Item.getByNameOrId("hbm:powder_flux"), 3)
                )
                .outputItems(
                        new ItemStack(Item.getByNameOrId("tfc:metal/ingot/pig_iron"), 3),
                        new ItemStack(ModItems.ingot_raw, 3, Mats.MAT_SLAG.id)
                ));

        instance.register((BlastFurnaceRecipe) new BlastFurnaceRecipe("blast.pig_iron_limonite_3")
                .setDuration(2000)
                .inputItems(
                        new SlagAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.LIMONITE_SLAG, 1), 300),
                        new RecipesCommon.ComparableStack(Item.getByNameOrId("hbm:powder_flux"), 3)
                )
                .outputItems(
                        new ItemStack(Item.getByNameOrId("tfc:metal/ingot/pig_iron"), 3),
                        new ItemStack(ModItems.ingot_raw, 3, Mats.MAT_SLAG.id)
                ));

        instance.register((BlastFurnaceRecipe) new BlastFurnaceRecipe("blast.pig_iron_hematite_4")
                .setDuration(2600)
                .inputItems(
                        new SlagAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.HEMATITE_SLAG, 1), 400),
                        new RecipesCommon.ComparableStack(Item.getByNameOrId("hbm:powder_flux"), 4)
                )
                .outputItems(
                        new ItemStack(Item.getByNameOrId("tfc:metal/ingot/pig_iron"), 4),
                        new ItemStack(ModItems.ingot_raw, 4, Mats.MAT_SLAG.id)
                ));

        instance.register((BlastFurnaceRecipe) new BlastFurnaceRecipe("blast.pig_iron_magnetite_4")
                .setDuration(2600)
                .inputItems(
                        new SlagAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.MAGNETITE_SLAG, 1), 400),
                        new RecipesCommon.ComparableStack(Item.getByNameOrId("hbm:powder_flux"), 4)
                )
                .outputItems(
                        new ItemStack(Item.getByNameOrId("tfc:metal/ingot/pig_iron"), 4),
                        new ItemStack(ModItems.ingot_raw, 4, Mats.MAT_SLAG.id)
                ));

        instance.register((BlastFurnaceRecipe) new BlastFurnaceRecipe("blast.pig_iron_limonite_4")
                .setDuration(2600)
                .inputItems(
                        new SlagAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.LIMONITE_SLAG, 1), 400),
                        new RecipesCommon.ComparableStack(Item.getByNameOrId("hbm:powder_flux"), 4)
                )
                .outputItems(
                        new ItemStack(Item.getByNameOrId("tfc:metal/ingot/pig_iron"), 4),
                        new ItemStack(ModItems.ingot_raw, 4, Mats.MAT_SLAG.id)
                ));
    }

    private ItemStack createBloomWithAmount(String itemId, int amount) {
        ItemStack stack = new ItemStack(Item.getByNameOrId(itemId), 1);
        net.dries007.tfc.api.capability.forge.IForgeableMeasurableMetal cap =
                (net.dries007.tfc.api.capability.forge.IForgeableMeasurableMetal)
                        stack.getCapability(net.dries007.tfc.api.capability.forge.CapabilityForgeable.FORGEABLE_CAPABILITY, null);
        if (cap != null) {
            cap.setMetal(net.dries007.tfc.api.types.Metal.WROUGHT_IRON);
            cap.setMetalAmount(amount);
        }
        return stack;
    }
}