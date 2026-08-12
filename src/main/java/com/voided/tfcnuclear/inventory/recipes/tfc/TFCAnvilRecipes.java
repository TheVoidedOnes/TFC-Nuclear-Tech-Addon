package com.voided.tfcnuclear.inventory.recipes.tfc;

import net.dries007.tfc.api.recipes.anvil.AnvilRecipe;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.util.forge.ForgeRule;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class TFCAnvilRecipes {

    private static final String MOD_ID = "tfcnuclear";

    private static ItemStack getTFCItem(String path) {
        net.minecraft.item.Item item = net.minecraftforge.fml.common.registry.ForgeRegistries.ITEMS.getValue(
                new ResourceLocation("tfc", path)
        );
        return item != null ? new ItemStack(item) : ItemStack.EMPTY;
    }

    private static ItemStack getHBMItem(String path) {
        net.minecraft.item.Item item = net.minecraftforge.fml.common.registry.ForgeRegistries.ITEMS.getValue(
                new ResourceLocation("hbm", path)
        );
        return item != null ? new ItemStack(item) : ItemStack.EMPTY;
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onAnvilRegistry(RegistryEvent.Register<AnvilRecipe> event) {
        IForgeRegistry<AnvilRecipe> registry = event.getRegistry();
        addBismuthAnvilRecipes(registry);
        addBismuthBronzeAnvilRecipes(registry);
        addNickelAnvilRecipes(registry);
        addSteelRodAnvilRecipes(registry);
    }

    private static void addBismuthAnvilRecipes(IForgeRegistry<AnvilRecipe> registry) {
        ItemStack bismuthIngot = getTFCItem("metal/ingot/bismuth");
        ItemStack bismuthDoubleIngot = getTFCItem("metal/double_ingot/bismuth");
        ItemStack bismuthSheet = getTFCItem("metal/sheet/bismuth");
        ItemStack bismuthLamp = getTFCItem("metal/lamp/bismuth");
        ItemStack bismuthTrapdoor = getTFCItem("metal/trapdoor/bismuth");

        if (!bismuthDoubleIngot.isEmpty() && !bismuthSheet.isEmpty()) {
            registry.register(new AnvilRecipe(
                    new ResourceLocation(MOD_ID, "bismuth_double_ingot_to_sheet"),
                    IIngredient.of(bismuthDoubleIngot),
                    bismuthSheet,
                    Metal.Tier.TIER_I,
                    null,
                    ForgeRule.HIT_ANY, ForgeRule.HIT_ANY, ForgeRule.HIT_ANY
            ));
        }

        if (!bismuthIngot.isEmpty() && !bismuthLamp.isEmpty()) {
            registry.register(new AnvilRecipe(
                    new ResourceLocation(MOD_ID, "bismuth_ingot_to_lamp"),
                    IIngredient.of(bismuthIngot),
                    bismuthLamp,
                    Metal.Tier.TIER_I,
                    null,
                    ForgeRule.HIT_ANY, ForgeRule.HIT_ANY
            ));
        }

        if (!bismuthSheet.isEmpty() && !bismuthTrapdoor.isEmpty()) {
            registry.register(new AnvilRecipe(
                    new ResourceLocation(MOD_ID, "bismuth_sheet_to_trapdoor"),
                    IIngredient.of(bismuthSheet),
                    bismuthTrapdoor,
                    Metal.Tier.TIER_I,
                    null,
                    ForgeRule.HIT_ANY, ForgeRule.HIT_ANY
            ));
        }
    }

    private static void addBismuthBronzeAnvilRecipes(IForgeRegistry<AnvilRecipe> registry) {
        ItemStack bronzeIngot = getTFCItem("metal/ingot/bismuth_bronze");
        ItemStack bronzeDoubleIngot = getTFCItem("metal/double_ingot/bismuth_bronze");
        ItemStack bronzeSheet = getTFCItem("metal/sheet/bismuth_bronze");
        ItemStack bronzeDoubleSheet = getTFCItem("metal/double_sheet/bismuth_bronze");
        ItemStack bronzeLamp = getTFCItem("metal/lamp/bismuth_bronze");
        ItemStack bronzeTrapdoor = getTFCItem("metal/trapdoor/bismuth_bronze");
        ItemStack bronzeTuyere = getTFCItem("metal/tuyere/bismuth_bronze");
        ItemStack bronzeUnfinishedHelmet = getTFCItem("metal/unfinished_helmet/bismuth_bronze");
        ItemStack bronzeUnfinishedChestplate = getTFCItem("metal/unfinished_chestplate/bismuth_bronze");
        ItemStack bronzeUnfinishedGreaves = getTFCItem("metal/unfinished_greaves/bismuth_bronze");
        ItemStack bronzeUnfinishedBoots = getTFCItem("metal/unfinished_boots/bismuth_bronze");
        ItemStack bronzeShield = getTFCItem("metal/shield/bismuth_bronze");

        if (!bronzeDoubleIngot.isEmpty() && !bronzeSheet.isEmpty()) {
            registry.register(new AnvilRecipe(
                    new ResourceLocation(MOD_ID, "bismuth_bronze_double_ingot_to_sheet"),
                    IIngredient.of(bronzeDoubleIngot),
                    bronzeSheet,
                    Metal.Tier.TIER_I,
                    null,
                    ForgeRule.HIT_ANY, ForgeRule.HIT_ANY, ForgeRule.HIT_ANY
            ));
        }

        if (!bronzeIngot.isEmpty() && !bronzeLamp.isEmpty()) {
            registry.register(new AnvilRecipe(
                    new ResourceLocation(MOD_ID, "bismuth_bronze_ingot_to_lamp"),
                    IIngredient.of(bronzeIngot),
                    bronzeLamp,
                    Metal.Tier.TIER_I,
                    null,
                    ForgeRule.HIT_ANY, ForgeRule.HIT_ANY
            ));
        }

        if (!bronzeSheet.isEmpty() && !bronzeTrapdoor.isEmpty()) {
            registry.register(new AnvilRecipe(
                    new ResourceLocation(MOD_ID, "bismuth_bronze_sheet_to_trapdoor"),
                    IIngredient.of(bronzeSheet),
                    bronzeTrapdoor,
                    Metal.Tier.TIER_I,
                    null,
                    ForgeRule.HIT_ANY, ForgeRule.HIT_ANY
            ));
        }

        if (!bronzeDoubleSheet.isEmpty() && !bronzeTuyere.isEmpty()) {
            registry.register(new AnvilRecipe(
                    new ResourceLocation(MOD_ID, "bismuth_bronze_double_sheet_to_tuyere"),
                    IIngredient.of(bronzeDoubleSheet),
                    bronzeTuyere,
                    Metal.Tier.TIER_I,
                    null,
                    ForgeRule.HIT_ANY, ForgeRule.HIT_ANY, ForgeRule.HIT_ANY
            ));
        }

        if (!bronzeDoubleSheet.isEmpty() && !bronzeUnfinishedHelmet.isEmpty()) {
            registry.register(new AnvilRecipe(
                    new ResourceLocation(MOD_ID, "bismuth_bronze_double_sheet_to_unfinished_helmet"),
                    IIngredient.of(bronzeDoubleSheet),
                    bronzeUnfinishedHelmet,
                    Metal.Tier.TIER_I,
                    null,
                    ForgeRule.HIT_ANY, ForgeRule.HIT_ANY, ForgeRule.HIT_ANY
            ));
        }

        if (!bronzeDoubleSheet.isEmpty() && !bronzeUnfinishedChestplate.isEmpty()) {
            registry.register(new AnvilRecipe(
                    new ResourceLocation(MOD_ID, "bismuth_bronze_double_sheet_to_unfinished_chestplate"),
                    IIngredient.of(bronzeDoubleSheet),
                    bronzeUnfinishedChestplate,
                    Metal.Tier.TIER_I,
                    null,
                    ForgeRule.HIT_ANY, ForgeRule.HIT_ANY, ForgeRule.HIT_ANY
            ));
        }

        if (!bronzeDoubleSheet.isEmpty() && !bronzeUnfinishedGreaves.isEmpty()) {
            registry.register(new AnvilRecipe(
                    new ResourceLocation(MOD_ID, "bismuth_bronze_double_sheet_to_unfinished_greaves"),
                    IIngredient.of(bronzeDoubleSheet),
                    bronzeUnfinishedGreaves,
                    Metal.Tier.TIER_I,
                    null,
                    ForgeRule.HIT_ANY, ForgeRule.HIT_ANY, ForgeRule.HIT_ANY
            ));
        }

        if (!bronzeSheet.isEmpty() && !bronzeUnfinishedBoots.isEmpty()) {
            registry.register(new AnvilRecipe(
                    new ResourceLocation(MOD_ID, "bismuth_bronze_sheet_to_unfinished_boots"),
                    IIngredient.of(bronzeSheet),
                    bronzeUnfinishedBoots,
                    Metal.Tier.TIER_I,
                    null,
                    ForgeRule.HIT_ANY, ForgeRule.HIT_ANY, ForgeRule.HIT_ANY
            ));
        }

        if (!bronzeDoubleSheet.isEmpty() && !bronzeShield.isEmpty()) {
            registry.register(new AnvilRecipe(
                    new ResourceLocation(MOD_ID, "bismuth_bronze_double_sheet_to_shield"),
                    IIngredient.of(bronzeDoubleSheet),
                    bronzeShield,
                    Metal.Tier.TIER_I,
                    null,
                    ForgeRule.HIT_ANY, ForgeRule.HIT_ANY, ForgeRule.HIT_ANY
            ));
        }
    }

    private static void addNickelAnvilRecipes(IForgeRegistry<AnvilRecipe> registry) {
        ItemStack nickelIngot = getTFCItem("metal/ingot/nickel");
        ItemStack nickelDoubleIngot = getTFCItem("metal/double_ingot/nickel");
        ItemStack nickelSheet = getTFCItem("metal/sheet/nickel");
        ItemStack nickelDoubleSheet = getTFCItem("metal/double_sheet/nickel");
        ItemStack nickelLamp = getTFCItem("metal/lamp/nickel");
        ItemStack nickelTrapdoor = getTFCItem("metal/trapdoor/nickel");
        ItemStack nickelTuyere = getTFCItem("metal/tuyere/nickel");

        if (!nickelDoubleIngot.isEmpty() && !nickelSheet.isEmpty()) {
            registry.register(new AnvilRecipe(
                    new ResourceLocation(MOD_ID, "nickel_double_ingot_to_sheet"),
                    IIngredient.of(nickelDoubleIngot),
                    nickelSheet,
                    Metal.Tier.TIER_I,
                    null,
                    ForgeRule.HIT_ANY, ForgeRule.HIT_ANY, ForgeRule.HIT_ANY
            ));
        }

        if (!nickelIngot.isEmpty() && !nickelLamp.isEmpty()) {
            registry.register(new AnvilRecipe(
                    new ResourceLocation(MOD_ID, "nickel_ingot_to_lamp"),
                    IIngredient.of(nickelIngot),
                    nickelLamp,
                    Metal.Tier.TIER_I,
                    null,
                    ForgeRule.HIT_ANY, ForgeRule.HIT_ANY
            ));
        }

        if (!nickelSheet.isEmpty() && !nickelTrapdoor.isEmpty()) {
            registry.register(new AnvilRecipe(
                    new ResourceLocation(MOD_ID, "nickel_sheet_to_trapdoor"),
                    IIngredient.of(nickelSheet),
                    nickelTrapdoor,
                    Metal.Tier.TIER_I,
                    null,
                    ForgeRule.HIT_ANY, ForgeRule.HIT_ANY
            ));
        }

        if (!nickelDoubleSheet.isEmpty() && !nickelTuyere.isEmpty()) {
            registry.register(new AnvilRecipe(
                    new ResourceLocation(MOD_ID, "nickel_double_sheet_to_tuyere"),
                    IIngredient.of(nickelDoubleSheet),
                    nickelTuyere,
                    Metal.Tier.TIER_I,
                    null,
                    ForgeRule.HIT_ANY, ForgeRule.HIT_ANY, ForgeRule.HIT_ANY
            ));
        }
    }

    private static void addSteelRodAnvilRecipes(IForgeRegistry<AnvilRecipe> registry) {
        ItemStack steelIngot = getHBMItem("ingot_steel");
        ItemStack carbonSteelIngot = getTFCItem("metal/ingot/high_carbon_steel");

        if (!steelIngot.isEmpty()) {
            ItemStack steelRod = getTFCItem("metal/rod/steel");
            if (!steelRod.isEmpty()) {
                ItemStack output = steelRod.copy();
                output.setCount(2);

                registry.register(new AnvilRecipe(
                        new ResourceLocation(MOD_ID, "ingot_steel_to_steel_rod"),
                        IIngredient.of(steelIngot),
                        output,
                        Metal.Tier.TIER_III,
                        null,
                        ForgeRule.HIT_ANY, ForgeRule.HIT_ANY, ForgeRule.HIT_ANY
                ));
            }
        }

        if (!carbonSteelIngot.isEmpty() && !steelIngot.isEmpty()) {
            registry.register(new AnvilRecipe(
                    new ResourceLocation(MOD_ID, "ingot_carbon_steel_to_steel_ingot"),
                    IIngredient.of(carbonSteelIngot),
                    steelIngot.copy(),
                    Metal.Tier.TIER_III,
                    null,
                    ForgeRule.HIT_ANY, ForgeRule.HIT_ANY, ForgeRule.HIT_ANY
            ));
        }
    }
}