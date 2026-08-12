package com.voided.tfcnuclear.overwrite_contents.mixin.recipes;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.inventory.RecipesCommon;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.recipes.anvil.AnvilRecipes;
import com.hbm.inventory.recipes.anvil.AnvilRecipes.AnvilConstructionRecipe;
import com.hbm.inventory.recipes.anvil.AnvilRecipes.AnvilOutput;
import com.hbm.inventory.recipes.anvil.AnvilSmithingMold;
import com.hbm.inventory.recipes.anvil.AnvilSmithingRecipe;
import com.hbm.items.ItemEnums;
import com.hbm.items.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.hbm.inventory.OreDictManager.*;
import static com.voided.tfcnuclear.inventory.material.TFCNuclearMaterialShapes.TFC_PICK;

@Mixin(value = AnvilRecipes.class, remap = false)
public class MixinAnvilRecipes {

    @Shadow
    private static void registerConstructionRecipes() {}

    @Shadow
    private static void registerConstructionStamps() {}

    @Shadow
    private static void registerConstructionAmmo() {}

    @Shadow
    private static void registerConstructionSirens() {}

    @Shadow
    private static void registerConstructionUpgrades() {}

    @Shadow
    private static void registerConstructionRecycling() {}

    private static final List<String> REMOVE_INPUT_ORE_DICT = new ArrayList<>();
    private static final List<String> REMOVE_INPUT_ITEM_IDS = new ArrayList<>();
    private static final List<String> REMOVE_OUTPUT_ORE_DICT = new ArrayList<>();
    private static final List<String> REMOVE_OUTPUT_ITEM_IDS = new ArrayList<>();

    static {
        REMOVE_INPUT_ORE_DICT.add("ingotIron");
        REMOVE_OUTPUT_ORE_DICT.add("plateCopper");
        REMOVE_INPUT_ORE_DICT.add("dustDiamond");
        REMOVE_OUTPUT_ITEM_IDS.add("hbm:pump_electric");
        REMOVE_OUTPUT_ITEM_IDS.add("hbm:furnace_combination");
        REMOVE_OUTPUT_ITEM_IDS.add("hbm:machine_blast_furnace");
        REMOVE_OUTPUT_ITEM_IDS.add("hbm:heater_firebox");
        REMOVE_INPUT_ITEM_IDS.add("hbm:powder_diamond");
        REMOVE_OUTPUT_ITEM_IDS.add("hbm:machine_assembly_machine");
    }

    private static void removeByInputOreDict(String dictKey) {
        Iterator<AnvilConstructionRecipe> iterator = AnvilRecipes.constructionRecipes.iterator();
        int removed = 0;

        while (iterator.hasNext()) {
            AnvilConstructionRecipe recipe = iterator.next();
            boolean shouldRemove = false;

            for (RecipesCommon.AStack input : recipe.input) {
                if (input instanceof OreDictStack) {
                    OreDictStack oreStack = (OreDictStack) input;
                    if (oreStack.name != null && oreStack.name.equals(dictKey)) {
                        shouldRemove = true;
                        break;
                    }
                }
            }

            if (shouldRemove) {
                iterator.remove();
                removed++;
            }
        }
    }

    private static void removeByInputItemId(String itemId) {
        Iterator<AnvilConstructionRecipe> iterator = AnvilRecipes.constructionRecipes.iterator();
        int removed = 0;

        while (iterator.hasNext()) {
            AnvilConstructionRecipe recipe = iterator.next();
            boolean shouldRemove = false;

            for (RecipesCommon.AStack input : recipe.input) {
                if (input instanceof ComparableStack) {
                    ComparableStack compStack = (ComparableStack) input;
                    String id = Item.REGISTRY.getNameForObject(compStack.item).toString();
                    if (id.equals(itemId)) {
                        shouldRemove = true;
                        break;
                    }
                }
            }

            if (shouldRemove) {
                iterator.remove();
                removed++;
            }
        }
    }

