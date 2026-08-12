package com.voided.tfcnuclear.inventory.material;

import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.NTMMaterial;
import net.minecraftforge.fml.common.Loader;

import static com.hbm.inventory.material.Mats.*;
import static com.voided.tfcnuclear.inventory.material.TFCNuclearMaterialShapes.*;
import static com.voided.tfcnuclear.inventory.material.TFCNuclearShapeInitializer.TFC_CHISEL;
import static com.voided.tfcnuclear.inventory.material.TFCNuclearShapeInitializer.TFC_HAMMER;
import static com.voided.tfcnuclear.inventory.material.TFCNuclearShapeInitializer.TFC_HOE;
import static com.voided.tfcnuclear.inventory.material.TFCNuclearShapeInitializer.TFC_JAVELIN;
import static com.voided.tfcnuclear.inventory.material.TFCNuclearShapeInitializer.TFC_KNIFE;
import static com.voided.tfcnuclear.inventory.material.TFCNuclearShapeInitializer.TFC_MACE;
import static com.voided.tfcnuclear.inventory.material.TFCNuclearShapeInitializer.TFC_PROSPECTOR;
import static com.voided.tfcnuclear.inventory.material.TFCNuclearShapeInitializer.TFC_SAW;
import static com.voided.tfcnuclear.inventory.material.TFCNuclearShapeInitializer.TFC_SCYTHE;
import static com.voided.tfcnuclear.inventory.material.TFCNuclearShapeInitializer.TFC_SHOVEL;
import static com.voided.tfcnuclear.inventory.material.TFCNuclearShapeInitializer.TFC_SWORD;

public class TFCNuclearMats {

    public static final NTMMaterial MAT_BRONZE;
    public static final NTMMaterial MAT_TIN;
    public static final NTMMaterial MAT_SILVER;
    public static final NTMMaterial MAT_PLATINUM;
    public static final NTMMaterial MAT_BLACKBRONZE;
    public static final NTMMaterial MAT_BRASS;
    public static final NTMMaterial MAT_ROSEGOLD;
    public static final NTMMaterial MAT_STERLINGSILVER;
    public static final NTMMaterial MAT_MAGNETITE;
    public static final NTMMaterial MAT_LIMONITE;
    public static NTMMaterial MAT_ZINC;
    public static final NTMMaterial MAT_CHROME;
    public static final NTMMaterial MAT_MOLYBDENUM;
    public static final NTMMaterial MAT_ELASTICCOPPER;
    public static final NTMMaterial MAT_BLACKSTEEL;
    public static final NTMMaterial MAT_WEAKREDSTEEL;
    public static final NTMMaterial MAT_WEAKBLUESTEEL;
    public static final NTMMaterial MAT_WEAKSTEEL;
    public static final NTMMaterial MAT_BLUESTEEL;
    public static final NTMMaterial MAT_REDSTEEL;
    public static final NTMMaterial MAT_PHOSPHORUS_TFC;
    public static final NTMMaterial MAT_PHOSPHATECHARGE;
    public static final NTMMaterial MAT_APATITE;

    public static void init() {}

