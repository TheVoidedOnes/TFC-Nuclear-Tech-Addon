package com.voided.tfcnuclear.overwrite_contents.mixin.handler;

import com.hbm.inventory.RecipesCommon;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.recipes.CrucibleRecipes;
import com.hbm.items.machine.ItemScraps;
import com.voided.tfcnuclear.compat.hbm.BloomCrucibleAStack;
import com.voided.tfcnuclear.inventory.material.TFCNuclearMats;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(value = CrucibleRecipes.class, remap = false)
public class MixinCrucibleTweaker {

    private static final Set<String> WROUGHT_PREFIXES = new HashSet<>(Arrays.asList(
            "plate", "castplate", "weldedplate",
            "shell", "ntmpipe", "wire", "densewire",
            "bolt", "dusttiny", "minecart", "rail",
            "billet", "barrel_light", "barrel_heavy",
            "receiver_light", "receiver_heavy",
            "mechanism", "stock", "grip", "pickHead"
    ));

    private static final Set<String> BRONZE_PREFIXES = new HashSet<>(Arrays.asList(
            "plate", "castplate", "weldedplate",
            "shell", "ntmpipe",
            "bolt", "dusttiny", "minecart", "rail",
            "billet", "barrel_light", "barrel_heavy",
            "receiver_light", "receiver_heavy",
            "mechanism", "stock", "grip", "pickHead"
    ));

    @Inject(method = "getSmeltingRecipes", at = @At("RETURN"), cancellable = true, remap = false)
    private static void fixSmeltingRecipes(CallbackInfoReturnable<HashMap<RecipesCommon.AStack, List<ItemStack>>> cir) {
        HashMap<RecipesCommon.AStack, List<ItemStack>> map = cir.getReturnValue();

        Iterator<Map.Entry<RecipesCommon.AStack, List<ItemStack>>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<RecipesCommon.AStack, List<ItemStack>> entry = iterator.next();
            if (entry.getKey() instanceof BloomCrucibleAStack) {
                iterator.remove();
            }
        }

        HashMap<RecipesCommon.AStack, List<ItemStack>> modified = new HashMap<>();

        for (Map.Entry<RecipesCommon.AStack, List<ItemStack>> entry : map.entrySet()) {
            RecipesCommon.AStack key = entry.getKey();
            List<ItemStack> value = entry.getValue();

            if (key instanceof RecipesCommon.OreDictStack) {
                String oreName = ((RecipesCommon.OreDictStack) key).name;
                String lower = oreName.toLowerCase();

                if (isWroughtIron(lower)) {
                    List<ItemStack> newStacks = new ArrayList<>();
                    for (ItemStack stack : value) {
                        if (stack.getItem() instanceof ItemScraps) {
                            MaterialStack mats = ItemScraps.getMats(stack);
                            if (mats != null && mats.material == Mats.MAT_IRON) {
                                newStacks.add(ItemScraps.create(
                                        new MaterialStack(Mats.MAT_WROUGHTIRON, mats.amount),
                                        true
                                ));
                            } else {
                                newStacks.add(stack);
                            }
                        } else {
                            newStacks.add(stack);
                        }
                    }
                    modified.put(key, newStacks);
                    continue;
                }

                if (isBronze(lower)) {
                    List<ItemStack> newStacks = new ArrayList<>();
                    for (ItemStack stack : value) {
                        if (stack.getItem() instanceof ItemScraps) {
                            MaterialStack mats = ItemScraps.getMats(stack);
                            if (mats != null && mats.material == Mats.MAT_COPPER) {
                                newStacks.add(ItemScraps.create(
                                        new MaterialStack(TFCNuclearMats.MAT_BRONZE, mats.amount),
                                        true
                                ));
                            } else {
                                newStacks.add(stack);
                            }
                        } else {
                            newStacks.add(stack);
                        }
                    }
                    modified.put(key, newStacks);
                    continue;
                }
            }

            modified.put(key, value);
        }

        cir.setReturnValue(modified);
    }

    private static boolean isWroughtIron(String lower) {
        if (lower.startsWith("ingot") || lower.startsWith("block") ||
                lower.startsWith("nugget") || lower.startsWith("fragment") ||
                lower.startsWith("dust") || lower.startsWith("ore") ||
                lower.startsWith("raw")) {
            return false;
        }
        if (!lower.endsWith("iron")) return false;
        for (String prefix : WROUGHT_PREFIXES) {
            if (lower.startsWith(prefix)) return true;
        }
        return false;
    }

    private static boolean isBronze(String lower) {
        if (lower.startsWith("ingot") || lower.startsWith("block") ||
                lower.startsWith("nugget") || lower.startsWith("fragment") ||
                lower.startsWith("dust") || lower.startsWith("ore") ||
                lower.startsWith("raw")) {
            return false;
        }
        if (!lower.endsWith("copper")) return false;
        for (String prefix : BRONZE_PREFIXES) {
            if (lower.startsWith(prefix)) return true;
        }
        return false;
    }
}