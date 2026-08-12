package com.voided.tfcnuclear.compat.tfc;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ITreeReplantHandler {
    boolean isTFCWood(IBlockState state);
    ItemStack getTFCSapling(IBlockState state);
    boolean canPlaceTFCSapling(World world, BlockPos pos, ItemStack sapling);
    IBlockState createSaplingState(ItemStack sapling);
}