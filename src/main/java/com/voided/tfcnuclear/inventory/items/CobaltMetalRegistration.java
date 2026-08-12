package com.voided.tfcnuclear.inventory.items;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import net.dries007.tfc.api.capability.metal.CapabilityMetalItem;
import net.dries007.tfc.api.capability.metal.MetalItemHandler;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Metal.Tier;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.RegistryManager;

public class CobaltMetalRegistration {

    public static Metal COBALT_METAL;

    public static void registerCobaltMetal() {
        COBALT_METAL = new Metal(
                new ResourceLocation("tfcnuclear", "cobalt"),
                Tier.TIER_V,
                false,
                0.42f,
                1495f,
                0x3D5A80,
                null,
                null
        );

        RegistryManager.ACTIVE.getRegistry(Metal.class).register(COBALT_METAL);

        registerCobaltIngotCapability();
    }

    private static void registerCobaltIngotCapability() {
        ItemStack cobaltIngot = new ItemStack(ModBlocks.block_cobalt);

        CapabilityMetalItem.CUSTOM_METAL_ITEMS.put(
                IIngredient.of(cobaltIngot),
                () -> new MetalItemHandler(
                        COBALT_METAL,
                        100,
                        true
                )
        );

        net.minecraftforge.oredict.OreDictionary.registerOre("ingotCobalt", cobaltIngot);
    }
}