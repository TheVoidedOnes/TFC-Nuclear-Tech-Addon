package com.voided.tfcnuclear.inventory.handler;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import net.dries007.tfc.api.capability.size.CapabilityItemSize;
import net.dries007.tfc.api.capability.size.ItemSizeHandler;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = "tfcnuclear")
public class ItemSizeRegistry {

    private static boolean registered = false;

    private static final String[] BIG_MACHINES = {
            "red_pylon_large", "machine_bigasstank", "machine_orbus", "machine_battery_redd",
            "machine_zirnox", "machine_assembly_factory", "machine_chemical_factory",
            "machine_purex", "machine_chungus", "machine_tower_small", "machine_tower_large",
            "machine_deuterium_tower", "machine_electrolyser", "machine_fracking_tower", "machine_flare",
            "chimney_brick", "chimney_industrial", "machine_excavator", "machine_ore_slopper",
            "machine_mining_laser", "machine_catalytic_cracker", "machine_vacuum_distill", "machine_refinery",
            "machine_coker", "fusion_torus", "fusion_klystron", "fusion_breeder", "fusion_collector",
            "fusion_boiler", "fusion_mhdt", "fusion_coupler", "fusion_plasma_forge", "icf",
            "pa_source", "pa_quadrupole", "pa_dipole", "pa_detector", "launch_pad_large",
            "launch_table", "nuke_gadget", "nuke_man", "nuke_mike", "nuke_tsar", "nuke_n2",
            "nuke_fstbmb", "machine_radar_large", "sat_mapper", "sat_scanner", "sat_radar",
            "sat_laser", "sat_foeq", "sat_resonator", "sat_miner", "sat_luner_miner",
            "sat_gerald", "soyuz_launcher", "large_vehicle_door", "silo_hatch_large",
            "machine_compressor", "machine_arc_furnace"
    };

    private static final String[] SMALL_MACHINES = {
            "machine_press", "machine_epress", "machine_conveyor_press", "machine_reactor_small_new",
            "machine_reactor_breeding", "machine_fluidtank",
            "fire_door", "water_door", "round_airlock_door", "secure_access_door", "silo_hatch",
            "machine_steam_engine", "machine_solar_boiler",
            "machine_ammo_press", "machine_assembly_machine", "machine_precass", "machine_arc_welder",
            "machine_soldering_station",
            "machine_chemical_plant", "machine_strand_caster", "machine_mixer", "machine_industrial_turbine",
            "machine_condenser_powered",
            "machine_liquefactor", "machine_solidifier", "machine_compressor_compact", "machine_well",
            "machine_pumpjack", "machine_autosaw",
            "machine_annihilator", "machine_turbofan", "machine_woodburner", "machine_turbine_gas",
            "machine_combustion_engine", "machine_fraction_tower",
            "fraction_spacer", "machine_catalytic_reformer", "machine_hydrotreater", "machine_pyrooven",
            "machine_drain", "machine_furnace_brick_off", "machine_rtg_furnace_off", "machine_centrifuge",
            "machine_gascent", "machine_silex", "machine_rotary_furnace", "machine_blast_furance",
            "machine_fel", "machine_crystallizer", "machine_cyclotron", "machine_exposure_chamber",
            "machine_radgen", "pump_electric", "pump_steam", "furnace_steel", "furnace_iron",
            "machine_ashpit", "furnace_combination", "machine_stirling", "machine_stirling_steel",
            "machine_sawmill", "heat_boiler", "machine_crucible", "machine_industrial_boiler", "watz",
            "watz_pump", "pa_rfc", "nuke_boy", "nuke_fleija", "nuke_prototype", "nuke_solinium", "nuke_custom",
            "machine_radar", "compact_launcher", "railgun_plasma"
    };

