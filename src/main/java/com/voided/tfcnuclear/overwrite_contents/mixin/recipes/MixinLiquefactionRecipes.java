package com.voided.tfcnuclear.overwrite_contents.mixin.recipes;

import com.hbm.inventory.RecipesCommon;
import com.hbm.inventory.fluid.FluidStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.LiquefactionRecipes;
import net.minecraft.init.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

@Mixin(value = LiquefactionRecipes.class, remap = false)
public class MixinLiquefactionRecipes {

    @Shadow
    public static HashMap<Object, FluidStack> recipes;

    @Inject(method = "registerDefaults", at = @At("RETURN"))
    private void onRegisterDefaults(CallbackInfo ci) {

        removeRecipeByInput(new RecipesCommon.ComparableStack(Blocks.STONE));
        removeRecipeByInput(new RecipesCommon.ComparableStack(Blocks.COBBLESTONE));


        recipes.put("stone", new FluidStack(250, Fluids.LAVA));
        recipes.put("cobblestone", new FluidStack(250, Fluids.LAVA));

    }

    private static void removeRecipeByInput(RecipesCommon.ComparableStack input) {
        if (recipes.containsKey(input)) {
            recipes.remove(input);
        }
    }
}