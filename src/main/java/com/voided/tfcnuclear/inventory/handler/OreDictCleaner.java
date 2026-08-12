package com.voided.tfcnuclear.inventory.handler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = "tfcnuclear")
public class OreDictCleaner {

    private static final Set<String> TFC_BLOCKED_ORES = new HashSet<>(Arrays.asList(
            "ingotBismuth", "dustBismuth", "nuggetBismuth",
            "ingotBismuthBronze", "dustBismuthBronze", "nuggetBismuthBronze", "ingotAnyBismoidBronze",
            "ingotSteel", "dustSteel",
            "dustGold",
            "ingotCopper", "dustCopper",
            "ingotLead", "dustLead", "nuggetLead",
            "gemCryolite", "gemLapis", "dustLapis", "dustSulfur", "dustLapisLazuli", "dyeBlue", "dustFlux", "dustSaltpeter",
            "gemLignite", "gemCoal", "dustCharcoal", "dyeBlack", "gemCinnabar", "gemMicrocline", "ingotCobalt",
            "dustNickel", "ingotNickel", "ingotDoubleNickel", "scrapNickel", "nuggetNickel", "sheetNickel", "sheetDoubleNickel"
    ));

    private static final Set<String> VANILLA_BLOCKED_ORES = new HashSet<>(Arrays.asList(
            "ingotGold", "nuggetGold",
            "gemLapis", "gemDiamond",
            "cobblestone"
    ));

    private static final Set<String> HBMSPACE_EXTRA_ORES = new HashSet<>(Arrays.asList(
            "ingotZinc", "dustZinc", "nuggetZinc"
    ));

    private static boolean cleaned = false;

    public static void cleanOreDict() {
        if (cleaned) {
            return;
        }

        cleanTfcOres();
        cleanVanillaOres();

        cleaned = true;
    }

    private static void cleanTfcOres() {
        Set<String> oresToRemove = new HashSet<>(TFC_BLOCKED_ORES);

        if (Loader.isModLoaded("hbmspace")) {
            oresToRemove.addAll(HBMSPACE_EXTRA_ORES);
        }

        for (String oreName : oresToRemove) {
            if (!OreDictionary.doesOreNameExist(oreName)) {
                continue;
            }

            List<ItemStack> allOres = OreDictionary.getOres(oreName, false);
            List<ItemStack> filtered = new ArrayList<>();

            for (ItemStack stack : allOres) {
                if (stack.isEmpty()) {
                    continue;
                }

                if (stack.getItem().getRegistryName() == null) {
                    filtered.add(stack);
                    continue;
                }

                String modId = stack.getItem().getRegistryName().getNamespace();

                if (!"tfc".equals(modId) && !"tfcnuclear".equals(modId)) {
                    filtered.add(stack);
                }
            }

            allOres.clear();
            allOres.addAll(filtered);
        }
    }

    private static void cleanVanillaOres() {
        for (String oreName : VANILLA_BLOCKED_ORES) {
            if (!OreDictionary.doesOreNameExist(oreName)) {
                continue;
            }

            List<ItemStack> allOres = OreDictionary.getOres(oreName, false);
            List<ItemStack> filtered = new ArrayList<>();

            for (ItemStack stack : allOres) {
                if (stack.isEmpty()) {
                    continue;
                }

                if (stack.getItem().getRegistryName() == null) {
                    filtered.add(stack);
                    continue;
                }

                String modId = stack.getItem().getRegistryName().getNamespace();

                if (!"minecraft".equals(modId) && !"tfcnuclear".equals(modId)) {
                    filtered.add(stack);
                }
            }

            allOres.clear();
            allOres.addAll(filtered);
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!cleaned) {
            cleanOreDict();
        }
    }
}
