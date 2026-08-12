package com.voided.tfcnuclear.overwrite_contents.mixin.handler;

import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.compat.jei.wrappers.UnmoldRecipeWrapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(UnmoldRecipeWrapper.class)
public class MixinUnmoldRecipesWrapper {

    @Inject(
            method = "<init>",
            at = @At("RETURN"),
            remap = false
    )
    private void onConstruct(Metal metal, Metal.ItemType type, CallbackInfo ci) {
        if (metal == null) return;

        String metalName = metal.getRegistryName().getPath();

        boolean isTargetMetal = metalName.equals("steel") ||
                metalName.equals("copper") ||
                metalName.equals("lead") || metalName.equals("zinc");

        if (!isTargetMetal) return;

        if (type != Metal.ItemType.INGOT) return;

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
        }

        if (hbmIngot != null && !hbmIngot.isEmpty()) {
            try {
                java.lang.reflect.Field outputField = UnmoldRecipeWrapper.class.getDeclaredField("output");
                outputField.setAccessible(true);
                outputField.set(this, hbmIngot);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private ItemStack getHBMItem(String itemId) {
        Item item = Item.getByNameOrId(itemId);
        return item != null ? new ItemStack(item, 1) : ItemStack.EMPTY;
    }
}