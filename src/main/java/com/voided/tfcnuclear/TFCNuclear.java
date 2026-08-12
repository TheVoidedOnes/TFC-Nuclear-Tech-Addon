package com.voided.tfcnuclear;

import com.voided.tfcnuclear.compat.tfc.ConfigOverwriteHandler;
import com.voided.tfcnuclear.compat.hbm.ItemRenamer;
import com.voided.tfcnuclear.compat.tfc.NetherPortalHandler;
import com.voided.tfcnuclear.inventory.fluids.TFCNuclearFluids;
import com.voided.tfcnuclear.inventory.handler.*;
import com.voided.tfcnuclear.inventory.items.CobaltMetalRegistration;
import com.voided.tfcnuclear.inventory.items.ModItems;
import com.voided.tfcnuclear.inventory.material.TFCNuclearMaterialShapes;
import com.voided.tfcnuclear.inventory.material.TFCNuclearMoldIntegration;
import com.voided.tfcnuclear.inventory.material.TFCNuclearMats;
import com.voided.tfcnuclear.inventory.recipes.tfc.TFCQuernRecipes;
import com.voided.tfcnuclear.inventory.recipes.vanilla.*;
import com.voided.tfcnuclear.proxy.CommonProxy;
import com.voided.tfcnuclear.world.OreSpawn.HBMOreSpawn;
import com.voided.tfcnuclear.world.OreSpawn.TFCOreSpawn;
import net.dries007.tfc.api.recipes.heat.HeatRecipe;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

import java.util.logging.Logger;

@Mod(modid = TFCNuclear.MOD_ID,
        name = TFCNuclear.NAME,
        version = TFCNuclear.VERSION,
        dependencies = "required-after:hbm;required-after:tfc")
public class TFCNuclear {
    public static final String MOD_ID = "tfcnuclear";
    public static final String NAME = "TFC Nuclear Tech Addon";
    public static final String VERSION = "2.0.0";

    @Mod.Instance
    public static TFCNuclear instance;

    public static final SimpleNetworkWrapper NETWORK =
            NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);

    @SidedProxy(clientSide = "com.voided.tfcnuclear.proxy.ClientProxy",
            serverSide = "com.voided.tfcnuclear.proxy.CommonProxy")
    public static CommonProxy proxy;
    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CobaltMetalRegistration.registerCobaltMetal();
        OreDictHandler.registerOreDict();
        TFCNuclearMaterialShapes.registerShapes();
        OreDictHandler.registerOreDict();
        TFCNuclearMats.init();
        TFCNuclearMoldIntegration.integrateMolds();
        proxy.preInit(event);
        OreDictHandler.registerOreDict();
        ItemSizeRegistry.registerSizes();
        HBMOreSpawn.generate(event);
        TFCOreSpawn.generate(event);
        TFCOreSpawn.clean(event);
        TFCNuclearFluids.writeFluidToConfig();
        ConfigOverwriteHandler.applyConfigOverwrites();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ItemRenamer());
        MinecraftForge.EVENT_BUS.register(new NetherPortalHandler());
        TFCQuernRecipes.addQuernRecipe();
        CraftingRecipesRegistry.addRecipes();
        FurnaceRecipesRegistry.addRecipes();
        SmeltingRecipeRemover.remove();
        HammerRecipesRegistry.register();
        OreDictHandler.registerOreDict();
        ModItems.registerOreDict();
        OreDictCleaner.cleanOreDict();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        OreDictHandler.registerOreDict();
    }
}