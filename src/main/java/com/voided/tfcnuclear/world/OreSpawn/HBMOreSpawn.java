package com.voided.tfcnuclear.world.OreSpawn;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class HBMOreSpawn {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILE_NAME = "ntm_ore_spawn_data.json";

    public static void generate(FMLPreInitializationEvent event) {
        File configDir = event.getModConfigurationDirectory();
        File targetFile = new File(configDir, "tfc/" + FILE_NAME);
        targetFile.getParentFile().mkdirs();

        Map<String, Object> oreData = new LinkedHashMap<>();

        addOre(oreData, "uranium_sedimentary", "hbm:ore_uranium", "hbm:nugget_uranium", 24, 12, "cluster", 180, 110, 130, 40, 0,"tfc:shale", "tfc:claystone", "tfc:limestone", "tfc:dolomite");
        addOre(oreData, "uranium_igneous", "hbm:ore_uranium", "hbm:nugget_uranium", 28, 18, "cluster", 140, 20, 90, 70, 0,"tfc:igneous_extrusive");

        addOre(oreData, "thorium_intrusive", "hbm:ore_thorium", "hbm:nugget_th232", 24, 14, "cluster", 120, 10, 80, 50,0, "tfc:granite", "tfc:rhyolite");
        addOre(oreData, "thorium_metamorphic", "hbm:ore_thorium", "hbm:nugget_th232", 18, 10, "cluster", 120, 30, 80, 80, 0,"tfc:gneiss");

        addOre(oreData, "titanium_intrusive", "hbm:ore_titanium", "hbm:powder_titanium", 18, 12, "cluster", 110, 5, 80, 60, 0,"tfc:gabbro", "tfc:basalt", "tfc:dacite");
        addOre(oreData, "titanium_metamorphic", "hbm:ore_titanium", "hbm:powder_titanium", 20, 20, "cluster", 120, 20, 105, 90, 0,"tfc:gneiss", "tfc:schist", "tfc:quartzite");
        addOre(oreData, "titanium_sedimentary", "hbm:ore_titanium", "hbm:powder_titanium", 50, 12, "sphere", 80, 110, 140, 40, 0,"tfc:rocksalt");

        addOre(oreData, "tungsten_metamorphic", "hbm:ore_tungsten", "hbm:powder_tungsten", 14, 24, "cluster", 120, 10,70, 50,0, "tfc:gneiss", "tfc:quartzite", "tfc:schist", "tfc:marble");
        addOre(oreData, "tungsten_intrusive_1", "hbm:ore_tungsten", "hbm:powder_tungsten", 20, 20, "cluster", 120, 10, 80, 90, 0,"tfc:granite", "tfc:rhyolite");
        addOre(oreData, "tungsten_sedimentary", "hbm:ore_tungsten", "hbm:powder_tungsten", 50, 12, "sphere", 180, 110, 140, 30, 0,"tfc:limestone", "tfc:dolomite");

        addOre(oreData, "beryllium_1", "hbm:ore_beryllium", "hbm:nugget_beryllium", 18, 12, "cluster", 130, 5, 70, 30, 0,"tfc:igneous_extrusive");
        addOre(oreData, "beryllium_2", "hbm:ore_beryllium", "hbm:nugget_beryllium", 12, 18, "cluster", 120, 20, 80, 60, 0,"tfc:quartzite", "tfc:schist", "tfc:marble");

        addOre(oreData, "cobalt_metamorphic", "hbm:ore_cobalt", "hbm:powder_cobalt_tiny", 14, 24, "cluster", 130, 10, 70, 60, 0,"tfc:slate", "tfc:phyllite", "tfc:schist", "tfc:gneiss", "tfc:quartzite");
        addOre(oreData, "cobalt_intrusive", "hbm:ore_cobalt", "hbm:nugget_cobalt", 18, 10, "cluster", 130, 5, 70, 40, 0,"tfc:gabbro", "tfc:diorite");

        addOre(oreData, "cinnabar_extrusive", "hbm:ore_cinnabar", "hbm:nugget_mercury", 18, 18, "cluster", 130, 30, 100, 70, 0,"tfc:igneous_extrusive");
        addOre(oreData, "cinnabar_sedimentary", "hbm:ore_cinnabar", "hbm:nugget_mercury_tiny", 50, 10, "sphere", 140,55, 130, 40, 0,"tfc:limestone", "tfc:dolomite", "tfc:chalk", "tfc:marble");

        addOre(oreData, "fluorite_1", "hbm:ore_fluorite", "hbm:fluorite", 8, 22, "cluster", 130, 10, 80, 70, 0,"tfc:granite", "tfc:gneiss", "tfc:quartzite", "tfc:schist");
        addOre(oreData, "fluorite_2", "hbm:ore_fluorite", "hbm:fluorite", 24, 12, "cluster", 150,  110, 130, 40,  0,"tfc:conglomerate", "tfc:claystone", "tfc:shale");
        addOre(oreData, "fluorite_3", "hbm:ore_fluorite", "hbm:fluorite", 50, 12, "sphere", 160, 110, 140, 20,  0,"tfc:limestone", "tfc:dolomite", "tfc:marble");

        addOre(oreData, "asbestos_igneous", "hbm:ore_asbestos", "hbm:powder_asbestos", 18, 12, "cluster", 120, 10, 70, 70, 0,"tfc:gabbro", "tfc:diorite", "tfc:basalt");
        addOre(oreData, "asbestos_metamorphic", "hbm:ore_asbestos", "hbm:powder_asbestos", 12, 20, "cluster", 120, 5, 80, 35, 0, "tfc:slate", "tfc:phyllite", "tfc:schist", "tfc:gneiss", "tfc:quartzite");
        addOre(oreData, "asbestos_surface", "hbm:ore_asbestos", "hbm:powder_asbestos", 8, 4, "sphere", 10,170, 220, 10, 0, "tfc:dolomite");

        addOre(oreData, "rare_earth_1", "hbm:ore_rare", "hbm:chunk_ore", 14, 22, "cluster", 130, 5, 70, 70, 0, "tfc:granite", "tfc:rhyolite");
        addOre(oreData, "rare_earth_2", "hbm:ore_rare", "hbm:chunk_ore", 50, 10, "sphere", 430, 120, 150, 40, 0, "tfc:sedimentary");
        addOre(oreData, "rare_earth_surface", "hbm:ore_rare", "hbm:chunk_ore", 8, 4, "sphere", 15, 170, 200, 10, 0,"tfc:sedimentary");

        addOre(oreData, "coltan", "hbm:ore_coltan", null, 7, 7, "sphere", 3000, 5, 90, 20, 0, "tfc:sedimentary", "tfc:igneous_intrusive", "tfc:igneous_extrusive", "tfc:metamorphic");
        addOre(oreData, "austalium", "hbm:ore_australium", "hbm:nugget_australium", 15, 25, "sphere", 6000, 10, 100, 80,0, "tfc:sedimentary", "tfc:igneous_intrusive", "tfc:igneous_extrusive", "tfc:metamorphic");
        addOre(oreData, "alex", "hbm:ore_alexandrite", null, 5,5, "sphere", 3000, 10, 100, 10, 0, "tfc:sedimentary", "tfc:igneous_intrusive", "tfc:igneous_extrusive", "tfc:metamorphic");

        addOre(oreData, "bauxite", "hbm:stone_resource", "minecraft:iron_nugget", 26, 18, "cluster", 150, 110, 130, 60, 5,  "tfc:conglomerate", "tfc:shale", "tfc:claystone");
        addOre(oreData, "bauxite_surface", "hbm:stone_resource", "minecraft:iron_nugget", 10, 14, "sphere", 10, 160, 200, 20, 5, "tfc:limestone", "tfc:dolomite");

        addOre(oreData, "oil_1", "hbm:ore_oil",null, 18, 12, "cluster", 120, 110, 140, 1000, 0, "tfc:shale", "tfc:claystone");
        addOre(oreData, "oil_2", "hbm:ore_oil", null, 45, 25, "sphere", 140, 110, 140, 1000, 0, "tfc:dolomite", "tfc:limestone");
        addOre(oreData, "oil_rich", "hbm:ore_oil", null, 35, 50, "sphere", 140, 5, 50, 1000, 0, "tfc:marble", "tfc:quartzite", "tfc:gneiss");

        addOre(oreData, "hbm_limestone", "hbm:stone_resource", "hbm:powder_limestone", 20, 2, "sphere", 170, 120, 150, 100, 4, "tfc:limestone");


        try (FileWriter writer = new FileWriter(targetFile)) {
            GSON.toJson(oreData, writer);
        } catch (IOException e) {
        }
    }

    private static void addOre(Map<String, Object> oreData, String name, String oreBlock, String loose,
                               int width, int height, String shape, int rarity,
                               int minimum_height, int maximum_height, int density, int meta, String... base_rocks) {
        Map<String, Object> ore = new LinkedHashMap<>();

        ore.put("ore", oreBlock);
        if (loose != null && !loose.isEmpty()) {
            ore.put("loose", loose);
        }
        ore.put("width", width);
        ore.put("height", height);
        ore.put("shape", shape);
        ore.put("rarity", rarity);
        ore.put("minimum_height", minimum_height);
        ore.put("maximum_height", maximum_height);
        ore.put("density", density);
        ore.put("meta", meta);
        ore.put("base_rocks", Arrays.asList(base_rocks));
        oreData.put(name, ore);
    }
}