package com.voided.tfcnuclear.compat.hbm;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;

public class ItemRenamer {

    private static final Map<String, String> RENAME_MAP = new HashMap<>();
    private static final Map<String, String> RENAME_MAP_ANY_META = new HashMap<>();

    static {
        addRename("hbm:plate_copper", 0, "hbm_plate_bronze");
        addRename("hbm:shell", 2900, "hbm_shell_bronze");
        addRename("hbm:pipe", 2900, "hbm_pipe_bronze");
        addRename("hbm:plate_cast", 2900, "hbm_plate_cast_bronze");
        addRename("hbm:plate_welded", 2900, "hbm_plate_welded_bronze");

        addRename("hbm:plate_iron", 0, "hbm_plate_wrought_iron");
        addRename("hbm:pipe", 2600, "hbm_pipe_wrought_iron");
        addRename("hbm:plate_cast", 2600, "hbm_plate_cast_wrought_iron");
        addRename("hbm:plate_welded", 2600, "hbm_plate_welded_wrought_iron");
        addRename("hbm:stamp_iron_flat", 0, "hbm_stamp_wrought_iron_flat");
        addRename("hbm:stamp_iron_plate", 0, "hbm_stamp_wrought_iron_plate");
        addRename("hbm:stamp_iron_wire", 0, "hbm_stamp_wrought_iron_wire");
        addRename("hbm:stamp_iron_circuit", 0, "hbm_stamp_wrought_iron_circuit");
        addRename("hbm:powder_iron", 0, "hbm_powder_raw_iron");

        addRename("minecraft:iron_ingot", 0, "mc_raw_iron_ingot");
        addRename("minecraft:iron_block", 0, "mc_raw_iron_block");
        addRename("minecraft:iron_nugget", 0, "mc_raw_iron_nugget");
    }

    private static void addRename(String itemId, int meta, String keyPart) {
        String fullKey = itemId + ":" + meta;
        RENAME_MAP.put(fullKey, "item." + keyPart);
    }

    private static void addRenameAnyMeta(String itemId, String keyPart) {
        RENAME_MAP_ANY_META.put(itemId, "item." + keyPart);
    }

    public static String getLocalizedName(ItemStack stack) {
        String translationKey = getTranslationKey(stack);
        if (translationKey != null) {
            return I18n.translateToLocal(translationKey);
        }
        return null;
    }

    public static String getLocalizedNameFormatted(ItemStack stack, Object... params) {
        String translationKey = getTranslationKey(stack);
        if (translationKey != null) {
            return I18n.translateToLocalFormatted(translationKey, params);
        }
        return null;
    }

    private static String getTranslationKey(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return null;
        }

        String itemId = stack.getItem().getRegistryName().toString();
        int meta = stack.getMetadata();

        String fullKey = itemId + ":" + meta;
        if (RENAME_MAP.containsKey(fullKey)) {
            return RENAME_MAP.get(fullKey);
        }

        if (RENAME_MAP_ANY_META.containsKey(itemId)) {
            return RENAME_MAP_ANY_META.get(itemId);
        }

        return null;
    }

    public static boolean hasRename(ItemStack stack) {
        return getTranslationKey(stack) != null;
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        String localizedName = getLocalizedName(stack);

        if (localizedName != null && !event.getToolTip().isEmpty()) {
            event.getToolTip().set(0, localizedName);
        }
    }
}