    static {
        TFCNuclearShapeInitializer.ensureInitialized();

        if (!Loader.isModLoaded("hbmspace")) {
            MAT_ZINC = makeSmeltable(25011, df("Zinc"), 0xE5F0F3).m();
        }
        MAT_BRONZE = makeSmeltable(25000, df("Bronze"), 0xCD7F32, 0xCD7F32, 0xCD7F32).setAutogen(TFC_PICK, TFC_HOE, TFC_SWORD, TFC_PROSPECTOR, TFC_AXE, TFC_SAW, TFC_SCYTHE, TFC_JAVELIN, TFC_CHISEL, TFC_HAMMER, TFC_KNIFE, TFC_SHOVEL, TFC_MACE).m();
        MAT_TIN = makeSmeltable(25002, df("Tin"), 0xACB5B9).m();
        MAT_SILVER = makeSmeltable(25003, df("Silver"), 0xC0C0C0).m();
        MAT_PLATINUM = makeSmeltable(25004, df("Platinum"), 0xD9D9D9).m();
        MAT_BLACKBRONZE = makeSmeltable(25005, df("BlackBronze"), 0x3A0A1F).setAutogen(TFC_PICK, TFC_HOE, TFC_SWORD, TFC_PROSPECTOR, TFC_AXE, TFC_SAW, TFC_SCYTHE, TFC_JAVELIN, TFC_CHISEL, TFC_HAMMER, TFC_KNIFE, TFC_SHOVEL, TFC_MACE).m();
        MAT_BRASS = makeSmeltable(25006, df("Brass"), 0xC3A343).m();
        MAT_ROSEGOLD = makeSmeltable(25007, df("RoseGold"), 0xB76E79).m();
        MAT_STERLINGSILVER = makeSmeltable(25008, df("SterlingSilver"), 0xB8BFC7).m();
        MAT_MAGNETITE = makeSmeltable(25009, df("Magnetite"), 0x2f3640).m();
        MAT_LIMONITE = makeSmeltable(25010, df("Limonite"), 0xbe7f51).m();
        MAT_CHROME = makeSmeltable(25012, df("Chrome"), 0xB8C4D0).m();
        MAT_MOLYBDENUM = makeSmeltable(25013, df("Molybdenum"), 0xB0B5B9).m();
        MAT_ELASTICCOPPER = makeSmeltable(25014, df("ElasticCopper"), 0x0A3D2F).setAutogen(TFC_PICK, TFC_HOE, TFC_SWORD, TFC_PROSPECTOR, TFC_AXE, TFC_SAW, TFC_SCYTHE, TFC_JAVELIN, TFC_CHISEL, TFC_HAMMER, TFC_KNIFE, TFC_SHOVEL, TFC_MACE).m();
        MAT_BLACKSTEEL = makeSmeltable(25015, df("BlackSteel"), 0x1A1D1F).setAutogen(TFC_PICK, TFC_HOE, TFC_SWORD, TFC_PROSPECTOR, TFC_AXE, TFC_SAW, TFC_SCYTHE, TFC_JAVELIN, TFC_CHISEL, TFC_HAMMER, TFC_KNIFE, TFC_SHOVEL, TFC_MACE).m();
        MAT_WEAKREDSTEEL = makeSmeltable(25016, df("WeakRedSteel"), 0x4A2C2A).m();
        MAT_WEAKBLUESTEEL = makeSmeltable(25017, df("WeakBlueSteel"), 0x1E3A5F).m();
        MAT_WEAKSTEEL = makeSmeltable(25018, df("WeakSteel"), 0x000).m();
        MAT_BLUESTEEL = makeSmeltable(25019, df("BlueSteel"), 0x1E3A5F).setAutogen(TFC_PICK, TFC_HOE, TFC_SWORD, TFC_PROSPECTOR, TFC_AXE, TFC_SAW, TFC_SCYTHE, TFC_JAVELIN, TFC_CHISEL, TFC_HAMMER, TFC_KNIFE, TFC_SHOVEL, TFC_MACE).m();
        MAT_REDSTEEL = makeSmeltable(25020, df("RedSteel"), 0x4A2C2A).setAutogen(TFC_PICK, TFC_HOE, TFC_SWORD, TFC_PROSPECTOR, TFC_AXE, TFC_SAW, TFC_SCYTHE, TFC_JAVELIN, TFC_CHISEL, TFC_HAMMER, TFC_KNIFE, TFC_SHOVEL, TFC_MACE).m();
        MAT_PHOSPHORUS_TFC = makeSmeltable(25021, df("Phosphorus"), 0xFFFACD).m();
        MAT_PHOSPHATECHARGE = makeSmeltable(25022, df("PhosphateCharge"), 0xB8A99C).m();
        MAT_APATITE = makeSmeltable(25023, df("Apatite"), 0x90EE90).m();
    }
}