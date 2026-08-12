package com.voided.tfcnuclear.overwrite_contents.mixin.recipes;

import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.RecipesCommon;
import com.hbm.inventory.recipes.CombinationRecipes;
import com.hbm.inventory.fluid.FluidStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ItemEnums;
import com.hbm.items.ModItems;
import com.hbm.util.Tuple.Pair;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

import static com.hbm.inventory.OreDictManager.LIGNITE;

@Mixin(CombinationRecipes.class)
public abstract class MixinCombinationRecipes {

    @Shadow
    public static HashMap<Object, Pair<ItemStack, FluidStack>> recipes;

    @Inject(method = "registerDefaults", at = @At("TAIL"), remap = false)
    private void onRegisterDefaults(CallbackInfo ci) {
        recipes.put(
                new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ingot_phosphorus),
                new Pair<>(
                        new ItemStack(ModItems.crystal_phosphorus), null));
        recipes.put(
                new RecipesCommon.ComparableStack(Item.getByNameOrId("tfc:powder/graphite")),
                new Pair<>(OreDictManager.DictFrame.fromOne(ModItems.coke, ItemEnums.EnumCokeType.COAL),
                        new FluidStack(Fluids.COALCREOSOTE, 150)));
    }
}
