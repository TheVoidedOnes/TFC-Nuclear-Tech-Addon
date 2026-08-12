package com.voided.tfcnuclear.overwrite_contents.mixin.handler;

import net.dries007.tfc.api.capability.size.ItemSizeHandler;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemSizeHandler.class)
public class MixinItemSize {

    @Shadow
    private Size size;

    @Shadow
    private Weight weight;

    @Shadow
    private boolean canStack;

    @Inject(method = "getSize", at = @At("HEAD"), cancellable = true, remap = false)
    public void onGetSize(ItemStack stack, CallbackInfoReturnable<Size> cir) {
        if (stack == null || stack.isEmpty()) return;

        if (stack.getItem() instanceof ItemBlock) {
            ItemBlock itemBlock = (ItemBlock) stack.getItem();
            Block block = itemBlock.getBlock();
            String blockName = block.getRegistryName().toString();

            if (blockName.startsWith("hbm:concrete") || blockName.startsWith("hbm:fusion_component") || blockName.startsWith("hbm:det_nuke") || blockName.startsWith("hbm:steel_scaffold") || blockName.startsWith("hbmspace:")) {
                cir.setReturnValue(Size.TINY);
            }
        }
    }

    @Inject(method = "getStackSize", at = @At("HEAD"), cancellable = true, remap = false)
    public void onGetStackSize(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (stack == null || stack.isEmpty()) return;

        if (stack.getItem() instanceof ItemBlock) {
            ItemBlock itemBlock = (ItemBlock) stack.getItem();
            Block block = itemBlock.getBlock();
            String blockName = block.getRegistryName().toString();

            if (blockName.startsWith("hbm:concrete") || blockName.startsWith("hbm:fusion_component") || blockName.startsWith("hbm:det_nuke") || blockName.startsWith("hbm:steel_scaffold") || blockName.startsWith("hbmspace:")) {
                cir.setReturnValue(64);
            }
        }
    }
}