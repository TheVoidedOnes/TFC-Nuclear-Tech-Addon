package com.voided.tfcnuclear.compat.hbmspace;

import net.minecraftforge.fml.common.Loader;

import java.lang.reflect.Field;

public class MatZincProvider {

    private static Object MAT_ZINC_FALLBACK = null;
    private static boolean isSpaceModAvailable = false;

    static {
        if (Loader.isModLoaded("hbmspace")) {
            try {
                Class<?> matsSpaceClass = Class.forName("com.hbmspace.inventory.materials.MatsSpace");
                Field field = matsSpaceClass.getField("MAT_ZINC");
                MAT_ZINC_FALLBACK = field.get(null);
                isSpaceModAvailable = true;
            } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
                isSpaceModAvailable = false;
            }
        }
    }

    public static Object getMatZinc() {
        return MAT_ZINC_FALLBACK;
    }

    public static boolean isSpaceMatZincAvailable() {
        return isSpaceModAvailable && MAT_ZINC_FALLBACK != null;
    }
}