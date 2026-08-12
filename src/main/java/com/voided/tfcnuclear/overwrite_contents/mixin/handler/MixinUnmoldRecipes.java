package com.voided.tfcnuclear.overwrite_contents.mixin.handler;

import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.recipes.UnmoldRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(UnmoldRecipe.class)
public class MixinUnmoldRecipes {

    @Shadow
    private Metal.ItemType type;

    @Inject(
            method = "getOutputItem",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void onGetOutputItem(IMoldHandler moldHandler, CallbackInfoReturnable<ItemStack> cir) {
        if (moldHandler == null) return;

        Metal metal = moldHandler.getMetal();
        if (metal == null) return;

        if (moldHandler.getAmount() != 100) return;

        String metalName = metal.getRegistryName().getPath();

        boolean isTargetMetal = metalName.equals("steel") ||
                metalName.equals("copper") ||
                metalName.equals("lead") || metalName.equals("zinc");

        if (!isTargetMetal) {
            return;
        }

        if (type != Metal.ItemType.INGOT) {
            return;
        }

        ItemStack hbmIngot = null;
        switch (metalName) {
            case "steel":
                hbmIngot = getHBMItem("hbm:ingot_steel");
                break;
            case "copper":
                hbmIngot = getHBMItem("hbm:ingot_copper");
                break;
            case "lead":
                hbmIngot = getHBMItem("hbm:ingot_lead");
                break;
            case "zinc":
                hbmIngot = getHBMItem("hbmspace:ingot_zinc");
                break;
        }

        if (hbmIngot != null && !hbmIngot.isEmpty()) {
            copyTemperature(moldHandler, hbmIngot);
            cir.setReturnValue(hbmIngot);
        }
    }

    private ItemStack getHBMItem(String itemId) {
        Item item = Item.getByNameOrId(itemId);
        return item != null ? new ItemStack(item, 1) : ItemStack.EMPTY;
    }

    private void copyTemperature(IMoldHandler moldHandler, ItemStack result) {
        try {
            float temp = moldHandler.getTemperature();
            net.dries007.tfc.api.capability.heat.IItemHeat heat =
                    result.getCapability(
                            net.dries007.tfc.api.capability.heat.CapabilityItemHeat.ITEM_HEAT_CAPABILITY,
                            null
                    );
            if (heat != null) {
                heat.setTemperature(temp);
            }
        } catch (Exception ignored) {}
    }
}