package com.voided.tfcnuclear.overwrite_contents.mixin.handler;

import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.IFood;
import net.dries007.tfc.CommonEventHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Mixin(value = CommonEventHandler.class, remap = false)
public class MixinCommonEventHandler {

    private static final Set<String> BLOCKED_ITEMS = new HashSet<>();

    static {
        BLOCKED_ITEMS.add("hbm:ingot_semtex");
        BLOCKED_ITEMS.add("hbm:powder_cement");
    }

    @Inject(method = "attachItemCapabilities",
            at = @At("HEAD"),
            cancellable = true,
            remap = false)
    private static void onAttachItemCapabilities(AttachCapabilitiesEvent<ItemStack> event, CallbackInfo ci) {
        ItemStack stack = event.getObject();
        if (stack.isEmpty()) return;

        ResourceLocation registryName = stack.getItem().getRegistryName();
        if (registryName == null) return;

        String itemId = registryName.toString();

        if (BLOCKED_ITEMS.contains(itemId)) {
            ci.cancel();
        }
    }
}
