package com.voided.tfcnuclear.world.OreSpawn;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TFCOreSpawn {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILE_NAME = "tfc_ore_spawn_data.json";

    public static void generate(FMLPreInitializationEvent event) {
        File configDir = event.getModConfigurationDirectory();
        File targetFile = new File(configDir, "tfc/" + FILE_NAME);
        targetFile.getParentFile().mkdirs();

        Map<String, Object> oreData = new LinkedHashMap<>();

        addOre(oreData, "hematite_1", "tfc:hematite", "tfc:ore/small/hematite", 20, 12, "cluster", 130, 110, 140, 40, "tfc:claystone", "tfc:shale", "tfc:conglomerate", "tfc:chalk");
        addOre(oreData, "hematite_2", "tfc:hematite", "tfc:ore/hematite", 26, 16, "cluster", 130, 30, 80, 70, "tfc:igneous_intrusive", "tfc:metamorphic");
        addOre(oreData, "limonite", "tfc:limonite", "tfc:ore/small/limonite", 50, 10, "sphere", 300, 110, 130, 30, "tfc:sedimentary");
        addOre(oreData, "magnetite_1", "tfc:magnetite", "tfc:ore/magnetite", 16, 28, "cluster", 140, 10, 70, 60, "tfc:igneous_intrusive");
        addOre(oreData, "magnetite_2", "tfc:magnetite", "tfc:ore/magnetite", 28, 16, "cluster", 130, 10, 80, 85, "tfc:igneous_extrusive");

        addOre(oreData, "native_copper_1", "tfc:native_copper", "tfc:ore/native_copper", 22, 16, "cluster", 120, 20, 90, 70, "tfc:basalt", "tfc:andesite");
        addOre(oreData, "native_copper_2", "tfc:native_copper", "tfc:ore/small/native_copper", 70, 6, "sphere", 150, 60, 140, 50, "tfc:claystone", "tfc:conglomerate", "tfc:shale", "tfc:phyllite");
        addOre(oreData, "malachite", "tfc:malachite", "tfc:ore/malachite", 28, 20, "cluster", 130, 70, 130, 50, "tfc:limestone", "tfc:dolomite", "tfc:chalk", "tfc:marble");

        addOre(oreData, "cassiterite_cluster1", "tfc:cassiterite", "tfc:ore/cassiterite", 16, 24, "cluster", 100, 5, 80, 70, "tfc:granite");
        addOre(oreData, "cassiterite_cluster2", "tfc:cassiterite", "tfc:ore/small/cassiterite", 26, 14, "cluster", 250, 110, 135, 50, "tfc:sedimentary");

        addOre(oreData, "galena_cluster1", "tfc:galena", "tfc:ore/galena", 22, 16, "cluster", 120, 25, 80, 70, "tfc:slate", "tfc:phyllite", "tfc:schist", "tfc:gneiss", "tfc:quartzite");
        addOre(oreData, "galena_cluster2", "tfc:galena", "tfc:ore/small/galena", 50, 10, "sphere", 180, 110, 150, 40, "tfc:limestone", "tfc:dolomite", "tfc:chalk");

        addOre(oreData, "gold_metamorphic", "tfc:native_gold", "tfc:ore/native_gold", 18, 14, "cluster", 130, 15, 80, 80, "tfc:metamorphic");
        addOre(oreData, "gold_sedimentary", "tfc:native_gold", "tfc:ore/small/native_gold", 50, 14, "sphere", 350, 110, 135, 40, "tfc:sedimentary");

        addOre(oreData, "sphalerite_metamorphic", "tfc:sphalerite", "tfc:ore/sphalerite", 14,8, "cluster", 130, 20,80, 60, "tfc:metamorphic");
        addOre(oreData, "sphalerite_mvt", "tfc:sphalerite", "tfc:ore/small/sphalerite", 50, 5, "sphere", 180, 110, 140, 40, "tfc:limestone", "tfc:dolomite", "tfc:chalk");
        addOre(oreData, "sphalerite_slate", "tfc:sphalerite", "tfc:ore/small/sphalerite", 30, 6, "sphere",160,110, 140, 30, "tfc:shale", "tfc:claystone");

        addOre(oreData, "chromite_main", "tfc:garnierite", "tfc:ore/garnierite", 18, 8, "cluster", 140, 20, 90, 50, "tfc:igneous_intrusive");
        addOre(oreData, "chromite_hydro", "tfc:garnierite", "tfc:ore/garnierite", 8, 16, "cluster", 130, 20, 80, 80, "tfc:metamorphic" );
        addOre(oreData, "chromite_sedimentary", "tfc:garnierite", "tfc:ore/small/garnierite", 30, 4, "sphere", 300, 110, 130, 30,"tfc:sedimentary");

        addOre(oreData, "platinum_igneous", "tfc:native_platinum", "tfc:ore/native_platinum", 16, 10, "cluster", 150, 5, 70, 60, "tfc:igneous_intrusive");
        addOre(oreData, "platinum_sedimentary", "tfc:native_platinum", "tfc:ore/small/native_platinum", 50, 4, "sphere", 215, 30, 135, 10, "tfc:claystone", "tfc:conglomerate", "tfc:schist", "tfc:slate", "tfc:phyllite");

        addOre(oreData, "molybdenite_intrusive", "tfc:bismuthinite", "tfc:ore/small/bismuthinite", 8, 16, "sphere", 130, 20, 80, 50, "tfc:granite", "tfc:diorite");
        addOre(oreData, "molybdenite_quartz", "tfc:bismuthinite", "tfc:ore/bismuthinite", 10,18, "cluster", 120, 10, 20, 80,"tfc:granite", "tfc:gneiss", "tfc:quartzite");
        addOre(oreData, "molybdenite_extrusive", "tfc:bismuthinite", "tfc:ore/small/bismuthinite", 16, 8, "cluster", 120, 5, 80, 30, "tfc:rhyolite", "tfc:dacite");

        addOre(oreData, "silver_hydrothermal", "tfc:native_silver", "tfc:ore/native_silver", 16, 10, "cluster", 130, 30, 70, 80, "tfc:metamorphic");
        addOre(oreData, "silver_sedimentary", "tfc:native_silver", "tfc:ore/small/native_silver", 50, 4, "sphere", 300, 120, 140, 15,"tfc:sedimentary");
        addOre(oreData, "silver_intrusive", "tfc:native_silver", "tfc:ore/native_silver", 13, 8, "cluster", 120, 5, 30, 40, "tfc:gabbro", "tfc:diorite");

        addOre(oreData, "tetrahedrite_hydro", "tfc:tetrahedrite", "tfc:ore/tetrahedrite", 16, 12, "cluster", 150, 20, 70, 60, "tfc:slate", "tfc:phyllite", "tfc:schist", "tfc:gneiss", "tfc:quartzite");
        addOre(oreData, "tetrahedrite_sedimentary", "tfc:tetrahedrite", "tfc:ore/small/tetrahedrite", 50, 5, "sphere", 160, 110, 140, 40, "tfc:shale", "tfc:claystone", "tfc:limestone", "tfc:dolomite");

        addOre(oreData, "malachite_surface", "tfc:malachite", "tfc:ore/small/malachite", 4, 10, "sphere", 10, 170, 220, 40, "tfc:limestone", "tfc:dolomite", "tfc:chalk", "tfc:rocksalt");
        addOre(oreData, "silver_surface", "tfc:native_silver", "tfc:ore/small/native_silver", 8, 5, "sphere", 10, 170, 220, 50, "tfc:limestone", "tfc:dolomite", "tfc:chalk");
        addOre(oreData, "redstone_surface", "tfc:cinnabar", "minecraft:redstone", 8, 6, "sphere", 15, 170, 220, 30, "tfc:sedimentary");
        addOre(oreData, "lapis_surface", "tfc:lapis_lazuli", "tfc:ore/lapis_lazuli", 8, 8, "sphere", 15, 170, 220, 30, "tfc:sedimentary");
        addOre(oreData, "kimberlite_surface", "tfc:kimberlite", "tfc:ore/kimberlite", 8, 6, "sphere", 10, 170, 220, 30, "tfc:rocksalt", "tfc:chalk", "tfc:conglomerate");
        addOre(oreData, "saltpeter_surface", "tfc:saltpeter", "hbm:niter", 7, 9, "sphere", 10, 170, 220, 45, "tfc:claystone", "tfc:rocksalt");
        addOre(oreData, "borax_surface", "tfc:borax", "tfc:ore/borax", 6, 8, "sphere", 10, 170, 220, 30, "tfc:sedimentary");

        addOre(oreData, "redstone_quartz", "tfc:cinnabar", "minecraft:redstone", 18, 12, "cluster", 120, 40, 100, 50, "tfc:quartzite", "tfc:slate", "tfc:phyllite", "tfc:schist");
        addOre(oreData, "redstone_rich", "tfc:cinnabar", "minecraft:redstone", 12, 20, "cluster", 110, 20, 70, 80, "tfc:igneous_intrusive");
        addOre(oreData, "redstone_extrusive", "tfc:cinnabar", "minecraft:redstone", 14, 10, "cluster", 130, 30, 80, 70, "tfc:igneous_extrusive");

        addOre(oreData, "lapis_rich", "tfc:lapis_lazuli", "tfc:ore/lapis_lazuli", 8, 16, "cluster", 120, 20, 100, 70, "tfc:marble");
        addOre(oreData, "lapis_poor", "tfc:lapis_lazuli", "tfc:ore/lapis_lazuli", 30, 10, "sphere", 140, 10, 70, 50,"tfc:igneous_intrusive");

        addOre(oreData, "bit_coal_1", "tfc:bituminous_coal", "hbm:powder_coal_tiny", 50, 4, "sphere", 160, 110, 140, 80, "tfc:limestone", "tfc:dolomite");
        addOre(oreData, "bit_coal_2", "tfc:bituminous_coal", "hbm:powder_coal_tiny", 25, 10, "cluster", 140, 60, 140, 90, "tfc:slate", "tfc:shale", "tfc:claystone");

        addOre(oreData, "lignite_main", "tfc:lignite", "tfc:ore/petrified_wood", 60, 4, "sphere", 80, 110, 135, 60, "tfc:shale");
        addOre(oreData, "lignite_rich", "tfc:lignite", "tfc:ore/petrified_wood", 25, 5, "cluster", 110, 110, 150, 70, "tfc:conglomerate", "tfc:claystone");

        addOre(oreData, "graphite_1", "tfc:graphite", "tfc:ore/graphite", 20, 10, "sphere", 130, 10, 80, 60, "tfc:gneiss", "tfc:marble");
        addOre(oreData, "graphite_2", "tfc:graphite", "tfc:ore/graphite", 24, 14, "sphere", 140, 10, 80, 70, "tfc:igneous_intrusive");
        addOre(oreData, "graphite_3", "tfc:graphite", "tfc:ore/graphite", 35, 6, "sphere", 150, 10, 80, 80,"tfc:slate", "tfc:phyllite", "tfc:schist", "tfc:quartzite");

        addOre(oreData, "kaolinite_1", "tfc:kaolinite", "tfc:ore/kaolinite", 18, 12, "cluster", 120, 30, 90, 70, "tfc:gneiss", "tfc:slate", "tfc:schist", "tfc:quartzite");
        addOre(oreData, "kaolinite_2", "tfc:kaolinite", "tfc:ore/kaolinite", 50, 10, "sphere", 130, 110, 150, 50, "tfc:shale", "tfc:claystone", "tfc:conglomerate");

        addOre(oreData, "kimberlite", "tfc:kimberlite", "tfc:ore/kimberlite", 8, 18, "cluster", 70, 5, 70, 90, "tfc:gabbro");

        addOre(oreData, "borax_main", "tfc:borax", "tfc:ore/borax", 35, 5, "sphere", 120, 110, 140, 40, "tfc:rocksalt");
        addOre(oreData, "borax_crystal", "tfc:borax", "tfc:ore/borax", 14, 6, "cluster", 150, 70, 140, 70, "tfc:limestone", "tfc:dolomite", "tfc:chalk", "tfc:marble");

        addOre(oreData, "sulfur", "tfc:sulfur", "tfc:ore/sulfur", 14, 20, "cluster", 120, 60, 90, 80, "tfc:igneous_extrusive");

        addOre(oreData, "cryolite_main", "tfc:cryolite", "tfc:ore/cryolite", 26,18, "cluster", 130, 10, 80, 80, "tfc:granite", "tfc:gneiss");
        addOre(oreData, "cryolite_poor", "tfc:cryolite", "tfc:ore/cryolite", 50, 12, "sphere", 120, 10, 80, 50, "tfc:rhyolite", "tfc:andesite");

        addOre(oreData, "saltpeter_main", "tfc:saltpeter", "hbm:niter", 50, 12, "sphere", 150, 110,130, 50, "tfc:rocksalt", "tfc:claystone");
        addOre(oreData, "saltpeter_rich", "tfc:saltpeter", "tfc:ore/saltpeter", 26, 16, "cluster", 160, 30, 140, 90, "tfc:limestone", "tfc:dolomite", "tfc:chalk", "tfc:marble");

        addOre(oreData, "apatite", "tfc:microcline", "tfc:ore/microcline", 14, 24, "cluster", 140, 10, 80, 60, "tfc:metamorphic");

        addOre(oreData, "quartzite_ore", "tfc:selenite", "hbm:powder_quartz", 60, 20, "sphere", 100, 10, 80, 80, "tfc:quartzite");
        addOre(oreData, "quartzite_metamorphic", "tfc:selenite", "hbm:powder_quartz", 30, 10, "sphere", 140, 10, 80, 20, "tfc:metamorphic");

        try (FileWriter writer = new FileWriter(targetFile)) {
            GSON.toJson(oreData, writer);
        } catch (IOException e) {
        }
    }

    private static void addOre(Map<String, Object> oreData, String name, String oreBlock, String loose,
                               int width, int height, String shape, int rarity,
                               int minimum_height, int maximum_height, int density, String... base_rocks) {
        Map<String, Object> ore = new LinkedHashMap<>();

        ore.put("ore", oreBlock);
        ore.put("loose", loose);
        ore.put("width", width);
        ore.put("height", height);
        ore.put("shape", shape);
        ore.put("rarity", rarity);
        ore.put("minimum_height", minimum_height);
        ore.put("maximum_height", maximum_height);
        ore.put("density", density);
        ore.put("base_rocks", Arrays.asList(base_rocks));

        oreData.put(name, ore);
    }

    private static final String ORE_SPAWN_DATA = "ore_spawn_data.json";

    public static void clean(FMLPreInitializationEvent event) {
        File configDir = event.getModConfigurationDirectory();
        File tfcFolder = new File(configDir, "tfc");

        if (!tfcFolder.exists()) {
            tfcFolder.mkdirs();
        }

        File oldFile = new File(tfcFolder, ORE_SPAWN_DATA);
        if (oldFile.exists()) {
            oldFile.delete();
        }
    }
}