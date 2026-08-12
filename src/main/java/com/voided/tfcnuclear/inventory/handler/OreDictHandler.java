package com.voided.tfcnuclear.inventory.handler;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictHandler {

    private static final String[][] BINDINGS = {
            {"oreNormalHematite", "tfc:ore/hematite", "0"},
            {"orePoorHematite", "tfc:ore/hematite", "1"},
            {"oreRichHematite", "tfc:ore/hematite", "2"},
            {"oreSmallHematite", "tfc:ore/small/hematite", "0"},

            {"oreNormalMalachite", "tfc:ore/malachite", "0"},
            {"orePoorMalachite", "tfc:ore/malachite", "1"},
            {"oreRichMalachite", "tfc:ore/malachite", "2"},
            {"oreSmallMalachite", "tfc:ore/small/malachite", "0"},

            {"oreNormalMagnetite", "tfc:ore/magnetite", "0"},
            {"orePoorMagnetite", "tfc:ore/magnetite", "1"},
            {"oreRichMagnetite", "tfc:ore/magnetite", "2"},
            {"oreSmallMagnetite", "tfc:ore/small/magnetite", "0"},

            {"oreNormalLimonite", "tfc:ore/limonite", "0"},
            {"orePoorLimonite", "tfc:ore/limonite", "1"},
            {"oreRichLimonite", "tfc:ore/limonite", "2"},
            {"oreSmallLimonite", "tfc:ore/small/limonite", "0"},

            {"oreNormalNativeCopper", "tfc:ore/native_copper", "0"},
            {"orePoorNativeCopper", "tfc:ore/native_copper", "1"},
            {"oreRichNativeCopper", "tfc:ore/native_copper", "2"},
            {"oreSmallNativeCopper", "tfc:ore/small/native_copper", "0"},

            {"oreNormalCassiterite", "tfc:ore/cassiterite", "0"},
            {"orePoorCassiterite", "tfc:ore/cassiterite", "1"},
            {"oreRichCassiterite", "tfc:ore/cassiterite", "2"},
            {"oreSmallCassiterite", "tfc:ore/small/cassiterite", "0"},

            {"oreNormalGalena", "tfc:ore/galena", "0"},
            {"orePoorGalena", "tfc:ore/galena", "1"},
            {"oreRichGalena", "tfc:ore/galena", "2"},
            {"oreSmallGalena", "tfc:ore/small/galena", "0"},

            {"oreNormalTetrahedrite", "tfc:ore/tetrahedrite", "0"},
            {"orePoorTetrahedrite", "tfc:ore/tetrahedrite", "1"},
            {"oreRichTetrahedrite", "tfc:ore/tetrahedrite", "2"},
            {"oreSmallTetrahedrite", "tfc:ore/small/tetrahedrite", "0"},

            {"oreNormalChromite", "tfc:ore/garnierite", "0"},
            {"orePoorChromite", "tfc:ore/garnierite", "1"},
            {"oreRichChromite", "tfc:ore/garnierite", "2"},
            {"oreSmallChromite", "tfc:ore/small/garnierite", "0"},

            {"oreNormalSphalerite", "tfc:ore/sphalerite", "0"},
            {"orePoorSphalerite", "tfc:ore/sphalerite", "1"},
            {"oreRichSphalerite", "tfc:ore/sphalerite", "2"},
            {"oreSmallSphalerite", "tfc:ore/small/sphalerite", "0"},

            {"oreNormalMolybdenum", "tfc:ore/bismuth", "0"},
            {"orePoorMolybdenum", "tfc:ore/bismuth", "1"},
            {"oreRichMolybdenum", "tfc:ore/bismuth", "2"},
            {"oreSmallMolybdenum", "tfc:ore/small/bismuth", "0"},

            {"ingotMolybdenum", "tfc:metal/ingot/bismuth"},
            {"ingotDoubleMolybdenum", "tfc:metal/double_ingot/bismuth"},
            {"nuggetMolybdenum", "tfc:metal/nugget/bismuth"},
            {"dustMolybdenum", "tfc:metal/dust/bismuth"},

            {"ingotElasticCopper", "tfc:metal/ingot/bismuth_bronze"},
            {"ingotDoubleElasticCopper", "tfc:metal/double_ingot/bismuth_bronze"},
            {"nuggetElasticCopper", "tfc:metal/nugget/bismuth_bronze"},
            {"dustElasticCopper", "tfc:metal/dust/bismuth_bronze"},

            {"oreRedstoneTFC", "tfc:ore/cinnabar"},
            {"dustLapisLazuli", "hbm:powder_lapis"},
            {"oreLapisLazuliTFC", "tfc:ore/lapis_lazuli"},
            {"dustFlux", "hbm:powder_flux"},
            {"dustSaltpeter", "hbm:niter"},
            {"oreSaltpeterTFC", "tfc:ore/saltpeter"},
            {"oreCryoliteTFC", "tfc:ore/cryolite"},
            {"oreSulfurTFC", "tfc:ore/sulfur"},
            {"oreLigniteTFC", "tfc:ore/lignite"},
            {"oreBituminousCoal", "tfc:ore/bituminous_coal"},
            {"oreKimberlite", "tfc:ore/kimberlite"},
            {"oreApatite", "tfc:ore/microcline"},

            {"ingotChrome", "tfc:metal/ingot/nickel"},
            {"ingotDoubleChrome", "tfc:metal/double_ingot/nickel"},
            {"scrapChrome", "tfc:metal/scrap/nickel"},
            {"dustChrome", "tfc:metal/dust/nickel"},
            {"nuggetChrome", "tfc:metal/nugget/nickel"},
            {"sheetChrome", "tfc:metal/sheet/nickel"},
            {"sheetDoubleChrome", "tfc:metal/double_sheet/nickel"},

            {"pickHeadElasticCopper", "tfc:metal/pick_head/bismuth_bronze"},
            {"pickHeadCopper", "tfc:metal/pick_head/copper"},
            {"pickHeadSteel", "tfc:metal/pick_head/steel"},
            {"pickHeadWroughtIron", "tfc:metal/pick_head/wrought_iron"},
            {"pickHeadBlackSteel", "tfc:metal/pick_head/black_steel"},
            {"pickHeadBronze", "tfc:metal/pick_head/bronze"},
            {"pickHeadBlackBronze", "tfc:metal/pick_head/black_bronze"},
            {"pickHeadRedSteel", "tfc:metal/pick_head/red_steel"},
            {"pickHeadBlueSteel", "tfc:metal/pick_head/blue_steel"},

            {"swordBladeCopper", "tfc:metal/sword_blade/copper"},
            {"swordBladeSteel", "tfc:metal/sword_blade/steel"},
            {"swordBladeWroughtIron", "tfc:metal/sword_blade/wrought_iron"},
            {"swordBladeBlackSteel", "tfc:metal/sword_blade/black_steel"},
            {"swordBladeBronze", "tfc:metal/sword_blade/bronze"},
            {"swordBladeBlackBronze", "tfc:metal/sword_blade/black_bronze"},
            {"swordBladeElasticCopper", "tfc:metal/sword_blade/bismuth_bronze"},
            {"swordBladeRedSteel", "tfc:metal/sword_blade/red_steel"},
            {"swordBladeBlueSteel", "tfc:metal/sword_blade/blue_steel"},

            {"axeHeadCopper", "tfc:metal/axe_head/copper"},
            {"axeHeadSteel", "tfc:metal/axe_head/steel"},
            {"axeHeadWroughtIron", "tfc:metal/axe_head/wrought_iron"},
            {"axeHeadBlackSteel", "tfc:metal/axe_head/black_steel"},
            {"axeHeadBronze", "tfc:metal/axe_head/bronze"},
            {"axeHeadBlackBronze", "tfc:metal/axe_head/black_bronze"},
            {"axeHeadElasticCopper", "tfc:metal/axe_head/bismuth_bronze"},
            {"axeHeadRedSteel", "tfc:metal/axe_head/red_steel"},
            {"axeHeadBlueSteel", "tfc:metal/axe_head/blue_steel"},

            {"shovelHeadCopper", "tfc:metal/shovel_head/copper"},
            {"shovelHeadSteel", "tfc:metal/shovel_head/steel"},
            {"shovelHeadWroughtIron", "tfc:metal/shovel_head/wrought_iron"},
            {"shovelHeadBlackSteel", "tfc:metal/shovel_head/black_steel"},
            {"shovelHeadBronze", "tfc:metal/shovel_head/bronze"},
            {"shovelHeadBlackBronze", "tfc:metal/shovel_head/black_bronze"},
            {"shovelHeadElasticCopper", "tfc:metal/shovel_head/bismuth_bronze"},
            {"shovelHeadRedSteel", "tfc:metal/shovel_head/red_steel"},
            {"shovelHeadBlueSteel", "tfc:metal/shovel_head/blue_steel"},

            {"hoeHeadCopper", "tfc:metal/hoe_head/copper"},
            {"hoeHeadSteel", "tfc:metal/hoe_head/steel"},
            {"hoeHeadWroughtIron", "tfc:metal/hoe_head/wrought_iron"},
            {"hoeHeadBlackSteel", "tfc:metal/hoe_head/black_steel"},
            {"hoeHeadBronze", "tfc:metal/hoe_head/bronze"},
            {"hoeHeadBlackBronze", "tfc:metal/hoe_head/black_bronze"},
            {"hoeHeadElasticCopper", "tfc:metal/hoe_head/bismuth_bronze"},
            {"hoeHeadRedSteel", "tfc:metal/hoe_head/red_steel"},
            {"hoeHeadBlueSteel", "tfc:metal/hoe_head/blue_steel"},

            {"propickHeadCopper", "tfc:metal/propick_head/copper"},
            {"propickHeadSteel", "tfc:metal/propick_head/steel"},
            {"propickHeadWroughtIron", "tfc:metal/propick_head/wrought_iron"},
            {"propickHeadBlackSteel", "tfc:metal/propick_head/black_steel"},
            {"propickHeadBronze", "tfc:metal/propick_head/bronze"},
            {"propickHeadBlackBronze", "tfc:metal/propick_head/black_bronze"},
            {"propickHeadElasticCopper", "tfc:metal/propick_head/bismuth_bronze"},
            {"propickHeadRedSteel", "tfc:metal/propick_head/red_steel"},
            {"propickHeadBlueSteel", "tfc:metal/propick_head/blue_steel"},

            {"chiselHeadCopper", "tfc:metal/chisel_head/copper"},
            {"chiselHeadSteel", "tfc:metal/chisel_head/steel"},
            {"chiselHeadWroughtIron", "tfc:metal/chisel_head/wrought_iron"},
            {"chiselHeadBlackSteel", "tfc:metal/chisel_head/black_steel"},
            {"chiselHeadBronze", "tfc:metal/chisel_head/bronze"},
            {"chiselHeadBlackBronze", "tfc:metal/chisel_head/black_bronze"},
            {"chiselHeadElasticCopper", "tfc:metal/chisel_head/bismuth_bronze"},
            {"chiselHeadRedSteel", "tfc:metal/chisel_head/red_steel"},
            {"chiselHeadBlueSteel", "tfc:metal/chisel_head/blue_steel"},

            {"maceHeadCopper", "tfc:metal/mace_head/copper"},
            {"maceHeadSteel", "tfc:metal/mace_head/steel"},
            {"maceHeadWroughtIron", "tfc:metal/mace_head/wrought_iron"},
            {"maceHeadBlackSteel", "tfc:metal/mace_head/black_steel"},
            {"maceHeadBronze", "tfc:metal/mace_head/bronze"},
            {"maceHeadBlackBronze", "tfc:metal/mace_head/black_bronze"},
            {"maceHeadElasticCopper", "tfc:metal/mace_head/bismuth_bronze"},
            {"maceHeadRedSteel", "tfc:metal/mace_head/red_steel"},
            {"maceHeadBlueSteel", "tfc:metal/mace_head/blue_steel"},

            {"sawBladeCopper", "tfc:metal/saw_blade/copper"},
            {"sawBladeSteel", "tfc:metal/saw_blade/steel"},
            {"sawBladeWroughtIron", "tfc:metal/saw_blade/wrought_iron"},
            {"sawBladeBlackSteel", "tfc:metal/saw_blade/black_steel"},
            {"sawBladeBronze", "tfc:metal/saw_blade/bronze"},
            {"sawBladeBlackBronze", "tfc:metal/saw_blade/black_bronze"},
            {"sawBladeElasticCopper", "tfc:metal/saw_blade/bismuth_bronze"},
            {"sawBladeRedSteel", "tfc:metal/saw_blade/red_steel"},
            {"sawBladeBlueSteel", "tfc:metal/saw_blade/blue_steel"},

            {"javelinHeadCopper", "tfc:metal/javelin_head/copper"},
            {"javelinHeadSteel", "tfc:metal/javelin_head/steel"},
            {"javelinHeadWroughtIron", "tfc:metal/javelin_head/wrought_iron"},
            {"javelinHeadBlackSteel", "tfc:metal/javelin_head/black_steel"},
            {"javelinHeadBronze", "tfc:metal/javelin_head/bronze"},
            {"javelinHeadBlackBronze", "tfc:metal/javelin_head/black_bronze"},
            {"javelinHeadElasticCopper", "tfc:metal/javelin_head/bismuth_bronze"},
            {"javelinHeadRedSteel", "tfc:metal/javelin_head/red_steel"},
            {"javelinHeadBlueSteel", "tfc:metal/javelin_head/blue_steel"},

            {"hammerHeadCopper", "tfc:metal/hammer_head/copper"},
            {"hammerHeadSteel", "tfc:metal/hammer_head/steel"},
            {"hammerHeadWroughtIron", "tfc:metal/hammer_head/wrought_iron"},
            {"hammerHeadBlackSteel", "tfc:metal/hammer_head/black_steel"},
            {"hammerHeadBronze", "tfc:metal/hammer_head/bronze"},
            {"hammerHeadBlackBronze", "tfc:metal/hammer_head/black_bronze"},
            {"hammerHeadElasticCopper", "tfc:metal/hammer_head/bismuth_bronze"},
            {"hammerHeadRedSteel", "tfc:metal/hammer_head/red_steel"},
            {"hammerHeadBlueSteel", "tfc:metal/hammer_head/blue_steel"},

            {"knifeBladeCopper", "tfc:metal/knife_blade/copper"},
            {"knifeBladeSteel", "tfc:metal/knife_blade/steel"},
            {"knifeBladeWroughtIron", "tfc:metal/knife_blade/wrought_iron"},
            {"knifeBladeBlackSteel", "tfc:metal/knife_blade/black_steel"},
            {"knifeBladeBronze", "tfc:metal/knife_blade/bronze"},
            {"knifeBladeBlackBronze", "tfc:metal/knife_blade/black_bronze"},
            {"knifeBladeElasticCopper", "tfc:metal/knife_blade/bismuth_bronze"},
            {"knifeBladeRedSteel", "tfc:metal/knife_blade/red_steel"},
            {"knifeBladeBlueSteel", "tfc:metal/knife_blade/blue_steel"},

            {"scytheBladeCopper", "tfc:metal/scythe_blade/copper"},
            {"scytheBladeSteel", "tfc:metal/scythe_blade/steel"},
            {"scytheBladeWroughtIron", "tfc:metal/scythe_blade/wrought_iron"},
            {"scytheBladeBlackSteel", "tfc:metal/scythe_blade/black_steel"},
            {"scytheBladeBronze", "tfc:metal/scythe_blade/bronze"},
            {"scytheBladeBlackBronze", "tfc:metal/scythe_blade/black_bronze"},
            {"scytheBladeElasticCopper", "tfc:metal/scythe_blade/bismuth_bronze"},
            {"scytheBladeRedSteel", "tfc:metal/scythe_blade/red_steel"},
            {"scytheBladeBlueSteel", "tfc:metal/scythe_blade/blue_steel"},

            {"resinWood", "tfc:wood/log/willow"},
            {"resinWood", "tfc:wood/log/kapok"},
            {"resinWood", "tfc:wood/log/palm"},
            {"ingotPhosphorus", "hbm:ingot_phosphorus"},
            {"blockPhosphorus", "hbm:block_white_phosphorus"}
    };

    public static void registerOreDict() {
        for (String[] binding : BINDINGS) {
            String oreDictName = binding[0];
            String itemId = binding[1];
            int meta = binding.length > 2 ? Integer.parseInt(binding[2]) : 0;

            ItemStack stack = new ItemStack(
                    ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemId)),
                    1,
                    meta
            );

            if (!stack.isEmpty()) {
                OreDictionary.registerOre(oreDictName, stack);
            }
        }
    }
}