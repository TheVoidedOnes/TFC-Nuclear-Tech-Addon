package com.voided.tfcnuclear.inventory.handler;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = "tfcnuclear")
public class ItemUseBlocker {

    private static final Set<String> BLOCKED_ITEMS = new HashSet<>();

    static {
        BLOCKED_ITEMS.add("hbm:ingot_semtex");
        BLOCKED_ITEMS.add("hbm:powder_cement");
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (isBlocked(event.getItemStack())) {
            event.setCanceled(true);
            event.setCancellationResult(EnumActionResult.FAIL);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
    }

    private static boolean isBlocked(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        ResourceLocation registryName = stack.getItem().getRegistryName();
        if (registryName == null) {
            return false;
        }

        return BLOCKED_ITEMS.contains(registryName.toString());
    }
}