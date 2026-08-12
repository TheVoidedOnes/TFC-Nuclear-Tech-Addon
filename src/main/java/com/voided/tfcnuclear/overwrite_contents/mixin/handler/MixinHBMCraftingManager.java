package com.voided.tfcnuclear.overwrite_contents.mixin.handler;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

@Mixin(targets = "com.hbm.main.CraftingManager")
public class MixinHBMCraftingManager {

    private static Item ironIngot = null;
    private static Item wroughtIron = null;
    private static final Item stoneBrick = Item.getItemFromBlock(Blocks.STONEBRICK);
    private static final int[] STONEBRICK_METAS = {0, 1, 2, 3};

    static {
        try {
            ironIngot = Items.IRON_INGOT;
            wroughtIron = Item.getByNameOrId("tfc:metal/ingot/wrought_iron");
        } catch (Exception e) {
        }
    }

    @Inject(method = "addRecipeAuto(Lnet/minecraft/item/ItemStack;[Ljava/lang/Object;)V",
            at = @At("HEAD"), remap = false)
    private static void onAddRecipeAuto(ItemStack output, Object[] args, CallbackInfo ci) {
        replaceIronInArgs(args);
        replaceStoneBrickInArgs(args);
    }

    @Inject(method = "addShapelessAuto(Lnet/minecraft/item/ItemStack;[Ljava/lang/Object;)V",
            at = @At("HEAD"), remap = false)
    private static void onAddShapelessAuto(ItemStack output, Object[] args, CallbackInfo ci) {
        replaceIronInArgs(args);
        replaceStoneBrickInArgs(args);
    }

    @Inject(method = "addRecipeAutoOreShapeless(Lnet/minecraft/item/ItemStack;[Ljava/lang/Object;)V",
            at = @At("HEAD"), remap = false)
    private static void onAddRecipeAutoOreShapeless(ItemStack output, Object[] args, CallbackInfo ci) {
        replaceIronInArgs(args);
        replaceStoneBrickInArgs(args);
    }

    @Inject(method = "add1To9Pair(Lnet/minecraft/item/Item;Lnet/minecraft/item/Item;)V",
            at = @At("HEAD"), remap = false)
    private static void onAdd1To9Pair(Item one, Item nine, CallbackInfo ci) {
        if (one == ironIngot) {
            try {
                ItemStack oneStack = new ItemStack(wroughtIron);
                ItemStack nineStack = new ItemStack(nine, 9);
                Method method = net.minecraftforge.fml.common.registry.GameRegistry.class.getDeclaredMethod(
                        "addShapedRecipe", ResourceLocation.class, ResourceLocation.class, ItemStack.class, Object[].class);
                method.setAccessible(true);
                ci.cancel();
            } catch (Exception e) {
            }
        }
    }

    private static void replaceIronInArgs(Object[] args) {
        if (ironIngot == null || wroughtIron == null) return;

        for (int i = 0; i < args.length; i++) {
            Object obj = args[i];

            if (obj instanceof ItemStack) {
                ItemStack stack = (ItemStack) obj;
                if (stack.getItem() == ironIngot) {
                    args[i] = new ItemStack(wroughtIron, stack.getCount(), stack.getMetadata());
                }
            }
            else if (obj instanceof String) {
                String str = (String) obj;
                if (str.equalsIgnoreCase("ingotIron")) {
                    args[i] = "ingotWroughtIron";
                }
            }
            else if (obj instanceof Ingredient) {
                Ingredient ingredient = (Ingredient) obj;
                for (ItemStack stack : ingredient.getMatchingStacks()) {
                    if (stack != null && stack.getItem() == ironIngot) {
                        args[i] = Ingredient.fromItem(wroughtIron);
                        break;
                    }
                }
            }
        }
    }