    private static void removeByOutputOreDict(String dictKey) {
        Iterator<AnvilConstructionRecipe> iterator = AnvilRecipes.constructionRecipes.iterator();
        int removed = 0;

        while (iterator.hasNext()) {
            AnvilConstructionRecipe recipe = iterator.next();
            boolean shouldRemove = false;

            for (AnvilOutput output : recipe.output) {
                if (output.stack == null || output.stack.isEmpty()) continue;
                int[] oreIds = OreDictionary.getOreIDs(output.stack);
                for (int id : oreIds) {
                    String name = OreDictionary.getOreName(id);
                    if (name != null && name.equals(dictKey)) {
                        shouldRemove = true;
                        break;
                    }
                }
                if (shouldRemove) break;
            }

            if (shouldRemove) {
                iterator.remove();
                removed++;
            }
        }
    }

    private static void removeByOutputItemId(String itemId) {
        Iterator<AnvilConstructionRecipe> iterator = AnvilRecipes.constructionRecipes.iterator();
        int removed = 0;

        while (iterator.hasNext()) {
            AnvilConstructionRecipe recipe = iterator.next();
            boolean shouldRemove = false;

            for (AnvilOutput output : recipe.output) {
                if (output.stack == null || output.stack.isEmpty()) continue;
                String id = Item.REGISTRY.getNameForObject(output.stack.getItem()).toString();
                if (id.equals(itemId)) {
                    shouldRemove = true;
                    break;
                }
            }

            if (shouldRemove) {
                iterator.remove();
                removed++;
            }
        }
    }

    private static void removeRecipes() {
        for (String dictKey : REMOVE_INPUT_ORE_DICT) {
            removeByInputOreDict(dictKey);
        }
        for (String itemId : REMOVE_INPUT_ITEM_IDS) {
            removeByInputItemId(itemId);
        }
        for (String dictKey : REMOVE_OUTPUT_ORE_DICT) {
            removeByOutputOreDict(dictKey);
        }
        for (String itemId : REMOVE_OUTPUT_ITEM_IDS) {
            removeByOutputItemId(itemId);
        }
    }

