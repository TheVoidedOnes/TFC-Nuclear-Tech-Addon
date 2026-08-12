package com.voided.tfcnuclear.overwrite_contents.mixin.handler;

import com.hbm.tileentity.machine.TileEntityMachineAutosaw;
import com.voided.tfcnuclear.compat.tfc.TFCTreeHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(value = TileEntityMachineAutosaw.class, remap = false)
public abstract class MixinTileAutosaw {

    @Inject(
            method = "fellTree",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void onFellTree(BlockPos hitPos, CallbackInfo ci) {
        fellTreeWithTFC(hitPos);
        ci.cancel();
    }

    @Unique
    private void fellTreeWithTFC(BlockPos hitPos) {
        TileEntityMachineAutosaw self = (TileEntityMachineAutosaw) (Object) this;
        World world = self.getWorld();

        int hitX = hitPos.getX();
        int hitY = hitPos.getY();
        int hitZ = hitPos.getZ();

        BlockPos hitCol = new BlockPos(hitX, -1, hitZ);

        java.util.HashMap<BlockPos, BlockPos> trunks = new java.util.HashMap<>();

        for (int dx = -9; dx <= 9; dx++) {
            for (int dz = -9; dz <= 9; dz++) {
                if (dx * dx + dz * dz > 81) continue;

                int colX = self.getPos().getX() + dx;
                int colZ = self.getPos().getZ() + dz;

                if (world.getBlockState(new BlockPos(colX, hitY, colZ)).getMaterial() != Material.WOOD) {
                    continue;
                }

                int baseY = hitY;
                while (hitY - baseY < 16 && world.getBlockState(new BlockPos(colX, baseY - 1, colZ)).getMaterial() == Material.WOOD) {
                    baseY--;
                }

                if (!canSupportSapling(world, new BlockPos(colX, baseY - 1, colZ))) {
                    continue;
                }

                trunks.put(new BlockPos(colX, -1, colZ), new BlockPos(colX, baseY, colZ));
            }
        }

        if (!trunks.containsKey(hitCol)) {
            int baseY = hitY;
            while (hitY - baseY < 16 && world.getBlockState(new BlockPos(hitX, baseY - 1, hitZ)).getMaterial() == Material.WOOD) {
                baseY--;
            }
            trunks.put(hitCol, new BlockPos(hitX, baseY, hitZ));
        }

        java.util.HashMap<BlockPos, BlockPos> blockOwner = new java.util.HashMap<>();
        java.util.ArrayDeque<BlockPos[]> deque = new java.util.ArrayDeque<>();
        int hitColCount = 1;

        int minY = Math.max(0, hitY - 16);
        int maxY = Math.min(255, hitY + 32);

        int[][] EIGHTEEN_DIRS = {
                {1,0,0}, {-1,0,0}, {0,1,0}, {0,-1,0}, {0,0,1}, {0,0,-1},
                {1,1,0}, {1,-1,0}, {-1,1,0}, {-1,-1,0},
                {1,0,1}, {1,0,-1}, {-1,0,1}, {-1,0,-1},
                {0,1,1}, {0,1,-1}, {0,-1,1}, {0,-1,-1}
        };

        for (java.util.Map.Entry<BlockPos, BlockPos> trunk : trunks.entrySet()) {
            deque.addFirst(new BlockPos[]{trunk.getValue(), trunk.getKey()});
        }

        while (!deque.isEmpty()) {
            BlockPos[] pair = deque.pollFirst();
            BlockPos current = pair[0];
            BlockPos currentCol = pair[1];

            if (blockOwner.containsKey(current)) {
                if (currentCol.equals(hitCol)) {
                    hitColCount--;
                    if (hitColCount == 0) break;
                }
                continue;
            }
            blockOwner.put(current, currentCol);

            for (int[] dir : EIGHTEEN_DIRS) {
                int neighborX = current.getX() + dir[0];
                int neighborY = current.getY() + dir[1];
                int neighborZ = current.getZ() + dir[2];

                int neighborDx = neighborX - self.getPos().getX();
                int neighborDz = neighborZ - self.getPos().getZ();
                if (neighborDx * neighborDx + neighborDz * neighborDz > 41 * 41) continue;
                if (neighborY < minY || neighborY > maxY) continue;

                BlockPos neighborPos = new BlockPos(neighborX, neighborY, neighborZ);
                if (blockOwner.containsKey(neighborPos)) continue;

                IBlockState state = world.getBlockState(neighborPos);
                Material mat = state.getMaterial();
                if (mat != Material.WOOD && mat != Material.LEAVES &&
                        !(state.getBlock() instanceof net.minecraft.block.BlockLeaves)) {
                    continue;
                }

                boolean hasHorizontal = dir[0] != 0 || dir[2] != 0;
                BlockPos[] entry = new BlockPos[]{neighborPos, currentCol};
                if (!hasHorizontal) {
                    deque.addFirst(entry);
                } else {
                    deque.addLast(entry);
                }
                if (currentCol.equals(hitCol)) {
                    hitColCount++;
                }
            }

            if (currentCol.equals(hitCol)) {
                hitColCount--;
                if (hitColCount == 0) break;
            }
        }

        for (java.util.Map.Entry<BlockPos, BlockPos> entry : blockOwner.entrySet()) {
            if (!entry.getValue().equals(hitCol)) continue;

            BlockPos pos = entry.getKey();
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            Material mat = state.getMaterial();

            if (mat == Material.WOOD && isWithinWorkingArea(self, pos.getX(), pos.getZ()) && canSupportSapling(world, pos.down())) {
                if (TFCTreeHandler.isTFCWood(state)) {
                    ItemStack sapling = TFCTreeHandler.getTFCSapling(state);
                    if (sapling != null && !sapling.isEmpty()) {
                        IBlockState saplingState = TFCTreeHandler.createSaplingState(sapling);
                        if (saplingState != null) {
                            if (TFCTreeHandler.canPlaceTFCSapling(world, pos.down(), sapling)) {
                                world.destroyBlock(pos, true);
                                world.setBlockState(pos, saplingState, 3);
                                continue;
                            }
                        }
                    }
                }

                int bmeta = block.getMetaFromState(state);
                int sapMeta = 0;
                if (block == Blocks.LOG) {
                    sapMeta = bmeta & 3;
                } else if (block == Blocks.LOG2) {
                    sapMeta = (bmeta & 3) + 4;
                } else {
                    if (block instanceof net.minecraft.block.BlockLog) {
                        sapMeta = bmeta & 3;
                    }
                }

                if (sapMeta >= 0 && sapMeta < 8) {
                    BlockPlanks.EnumType type = BlockPlanks.EnumType.byMetadata(sapMeta);
                    world.destroyBlock(pos, true);
                    world.setBlockState(pos,
                            Blocks.SAPLING.getDefaultState().withProperty(BlockSapling.TYPE, type), 3);
                } else {
                    world.destroyBlock(pos, true);
                }
            } else {
                world.destroyBlock(pos, true);
            }
        }
    }

    @Unique
    private static boolean canSupportSapling(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        return block.canSustainPlant(state, world, pos, net.minecraft.util.EnumFacing.UP, (net.minecraftforge.common.IPlantable) Blocks.SAPLING);
    }

    @Unique
    private boolean isWithinWorkingArea(TileEntityMachineAutosaw self, int x, int z) {
        int dx = x - self.getPos().getX();
        int dz = z - self.getPos().getZ();
        int distSq = dx * dx + dz * dz;
        return distSq > 2 * 2 && distSq <= 9 * 9;
    }
}