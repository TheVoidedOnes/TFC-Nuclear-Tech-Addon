package com.voided.tfcnuclear.overwrite_contents.mixin.handler;

import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMold;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(value = ItemMold.class, remap = false)
public class MixinItemMold {

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    private void onInit(String s, CallbackInfo ci) {
        ItemMold.Mold stampMold = null;
        for (ItemMold.Mold mold : ItemMold.molds) {
            if (mold.id == 7) {
                stampMold = mold;
                break;
            }
        }

        if (stampMold != null) {
            ItemMold.molds.remove(stampMold);
            ItemMold.moldById.remove(7);
        }

        ItemMold instance = (ItemMold)(Object)this;

        ItemMold.MoldMulti newMold = instance.new MoldMulti(
                7, 0, "stamp",
                MaterialShapes.INGOT.q(4),
                Mats.MAT_STONE, (Supplier<ItemStack>) () -> new ItemStack(ModItems.stamp_stone_flat),
                Mats.MAT_WROUGHTIRON, (Supplier<ItemStack>) () -> new ItemStack(ModItems.stamp_iron_flat),
                Mats.MAT_STEEL, (Supplier<ItemStack>) () -> new ItemStack(ModItems.stamp_steel_flat),
                Mats.MAT_TITANIUM, (Supplier<ItemStack>) () -> new ItemStack(ModItems.stamp_titanium_flat),
                Mats.MAT_OBSIDIAN, (Supplier<ItemStack>) () -> new ItemStack(ModItems.stamp_obsidian_flat)
        );

        ItemMold.molds.add(newMold);
        ItemMold.moldById.put(7, newMold);
    }
}