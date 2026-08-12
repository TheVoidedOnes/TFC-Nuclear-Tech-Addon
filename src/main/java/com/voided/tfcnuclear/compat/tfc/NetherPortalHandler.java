package com.voided.tfcnuclear.compat.tfc;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NetherPortalHandler {

    @SubscribeEvent
    public void onPortalSpawn(BlockEvent.PortalSpawnEvent event) {
        event.setCanceled(true);

        World world = event.getWorld();
        BlockPos pos = event.getPos();

        if (!world.isRemote && world.isAirBlock(pos)) {
            world.setBlockState(pos, Blocks.FIRE.getDefaultState());
        }
    }
}