package com.voided.tfcnuclear.overwrite_contents.mixin.world;

import com.google.common.base.Predicate;
import com.hbm.world.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WorldUtil.class, remap = false)
public class MixinWorldUtil {

    @Shadow
    private static Predicate<IBlockState> STONE_PREDICATE;

    @Inject(
            method = "<clinit>",
            at = @At("RETURN")
    )
    private static void onStaticInit(CallbackInfo ci) {
        STONE_PREDICATE = state -> {
            if (state == null) return false;
            Block block = state.getBlock();

            ResourceLocation registryName = block.getRegistryName();
            if (registryName == null) return false;
            String name = registryName.toString();

            if (block == Blocks.STONE) return true;

            if (name.startsWith("tfc:raw/")) return true;

            try {
                int[] ids = OreDictionary.getOreIDs(new net.minecraft.item.ItemStack(block));
                for (int id : ids) {
                    if (OreDictionary.getOreName(id).equals("stone")) {
                        return true;
                    }
                }
            } catch (IllegalArgumentException | NullPointerException e) {
            }

            return false;
        };
    }
}