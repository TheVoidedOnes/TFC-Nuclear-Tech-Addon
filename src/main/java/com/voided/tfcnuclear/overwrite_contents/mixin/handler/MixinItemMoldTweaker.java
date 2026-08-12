package com.voided.tfcnuclear.overwrite_contents.mixin.handler;

import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.items.machine.ItemMold;
import com.voided.tfcnuclear.inventory.material.TFCNuclearMats;
import com.voided.tfcnuclear.inventory.material.TFCNuclearShapeInitializer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

import static com.voided.tfcnuclear.inventory.material.TFCNuclearMaterialShapes.*;
import static com.voided.tfcnuclear.inventory.material.TFCNuclearShapeInitializer.TFC_CHISEL;
import static com.voided.tfcnuclear.inventory.material.TFCNuclearShapeInitializer.TFC_HAMMER;
import static com.voided.tfcnuclear.inventory.material.TFCNuclearShapeInitializer.TFC_HOE;
import static com.voided.tfcnuclear.inventory.material.TFCNuclearShapeInitializer.TFC_JAVELIN;
import static com.voided.tfcnuclear.inventory.material.TFCNuclearShapeInitializer.TFC_KNIFE;
import static com.voided.tfcnuclear.inventory.material.TFCNuclearShapeInitializer.TFC_MACE;
import static com.voided.tfcnuclear.inventory.material.TFCNuclearShapeInitializer.TFC_PROSPECTOR;
import static com.voided.tfcnuclear.inventory.material.TFCNuclearShapeInitializer.TFC_SAW;
import static com.voided.tfcnuclear.inventory.material.TFCNuclearShapeInitializer.TFC_SCYTHE;
import static com.voided.tfcnuclear.inventory.material.TFCNuclearShapeInitializer.TFC_SHOVEL;
import static com.voided.tfcnuclear.inventory.material.TFCNuclearShapeInitializer.TFC_SWORD;

@Mixin(value = ItemMold.MoldShape.class, remap = false)
public abstract class MixinItemMoldTweaker {

    @Shadow
    MaterialShapes shape;

    @Shadow
    int amount;

    static {
        TFCNuclearShapeInitializer.ensureInitialized();
    }

    @Unique
    private ItemStack getOreDictOutput(NTMMaterial mat) {
        for (String name : mat.names) {
            String od = shape.name() + name;
            List<ItemStack> ores = OreDictionary.getOres(od);
            if (!ores.isEmpty()) {
                ItemStack copy = ores.get(0).copy();
                copy.setCount(this.amount);
                return copy;
            }
        }
        return ItemStack.EMPTY;
    }

    @Unique
    private static final Set<MaterialShapes> IRON_ALLOWED = new HashSet<>(Arrays.asList(
            MaterialShapes.BLOCK, MaterialShapes.INGOT, MaterialShapes.NUGGET
    ));

    @Unique
    private static final Set<MaterialShapes> WROUGHT_AS_IRON = new HashSet<>(Arrays.asList(
            MaterialShapes.PLATE, MaterialShapes.CASTPLATE, MaterialShapes.WELDEDPLATE,
            MaterialShapes.SHELL, MaterialShapes.PIPE, MaterialShapes.WIRE,
            MaterialShapes.DENSEWIRE, MaterialShapes.BOLT, MaterialShapes.DUST,
            MaterialShapes.DUSTTINY, MaterialShapes.BILLET, MaterialShapes.LIGHTBARREL,
            MaterialShapes.HEAVYBARREL, MaterialShapes.LIGHTRECEIVER, MaterialShapes.HEAVYRECEIVER,
            MaterialShapes.MECHANISM, MaterialShapes.STOCK, MaterialShapes.GRIP
    ));

    @Unique
    private static final Set<MaterialShapes> WROUGHT_OWN = new HashSet<>(Arrays.asList(
            MaterialShapes.BLOCK, MaterialShapes.INGOT, MaterialShapes.NUGGET,
            TFC_PICK, TFC_HOE, TFC_SWORD, TFC_PROSPECTOR, TFC_AXE, TFC_SAW,
            TFC_SCYTHE, TFC_JAVELIN, TFC_CHISEL, TFC_HAMMER, TFC_KNIFE, TFC_SHOVEL, TFC_MACE
    ));