    private static void replaceStoneBrickInArgs(Object[] args) {
        for (int i = 0; i < args.length; i++) {
            Object obj = args[i];

            // ItemStack
            if (obj instanceof ItemStack) {
                ItemStack stack = (ItemStack) obj;
                if (stack.getItem() == stoneBrick) {
                    int meta = stack.getMetadata();
                    if (meta >= 0 && meta <= 3) {
                        args[i] = "stoneBrick";
                    }
                }
            }
            else if (obj instanceof String) {
            }
            else if (obj instanceof Ingredient) {
                Ingredient ingredient = (Ingredient) obj;
                for (ItemStack stack : ingredient.getMatchingStacks()) {
                    if (stack != null && stack.getItem() == stoneBrick) {
                        int meta = stack.getMetadata();
                        if (meta >= 0 && meta <= 3) {
                            args[i] = new OreIngredient("stoneBrick");
                            break;
                        }
                    }
                }
            }
        }
    }

    @Inject(method = "init", at = @At("TAIL"), remap = false)
    private static void onInit(CallbackInfo ci) {
        if (ironIngot == null || wroughtIron == null) return;

        try {
            net.minecraftforge.fml.common.registry.ForgeRegistries.RECIPES.forEach(recipe -> {
                replaceInRecipe(recipe);
            });
        } catch (Exception e) {
        }
    }

    private static void replaceInRecipe(IRecipe recipe) {
        if (recipe instanceof ShapedRecipes) {
            ShapedRecipes shaped = (ShapedRecipes) recipe;
            for (int i = 0; i < shaped.recipeItems.size(); i++) {
                Ingredient ingredient = shaped.recipeItems.get(i);
                if (ingredient != null && ingredient != Ingredient.EMPTY) {
                    for (ItemStack stack : ingredient.getMatchingStacks()) {
                        if (stack != null && stack.getItem() == ironIngot) {
                            shaped.recipeItems.set(i, Ingredient.fromItem(wroughtIron));
                            break;
                        }
                    }
                    for (ItemStack stack : ingredient.getMatchingStacks()) {
                        if (stack != null && stack.getItem() == stoneBrick) {
                            int meta = stack.getMetadata();
                            if (meta >= 0 && meta <= 3) {
                                shaped.recipeItems.set(i, new OreIngredient("stoneBrick"));
                                break;
                            }
                        }
                    }
                }
            }
        } else if (recipe instanceof ShapelessRecipes) {
            ShapelessRecipes shapeless = (ShapelessRecipes) recipe;
            for (int i = 0; i < shapeless.recipeItems.size(); i++) {
                Ingredient ingredient = shapeless.recipeItems.get(i);
                if (ingredient != null && ingredient != Ingredient.EMPTY) {
                    for (ItemStack stack : ingredient.getMatchingStacks()) {
                        if (stack != null && stack.getItem() == ironIngot) {
                            shapeless.recipeItems.set(i, Ingredient.fromItem(wroughtIron));
                            break;
                        }
                    }
                    for (ItemStack stack : ingredient.getMatchingStacks()) {
                        if (stack != null && stack.getItem() == stoneBrick) {
                            int meta = stack.getMetadata();
                            if (meta >= 0 && meta <= 3) {
                                shapeless.recipeItems.set(i, new OreIngredient("stoneBrick"));
                                break;
                            }
                        }
                    }
                }
            }
        } else if (recipe instanceof ShapedOreRecipe) {
            replaceInShapedOreRecipe((ShapedOreRecipe) recipe);
        } else if (recipe instanceof ShapelessOreRecipe) {
            replaceInShapelessOreRecipe((ShapelessOreRecipe) recipe);
        }
    }

