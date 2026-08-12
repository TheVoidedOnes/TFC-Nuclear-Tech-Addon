package com.voided.tfcnuclear.overwrite_contents.mixin.handler;

import com.hbm.inventory.RecipesCommon;
import com.hbm.inventory.recipes.ElectrolyserMetalRecipes;
import com.hbm.items.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.util.Map;

@Mixin(ElectrolyserMetalRecipes.class)
public abstract class MixinElectrolyserMetalRecipes {

    @Inject(
            method = "registerDefaults",
            at = @At("TAIL"),
            remap = false
    )
    private void onRegisterDefaults(CallbackInfo ci) {
        try {
            Field field = ElectrolyserMetalRecipes.class.getDeclaredField("recipes");
            field.setAccessible(true);
            Map<RecipesCommon.AStack, ElectrolyserMetalRecipes.ElectrolysisMetalRecipe> recipes =
                    (Map<RecipesCommon.AStack, ElectrolyserMetalRecipes.ElectrolysisMetalRecipe>) field.get(null);

            RecipesCommon.ComparableStack toRemove = new RecipesCommon.ComparableStack(ModItems.crystal_trixite);
            recipes.remove(toRemove);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}