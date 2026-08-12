package com.voided.tfcnuclear.inventory.fluids;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.hbm.main.MainRegistry;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class TFCNuclearFluids {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static void addFluid(JsonObject root, String key, String name, int id, int color, int temperature, int p) {
        if (root.has(key)) {
            return;
        }

        JsonObject fluid = new JsonObject();
        fluid.addProperty("name", name);
        fluid.addProperty("id", id);
        fluid.addProperty("color", color);
        fluid.addProperty("tint", color);
        fluid.addProperty("p", p);
        fluid.addProperty("f", 0);
        fluid.addProperty("r", 0);
        fluid.addProperty("symbol", "NONE");
        fluid.addProperty("texture", key);
        fluid.addProperty("temperature", temperature);

        root.add(key, fluid);
    }

    private static boolean allFluidsExist(JsonObject root) {
        return root.has("LIMEWATER") &&
                root.has("AACS") &&
                root.has("IS") &&
                root.has("GS") &&
                root.has("LS") &&
                root.has("CS") &&
                root.has("CrS") &&
                root.has("ZS");
    }

    public static void writeFluidToConfig() {
        File configDir = MainRegistry.configHbmDir;
        if (!configDir.exists()) {
            configDir.mkdirs();
        }

        File fluidConfig = new File(configDir, "hbmFluidTypes.json");
        JsonObject root;

        try {
            if (fluidConfig.exists()) {
                try (FileReader reader = new FileReader(fluidConfig)) {
                    root = GSON.fromJson(reader, JsonObject.class);
                }
            } else {
                root = new JsonObject();
            }

            if (allFluidsExist(root)) {
                return;
            }

            addFluid(root, "LIMEWATER", "Limewater", 1001, 16775910, 20, 0);
            addFluid(root, "AACS", "Acid-activated Clay Slurry", 1002, 15238218, 100, 0);
            addFluid(root, "IS", "Iron Sulfide", 1003, 12632256, 64, 0);
            addFluid(root, "GS", "Gold Sulfide", 1004, 16766720, 45, 0);
            addFluid(root, "LS", "Lead Sulfide", 1005, 2825037, 320, 0);
            addFluid(root, "CS", "Copper Sulfide", 1006, 13467442, 110, 0);
            addFluid(root, "CrS", "Chrome Sulfite", 1007, 17510, 60, 3);
            addFluid(root, "ZS", "Zinc Sulfide", 1008, 16777215, 85, 0);

            try (FileWriter writer = new FileWriter(fluidConfig)) {
                GSON.toJson(root, writer);
            }

        } catch (Exception ignored) {
        }
    }
}