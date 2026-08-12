package com.voided.tfcnuclear.compat.tfcambiental;

import com.hbm.api.fluidmk2.FluidNode;
import com.hbm.blocks.network.FluidDuctBase;
import com.hbm.blocks.network.FluidDuctBox;
import com.hbm.blocks.network.FluidDuctStandard;
import com.hbm.blocks.network.IBlockFluidDuct;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.tileentity.network.TileEntityPipeBaseNT;
import com.hbm.uninos.UniNodespace;
import com.lumintorious.ambiental.api.TemperatureRegistry;
import com.lumintorious.ambiental.modifiers.BlockModifier;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;

public class HBMPipeHeatProvider {

    private static int registeredCount = 0;

    @Optional.Method(modid = "tfcambiental")
    public static void register() {
        TemperatureRegistry.BLOCKS.register((IBlockState state, BlockPos pos, EntityPlayer player) -> {
            World world = player.world;
            Block block = state.getBlock();

            boolean isPipe = false;

            if (block instanceof IBlockFluidDuct ||
                    block instanceof FluidDuctBase ||
                    block instanceof FluidDuctBox ||
                    block instanceof FluidDuctStandard) {
                isPipe = true;
            }

            if (block.getRegistryName() != null) {
                String path = block.getRegistryName().getPath();
                if (path.contains("duct") || path.contains("pipe")) {
                    isPipe = true;
                }
            }

            if (isPipe) {
                TileEntity te = world.getTileEntity(pos);

                if (te instanceof TileEntityPipeBaseNT) {
                    TileEntityPipeBaseNT pipe = (TileEntityPipeBaseNT) te;
                    FluidType fluidType = pipe.getType();

                    if (fluidType != Fluids.NONE && isHotFluid(fluidType)) {
                        if (isNetworkActive(world, pos, fluidType)) {
                            float heatValue = getHeatValueForFluid(fluidType);
                            registeredCount++;
                            return new BlockModifier("hbm_hot_pipe", heatValue, 2.0f, true);
                        }
                    }
                }
            }
            return null;
        });
    }

    private static boolean isNetworkActive(World world, BlockPos pos, FluidType fluidType) {
        try {
            FluidNode node = (FluidNode) UniNodespace.getNode(world, pos, fluidType.getNetworkProvider());

            if (node == null || node.net == null || !node.net.isValid()) {
                return false;
            }

            return node.net.fluidTracker > 0 || (!node.net.receiverEntries.isEmpty() && !node.net.providerEntries.isEmpty());

        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isHotFluid(FluidType fluidType) {
        return fluidType == Fluids.STEAM || fluidType == Fluids.GAS;
    }

    private static float getHeatValueForFluid(FluidType fluidType) {
        if (fluidType == Fluids.STEAM) return 10.0f;
        if (fluidType == Fluids.GAS) return 10.0f;
        return 0;
    }

    public static int getRegisteredCount() {
        return registeredCount;
    }
}