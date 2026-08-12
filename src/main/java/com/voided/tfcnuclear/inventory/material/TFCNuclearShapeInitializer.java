package com.voided.tfcnuclear.inventory.material;

import com.hbm.inventory.material.MaterialShapes;
import com.hbm.main.MainRegistry;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class TFCNuclearShapeInitializer {

    public static MaterialShapes TFC_PICK;
    public static MaterialShapes TFC_AXE;
    public static MaterialShapes TFC_SHOVEL;
    public static MaterialShapes TFC_HOE;
    public static MaterialShapes TFC_SWORD;
    public static MaterialShapes TFC_CHISEL;
    public static MaterialShapes TFC_MACE;
    public static MaterialShapes TFC_SAW;
    public static MaterialShapes TFC_JAVELIN;
    public static MaterialShapes TFC_HAMMER;
    public static MaterialShapes TFC_PROSPECTOR;
    public static MaterialShapes TFC_KNIFE;
    public static MaterialShapes TFC_SCYTHE;

    private static boolean initialized = false;

    public static void ensureInitialized() {
        if (initialized) return;
        initializeShapes();
        initialized = true;
    }

    private static void initializeShapes() {
        try {
            Constructor<MaterialShapes> constructor = MaterialShapes.class.getDeclaredConstructor(
                    int.class, String[].class
            );
            constructor.setAccessible(true);

            TFC_PICK = createShape(constructor, "pickHead");
            TFC_AXE = createShape(constructor, "axeHead");
            TFC_SHOVEL = createShape(constructor, "shovelHead");
            TFC_HOE = createShape(constructor, "hoeHead");
            TFC_SWORD = createShape(constructor, "swordBlade");
            TFC_PROSPECTOR = createShape(constructor, "propickHead");
            TFC_CHISEL = createShape(constructor, "chiselHead");
            TFC_MACE = createShape(constructor, "maceHead");
            TFC_SAW = createShape(constructor, "sawBlade");
            TFC_JAVELIN = createShape(constructor, "javelinHead");
            TFC_HAMMER = createShape(constructor, "hammerHead");
            TFC_KNIFE = createShape(constructor, "knifeBlade");
            TFC_SCYTHE = createShape(constructor, "scytheBlade");

            disableAutogen(TFC_PICK, TFC_AXE, TFC_SHOVEL, TFC_HOE, TFC_SWORD,
                    TFC_PROSPECTOR, TFC_CHISEL, TFC_MACE, TFC_SAW, TFC_JAVELIN,
                    TFC_HAMMER, TFC_KNIFE, TFC_SCYTHE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static MaterialShapes createShape(Constructor<MaterialShapes> constructor, String prefix) throws Exception {
        return constructor.newInstance(MaterialShapes.INGOT.q(1), new String[]{prefix});
    }

    private static void disableAutogen(MaterialShapes... shapes) throws Exception {
        Field noAutogenField = MaterialShapes.class.getDeclaredField("noAutogen");
        noAutogenField.setAccessible(true);
        for (MaterialShapes shape : shapes) {
            noAutogenField.setBoolean(shape, true);
        }
    }
}