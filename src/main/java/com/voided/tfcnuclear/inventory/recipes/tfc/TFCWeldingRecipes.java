package com.voided.tfcnuclear.inventory.recipes.tfc;

import net.dries007.tfc.api.recipes.WeldingRecipe;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class TFCWeldingRecipes {

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
    public static void onWeldingRegistry(RegistryEvent.Register<WeldingRecipe> event) {
        IForgeRegistry<WeldingRecipe> registry = event.getRegistry();
        addBismuthWeldingRecipes(registry);
        addBismuthBronzeWeldingRecipes(registry);
        addNickelWeldingRecipes(registry);
        addCobaltWeldingRecipes(registry);
    }

    private static void addBismuthWeldingRecipes(IForgeRegistry<WeldingRecipe> registry) {
        ItemStack bismuthIngot = getTFCItem("metal/ingot/bismuth");
        ItemStack bismuthDoubleIngot = getTFCItem("metal/double_ingot/bismuth");
        ItemStack bismuthSheet = getTFCItem("metal/sheet/bismuth");
        ItemStack bismuthDoubleSheet = getTFCItem("metal/double_sheet/bismuth");

        if (!bismuthIngot.isEmpty() && !bismuthDoubleIngot.isEmpty()) {
            registry.register(new WeldingRecipe(
                    new ResourceLocation(MOD_ID, "bismuth_ingot_to_double"),
                    IIngredient.of(bismuthIngot),
                    IIngredient.of(bismuthIngot),
                    bismuthDoubleIngot,
                    Metal.Tier.TIER_I
            ));
        }

        if (!bismuthSheet.isEmpty() && !bismuthDoubleSheet.isEmpty()) {
            registry.register(new WeldingRecipe(
                    new ResourceLocation(MOD_ID, "bismuth_sheet_to_double"),
                    IIngredient.of(bismuthSheet),
                    IIngredient.of(bismuthSheet),
                    bismuthDoubleSheet,
                    Metal.Tier.TIER_I
            ));
        }
    }

    private static void addBismuthBronzeWeldingRecipes(IForgeRegistry<WeldingRecipe> registry) {
        ItemStack bronzeIngot = getTFCItem("metal/ingot/bismuth_bronze");
        ItemStack bronzeDoubleIngot = getTFCItem("metal/double_ingot/bismuth_bronze");
        ItemStack bronzeSheet = getTFCItem("metal/sheet/bismuth_bronze");
        ItemStack bronzeDoubleSheet = getTFCItem("metal/double_sheet/bismuth_bronze");
        ItemStack bronzeKnifeBlade = getTFCItem("metal/knife_blade/bismuth_bronze");
        ItemStack bronzeShears = getTFCItem("metal/shears/bismuth_bronze");
        ItemStack bronzeUnfinishedHelmet = getTFCItem("metal/unfinished_helmet/bismuth_bronze");
        ItemStack bronzeHelmet = getTFCItem("metal/helmet/bismuth_bronze");
        ItemStack bronzeUnfinishedChestplate = getTFCItem("metal/unfinished_chestplate/bismuth_bronze");
        ItemStack bronzeChestplate = getTFCItem("metal/chestplate/bismuth_bronze");
        ItemStack bronzeUnfinishedGreaves = getTFCItem("metal/unfinished_greaves/bismuth_bronze");
        ItemStack bronzeGreaves = getTFCItem("metal/greaves/bismuth_bronze");
        ItemStack bronzeUnfinishedBoots = getTFCItem("metal/unfinished_boots/bismuth_bronze");
        ItemStack bronzeBoots = getTFCItem("metal/boots/bismuth_bronze");

        if (!bronzeIngot.isEmpty() && !bronzeDoubleIngot.isEmpty()) {
            registry.register(new WeldingRecipe(
                    new ResourceLocation(MOD_ID, "bismuth_bronze_ingot_to_double"),
                    IIngredient.of(bronzeIngot),
                    IIngredient.of(bronzeIngot),
                    bronzeDoubleIngot,
                    Metal.Tier.TIER_I
            ));
        }

        if (!bronzeSheet.isEmpty() && !bronzeDoubleSheet.isEmpty()) {
            registry.register(new WeldingRecipe(
                    new ResourceLocation(MOD_ID, "bismuth_bronze_sheet_to_double"),
                    IIngredient.of(bronzeSheet),
                    IIngredient.of(bronzeSheet),
                    bronzeDoubleSheet,
                    Metal.Tier.TIER_I
            ));
        }

        if (!bronzeKnifeBlade.isEmpty() && !bronzeShears.isEmpty()) {
            registry.register(new WeldingRecipe(
                    new ResourceLocation(MOD_ID, "bismuth_bronze_knife_blades_to_shears"),
                    IIngredient.of(bronzeKnifeBlade),
                    IIngredient.of(bronzeKnifeBlade),
                    bronzeShears,
                    Metal.Tier.TIER_I
            ));
        }

        if (!bronzeUnfinishedHelmet.isEmpty() && !bronzeSheet.isEmpty() && !bronzeHelmet.isEmpty()) {
            registry.register(new WeldingRecipe(
                    new ResourceLocation(MOD_ID, "bismuth_bronze_helmet"),
                    IIngredient.of(bronzeUnfinishedHelmet),
                    IIngredient.of(bronzeSheet),
                    bronzeHelmet,
                    Metal.Tier.TIER_I
            ));
        }

        if (!bronzeUnfinishedChestplate.isEmpty() && !bronzeSheet.isEmpty() && !bronzeChestplate.isEmpty()) {
            registry.register(new WeldingRecipe(
                    new ResourceLocation(MOD_ID, "bismuth_bronze_chestplate"),
                    IIngredient.of(bronzeUnfinishedChestplate),
                    IIngredient.of(bronzeSheet),
                    bronzeChestplate,
                    Metal.Tier.TIER_I
            ));
        }

        if (!bronzeUnfinishedGreaves.isEmpty() && !bronzeSheet.isEmpty() && !bronzeGreaves.isEmpty()) {
            registry.register(new WeldingRecipe(
                    new ResourceLocation(MOD_ID, "bismuth_bronze_greaves"),
                    IIngredient.of(bronzeUnfinishedGreaves),
                    IIngredient.of(bronzeSheet),
                    bronzeGreaves,
                    Metal.Tier.TIER_I
            ));
        }

        if (!bronzeUnfinishedBoots.isEmpty() && !bronzeSheet.isEmpty() && !bronzeBoots.isEmpty()) {
            registry.register(new WeldingRecipe(
                    new ResourceLocation(MOD_ID, "bismuth_bronze_boots"),
                    IIngredient.of(bronzeUnfinishedBoots),
                    IIngredient.of(bronzeSheet),
                    bronzeBoots,
                    Metal.Tier.TIER_I
            ));
        }
    }

    private static void addNickelWeldingRecipes(IForgeRegistry<WeldingRecipe> registry) {
        ItemStack nickelIngot = getTFCItem("metal/ingot/nickel");
        ItemStack nickelDoubleIngot = getTFCItem("metal/double_ingot/nickel");
        ItemStack nickelSheet = getTFCItem("metal/sheet/nickel");
        ItemStack nickelDoubleSheet = getTFCItem("metal/double_sheet/nickel");
        ItemStack nickelKnifeBlade = getTFCItem("metal/knife_blade/nickel");
        ItemStack nickelShears = getTFCItem("metal/shears/nickel");

        if (!nickelIngot.isEmpty() && !nickelDoubleIngot.isEmpty()) {
            registry.register(new WeldingRecipe(
                    new ResourceLocation(MOD_ID, "nickel_ingot_to_double"),
                    IIngredient.of(nickelIngot),
                    IIngredient.of(nickelIngot),
                    nickelDoubleIngot,
                    Metal.Tier.TIER_I
            ));
        }

        if (!nickelSheet.isEmpty() && !nickelDoubleSheet.isEmpty()) {
            registry.register(new WeldingRecipe(
                    new ResourceLocation(MOD_ID, "nickel_sheet_to_double"),
                    IIngredient.of(nickelSheet),
                    IIngredient.of(nickelSheet),
                    nickelDoubleSheet,
                    Metal.Tier.TIER_I
            ));
        }

        if (!nickelKnifeBlade.isEmpty() && !nickelShears.isEmpty()) {
            registry.register(new WeldingRecipe(
                    new ResourceLocation(MOD_ID, "nickel_knife_blades_to_shears"),
                    IIngredient.of(nickelKnifeBlade),
                    IIngredient.of(nickelKnifeBlade),
                    nickelShears,
                    Metal.Tier.TIER_I
            ));
        }
    }

    private static void addCobaltWeldingRecipes(IForgeRegistry<WeldingRecipe> registry) {
        ItemStack blackSteelPick = getTFCItem("metal/pick/black_steel");
        ItemStack blackSteelAxe = getTFCItem("metal/axe/black_steel");
        ItemStack blackSteelHoe = getTFCItem("metal/hoe/black_steel");
        ItemStack blackSteelSword = getTFCItem("metal/sword/black_steel");
        ItemStack blackSteelShovel = getTFCItem("metal/shovel/black_steel");
        ItemStack blackSteelHelmet = getTFCItem("metal/helmet/black_steel");
        ItemStack blackSteelChestplate = getTFCItem("metal/chestplate/black_steel");
        ItemStack blackSteelGreaves = getTFCItem("metal/greaves/black_steel");
        ItemStack blackSteelBoots = getTFCItem("metal/boots/black_steel");

        ItemStack cobaltIngot = getHBMItem("ingot_cobalt");
        ItemStack cobaltPickaxe = getHBMItem("cobalt_pickaxe");
        ItemStack cobaltAxe = getHBMItem("cobalt_axe");
        ItemStack cobaltHoe = getHBMItem("cobalt_hoe");
        ItemStack cobaltSword = getHBMItem("cobalt_sword");
        ItemStack cobaltShovel = getHBMItem("cobalt_shovel");
        ItemStack cobaltHelmet = getHBMItem("cobalt_helmet");
        ItemStack cobaltPlate = getHBMItem("cobalt_plate");
        ItemStack cobaltLegs = getHBMItem("cobalt_legs");
        ItemStack cobaltBoots = getHBMItem("cobalt_boots");

        if (cobaltIngot.isEmpty()) {
            return;
        }

        registerCobaltToolRecipe(registry, blackSteelPick, cobaltIngot, cobaltPickaxe, "cobalt_pickaxe");
        registerCobaltToolRecipe(registry, blackSteelAxe, cobaltIngot, cobaltAxe, "cobalt_axe");
        registerCobaltToolRecipe(registry, blackSteelHoe, cobaltIngot, cobaltHoe, "cobalt_hoe");
        registerCobaltToolRecipe(registry, blackSteelSword, cobaltIngot, cobaltSword, "cobalt_sword");
        registerCobaltToolRecipe(registry, blackSteelShovel, cobaltIngot, cobaltShovel, "cobalt_shovel");

        registerCobaltArmorRecipe(registry, blackSteelHelmet, cobaltIngot, cobaltHelmet, "cobalt_helmet");
        registerCobaltArmorRecipe(registry, blackSteelChestplate, cobaltIngot, cobaltPlate, "cobalt_plate");
        registerCobaltArmorRecipe(registry, blackSteelGreaves, cobaltIngot, cobaltLegs, "cobalt_legs");
        registerCobaltArmorRecipe(registry, blackSteelBoots, cobaltIngot, cobaltBoots, "cobalt_boots");
    }

    private static void registerCobaltToolRecipe(IForgeRegistry<WeldingRecipe> registry, ItemStack baseTool, ItemStack cobaltIngot, ItemStack result, String name) {
        if (baseTool.isEmpty() || result.isEmpty()) {
            return;
        }

        ItemStack cobaltStack = cobaltIngot.copy();
        cobaltStack.setCount(1);

        registry.register(new WeldingRecipe(
                new ResourceLocation(MOD_ID, name),
                IIngredient.of(baseTool),
                IIngredient.of(cobaltStack),
                result,
                Metal.Tier.TIER_I
        ));
    }

    private static void registerCobaltArmorRecipe(IForgeRegistry<WeldingRecipe> registry, ItemStack baseArmor, ItemStack cobaltIngot, ItemStack result, String name) {
        if (baseArmor.isEmpty() || result.isEmpty()) {
            return;
        }

        ItemStack cobaltStack = cobaltIngot.copy();
        cobaltStack.setCount(1);

        registry.register(new WeldingRecipe(
                new ResourceLocation(MOD_ID, name),
                IIngredient.of(baseArmor),
                IIngredient.of(cobaltStack),
                result,
                Metal.Tier.TIER_I
        ));
    }
}