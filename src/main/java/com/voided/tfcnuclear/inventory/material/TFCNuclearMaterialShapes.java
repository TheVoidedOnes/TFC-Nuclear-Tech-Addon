package com.voided.tfcnuclear.inventory.material;

import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.main.MainRegistry;

import static com.voided.tfcnuclear.inventory.material.TFCNuclearShapeInitializer.*;

public class TFCNuclearMaterialShapes {

    public static MaterialShapes TFC_PICK = TFCNuclearShapeInitializer.TFC_PICK;
    public static MaterialShapes TFC_AXE = TFCNuclearShapeInitializer.TFC_AXE;
    public static MaterialShapes TFC_CHISEL = TFCNuclearShapeInitializer.TFC_CHISEL;
    public static MaterialShapes TFC_HAMMER = TFCNuclearShapeInitializer.TFC_HAMMER;
    public static MaterialShapes TFC_HOE = TFCNuclearShapeInitializer.TFC_HOE;
    public static MaterialShapes TFC_JAVELIN = TFCNuclearShapeInitializer.TFC_JAVELIN;
    public static MaterialShapes TFC_SCYTHE = TFCNuclearShapeInitializer.TFC_SCYTHE;
    public static MaterialShapes TFC_SHOVEL = TFCNuclearShapeInitializer.TFC_SHOVEL;
    public static MaterialShapes TFC_SWORD = TFCNuclearShapeInitializer.TFC_SWORD;
    public static MaterialShapes TFC_KNIFE = TFCNuclearShapeInitializer.TFC_KNIFE;
    public static MaterialShapes TFC_MACE = TFCNuclearShapeInitializer.TFC_MACE;
    public static MaterialShapes TFC_SAW = TFCNuclearShapeInitializer.TFC_SAW;
    public static MaterialShapes TFC_PROSPECTOR = TFCNuclearShapeInitializer.TFC_PROSPECTOR;

    private static boolean registered = false;

    public static void registerShapes() {
        if (registered) return;

        TFCNuclearShapeInitializer.ensureInitialized();

        try {
            Mats.MAT_WROUGHTIRON.setAutogen(TFC_PICK, TFC_HOE, TFC_SWORD, TFC_PROSPECTOR,
                    TFC_AXE, TFC_SAW, TFC_SCYTHE, TFC_JAVELIN, TFC_CHISEL, TFC_HAMMER,
                    TFC_KNIFE, TFC_SHOVEL, TFC_MACE);

            Mats.MAT_COPPER.setAutogen(TFC_PICK, TFC_HOE, TFC_SWORD, TFC_PROSPECTOR,
                    TFC_AXE, TFC_SAW, TFC_SCYTHE, TFC_JAVELIN, TFC_CHISEL, TFC_HAMMER,
                    TFC_KNIFE, TFC_SHOVEL, TFC_MACE);

            Mats.MAT_STEEL.setAutogen(TFC_PICK, TFC_HOE, TFC_SWORD, TFC_PROSPECTOR,
                    TFC_AXE, TFC_SAW, TFC_SCYTHE, TFC_JAVELIN, TFC_CHISEL, TFC_HAMMER,
                    TFC_KNIFE, TFC_SHOVEL, TFC_MACE);

            if (TFCNuclearMats.MAT_BRONZE != null) {
                TFCNuclearMats.MAT_BRONZE.setAutogen(TFC_PICK, TFC_HOE, TFC_SWORD, TFC_PROSPECTOR,
                        TFC_AXE, TFC_SAW, TFC_SCYTHE, TFC_JAVELIN, TFC_CHISEL, TFC_HAMMER,
                        TFC_KNIFE, TFC_SHOVEL, TFC_MACE);
            }

            registered = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}