    private static void replaceInShapedOreRecipe(ShapedOreRecipe recipe) {
        try {
            Field inputField = ShapedOreRecipe.class.getDeclaredField("input");
            inputField.setAccessible(true);
            Object[] input = (Object[]) inputField.get(recipe);

            for (int i = 0; i < input.length; i++) {
                Object obj = input[i];
                if (obj instanceof ItemStack) {
                    ItemStack stack = (ItemStack) obj;
                    if (stack.getItem() == ironIngot) {
                        input[i] = new ItemStack(wroughtIron, stack.getCount(), stack.getMetadata());
                    }
                    if (stack.getItem() == stoneBrick) {
                        int meta = stack.getMetadata();
                        if (meta >= 0 && meta <= 3) {
                            input[i] = "stoneBrick";
                        }
                    }
                } else if (obj instanceof Ingredient) {
                    Ingredient ingredient = (Ingredient) obj;
                    for (ItemStack stack : ingredient.getMatchingStacks()) {
                        if (stack != null && stack.getItem() == ironIngot) {
                            input[i] = Ingredient.fromItem(wroughtIron);
                            break;
                        }
                    }
                    for (ItemStack stack : ingredient.getMatchingStacks()) {
                        if (stack != null && stack.getItem() == stoneBrick) {
                            int meta = stack.getMetadata();
                            if (meta >= 0 && meta <= 3) {
                                input[i] = new OreIngredient("stoneBrick");
                                break;
                            }
                        }
                    }
                } else if (obj instanceof List) {
                    List<ItemStack> list = (List<ItemStack>) obj;
                    for (int j = 0; j < list.size(); j++) {
                        ItemStack stack = list.get(j);
                        if (stack != null) {
                            if (stack.getItem() == ironIngot) {
                                list.set(j, new ItemStack(wroughtIron, stack.getCount(), stack.getMetadata()));
                            }
                            if (stack.getItem() == stoneBrick) {
                                int meta = stack.getMetadata();
                                if (meta >= 0 && meta <= 3) {
                                    list.set(j, new ItemStack(stoneBrick, stack.getCount(), meta));
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    private static void replaceInShapelessOreRecipe(ShapelessOreRecipe recipe) {
        try {
            Field inputField = ShapelessOreRecipe.class.getDeclaredField("input");
            inputField.setAccessible(true);
            List<Object> input = (List<Object>) inputField.get(recipe);

            for (int i = 0; i < input.size(); i++) {
                Object obj = input.get(i);
                if (obj instanceof ItemStack) {
                    ItemStack stack = (ItemStack) obj;
                    if (stack.getItem() == ironIngot) {
                        input.set(i, new ItemStack(wroughtIron, stack.getCount(), stack.getMetadata()));
                    }
                    if (stack.getItem() == stoneBrick) {
                        int meta = stack.getMetadata();
                        if (meta >= 0 && meta <= 3) {
                            input.set(i, "stoneBrick");
                        }
                    }
                } else if (obj instanceof Ingredient) {
                    Ingredient ingredient = (Ingredient) obj;
                    for (ItemStack stack : ingredient.getMatchingStacks()) {
                        if (stack != null && stack.getItem() == ironIngot) {
                            input.set(i, Ingredient.fromItem(wroughtIron));
                            break;
                        }
                    }
                    for (ItemStack stack : ingredient.getMatchingStacks()) {
                        if (stack != null && stack.getItem() == stoneBrick) {
                            int meta = stack.getMetadata();
                            if (meta >= 0 && meta <= 3) {
                                input.set(i, new OreIngredient("stoneBrick"));
                                break;
                            }
                        }
                    }
                } else if (obj instanceof List) {
                    List<ItemStack> list = (List<ItemStack>) obj;
                    for (int j = 0; j < list.size(); j++) {
                        ItemStack stack = list.get(j);
                        if (stack != null) {
                            if (stack.getItem() == ironIngot) {
                                list.set(j, new ItemStack(wroughtIron, stack.getCount(), stack.getMetadata()));
                            }
                            if (stack.getItem() == stoneBrick) {
                                int meta = stack.getMetadata();
                                if (meta >= 0 && meta <= 3) {
                                    list.set(j, new ItemStack(stoneBrick, stack.getCount(), meta));
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }
}