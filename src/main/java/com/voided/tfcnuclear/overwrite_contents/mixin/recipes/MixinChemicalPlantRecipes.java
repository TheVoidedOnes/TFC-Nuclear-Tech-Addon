package com.voided.tfcnuclear.overwrite_contents.mixin.recipes;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.inventory.RecipesCommon;
import com.hbm.inventory.fluid.FluidStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.ChemicalPlantRecipes;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.inventory.recipes.loader.GenericRecipes;
import com.hbm.items.ModItems;
import com.voided.tfcnuclear.compat.hbm.SlagChemicalAStack;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.hbm.inventory.OreDictManager.*;

@Mixin(value = ChemicalPlantRecipes.class, remap = false)
public abstract class MixinChemicalPlantRecipes extends GenericRecipes<GenericRecipe> {


    @Inject(method = "registerDefaults", at = @At("RETURN"))
    private void onRegisterDefaults(CallbackInfo ci) {

        ChemicalPlantRecipes.INSTANCE.removeRecipeByName("chem.aggregate");
        ChemicalPlantRecipes.INSTANCE.removeRecipeByName("chem.concrete");
        ChemicalPlantRecipes.INSTANCE.removeRecipeByName("chem.concreteasbestos");
        ChemicalPlantRecipes.INSTANCE.removeRecipeByName("chem.ducrete");
        ChemicalPlantRecipes.INSTANCE.removeRecipeByName("chem.liquidconk");
        ChemicalPlantRecipes.INSTANCE.removeRecipeByName("chem.asphalt");
        ChemicalPlantRecipes.INSTANCE.removeRecipeByName("chem.asphalt");
        ChemicalPlantRecipes.INSTANCE.removeRecipeByName("chem.synleather");
        ChemicalPlantRecipes.INSTANCE.removeRecipeByName("chem.cobble");
        ChemicalPlantRecipes.INSTANCE.removeRecipeByName("chem.stone");

        //OVERWRITE
        this.register(new GenericRecipe("chem.aggregate_new").setupNamed(320, 500).setPools(GenericRecipes.POOL_PREFIX_DISCOVER + ".stone")
                .inputItems(new RecipesCommon.OreDictStack("cobblestone", 16))
                .outputItems(new ItemStack(Blocks.GRAVEL, 8), new ItemStack(Blocks.SAND, 8)));
        this.register(new GenericRecipe("chem.concrete_new").setup(100, 100)
                .inputItems(new RecipesCommon.ComparableStack(ModItems.powder_cement, 1), new RecipesCommon.OreDictStack("gravel", 8), new RecipesCommon.OreDictStack("sand", 8))
                .inputFluids(new FluidStack(Fluids.WATER, 2_000))
                .outputItems(new ItemStack(ModBlocks.concrete_smooth, 16)));
        this.register(new GenericRecipe("chem.concreteasbestos_new").setup(100, 100)
                .inputItems(new RecipesCommon.ComparableStack(ModItems.powder_cement, 4), new RecipesCommon.OreDictStack(ASBESTOS.ingot(), (GeneralConfig.enableLBSM && GeneralConfig.enableLBSMSimpleChemsitry) ? 1 : 4), new RecipesCommon.OreDictStack("sand", 8))
                .inputFluids(new FluidStack(Fluids.WATER, 2_000))
                .outputItems(new ItemStack(ModBlocks.concrete_asbestos, 16)));
        this.register(new GenericRecipe("chem.ducrete_new").setup(150, 100)
                .inputItems(new RecipesCommon.ComparableStack(ModItems.powder_cement, 4), new RecipesCommon.OreDictStack(FERRO.ingot()), new RecipesCommon.OreDictStack("sand", 8))
                .inputFluids(new FluidStack(Fluids.WATER, 2_000))
                .outputItems(new ItemStack(ModBlocks.ducrete_smooth, 8)));
        this.register(new GenericRecipe("chem.liquidconk_new").setup(100, 100)
                .inputItems(new RecipesCommon.ComparableStack(ModItems.powder_cement, 1), new RecipesCommon.OreDictStack("gravel", 8), new RecipesCommon.OreDictStack("sand", 8))
                .inputFluids(new FluidStack(Fluids.WATER, 2_000))
                .outputFluids(new FluidStack(Fluids.CONCRETE, 16_000)));
        this.register(new GenericRecipe("chem.asphalt_new").setup(100, 100)
                .inputItems(new RecipesCommon.OreDictStack("gravel", 2), new RecipesCommon.OreDictStack("sand", 6))
                .inputFluids(new FluidStack(Fluids.BITUMEN, 1_000))
                .outputItems(new ItemStack(ModBlocks.asphalt, 16)));

        //NEW
        this.register(new GenericRecipe("chem.limewater").setup(200, 100)
                .inputItems(new RecipesCommon.OreDictStack("dustFlux", 1))
                .inputFluids(new FluidStack(Fluids.WATER, 500))
                .outputFluids(new FluidStack(Fluids.fromName("LIMEWATER"), 500)));
        this.register(new GenericRecipe("chem.mortar").setup(140, 100)
                .inputItems(new RecipesCommon.OreDictStack("sand", 1))
                .inputFluids(new FluidStack(Fluids.fromName("LIMEWATER"), 100))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:mortar"), 16)));
        this.register(new GenericRecipe("chem.glue").setup(160, 100)
                .inputItems(new RecipesCommon.ComparableStack(Items.DYE, 1, 15))
                .inputFluids(new FluidStack(Fluids.fromName("LIMEWATER"), 500))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:glue"), 1)));
        this.register(new GenericRecipe("chem.AACS").setup(160, 100)
                .inputItems(new RecipesCommon.ComparableStack(Items.CLAY_BALL, 1),
                            new RecipesCommon.ComparableStack(Item.getByNameOrId("tfc:powder/kaolinite")))
                .inputFluids(new FluidStack(Fluids.SULFURIC_ACID, 100))
                .outputFluids(new FluidStack(Fluids.fromName("AACS"), 200)));
        this.register(new GenericRecipe("chem.IS_1").setup(120, 100)
                .inputItems(new SlagChemicalAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.LIMONITE_SLAG, 1), 100))
                .inputFluids(new FluidStack(Fluids.SULFURIC_ACID, 100))
                .outputFluids(new FluidStack(Fluids.fromName("IS"), 200)));
        this.register(new GenericRecipe("chem.IS_2").setup(120, 100)
                .inputItems(new SlagChemicalAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.HEMATITE_SLAG, 1), 100))
                .inputFluids(new FluidStack(Fluids.SULFURIC_ACID, 100))
                .outputFluids(new FluidStack(Fluids.fromName("IS"), 200)));
        this.register(new GenericRecipe("chem.IS_3").setup(120, 100)
                .inputItems(new SlagChemicalAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.MAGNETITE_SLAG, 1), 100))
                .inputFluids(new FluidStack(Fluids.SULFURIC_ACID, 100))
                .outputFluids(new FluidStack(Fluids.fromName("IS"), 200)));
        this.register(new GenericRecipe("chem.GS").setup(120, 100)
                .inputItems(new SlagChemicalAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.GOLD_SLAG, 1), 100))
                .inputFluids(new FluidStack(Fluids.SULFURIC_ACID, 100))
                .outputFluids(new FluidStack(Fluids.fromName("GS"), 200)));
        this.register(new GenericRecipe("chem.CS").setup(120, 100)
                .inputItems(new SlagChemicalAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.COPPER_SLAG, 1), 100))
                .inputFluids(new FluidStack(Fluids.SULFURIC_ACID, 100))
                .outputFluids(new FluidStack(Fluids.fromName("CS"), 200)));
        this.register(new GenericRecipe("chem.LS").setup(120, 100)
                .inputItems(new SlagChemicalAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.GALENA_SLAG, 1), 100))
                .inputFluids(new FluidStack(Fluids.SULFURIC_ACID, 100))
                .outputFluids(new FluidStack(Fluids.fromName("LS"), 200)));
        this.register(new GenericRecipe("chem.ZS").setup(120, 100)
                .inputItems(new SlagChemicalAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.ZINC_SLAG, 1), 100))
                .inputFluids(new FluidStack(Fluids.SULFURIC_ACID, 100))
                .outputFluids(new FluidStack(Fluids.fromName("ZS"), 200)));
        this.register(new GenericRecipe("chem.soaked_small_hide").setup(120, 100)
                .inputItems(new RecipesCommon.ComparableStack(Item.getByNameOrId("tfc:hide/raw/small")))
                .inputFluids(new FluidStack(Fluids.fromName("LIMEWATER"), 300))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:hide/soaked/small"), 1)));
        this.register(new GenericRecipe("chem.prepared_small_hide").setup(160, 100)
                .inputItems(new RecipesCommon.ComparableStack(Item.getByNameOrId("tfc:hide/scraped/small")))
                .inputFluids(new FluidStack(Fluids.fromName("CrS"), 300))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:hide/prepared/small"), 1)));
        this.register(new GenericRecipe("chem.leather_small").setup(160, 100)
                .inputItems(new RecipesCommon.ComparableStack(Item.getByNameOrId("tfc:hide/prepared/small")))
                .inputFluids(new FluidStack(Fluids.WOODOIL, 300))
                .outputItems(new ItemStack(Item.getByNameOrId("minecraft:leather"), 2)));
        this.register(new GenericRecipe("chem.soaked_medium_hide").setup(120, 100)
                .inputItems(new RecipesCommon.ComparableStack(Item.getByNameOrId("tfc:hide/raw/medium")))
                .inputFluids(new FluidStack(Fluids.fromName("LIMEWATER"), 400))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:hide/soaked/medium"), 1)));
        this.register(new GenericRecipe("chem.prepared_medium_hide").setup(160, 100)
                .inputItems(new RecipesCommon.ComparableStack(Item.getByNameOrId("tfc:hide/scraped/medium")))
                .inputFluids(new FluidStack(Fluids.fromName("CrS"), 400))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:hide/prepared/medium"), 1)));
        this.register(new GenericRecipe("chem.leather_medium").setup(160, 100)
                .inputItems(new RecipesCommon.ComparableStack(Item.getByNameOrId("tfc:hide/prepared/medium")))
                .inputFluids(new FluidStack(Fluids.WOODOIL, 400))
                .outputItems(new ItemStack(Item.getByNameOrId("minecraft:leather"), 4)));
        this.register(new GenericRecipe("chem.soaked_large_hide").setup(120, 100)
                .inputItems(new RecipesCommon.ComparableStack(Item.getByNameOrId("tfc:hide/raw/large")))
                .inputFluids(new FluidStack(Fluids.fromName("LIMEWATER"), 500))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:hide/soaked/large"), 1)));
        this.register(new GenericRecipe("chem.prepared_large_hide").setup(160, 100)
                .inputItems(new RecipesCommon.ComparableStack(Item.getByNameOrId("tfc:hide/scraped/large")))
                .inputFluids(new FluidStack(Fluids.fromName("CrS"), 500))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:hide/prepared/large"), 1)));
        this.register(new GenericRecipe("chem.leather_large").setup(160, 100)
                .inputItems(new RecipesCommon.ComparableStack(Item.getByNameOrId("tfc:hide/prepared/large")))
                .inputFluids(new FluidStack(Fluids.WOODOIL, 500))
                .outputItems(new ItemStack(Item.getByNameOrId("minecraft:leather"), 6)));
        this.register(new GenericRecipe("chem.CrS").setup(120, 100)
                .inputItems(new SlagChemicalAStack(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.CHROME_SLAG, 1), 100))
                .inputFluids(new FluidStack(Fluids.SULFURIC_ACID, 100))
                .outputFluids(new FluidStack(Fluids.fromName("CrS"), 150)));
        this.register(new GenericRecipe("chem.KerFJ").setup(120, 100)
                .inputItems(new RecipesCommon.ComparableStack(ModItems.sulfur, 1), new RecipesCommon.ComparableStack(ModItems.niter, 1))
                .inputFluids(new FluidStack(Fluids.OXYHYDROGEN, 2000))
                .outputItems(new ItemStack(com.voided.tfcnuclear.inventory.items.ModItems.KER_FJ)));
        this.register(new GenericRecipe("chem.scraped_small_hide").setup(120, 100)
                .inputItems(new RecipesCommon.ComparableStack(com.voided.tfcnuclear.inventory.items.ModItems.KER_FJ, 1), new RecipesCommon.ComparableStack(Item.getByNameOrId("tfc:hide/soaked/small"), 1))
                .inputFluids(new FluidStack(Fluids.WATER, 1000))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:hide/scraped/small"))));
        this.register(new GenericRecipe("chem.scraped_medium_hide").setup(120, 100)
                .inputItems(new RecipesCommon.ComparableStack(com.voided.tfcnuclear.inventory.items.ModItems.KER_FJ, 2), new RecipesCommon.ComparableStack(Item.getByNameOrId("tfc:hide/soaked/medium"), 1))
                .inputFluids(new FluidStack(Fluids.WATER, 2000))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:hide/scraped/medium"))));
        this.register(new GenericRecipe("chem.scraped_large_hide").setup(120, 100)
                .inputItems(new RecipesCommon.ComparableStack(com.voided.tfcnuclear.inventory.items.ModItems.KER_FJ, 3), new RecipesCommon.ComparableStack(Item.getByNameOrId("tfc:hide/soaked/large"), 1))
                .inputFluids(new FluidStack(Fluids.WATER, 3000))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:hide/scraped/large"))));
        this.register(new GenericRecipe("chem.scraped_small_sheepskin").setup(120, 100)
                .inputItems(new RecipesCommon.ComparableStack(com.voided.tfcnuclear.inventory.items.ModItems.KER_FJ, 1), new RecipesCommon.ComparableStack(Item.getByNameOrId("tfc:hide/sheepskin/small"), 1))
                .inputFluids(new FluidStack(Fluids.WATER, 1000))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:animal/product/wool"), 2), new ItemStack(Item.getByNameOrId("tfc:hide/raw/small"))));
        this.register(new GenericRecipe("chem.scraped_medium_sheepskin").setup(120, 100)
                .inputItems(new RecipesCommon.ComparableStack(com.voided.tfcnuclear.inventory.items.ModItems.KER_FJ, 2), new RecipesCommon.ComparableStack(Item.getByNameOrId("tfc:hide/sheepskin/medium"), 1))
                .inputFluids(new FluidStack(Fluids.WATER, 2000))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:animal/product/wool"), 4), new ItemStack(Item.getByNameOrId("tfc:hide/raw/medium"))));
        this.register(new GenericRecipe("chem.scraped_large_sheepskin").setup(120, 100)
                .inputItems(new RecipesCommon.ComparableStack(com.voided.tfcnuclear.inventory.items.ModItems.KER_FJ, 3), new RecipesCommon.ComparableStack(Item.getByNameOrId("tfc:hide/sheepskin/large"), 1))
                .inputFluids(new FluidStack(Fluids.WATER, 3000))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:animal/product/wool"), 6), new ItemStack(Item.getByNameOrId("tfc:hide/raw/large"))));

        //COBBLE
        this.register(new GenericRecipe("chem.cobble_granite").setup(20, 100)
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:cobble/granite"))));
        this.register(new GenericRecipe("chem.cobble_diorite").setup(20, 100)
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:cobble/diorite"))));
        this.register(new GenericRecipe("chem.cobble_gabbro").setup(20, 100)
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:cobble/gabbro"))));
        this.register(new GenericRecipe("chem.cobble_shale").setup(20, 100)
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:cobble/shale"))));
        this.register(new GenericRecipe("chem.cobble_claystone").setup(20, 100)
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:cobble/claystone"))));
        this.register(new GenericRecipe("chem.cobble_rocksalt").setup(20, 100)
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:cobble/rocksalt"))));
        this.register(new GenericRecipe("chem.cobble_limestone").setup(20, 100)
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:cobble/limestone"))));
        this.register(new GenericRecipe("chem.cobble_conglomerate").setup(20, 100)
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:cobble/conglomerate"))));
        this.register(new GenericRecipe("chem.cobble_dolomite").setup(20, 100)
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:cobble/dolomite"))));
        this.register(new GenericRecipe("chem.cobble_chert").setup(20, 100)
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:cobble/chert"))));
        this.register(new GenericRecipe("chem.cobble_chalk").setup(20, 100)
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:cobble/chalk"))));
        this.register(new GenericRecipe("chem.cobble_rhyolite").setup(20, 100)
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:cobble/rhyolite"))));
        this.register(new GenericRecipe("chem.cobble_basalt").setup(20, 100)
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:cobble/basalt"))));
        this.register(new GenericRecipe("chem.cobble_andesite").setup(20, 100)
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:cobble/andesite"))));
        this.register(new GenericRecipe("chem.cobble_dacite").setup(20, 100)
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:cobble/dacite"))));
        this.register(new GenericRecipe("chem.cobble_quartzite").setup(20, 100)
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:cobble/quartzite"))));
        this.register(new GenericRecipe("chem.cobble_slate").setup(20, 100)
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:cobble/slate"))));
        this.register(new GenericRecipe("chem.cobble_phyllite").setup(20, 100)
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:cobble/phyllite"))));
        this.register(new GenericRecipe("chem.cobble_schist").setup(20, 100)
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:cobble/schist"))));
        this.register(new GenericRecipe("chem.cobble_gneiss").setup(20, 100)
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:cobble/gneiss"))));
        this.register(new GenericRecipe("chem.cobble_marble").setup(20, 100)
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:cobble/marble"))));

        //RAW
        this.register(new GenericRecipe("chem.raw_granite").setup(60, 500).setPools(GenericRecipes.POOL_PREFIX_DISCOVER + ".stone")
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25), new FluidStack(Fluids.AIR, 4_000))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:raw/granite"))));
        this.register(new GenericRecipe("chem.raw_diorite").setup(60, 500).setPools(GenericRecipes.POOL_PREFIX_DISCOVER + ".stone")
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25), new FluidStack(Fluids.AIR, 4_000))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:raw/diorite"))));
        this.register(new GenericRecipe("chem.raw_gabbro").setup(60, 500).setPools(GenericRecipes.POOL_PREFIX_DISCOVER + ".stone")
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25), new FluidStack(Fluids.AIR, 4_000))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:raw/gabbro"))));
        this.register(new GenericRecipe("chem.raw_shale").setup(60, 500).setPools(GenericRecipes.POOL_PREFIX_DISCOVER + ".stone")
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25), new FluidStack(Fluids.AIR, 4_000))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:raw/shale"))));
        this.register(new GenericRecipe("chem.raw_claystone").setup(60, 500).setPools(GenericRecipes.POOL_PREFIX_DISCOVER + ".stone")
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25), new FluidStack(Fluids.AIR, 4_000))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:raw/claystone"))));
        this.register(new GenericRecipe("chem.raw_rocksalt").setup(60, 500).setPools(GenericRecipes.POOL_PREFIX_DISCOVER + ".stone")
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25), new FluidStack(Fluids.AIR, 4_000))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:raw/rocksalt"))));
        this.register(new GenericRecipe("chem.raw_limestone").setup(60, 500).setPools(GenericRecipes.POOL_PREFIX_DISCOVER + ".stone")
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25), new FluidStack(Fluids.AIR, 4_000))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:raw/limestone"))));
        this.register(new GenericRecipe("chem.raw_conglomerate").setup(60, 500).setPools(GenericRecipes.POOL_PREFIX_DISCOVER + ".stone")
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25), new FluidStack(Fluids.AIR, 4_000))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:raw/conglomerate"))));
        this.register(new GenericRecipe("chem.raw_dolomite").setup(60, 500).setPools(GenericRecipes.POOL_PREFIX_DISCOVER + ".stone")
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25), new FluidStack(Fluids.AIR, 4_000))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:raw/dolomite"))));
        this.register(new GenericRecipe("chem.raw_chert").setup(60, 500).setPools(GenericRecipes.POOL_PREFIX_DISCOVER + ".stone")
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25), new FluidStack(Fluids.AIR, 4_000))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:raw/chert"))));
        this.register(new GenericRecipe("chem.raw_chalk").setup(60, 500).setPools(GenericRecipes.POOL_PREFIX_DISCOVER + ".stone")
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25), new FluidStack(Fluids.AIR, 4_000))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:raw/chalk"))));
        this.register(new GenericRecipe("chem.raw_rhyolite").setup(60, 500).setPools(GenericRecipes.POOL_PREFIX_DISCOVER + ".stone")
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25), new FluidStack(Fluids.AIR, 4_000))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:raw/rhyolite"))));
        this.register(new GenericRecipe("chem.raw_basalt").setup(60, 500).setPools(GenericRecipes.POOL_PREFIX_DISCOVER + ".stone")
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25), new FluidStack(Fluids.AIR, 4_000))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:raw/basalt"))));
        this.register(new GenericRecipe("chem.raw_andesite").setup(60, 500).setPools(GenericRecipes.POOL_PREFIX_DISCOVER + ".stone")
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25), new FluidStack(Fluids.AIR, 4_000))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:raw/andesite"))));
        this.register(new GenericRecipe("chem.raw_dacite").setup(60, 500).setPools(GenericRecipes.POOL_PREFIX_DISCOVER + ".stone")
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25), new FluidStack(Fluids.AIR, 4_000))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:raw/dacite"))));
        this.register(new GenericRecipe("chem.raw_quartzite").setup(60, 500).setPools(GenericRecipes.POOL_PREFIX_DISCOVER + ".stone")
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25), new FluidStack(Fluids.AIR, 4_000))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:raw/quartzite"))));
        this.register(new GenericRecipe("chem.raw_slate").setup(60, 500).setPools(GenericRecipes.POOL_PREFIX_DISCOVER + ".stone")
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25), new FluidStack(Fluids.AIR, 4_000))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:raw/slate"))));
        this.register(new GenericRecipe("chem.raw_phyllite").setup(60, 500).setPools(GenericRecipes.POOL_PREFIX_DISCOVER + ".stone")
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25), new FluidStack(Fluids.AIR, 4_000))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:raw/phyllite"))));
        this.register(new GenericRecipe("chem.raw_schist").setup(60, 500).setPools(GenericRecipes.POOL_PREFIX_DISCOVER + ".stone")
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25), new FluidStack(Fluids.AIR, 4_000))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:raw/schist"))));
        this.register(new GenericRecipe("chem.raw_gneiss").setup(60, 500).setPools(GenericRecipes.POOL_PREFIX_DISCOVER + ".stone")
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25), new FluidStack(Fluids.AIR, 4_000))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:raw/gneiss"))));
        this.register(new GenericRecipe("chem.raw_marble").setup(60, 500).setPools(GenericRecipes.POOL_PREFIX_DISCOVER + ".stone")
                .inputFluids(new FluidStack(Fluids.WATER, 1_000), new FluidStack(Fluids.LAVA, 25), new FluidStack(Fluids.AIR, 4_000))
                .outputItems(new ItemStack(Item.getByNameOrId("tfc:raw/marble"))));
    }
}