package com.voided.tfcnuclear.inventory.recipes.vanilla;

import com.voided.tfcnuclear.inventory.items.ItemSlagBase;
import com.voided.tfcnuclear.inventory.items.ModItems;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.HashMap;
import java.util.Map;

public class OreCombineRecipes extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    private static final Map<String, Item> ORE_TO_SLAG = new HashMap<>();

    static {
        ORE_TO_SLAG.put("tfc:ore/hematite", ModItems.HEMATITE_SLAG);
        ORE_TO_SLAG.put("tfc:ore/small/hematite", ModItems.HEMATITE_SLAG);

        ORE_TO_SLAG.put("tfc:ore/limonite", ModItems.LIMONITE_SLAG);
        ORE_TO_SLAG.put("tfc:ore/small/limonite", ModItems.LIMONITE_SLAG);

        ORE_TO_SLAG.put("tfc:ore/magnetite", ModItems.MAGNETITE_SLAG);
        ORE_TO_SLAG.put("tfc:ore/small/magnetite", ModItems.MAGNETITE_SLAG);

        ORE_TO_SLAG.put("tfc:ore/native_copper", ModItems.COPPER_SLAG);
        ORE_TO_SLAG.put("tfc:ore/small/native_copper", ModItems.COPPER_SLAG);

        ORE_TO_SLAG.put("tfc:ore/galena", ModItems.GALENA_SLAG);
        ORE_TO_SLAG.put("tfc:ore/small/galena", ModItems.GALENA_SLAG);

        ORE_TO_SLAG.put("tfc:ore/native_gold", ModItems.GOLD_SLAG);
        ORE_TO_SLAG.put("tfc:ore/small/native_gold", ModItems.GOLD_SLAG);

        ORE_TO_SLAG.put("tfc:ore/garnierite", ModItems.CHROME_SLAG);
        ORE_TO_SLAG.put("tfc:ore/small/garnierite", ModItems.CHROME_SLAG);

        ORE_TO_SLAG.put("tfc:ore/sphalerite", ModItems.ZINC_SLAG);
        ORE_TO_SLAG.put("tfc:ore/small/sphalerite", ModItems.ZINC_SLAG);
    }

    private static final Map<String, Map<Integer, Integer>> ORE_VALUES = new HashMap<>();

    static {
        Map<Integer, Integer> hematiteValues = new HashMap<>();
        hematiteValues.put(0, 25);
        hematiteValues.put(1, 15);
        hematiteValues.put(2, 35);
        ORE_VALUES.put("tfc:ore/hematite", hematiteValues);
        ORE_VALUES.put("tfc:ore/small/hematite", new HashMap<Integer, Integer>() {{ put(0, 10); }});

        Map<Integer, Integer> limoniteValues = new HashMap<>();
        limoniteValues.put(0, 25);
        limoniteValues.put(1, 15);
        limoniteValues.put(2, 35);
        ORE_VALUES.put("tfc:ore/limonite", limoniteValues);
        ORE_VALUES.put("tfc:ore/small/limonite", new HashMap<Integer, Integer>() {{ put(0, 10); }});

        Map<Integer, Integer> magnetiteValues = new HashMap<>();
        magnetiteValues.put(0, 25);
        magnetiteValues.put(1, 15);
        magnetiteValues.put(2, 35);
        ORE_VALUES.put("tfc:ore/magnetite", magnetiteValues);
        ORE_VALUES.put("tfc:ore/small/magnetite", new HashMap<Integer, Integer>() {{ put(0, 10); }});

        Map<Integer, Integer> goldValues = new HashMap<>();
        goldValues.put(0, 25);
        goldValues.put(1, 15);
        goldValues.put(2, 35);
        ORE_VALUES.put("tfc:ore/native_gold", goldValues);
        ORE_VALUES.put("tfc:ore/small/native_gold", new HashMap<Integer, Integer>() {{ put(0, 10); }});

        Map<Integer, Integer> galenaValues = new HashMap<>();
        galenaValues.put(0, 25);
        galenaValues.put(1, 15);
        galenaValues.put(2, 35);
        ORE_VALUES.put("tfc:ore/galena", galenaValues);
        ORE_VALUES.put("tfc:ore/small/galena", new HashMap<Integer, Integer>() {{ put(0, 10); }});

        Map<Integer, Integer> copperValues = new HashMap<>();
        copperValues.put(0, 25);
        copperValues.put(1, 15);
        copperValues.put(2, 35);
        ORE_VALUES.put("tfc:ore/native_copper", copperValues);
        ORE_VALUES.put("tfc:ore/small/native_copper", new HashMap<Integer, Integer>() {{ put(0, 10); }});

        Map<Integer, Integer> chromeValues = new HashMap<>();
        chromeValues.put(0, 25);
        chromeValues.put(1, 15);
        chromeValues.put(2, 35);
        ORE_VALUES.put("tfc:ore/garnierite", chromeValues);
        ORE_VALUES.put("tfc:ore/small/garnierite", new HashMap<Integer, Integer>() {{ put(0, 10); }});

        Map<Integer, Integer> zincValues = new HashMap<>();
        zincValues.put(0, 25);
        zincValues.put(1, 15);
        zincValues.put(2, 35);
        // ❌ БАГ: здесь должно быть zincValues, а не chromeValues!
        ORE_VALUES.put("tfc:ore/sphalerite", zincValues);
        ORE_VALUES.put("tfc:ore/small/sphalerite", new HashMap<Integer, Integer>() {{ put(0, 10); }});
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        int oreCount = 0;
        boolean hasHammer = false;
        boolean hasOnlyValidItems = true;
        Item expectedSlag = null;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);

            if (!stack.isEmpty()) {
                if (isHammer(stack)) {
                    if (hasHammer) return false;
                    hasHammer = true;
                }
                else if (isValidOre(stack)) {
                    oreCount++;
                    Item slag = getSlagForOre(stack);
                    if (expectedSlag == null) {
                        expectedSlag = slag;
                    } else if (expectedSlag != slag) {
                        return false; // Разные типы руд
                    }
                }
                // ❌ УДАЛЕНО: больше не принимаем шлак как ингредиент
                else {
                    hasOnlyValidItems = false;
                    break;
                }
            }
        }

        // ❌ УДАЛЕНО: проверка на slagCount
        return hasOnlyValidItems && hasHammer && oreCount >= 1 && expectedSlag != null;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        int totalAmount = 0;
        Item targetSlagItem = null;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);

            if (!stack.isEmpty() && isValidOre(stack)) {
                totalAmount += getAmountFromOre(stack);
                if (targetSlagItem == null) {
                    targetSlagItem = getSlagForOre(stack);
                }
            }
        }

        if (targetSlagItem == null || totalAmount <= 0) {
            return ItemStack.EMPTY;
        }

        // ❌ УДАЛЕНО: существующий шлак больше не используется для объединения
        ItemStack result = new ItemStack(targetSlagItem);

        if (totalAmount > ItemSlagBase.MAX_AMOUNT) {
            totalAmount = ItemSlagBase.MAX_AMOUNT;
        }

        ItemSlagBase.setAmount(result, totalAmount);
        return result;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(ModItems.HEMATITE_SLAG);
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty() && isHammer(stack)) {
                ItemStack damagedHammer = stack.copy();
                damagedHammer.setItemDamage(damagedHammer.getItemDamage() + 1);

                if (damagedHammer.getItemDamage() < damagedHammer.getMaxDamage()) {
                    remaining.set(i, damagedHammer);
                }
                break;
            }
        }

        return remaining;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    private boolean isHammer(ItemStack stack) {
        if (stack.isEmpty()) return false;
        int[] oreIds = OreDictionary.getOreIDs(stack);
        for (int id : oreIds) {
            if ("hammer".equals(OreDictionary.getOreName(id))) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidOre(ItemStack stack) {
        if (stack.isEmpty()) return false;
        String itemId = stack.getItem().getRegistryName().toString();
        return ORE_TO_SLAG.containsKey(itemId);
    }

    private Item getSlagForOre(ItemStack stack) {
        if (stack.isEmpty()) return null;
        String itemId = stack.getItem().getRegistryName().toString();
        return ORE_TO_SLAG.get(itemId);
    }

    private int getAmountFromOre(ItemStack stack) {
        if (stack.isEmpty()) return 0;
        String itemId = stack.getItem().getRegistryName().toString();
        Map<Integer, Integer> values = ORE_VALUES.get(itemId);
        if (values != null) {
            return values.getOrDefault(stack.getMetadata(), 0);
        }
        return 0;
    }
}