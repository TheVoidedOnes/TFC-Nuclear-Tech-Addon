package com.voided.tfcnuclear.inventory.items;

import com.voided.tfcnuclear.TFCNuclear;
import com.voided.tfcnuclear.inventory.recipes.vanilla.OreCombineRecipes;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

@Mod.EventBusSubscriber(modid = TFCNuclear.MOD_ID)
public class ModItems {

    public static final Item HEMATITE_SLAG = new ItemHematiteSlag();
    public static final Item LIMONITE_SLAG = new ItemLimoniteSlag();
    public static final Item MAGNETITE_SLAG = new ItemMagnetiteSlag();
    public static final Item GALENA_SLAG = new ItemGalenaSlag();
    public static final Item GOLD_SLAG = new ItemGoldSlag();
    public static final Item COPPER_SLAG = new ItemCopperSlag();
    public static final Item CHROME_SLAG = new ItemChromeSlag();
    public static final Item ZINC_SLAG = new ItemZincSlag();
    public static final Item FIRED_CATALYST_CLAY = new ItemFiredCatalystClay();
    public static final Item ACID_ACTIVATED_CLAY = new ItemAcidActivatedClay();
    public static final Item KER_FJ = new ItemKerFJ();
    public static final Item INGOT_PHOSPHATE_CHARGE = new ItemIngotPhosphateCharge();
    public static final Item CRYSTAL_APATITE = new ItemCrystalApatite();

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                HEMATITE_SLAG,
                LIMONITE_SLAG,
                MAGNETITE_SLAG,
                GALENA_SLAG,
                GOLD_SLAG,
                COPPER_SLAG,
                CHROME_SLAG,
                ZINC_SLAG,
                FIRED_CATALYST_CLAY,
                ACID_ACTIVATED_CLAY,
                KER_FJ,
                INGOT_PHOSPHATE_CHARGE,
                CRYSTAL_APATITE
        );
    }
    public static void registerOreDict() {

        OreDictionary.registerOre("ingotPhosphateCharge", INGOT_PHOSPHATE_CHARGE);
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        event.getRegistry().register(
                new OreCombineRecipes().setRegistryName(TFCNuclear.MOD_ID, "ore_combine")
        );
    }
}