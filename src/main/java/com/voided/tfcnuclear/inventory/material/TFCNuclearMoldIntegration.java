package com.voided.tfcnuclear.inventory.material;

import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMold;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

public class TFCNuclearMoldIntegration {

    public static int PICK_MOLD_ID;
    public static int AXE_MOLD_ID;
    public static int SHOVEL_MOLD_ID;
    public static int HOE_MOLD_ID;
    public static int SWORD_MOLD_ID;
    public static int CHISEL_MOLD_ID;
    public static int MACE_MOLD_ID;
    public static int KNIFE_MOLD_ID;
    public static int SCYTHE_MOLD_ID;
    public static int HAMMER_MOLD_ID;
    public static int JAVELIN_MOLD_ID;
    public static int SAW_MOLD_ID;
    public static int PROSPECTOR_MOLD_ID;

    private static final HashMap<Integer, String> moldModelMap = new HashMap<>();

    public static void integrateMolds() {
        try {
            ItemMold hbmMold = (ItemMold) ModItems.mold;
            if (hbmMold == null) {
                return;
            }

            Field moldsField = ItemMold.class.getDeclaredField("molds");
            moldsField.setAccessible(true);
            List<ItemMold.Mold> molds = (List<ItemMold.Mold>) moldsField.get(null);

            Field moldByIdField = ItemMold.class.getDeclaredField("moldById");
            moldByIdField.setAccessible(true);
            HashMap<Integer, ItemMold.Mold> moldById = (HashMap<Integer, ItemMold.Mold>) moldByIdField.get(null);

            int nextId = getNextMoldId(moldById);

            PICK_MOLD_ID = nextId;
            moldModelMap.put(PICK_MOLD_ID, "pickhead");
            ItemMold.Mold pickMold = hbmMold.new MoldShape(
                    nextId++, 0, "pickHead", TFCNuclearMaterialShapes.TFC_PICK
            );

            AXE_MOLD_ID = nextId;
            moldModelMap.put(AXE_MOLD_ID, "axehead");
            ItemMold.Mold axeMold = hbmMold.new MoldShape(
                    nextId++, 0, "axeHead", TFCNuclearMaterialShapes.TFC_AXE
            );

            SHOVEL_MOLD_ID = nextId;
            moldModelMap.put(SHOVEL_MOLD_ID, "shovelhead");
            ItemMold.Mold shovelMold = hbmMold.new MoldShape(
                    nextId++, 0, "shovelHead", TFCNuclearMaterialShapes.TFC_SHOVEL
            );

            HOE_MOLD_ID = nextId;
            moldModelMap.put(HOE_MOLD_ID, "hoehead");
            ItemMold.Mold hoeMold = hbmMold.new MoldShape(
                    nextId++, 0, "hoeHead", TFCNuclearMaterialShapes.TFC_HOE
            );

            SWORD_MOLD_ID = nextId;
            moldModelMap.put(SWORD_MOLD_ID, "swordblade");
            ItemMold.Mold swordMold = hbmMold.new MoldShape(
                    nextId++, 0, "swordBlade", TFCNuclearMaterialShapes.TFC_SWORD
            );

            KNIFE_MOLD_ID = nextId;
            moldModelMap.put(KNIFE_MOLD_ID, "knifeblade");
            ItemMold.Mold knifeMold = hbmMold.new MoldShape(
                    nextId++, 0, "knifeBlade", TFCNuclearMaterialShapes.TFC_KNIFE
            );

            MACE_MOLD_ID = nextId;
            moldModelMap.put(MACE_MOLD_ID, "macehead");
            ItemMold.Mold maceMold = hbmMold.new MoldShape(
                    nextId++, 0, "maceHead", TFCNuclearMaterialShapes.TFC_MACE
            );

            SAW_MOLD_ID = nextId;
            moldModelMap.put(SAW_MOLD_ID, "sawblade");
            ItemMold.Mold sawMold = hbmMold.new MoldShape(
                    nextId++, 0, "sawBlade", TFCNuclearMaterialShapes.TFC_SAW
            );

            CHISEL_MOLD_ID = nextId;
            moldModelMap.put(CHISEL_MOLD_ID, "chiselhead");
            ItemMold.Mold chiselMold = hbmMold.new MoldShape(
                    nextId++, 0, "chiselHead", TFCNuclearMaterialShapes.TFC_CHISEL
            );

            PROSPECTOR_MOLD_ID = nextId;
            moldModelMap.put(PROSPECTOR_MOLD_ID, "prospectorhead");
            ItemMold.Mold prospectorMold = hbmMold.new MoldShape(
                    nextId++, 0, "propickHead", TFCNuclearMaterialShapes.TFC_PROSPECTOR
            );

            JAVELIN_MOLD_ID = nextId;
            moldModelMap.put(JAVELIN_MOLD_ID, "javelinhead");
            ItemMold.Mold javelinMold = hbmMold.new MoldShape(
                    nextId++, 0, "javelinHead", TFCNuclearMaterialShapes.TFC_JAVELIN
            );

            HAMMER_MOLD_ID = nextId;
            moldModelMap.put(HAMMER_MOLD_ID, "hammerhead");
            ItemMold.Mold hammerMold = hbmMold.new MoldShape(
                    nextId++, 0, "hammerHead", TFCNuclearMaterialShapes.TFC_HAMMER
            );

            SCYTHE_MOLD_ID = nextId;
            moldModelMap.put(SCYTHE_MOLD_ID, "scytheblade");
            ItemMold.Mold scytheMold = hbmMold.new MoldShape(
                    nextId++, 0, "scytheBlade", TFCNuclearMaterialShapes.TFC_SCYTHE
            );

            molds.add(pickMold);
            molds.add(axeMold);
            molds.add(shovelMold);
            molds.add(hoeMold);
            molds.add(swordMold);
            molds.add(scytheMold);
            molds.add(hammerMold);
            molds.add(knifeMold);
            molds.add(javelinMold);
            molds.add(prospectorMold);
            molds.add(chiselMold);
            molds.add(sawMold);
            molds.add(maceMold);

            moldById.put(pickMold.id, pickMold);
            moldById.put(axeMold.id, axeMold);
            moldById.put(shovelMold.id, shovelMold);
            moldById.put(hoeMold.id, hoeMold);
            moldById.put(swordMold.id, swordMold);
            moldById.put(scytheMold.id, scytheMold);
            moldById.put(knifeMold.id, knifeMold);
            moldById.put(hammerMold.id, hammerMold);
            moldById.put(sawMold.id, sawMold);
            moldById.put(prospectorMold.id, prospectorMold);
            moldById.put(chiselMold.id, chiselMold);
            moldById.put(javelinMold.id, javelinMold);
            moldById.put(maceMold.id, maceMold);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        for (int id : moldModelMap.keySet()) {
            String name = moldModelMap.get(id);
            ModelLoader.setCustomModelResourceLocation(
                    ModItems.mold,
                    id,
                    new ModelResourceLocation("hbm:mold_" + name, "inventory")
            );
        }
    }

    private static int getNextMoldId(HashMap<Integer, ItemMold.Mold> moldById) {
        int maxId = 0;
        for (Integer id : moldById.keySet()) {
            if (id > maxId) maxId = id;
        }
        return maxId + 1;
    }
}