package com.voided.tfcnuclear.compat.tfcambiental;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class AmbientalIntegration {

    private static boolean ambientalLoaded = false;
    private static boolean initialized = false;

    public static boolean isAmbientalLoaded() {
        return ambientalLoaded;
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (initialized || event.phase != TickEvent.Phase.START) {
            return;
        }

        initialized = true;
        ambientalLoaded = Loader.isModLoaded("tfcambiental");

        if (ambientalLoaded) {
            try {
                Class.forName("com.lumintorious.ambiental.api.TemperatureRegistry");
                HBMPipeHeatProvider.register();
            } catch (Exception e) {
            }
        }
    }
}