    private static final String[] PREFIXES = {
            "mp_", "ingot_", "powder_", "nugget_", "plate_", "waste_", "stamp_",
            "fusion_shield_", "ore_", "bedrock_", "rbmk_", "qe_", "sliding_",
            "anvil_", "heater_", "dfc_", "turret_", "missile_"
    };

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !registered) {
            registered = true;
            registerSizes();
        }
    }

    public static void registerSizes() {
        registerItem(Item.getItemFromBlock(ModBlocks.machine_shredder), Size.NORMAL, Weight.MEDIUM, false);
        registerItem(Item.getItemFromBlock(ModBlocks.machine_diesel), Size.NORMAL, Weight.MEDIUM, false);

        for (String id : BIG_MACHINES) {
            registerMachine(id, Size.VERY_LARGE, Weight.VERY_HEAVY);
        }

        for (String id : SMALL_MACHINES) {
            registerMachine(id, Size.HUGE, Weight.HEAVY);
        }

        for (String prefix : PREFIXES) {
            registerByPrefix(prefix);
        }
    }

    private static void registerItem(Item item, Size size, Weight weight, boolean canStack) {
        if (item != null) {
            CapabilityItemSize.CUSTOM_ITEMS.put(
                    IIngredient.of(item),
                    () -> ItemSizeHandler.get(size, weight, canStack)
            );
        }
    }

    private static void registerMachine(String id, Size size, Weight weight) {
        Item item = Item.REGISTRY.getObject(new ResourceLocation("hbm", id));
        if (item != null) {
            CapabilityItemSize.CUSTOM_ITEMS.put(
                    IIngredient.of(item),
                    () -> ItemSizeHandler.get(size, weight, false)
            );
        }
    }

    private static void registerByPrefix(String prefix) {
        for (Item item : Item.REGISTRY) {
            ResourceLocation loc = Item.REGISTRY.getNameForObject(item);
            if (loc != null && loc.getNamespace().equals("hbm") && loc.getPath().startsWith(prefix)) {
                CapabilityItemSize.CUSTOM_ITEMS.put(
                        IIngredient.of(item),
                        () -> ItemSizeHandler.get(getSizeForPrefix(prefix), getWeightForPrefix(prefix), canStackForPrefix(prefix))
                );
            }
        }
    }

    private static Size getSizeForPrefix(String prefix) {
        switch (prefix) {
            case "powder_":
            case "nugget_":
            case "ore_":
                return Size.SMALL;
            case "mp_":
            case "bedrock_":
            case "qe_":
            case "sliding_":
            case "dfc_":
                return Size.NORMAL;
            case "ingot_":
            case "plate_":
            case "waste_":
            case "rbmk_":
            case "heater_":
                return Size.LARGE;
            case "stamp_":
                return Size.NORMAL;
            case "fusion_shield_":
            case "missile_":
                return Size.VERY_LARGE;
            case "anvil_":
            case "turret_":
                return Size.HUGE;
            default:
                return Size.NORMAL;
        }
    }

    private static Weight getWeightForPrefix(String prefix) {
        switch (prefix) {
            case "powder_":
            case "nugget_":
                return Weight.VERY_LIGHT;
            case "ore_":
                return Weight.MEDIUM;
            case "mp_":
            case "bedrock_":
            case "qe_":
            case "sliding_":
            case "dfc_":
            case "stamp_":
                return Weight.HEAVY;
            case "ingot_":
            case "plate_":
            case "waste_":
            case "heater_":
                return Weight.LIGHT;
            case "fusion_shield_":
            case "anvil_":
            case "turret_":
            case "missile_":
                return Weight.VERY_HEAVY;
            case "rbmk_":
                return Weight.HEAVY;
            default:
                return Weight.MEDIUM;
        }
    }

    private static boolean canStackForPrefix(String prefix) {
        switch (prefix) {
            case "mp_":
            case "fusion_shield_":
            case "anvil_":
            case "turret_":
            case "missile_":
                return false;
            default:
                return true;
        }
    }
}