package com.voided.tfcnuclear.compat.tfcambiental;

import net.minecraftforge.fml.common.Loader;

public class AmbientalCompat {

    private static final String AMBIENTAL_MODID = "tfcambiental";

    public static boolean canUseAmbientalAPI() {
        try {
            Class.forName("com.lumintorious.ambiental.api.TemperatureRegistry");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static boolean isAmbientalLoaded() {
        return Loader.isModLoaded(AMBIENTAL_MODID);
    }
}