    @Unique
    private static final Set<MaterialShapes> COPPER_ALLOWED = new HashSet<>(Arrays.asList(
            MaterialShapes.BLOCK, MaterialShapes.INGOT, MaterialShapes.NUGGET,
            TFC_PICK, TFC_HOE, TFC_SWORD, TFC_PROSPECTOR, TFC_AXE, TFC_SAW,
            TFC_SCYTHE, TFC_JAVELIN, TFC_CHISEL, TFC_HAMMER, TFC_KNIFE, TFC_SHOVEL, TFC_MACE
    ));

    @Unique
    private static final Set<MaterialShapes> BRONZE_AS_COPPER = new HashSet<>(Arrays.asList(
            MaterialShapes.PLATE, MaterialShapes.CASTPLATE, MaterialShapes.WELDEDPLATE,
            MaterialShapes.SHELL, MaterialShapes.PIPE,
            MaterialShapes.BOLT, MaterialShapes.DUST,
            MaterialShapes.DUSTTINY, MaterialShapes.BILLET, MaterialShapes.LIGHTBARREL,
            MaterialShapes.HEAVYBARREL, MaterialShapes.LIGHTRECEIVER, MaterialShapes.HEAVYRECEIVER,
            MaterialShapes.MECHANISM, MaterialShapes.STOCK, MaterialShapes.GRIP
    ));

    @Unique
    private static final Set<MaterialShapes> BRONZE_OWN = new HashSet<>(Arrays.asList(
            MaterialShapes.BLOCK, MaterialShapes.INGOT, MaterialShapes.NUGGET,
            TFC_PICK, TFC_HOE, TFC_SWORD, TFC_PROSPECTOR, TFC_AXE, TFC_SAW,
            TFC_SCYTHE, TFC_JAVELIN, TFC_CHISEL, TFC_HAMMER, TFC_KNIFE, TFC_SHOVEL, TFC_MACE
    ));

    @Inject(
            method = "getOutput",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void handleMoldOutput(NTMMaterial mat, CallbackInfoReturnable<ItemStack> cir) {
        if (mat == Mats.MAT_IRON) {
            if (!IRON_ALLOWED.contains(shape)) {
                cir.setReturnValue(ItemStack.EMPTY);
                return;
            }
            cir.setReturnValue(getOreDictOutput(mat));
            return;
        }

        if (mat == Mats.MAT_WROUGHTIRON) {
            if (WROUGHT_AS_IRON.contains(shape)) {
                ItemStack result = getOreDictOutput(Mats.MAT_IRON);
                if (!result.isEmpty()) {
                    result.setCount(this.amount);
                }
                cir.setReturnValue(result);
                return;
            }
            if (WROUGHT_OWN.contains(shape)) {
                cir.setReturnValue(getOreDictOutput(mat));
                return;
            }
            cir.setReturnValue(ItemStack.EMPTY);
            return;
        }

        if (mat == Mats.MAT_COPPER) {
            if (!COPPER_ALLOWED.contains(shape)) {
                cir.setReturnValue(ItemStack.EMPTY);
                return;
            }
            cir.setReturnValue(getOreDictOutput(mat));
            return;
        }

        if (mat == TFCNuclearMats.MAT_BRONZE) {
            if (BRONZE_AS_COPPER.contains(shape)) {
                ItemStack result = getBronzeOutput();
                if (!result.isEmpty()) {
                    result.setCount(this.amount);
                }
                cir.setReturnValue(result);
                return;
            }
            if (BRONZE_OWN.contains(shape)) {
                cir.setReturnValue(getOreDictOutput(mat));
                return;
            }
            cir.setReturnValue(ItemStack.EMPTY);
            return;
        }
    }

    @Unique
    private ItemStack getBronzeOutput() {
        ItemStack copperResult = getOreDictOutput(Mats.MAT_COPPER);
        if (!copperResult.isEmpty()) {
            ItemStack bronzeResult = copperResult.copy();

            if (!bronzeResult.hasTagCompound()) {
                bronzeResult.setTagCompound(new net.minecraft.nbt.NBTTagCompound());
            }

            bronzeResult.getTagCompound().setBoolean("isBronze", true);
            bronzeResult.getTagCompound().setString("material", "bronze");

            bronzeResult.setCount(1);
            return bronzeResult;
        }
        return ItemStack.EMPTY;
    }
}