    @Inject(method = "registerConstruction", at = @At("RETURN"), remap = false)
    private static void injectCustomConstructionRecipes(CallbackInfo ci) {
        boolean exp = GeneralConfig.enableExpensiveMode;
        removeRecipes();

        AnvilRecipes.constructionRecipes.add(new AnvilConstructionRecipe(
                new OreDictStack("ingotWroughtIron", 1),
                new AnvilOutput(new ItemStack(ModItems.plate_iron, 1))
        ).setTier(3));

        AnvilRecipes.constructionRecipes.add(new AnvilConstructionRecipe(
                new OreDictStack("ingotBronze", 1),
                new AnvilOutput(new ItemStack(ModItems.plate_copper, 1))
        ).setTier(3));

        AnvilRecipes.constructionRecipes.add(new AnvilConstructionRecipe(
                new OreDictStack("dustDiamond", 1),
                new AnvilOutput(new ItemStack(Item.getByNameOrId("tfc:gem/diamond"), 1, 2))
        ).setTier(3));

        AnvilRecipes.constructionRecipes.add(new AnvilConstructionRecipe(
                new RecipesCommon.AStack[] {
                        new OreDictStack("stoneBrick", 16),
                        new ComparableStack(ModBlocks.brick_fire, 6),
                        new OreDictStack("sheetDoubleWroughtIron", 6),
                        new OreDictStack("boltSteel", 16),
                        new OreDictStack("shellTitanium", 4)
                },
                new AnvilOutput(new ItemStack(ModBlocks.machine_annihilator))
        ).setTier(2));

        AnvilRecipes.constructionRecipes.add(new AnvilConstructionRecipe(
                new RecipesCommon.AStack[] {
                        new OreDictStack("stone", 8),
                        new OreDictStack(STEEL.plate(), 2),
                        new OreDictStack("ingotWroughtIron", 4)
                },
                new AnvilOutput(new ItemStack(ModBlocks.machine_ashpit))
        ).setTier(2));

        AnvilRecipes.constructionRecipes.add(new AnvilConstructionRecipe(
                new RecipesCommon.AStack[] {
                        new OreDictStack("stoneBrick", 16),
                        new ComparableStack(ModBlocks.brick_fire, 4),
                        new ComparableStack(ModBlocks.steel_grate, 16),
                        new OreDictStack("ingotWroughtIron", 6),
                        new OreDictStack(STEEL.plate(), 16),
                        new OreDictStack(CU.ingot(), 8)
                },
                new AnvilOutput(new ItemStack(ModBlocks.furnace_steel))
        ).setTier(2));

        AnvilRecipes.constructionRecipes.add(new AnvilConstructionRecipe(
                new RecipesCommon.AStack[] {
                        new OreDictStack("stoneBrick", 8),
                        new ComparableStack(ModBlocks.brick_fire, 8),
                        new OreDictStack("ingotWroughtIron", 6),
                        new OreDictStack(CU.plate(), 8)
                },
                new AnvilOutput(new ItemStack(ModBlocks.machine_rotary_furnace))
        ).setTier(2));

        AnvilRecipes.constructionRecipes.add(new AnvilConstructionRecipe(
                new RecipesCommon.AStack[] {
                        new OreDictStack(KEY_PLANKS, 16),
                        new OreDictStack(STEEL.plate(), 6),
                        new OreDictStack(CU.ingot(), 8),
                        new OreDictStack("ingotWroughtIron", 4),
                        new ComparableStack(ModItems.sawblade)
                },
                new AnvilOutput(new ItemStack(ModBlocks.machine_sawmill))
        ).setTier(2));

        AnvilRecipes.constructionRecipes.add(new AnvilConstructionRecipe(
                new RecipesCommon.AStack[] {
                        new OreDictStack(STEEL.plate(), 4),
                        new OreDictStack("ingotWroughtIron", 12),
                        new OreDictStack(CU.ingot(), 2),
                        new ComparableStack(ModItems.circuit, 2, ItemEnums.EnumCircuitType.VACUUM_TUBE.ordinal()),
                        new ComparableStack(ModItems.sawblade)
                },
                new AnvilOutput(new ItemStack(ModBlocks.machine_autosaw))
        ).setTier(2));

        AnvilRecipes.constructionRecipes.add(new AnvilConstructionRecipe(
                new RecipesCommon.AStack[] {
                        new ComparableStack(ModItems.mold_base),
                        new OreDictStack("ingotWroughtIron", 2)
                },
                new AnvilOutput(new ItemStack(ModItems.mold, 1, 16))
        ).setTier(1));

        AnvilRecipes.constructionRecipes.add(new AnvilConstructionRecipe(
                new RecipesCommon.AStack[] {
                        new ComparableStack(ModItems.mold_base),
                        new OreDictStack("ingotWroughtIron", 2)
                },
                new AnvilOutput(new ItemStack(ModItems.mold, 1, 17))
        ).setTier(1));

        AnvilRecipes.constructionRecipes.add(new AnvilConstructionRecipe(
                new RecipesCommon.AStack[] {
                        new OreDictStack("stoneBrick", 6),
                        new ComparableStack(ModBlocks.brick_fire, 20),
                        new OreDictStack(CU.plate(), 4),
                        new ComparableStack(Item.getByNameOrId("tfc:metal/double_sheet/steel"), 8),
                        new OreDictStack("tuyere", 2),
                },
                new AnvilOutput(new ItemStack(ModBlocks.machine_blast_furnace))
        ).setTier(2));

        AnvilRecipes.constructionRecipes.add(new AnvilConstructionRecipe(
                new RecipesCommon.AStack[] {
                        new OreDictStack("stoneBrick", 8),
                        new OreDictStack(STEEL.plate(), 16),
                        new OreDictStack(PB.pipe(), 4),
                        new ComparableStack(ModItems.motor, 2),
                        new ComparableStack(ModItems.circuit, 4, ItemEnums.EnumCircuitType.VACUUM_TUBE.ordinal())
                },
                new AnvilOutput(new ItemStack(ModBlocks.pump_electric))
        ).setTier(1));

        AnvilRecipes.constructionRecipes.add(new AnvilConstructionRecipe(
                new RecipesCommon.AStack[] {
                        new OreDictStack("stoneBrick", 8),
                        new OreDictStack(KEY_LOG, 16),
                        new OreDictStack(CU.plateCast(), 2),
                        new OreDictStack("sheetDoubleSteel", 2),
                        new ComparableStack(ModBlocks.brick_fire, 8)
                },
                new AnvilOutput(new ItemStack(ModBlocks.furnace_combination))
        ).setTier(2));

        AnvilRecipes.constructionRecipes.add(new AnvilConstructionRecipe(
                new RecipesCommon.AStack[] {
                        new OreDictStack("ingotWroughtIron", 6),
                        new OreDictStack(STEEL.plate(), 8),
                        new OreDictStack(CU.ingot(), 8),
                        new ComparableStack(ModItems.coil_copper, 4)
                },
                new AnvilOutput(new ItemStack(ModBlocks.heater_firebox))
        ).setTier(2));

        AnvilRecipes.constructionRecipes.add(new AnvilConstructionRecipe(
                new RecipesCommon.AStack[] {
                        new OreDictStack("blockSteel", 1),
                        new ComparableStack(Blocks.PISTON, 1),
                        new OreDictStack("boltSteel", 12),
                        new ComparableStack(Item.getByNameOrId("tfc:metal/double_sheet/steel"), 2),
                        new ComparableStack(Items.BRICK, 8),
                },
                new AnvilOutput(new ItemStack(ModBlocks.machine_press))
        ).setTier(2));

        AnvilRecipes.constructionRecipes.add(new AnvilConstructionRecipe(
                new RecipesCommon.AStack[] {
                        new ComparableStack(Blocks.BRICK_BLOCK, 2),
                        new OreDictStack(STEEL.pipe(), 2),
                        new ComparableStack(ModItems.plate_steel, 6),
                        new OreDictStack("ingotWroughtIron", 4),
                        new ComparableStack(ModItems.coil_copper, 2),
                        new ComparableStack(ModItems.plate_polymer, 2)
                },
                new AnvilOutput(new ItemStack(ModBlocks.machine_wood_burner))
        ).setTier(2));

        AnvilRecipes.constructionRecipes.add(new AnvilConstructionRecipe(
                new RecipesCommon.AStack[] {
                        new OreDictStack("stoneBrick", 4),
                        new ComparableStack(Blocks.BRICK_BLOCK, 2),
                        new ComparableStack(ModBlocks.steel_grate, 2),
                        new OreDictStack("ingotWroughtIron", 8),
                },
                new AnvilOutput(new ItemStack(ModBlocks.furnace_iron))
        ).setTier(2));

        AnvilRecipes.constructionRecipes.add(new AnvilConstructionRecipe(
                new RecipesCommon.AStack[] {
                        new ComparableStack(Item.getByNameOrId("tfc:metal/double_sheet/steel"), 2),
                        new OreDictStack("boltSteel", 8),
                        new OreDictStack(CU.plateCast(), 2),
                        new ComparableStack(ModItems.coil_tungsten, 4),
                        new ComparableStack(ModItems.plate_polymer, 4),
                        new ComparableStack(ModItems.circuit, 1, ItemEnums.EnumCircuitType.ANALOG),
                },
                new AnvilOutput(new ItemStack(ModBlocks.machine_electric_furnace_off))
        ).setTier(2));

        AnvilRecipes.constructionRecipes.add(new AnvilConstructionRecipe(
                new RecipesCommon.AStack[] {
                        new OreDictStack("sheetDoubleSteel", 4),
                        new OreDictStack(CU.plate(), 4),
                        new OreDictStack("shellTitanium", 2),
                        new ComparableStack(ModItems.motor, 2),
                        exp ? new ComparableStack(ModItems.circuit, 2, ItemEnums.EnumCircuitType.ANALOG) : new ComparableStack(ModItems.circuit, 4, ItemEnums.EnumCircuitType.VACUUM_TUBE)
                },
                new AnvilOutput(new ItemStack(ModBlocks.machine_assembly_machine))
        ).setTier(2));

        AnvilRecipes.constructionRecipes.add(new AnvilConstructionRecipe(
                new RecipesCommon.AStack[] {
                        new OreDictStack(STEEL.plateCast(), 4),
                        new OreDictStack("sheetWroughtIron", 6),
                        new OreDictStack(W.ingot(), 8),
                        new ComparableStack(ModBlocks.machine_transformer, 1),
                        new ComparableStack(ModItems.arc_electrode, 2),
                        new OreDictStack(CU.wireFine(), 16)
                },
                new AnvilOutput(new ItemStack(ModBlocks.machine_arc_welder))
        ).setTier(2));

        AnvilRecipes.constructionRecipes.add(new AnvilConstructionRecipe(
                new RecipesCommon.AStack[] {
                        new OreDictStack(STEEL.plateCast(), 2),
                        new OreDictStack("sheetWroughtIron", 4),
                        new ComparableStack(ModItems.coil_copper, 4),
                        new OreDictStack(W.bolt(), 4),
                        new ComparableStack(ModItems.circuit, 2, ItemEnums.EnumCircuitType.VACUUM_TUBE.ordinal())
                },
                new AnvilOutput(new ItemStack(ModBlocks.machine_soldering_station))
        ).setTier(2));

        AnvilRecipes.smithingRecipes.add(new AnvilSmithingMold(29, new OreDictStack("pickHead"), new OreDictStack("pickHead")));
        AnvilRecipes.smithingRecipes.add(new AnvilSmithingMold(30, new OreDictStack("axeHead"), new OreDictStack("axeHead")));
        AnvilRecipes.smithingRecipes.add(new AnvilSmithingMold(31, new OreDictStack("shovelHead"), new OreDictStack("shovelHead")));
        AnvilRecipes.smithingRecipes.add(new AnvilSmithingMold(32, new OreDictStack("hoeHead"), new OreDictStack("hoeHead")));
        AnvilRecipes.smithingRecipes.add(new AnvilSmithingMold(33, new OreDictStack("swordBlade"), new OreDictStack("swordBlade")));
        AnvilRecipes.smithingRecipes.add(new AnvilSmithingMold(34, new OreDictStack("knifeBlade"), new OreDictStack("knifeBlade")));
        AnvilRecipes.smithingRecipes.add(new AnvilSmithingMold(35, new OreDictStack("maceHead"), new OreDictStack("maceHead")));
        AnvilRecipes.smithingRecipes.add(new AnvilSmithingMold(36, new OreDictStack("sawBlade"), new OreDictStack("sawBlade")));
        AnvilRecipes.smithingRecipes.add(new AnvilSmithingMold(37, new OreDictStack("chiselHead"), new OreDictStack("chiselHead")));
        AnvilRecipes.smithingRecipes.add(new AnvilSmithingMold(38, new OreDictStack("propickHead"), new OreDictStack("propickHead")));
        AnvilRecipes.smithingRecipes.add(new AnvilSmithingMold(39, new OreDictStack("javelinHead"), new OreDictStack("javelinHead")));
        AnvilRecipes.smithingRecipes.add(new AnvilSmithingMold(40, new OreDictStack("hammerHead"), new OreDictStack("hammerHead")));
        AnvilRecipes.smithingRecipes.add(new AnvilSmithingMold(41, new OreDictStack("scytheBlade"), new OreDictStack("scytheBlade")));
    }
}