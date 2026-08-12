package com.voided.tfcnuclear.overwrite_contents.mixin.handler;

import net.dries007.tfc.api.recipes.heat.HeatRecipe;
import net.dries007.tfc.types.DefaultRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryModifiable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DefaultRecipes.class)
public class MixinTFCDefaultRecipes {

    @Inject(
            method = "onRegisterHeatRecipeEvent",
            at = @At("RETURN"),
            remap = false
    )
    private static void removeUnfiredFireBrickRecipe(RegistryEvent.Register<HeatRecipe> event, CallbackInfo ci) {

        IForgeRegistry<HeatRecipe> registry = event.getRegistry();

        if (registry instanceof IForgeRegistryModifiable) {
            IForgeRegistryModifiable<HeatRecipe> modifiableRegistry = (IForgeRegistryModifiable<HeatRecipe>) registry;

            ResourceLocation recipeKey = new ResourceLocation("tfc", "unfired_fire_brick");
            modifiableRegistry.remove(recipeKey);

            ResourceLocation firedRecipeKey = new ResourceLocation("tfc", "fired_fire_brick");
            modifiableRegistry.remove(firedRecipeKey);
        }
    }
}