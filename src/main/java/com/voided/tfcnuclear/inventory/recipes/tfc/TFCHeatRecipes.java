package com.voided.tfcnuclear.inventory.recipes.tfc;

import com.voided.tfcnuclear.TFCNuclear;
import net.dries007.tfc.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.heat.ItemHeatHandler;
import net.dries007.tfc.api.recipes.heat.HeatRecipe;
import net.dries007.tfc.api.recipes.heat.HeatRecipeSimple;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class TFCHeatRecipes {

    private static final String MOD_ID = "tfcnuclear";

    private static ItemStack getTFCItem(String path) {
        Item item = net.minecraftforge.fml.common.registry.ForgeRegistries.ITEMS.getValue(
                new ResourceLocation("tfc", path)
        );
        return item != null ? new ItemStack(item) : ItemStack.EMPTY;
    }

    private static ItemStack getHBMItem(String path) {
        Item item = net.minecraftforge.fml.common.registry.ForgeRegistries.ITEMS.getValue(
                new ResourceLocation("hbm", path)
        );
        return item != null ? new ItemStack(item) : ItemStack.EMPTY;
    }

    private static Metal getTFCMetal(String name) {
        return net.dries007.tfc.api.registries.TFCRegistries.METALS.getValue(new ResourceLocation("tfc", name));
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onHeatRegistry(RegistryEvent.Register<HeatRecipe> event) {
        IForgeRegistry<HeatRecipe> registry = event.getRegistry();

        addMetalMeltingRecipes(registry);
        addCustomHeatRecipes(registry);

        addHeatCapabilities();
    }

    private static void addMetalMeltingRecipes(IForgeRegistry<HeatRecipe> registry) {
        addBismuthHeatRecipes(registry);
        addBismuthBronzeHeatRecipes(registry);
        addNickelHeatRecipes(registry);
    }

    private static void addBismuthHeatRecipes(IForgeRegistry<HeatRecipe> registry) {
        Metal bismuthMetal = getTFCMetal("bismuth");
        if (bismuthMetal == null) {
            return;
        }

        ItemStack bismuthIngot = getTFCItem("metal/ingot/bismuth");
        ItemStack bismuthDoubleIngot = getTFCItem("metal/double_ingot/bismuth");
        ItemStack bismuthSheet = getTFCItem("metal/sheet/bismuth");
        ItemStack bismuthDoubleSheet = getTFCItem("metal/double_sheet/bismuth");

        float meltTemp = bismuthMetal.getMeltTemp();

        if (!bismuthIngot.isEmpty()) {
            registry.register(new HeatRecipeMelting("melt_bismuth_ingot", bismuthIngot, meltTemp, bismuthMetal, 100));
        }
        if (!bismuthDoubleIngot.isEmpty()) {
            registry.register(new HeatRecipeMelting("melt_bismuth_double_ingot", bismuthDoubleIngot, meltTemp, bismuthMetal, 200));
        }
        if (!bismuthSheet.isEmpty()) {
            registry.register(new HeatRecipeMelting("melt_bismuth_sheet", bismuthSheet, meltTemp, bismuthMetal, 100));
        }
        if (!bismuthDoubleSheet.isEmpty()) {
            registry.register(new HeatRecipeMelting("melt_bismuth_double_sheet", bismuthDoubleSheet, meltTemp, bismuthMetal, 200));
        }
    }

    private static void addBismuthBronzeHeatRecipes(IForgeRegistry<HeatRecipe> registry) {
        Metal bismuthBronzeMetal = getTFCMetal("bismuth_bronze");
        if (bismuthBronzeMetal == null) {
            return;
        }

        ItemStack bronzeIngot = getTFCItem("metal/ingot/bismuth_bronze");
        ItemStack bronzeDoubleIngot = getTFCItem("metal/double_ingot/bismuth_bronze");
        ItemStack bronzeSheet = getTFCItem("metal/sheet/bismuth_bronze");
        ItemStack bronzeDoubleSheet = getTFCItem("metal/double_sheet/bismuth_bronze");

        float meltTemp = bismuthBronzeMetal.getMeltTemp();

        if (!bronzeIngot.isEmpty()) {
            registry.register(new HeatRecipeMelting("melt_bismuth_bronze_ingot", bronzeIngot, meltTemp, bismuthBronzeMetal, 100));
        }
        if (!bronzeDoubleIngot.isEmpty()) {
            registry.register(new HeatRecipeMelting("melt_bismuth_bronze_double_ingot", bronzeDoubleIngot, meltTemp, bismuthBronzeMetal, 200));
        }
        if (!bronzeSheet.isEmpty()) {
            registry.register(new HeatRecipeMelting("melt_bismuth_bronze_sheet", bronzeSheet, meltTemp, bismuthBronzeMetal, 100));
        }
        if (!bronzeDoubleSheet.isEmpty()) {
            registry.register(new HeatRecipeMelting("melt_bismuth_bronze_double_sheet", bronzeDoubleSheet, meltTemp, bismuthBronzeMetal, 200));
        }
    }

    private static void addNickelHeatRecipes(IForgeRegistry<HeatRecipe> registry) {
        Metal nickelMetal = getTFCMetal("nickel");
        if (nickelMetal == null) {
            return;
        }

        ItemStack nickelIngot = getTFCItem("metal/ingot/nickel");
        ItemStack nickelDoubleIngot = getTFCItem("metal/double_ingot/nickel");
        ItemStack nickelSheet = getTFCItem("metal/sheet/nickel");
        ItemStack nickelDoubleSheet = getTFCItem("metal/double_sheet/nickel");

        float meltTemp = nickelMetal.getMeltTemp();

        if (!nickelIngot.isEmpty()) {
            registry.register(new HeatRecipeMelting("melt_nickel_ingot", nickelIngot, meltTemp, nickelMetal, 100));
        }
        if (!nickelDoubleIngot.isEmpty()) {
            registry.register(new HeatRecipeMelting("melt_nickel_double_ingot", nickelDoubleIngot, meltTemp, nickelMetal, 200));
        }
        if (!nickelSheet.isEmpty()) {
            registry.register(new HeatRecipeMelting("melt_nickel_sheet", nickelSheet, meltTemp, nickelMetal, 100));
        }
        if (!nickelDoubleSheet.isEmpty()) {
            registry.register(new HeatRecipeMelting("melt_nickel_double_sheet", nickelDoubleSheet, meltTemp, nickelMetal, 200));
        }
    }

    private static void addCustomHeatRecipes(IForgeRegistry<HeatRecipe> registry) {
        addClayToFireclayRecipe(registry);
        addUnfiredToFiredBrickRecipe(registry);
    }

    private static void addClayToFireclayRecipe(IForgeRegistry<HeatRecipe> registry) {
        IIngredient<ItemStack> clayIngredient = IIngredient.of(new ItemStack(Items.CLAY_BALL));
        ItemStack fireclayOutput = new ItemStack(Item.getByNameOrId("hbm:ball_fireclay"));
        float transformTemp = 1500f;

        if (!fireclayOutput.isEmpty()) {
            HeatRecipeSimple clayToFireclay = new HeatRecipeSimple(
                    clayIngredient,
                    fireclayOutput,
                    transformTemp
            );
            clayToFireclay.setRegistryName(new ResourceLocation(MOD_ID, "clay_to_fireclay"));
            registry.register(clayToFireclay);
        }
    }

    private static void addUnfiredToFiredBrickRecipe(IForgeRegistry<HeatRecipe> registry) {
        Item unfiredBrick = Item.getByNameOrId("tfc:ceramics/unfired/fire_brick");
        Item firedBrick = Item.getByNameOrId("hbm:ingot_firebrick");

        if (unfiredBrick != null && firedBrick != null) {
            IIngredient<ItemStack> unfiredBrickIngredient = IIngredient.of(new ItemStack(unfiredBrick));
            ItemStack firedBrickOutput = new ItemStack(firedBrick);

            HeatRecipeSimple unfiredToFired = new HeatRecipeSimple(
                    unfiredBrickIngredient,
                    firedBrickOutput,
                    1500f
            );
            unfiredToFired.setRegistryName(new ResourceLocation(MOD_ID, "unfired_to_fired_brick"));
            registry.register(unfiredToFired);
        }
    }

    private static void addHeatCapabilities() {
        addHeatCapabilityToClay();
        addHeatCapabilityToFirebrick();
        addHeatCapabilityToFireclay();
        addHeatCapabilityToCobalt();
        addHeatCapabilityToCobaltBlock();
    }

    private static void addHeatCapabilityToClay() {
        Item clayBall = Item.getByNameOrId("minecraft:clay_ball");
        if (clayBall != null) {
            CapabilityItemHeat.CUSTOM_ITEMS.put(
                    IIngredient.of(clayBall),
                    () -> new ItemHeatHandler(null, 0.5f, 1500f)
            );
        }
    }

    private static void addHeatCapabilityToFirebrick() {
        Item firebrick = Item.getByNameOrId("hbm:ingot_firebrick");
        if (firebrick != null) {
            CapabilityItemHeat.CUSTOM_ITEMS.put(
                    IIngredient.of(firebrick),
                    () -> new ItemHeatHandler(null, 1.0f, 2500f)
            );
        }
    }

    private static void addHeatCapabilityToFireclay() {
        Item fireclay = Item.getByNameOrId("hbm:ball_fireclay");
        if (fireclay != null) {
            CapabilityItemHeat.CUSTOM_ITEMS.put(
                    IIngredient.of(fireclay),
                    () -> new ItemHeatHandler(null, 0.8f, 2000f)
            );
        }
    }

    private static void addHeatCapabilityToCobalt() {
        Item cobalt = Item.getByNameOrId("hbm:ingot_cobalt");
        if (cobalt != null) {
            CapabilityItemHeat.CUSTOM_ITEMS.put(
                    IIngredient.of(cobalt),
                    () -> new ItemHeatHandler(null, 0.8f, 2000f)
            );
        }
    }

    private static void addHeatCapabilityToCobaltBlock() {
        Item cobaltBlock = Item.getByNameOrId("hbm:block_cobalt");
        if (cobaltBlock != null) {
            CapabilityItemHeat.CUSTOM_ITEMS.put(
                    IIngredient.of(cobaltBlock),
                    () -> new ItemHeatHandler(null, 0.8f, 2000f)
            );
        }
    }

    private static class HeatRecipeMelting extends HeatRecipe {
        private final Metal metal;
        private final int amount;

        public HeatRecipeMelting(String name, ItemStack input, float transformTemp, Metal metal, int amount) {
            super(IIngredient.of(input), transformTemp, metal.getTier());
            this.metal = metal;
            this.amount = amount;
            setRegistryName(new ResourceLocation(MOD_ID, name));
        }

        @Override
        @Nullable
        public FluidStack getOutputFluid(ItemStack input) {
            if (isValidInput(input, metal.getTier())) {
                return new FluidStack(FluidsTFC.getFluidFromMetal(metal), amount);
            }
            return null;
        }

        public Metal getMetal() {
            return metal;
        }
    }
}