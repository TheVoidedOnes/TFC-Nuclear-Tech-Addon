package com.voided.tfcnuclear.overwrite_contents.mixin.world;

import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.RockCategory;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.world.classic.genlayers.datalayers.rock.GenLayerRockInit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(GenLayerRockInit.class)
public abstract class MixinGenLayerRockInit
{
    @Shadow
    private int[] layerRocks;

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    private void onInit(long par1, RockCategory.Layer rocks, CallbackInfo ci)
    {
        List<Integer> rockIds = new ArrayList<>();

        for (Rock rock : TFCRegistries.ROCKS.getValuesCollection())
        {
            RockCategory category = rock.getRockCategory();
            if (category == null) continue;

            String path = category.getRegistryName().getPath();
            boolean isSedimentary = path.equals("sedimentary") ||
                    path.equals("limestone") ||
                    path.equals("shale") ||
                    path.equals("claystone") ||
                    path.equals("dolomite") ||
                    path.equals("conglomerate") ||
                    path.equals("chalk") ||
                    path.equals("chert") ||
                    path.contains("rocksalt");

            boolean shouldInclude;
            if (rocks.layer == 1) // TOP
            {
                shouldInclude = isSedimentary && rock.isNaturallyGenerating();
            }
            else
            {
                shouldInclude = !isSedimentary && rock.isNaturallyGenerating();
            }

            if (shouldInclude)
            {
                int id = ((net.minecraftforge.registries.ForgeRegistry<Rock>) TFCRegistries.ROCKS).getID(rock);
                rockIds.add(id);
            }
        }
        this.layerRocks = rockIds.stream().sorted().mapToInt(Integer::intValue).toArray();
    }
}