package com.voided.tfcnuclear.compat.tfc;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import java.util.HashMap;
import java.util.Map;

public class TFCTreeHandler {

    private static final Map<String, String> WOOD_TO_SAPLING = new HashMap<>();
    private static final Map<String, IBlockState> SAPLING_STATE_CACHE = new HashMap<>();
    private static final Map<String, Boolean> TFC_WOOD_CACHE = new HashMap<>();

    static {
        addMapping("tfc:wood/log/acacia", "tfc:wood/sapling/acacia");
        addMapping("tfc:wood/log/ash", "tfc:wood/sapling/ash");
        addMapping("tfc:wood/log/aspen", "tfc:wood/sapling/aspen");
        addMapping("tfc:wood/log/birch", "tfc:wood/sapling/birch");
        addMapping("tfc:wood/log/blackwood", "tfc:wood/sapling/blackwood");
        addMapping("tfc:wood/log/chestnut", "tfc:wood/sapling/chestnut");
        addMapping("tfc:wood/log/douglas_fir", "tfc:wood/sapling/douglas_fir");
        addMapping("tfc:wood/log/hickory", "tfc:wood/sapling/hickory");
        addMapping("tfc:wood/log/kapok", "tfc:wood/sapling/kapok");
        addMapping("tfc:wood/log/maple", "tfc:wood/sapling/maple");
        addMapping("tfc:wood/log/oak", "tfc:wood/sapling/oak");
        addMapping("tfc:wood/log/palm", "tfc:wood/sapling/palm");
        addMapping("tfc:wood/log/pine", "tfc:wood/sapling/pine");
        addMapping("tfc:wood/log/rosewood", "tfc:wood/sapling/rosewood");
        addMapping("tfc:wood/log/sequoia", "tfc:wood/sapling/sequoia");
        addMapping("tfc:wood/log/spruce", "tfc:wood/sapling/spruce");
        addMapping("tfc:wood/log/sycamore", "tfc:wood/sapling/sycamore");
        addMapping("tfc:wood/log/white_cedar", "tfc:wood/sapling/white_cedar");
        addMapping("tfc:wood/log/willow", "tfc:wood/sapling/willow");
    }

    private static void addMapping(String woodId, String saplingId) {
        WOOD_TO_SAPLING.put(woodId, saplingId);

        try {
            Block saplingBlock = Block.getBlockFromName(saplingId);
            if (saplingBlock != null) {
                IBlockState defaultState = saplingBlock.getDefaultState();
                SAPLING_STATE_CACHE.put(saplingId, defaultState);

                for (int meta = 0; meta < 16; meta++) {
                    try {
                        IBlockState state = saplingBlock.getStateFromMeta(meta);
                        if (state != null && state.getBlock() == saplingBlock) {
                            String key = saplingId + ":" + meta;
                            SAPLING_STATE_CACHE.put(key, state);
                        }
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }

    public static boolean canPlaceTFCSapling(World world, BlockPos pos, ItemStack sapling) {
        if (sapling == null || sapling.isEmpty()) {
            return false;
        }
        if (world == null || pos == null) {
            return false;
        }

        Item saplingItem = sapling.getItem();
        Block saplingBlock = Block.getBlockFromItem(saplingItem);

        if (saplingBlock == null) {
            return false;
        }

        if (saplingBlock instanceof IPlantable) {
            IPlantable plantable = (IPlantable) saplingBlock;
            IBlockState soilState = world.getBlockState(pos);
            Block soilBlock = soilState.getBlock();
            return soilBlock.canSustainPlant(soilState, world, pos, EnumFacing.UP, plantable);
        }

        if (saplingBlock instanceof BlockSapling) {
            IBlockState soilState = world.getBlockState(pos);
            Block soilBlock = soilState.getBlock();
            return soilBlock.canSustainPlant(soilState, world, pos, EnumFacing.UP, (IPlantable) saplingBlock);
        }

        IBlockState soilState = world.getBlockState(pos);
        String soilId = getBlockId(soilState);
        if (soilId.startsWith("tfc:")) {
            return soilId.contains("grass") ||
                    soilId.contains("dirt") ||
                    soilId.contains("clay") ||
                    soilId.contains("loam") ||
                    soilId.contains("soil");
        }

        return false;
    }

    public static IBlockState createSaplingState(ItemStack sapling) {
        if (sapling == null || sapling.isEmpty()) {
            return null;
        }

        Item saplingItem = sapling.getItem();
        Block block = Block.getBlockFromItem(saplingItem);
        if (block == null) {
            return null;
        }

        int meta = sapling.getMetadata();

        try {
            IBlockState state = block.getStateFromMeta(meta);
            if (state != null && state.getBlock() == block) {
                return state;
            }
        } catch (Exception ignored) {
        }

        try {
            return block.getDefaultState();
        } catch (Exception ignored) {
        }

        return null;
    }

    public static String getBlockId(IBlockState state) {
        if (state == null || state.getBlock() == null) {
            return "";
        }
        return Block.REGISTRY.getNameForObject(state.getBlock()).toString();
    }

    public static ItemStack getTFCSapling(IBlockState woodState) {
        if (woodState == null) {
            return null;
        }

        String woodId = getBlockId(woodState);
        int meta = woodState.getBlock().getMetaFromState(woodState);

        String saplingId = WOOD_TO_SAPLING.get(woodId);

        if (saplingId == null) {
            for (Map.Entry<String, String> entry : WOOD_TO_SAPLING.entrySet()) {
                String key = entry.getKey();
                if (woodId.startsWith(key) ||
                        woodId.endsWith(key) ||
                        woodId.contains(key) ||
                        key.contains(woodId)) {
                    saplingId = entry.getValue();
                    break;
                }
            }
        }

        if (saplingId != null) {
            Block saplingBlock = Block.getBlockFromName(saplingId);
            if (saplingBlock != null) {
                try {
                    IBlockState saplingState = saplingBlock.getStateFromMeta(meta % 8);
                    if (saplingState != null && saplingState.getBlock() == saplingBlock) {
                        return new ItemStack(saplingBlock, 1, meta % 8);
                    }
                } catch (Exception ignored) {
                }

                try {
                    IBlockState defaultState = saplingBlock.getDefaultState();
                    return new ItemStack(saplingBlock, 1,
                            saplingBlock.getMetaFromState(defaultState));
                } catch (Exception ignored) {
                }
            }
        }

        return null;
    }

    public static boolean isTFCWood(IBlockState state) {
        if (state == null || state.getBlock() == null) {
            return false;
        }

        String blockId = getBlockId(state);

        if (TFC_WOOD_CACHE.containsKey(blockId)) {
            return TFC_WOOD_CACHE.get(blockId);
        }

        boolean isTFC = blockId.startsWith("tfc:") &&
                !blockId.contains("sapling") &&
                !blockId.contains("planks") &&
                !blockId.contains("leaves") &&
                !blockId.contains("stripped") &&
                !blockId.contains("fence") &&
                !blockId.contains("stairs") &&
                !blockId.contains("slab") &&
                !blockId.contains("button") &&
                !blockId.contains("door") &&
                !blockId.contains("trapdoor") &&
                (blockId.contains("/log") ||
                        blockId.contains("/wood") ||
                        blockId.endsWith("_log") ||
                        blockId.contains(":wood/") ||
                        blockId.endsWith("_wood"));

        TFC_WOOD_CACHE.put(blockId, isTFC);
        return isTFC;
    }
}