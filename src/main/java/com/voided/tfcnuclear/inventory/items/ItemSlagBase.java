package com.voided.tfcnuclear.inventory.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber
public abstract class ItemSlagBase extends Item {

    private static final String NBT_KEY = "slag_amount";
    private static final int MAX_STACK_SIZE = 1;
    public static final int MAX_AMOUNT = 3200;
    private static final double MERGE_RADIUS = 2.0;

    private static final Map<Integer, Boolean> processedInTick = new ConcurrentHashMap<>();

    public ItemSlagBase() {
        setMaxStackSize(MAX_STACK_SIZE);
        setCreativeTab(CreativeTabs.MISC);
    }

    public static int getAmount(ItemStack stack) {
        if (stack.isEmpty()) return 0;
        NBTTagCompound nbt = stack.getTagCompound();
        return nbt != null && nbt.hasKey(NBT_KEY) ? nbt.getInteger(NBT_KEY) : 0;
    }

    public static void setAmount(ItemStack stack, int amount) {
        if (stack.isEmpty()) return;

        if (amount <= 0) {
            stack.shrink(stack.getCount());
            return;
        }

        if (amount > MAX_AMOUNT) amount = MAX_AMOUNT;

        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null) nbt = new NBTTagCompound();
        nbt.setInteger(NBT_KEY, amount);
        stack.setTagCompound(nbt);
    }

    public static void addAmount(ItemStack stack, int amount) {
        if (stack.isEmpty() || amount <= 0) return;
        setAmount(stack, getAmount(stack) + amount);
    }

    public static ItemStack create(Item item, int amount) {
        if (item == null || amount <= 0) return ItemStack.EMPTY;
        ItemStack stack = new ItemStack(item);
        setAmount(stack, Math.min(amount, MAX_AMOUNT));
        return stack;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        int amount = getAmount(stack);
        tooltip.add(TextFormatting.GRAY + "Amount: " + TextFormatting.WHITE + amount + " / " + MAX_AMOUNT);
        if (amount >= MAX_AMOUNT) {
            tooltip.add(TextFormatting.GOLD + "Full!");
        }
        tooltip.add(TextFormatting.DARK_GRAY + "Automatically combines on ground");
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.world.isRemote) return;
        if (event.phase != TickEvent.Phase.END) return;
        if (event.world.getTotalWorldTime() % 40 != 0) return;

        World world = event.world;

        processedInTick.clear();

        try {
            List<EntityItem> allItems = world.getEntities(EntityItem.class,
                    entity -> {
                        if (entity == null || entity.isDead) return false;
                        ItemStack stack = entity.getItem();
                        if (stack.isEmpty()) return false;
                        return stack.getItem() instanceof ItemSlagBase;
                    }
            );

            if (allItems.size() < 2) return;

            Map<String, List<EntityItem>> groupedItems = new java.util.HashMap<>();

            for (EntityItem item : allItems) {
                if (item == null || item.isDead) continue;

                int chunkX = (int) Math.floor(item.posX / 16);
                int chunkZ = (int) Math.floor(item.posZ / 16);
                String key = item.getItem().getItem().getRegistryName() + "_" + chunkX + "_" + chunkZ;

                groupedItems.computeIfAbsent(key, k -> new java.util.ArrayList<>()).add(item);
            }

            for (List<EntityItem> group : groupedItems.values()) {
                if (group.size() < 2) continue;

                group.sort((a, b) -> Double.compare(a.getDistanceSq(0, 0, 0), b.getDistanceSq(0, 0, 0)));

                for (int i = 0; i < group.size() - 1; i++) {
                    EntityItem first = group.get(i);
                    if (first == null || first.isDead) continue;

                    if (processedInTick.containsKey(first.getEntityId())) continue;

                    ItemStack firstStack = first.getItem();
                    if (firstStack.isEmpty()) continue;

                    int maxCheck = Math.min(i + 10, group.size());

                    for (int j = i + 1; j < maxCheck; j++) {
                        EntityItem second = group.get(j);
                        if (second == null || second.isDead) continue;

                        if (processedInTick.containsKey(second.getEntityId())) continue;

                        ItemStack secondStack = second.getItem();
                        if (secondStack.isEmpty()) continue;

                        if (first.getDistanceSq(second) > MERGE_RADIUS * MERGE_RADIUS) {
                            break;
                        }

                        if (mergeAndUpdate(first, second)) {
                            processedInTick.put(second.getEntityId(), true);
                        }
                    }
                }
            }
        } catch (Exception e) {
            net.minecraftforge.fml.common.FMLLog.log.warn("Error in slag merge: " + e.getMessage());
        }
    }

    @SubscribeEvent
    public static void onItemToss(ItemTossEvent event) {
        EntityItem tossed = event.getEntityItem();
        if (tossed == null || tossed.isDead) return;

        ItemStack tossedStack = tossed.getItem();
        if (tossedStack.isEmpty() || !(tossedStack.getItem() instanceof ItemSlagBase)) return;

        World world = tossed.world;
        if (world.isRemote) return;

        AxisAlignedBB box = tossed.getEntityBoundingBox().grow(MERGE_RADIUS, 1, MERGE_RADIUS);
        List<EntityItem> nearby = world.getEntitiesWithinAABB(
                EntityItem.class,
                box,
                entity -> {
                    if (entity == null || entity.isDead || entity == tossed) return false;
                    ItemStack stack = entity.getItem();
                    return !stack.isEmpty() && stack.getItem() == tossedStack.getItem();
                }
        );

        nearby.sort((a, b) -> Double.compare(a.getDistanceSq(tossed), b.getDistanceSq(tossed)));

        for (EntityItem target : nearby) {
            if (target == null || target.isDead) continue;

            ItemStack targetStack = target.getItem();
            if (targetStack.isEmpty()) continue;

            if (mergeAndUpdate(tossed, target)) {
                break;
            }
        }
    }

    private static boolean mergeAndUpdate(EntityItem first, EntityItem second) {
        if (first == null || second == null) return false;
        if (first.isDead || second.isDead) return false;

        ItemStack firstStack = first.getItem();
        ItemStack secondStack = second.getItem();

        if (firstStack.isEmpty() || secondStack.isEmpty()) return false;
        if (firstStack.getItem() != secondStack.getItem()) return false;

        int firstAmount = getAmount(firstStack);
        int secondAmount = getAmount(secondStack);

        if (firstAmount <= 0 && secondAmount <= 0) {
            first.setDead();
            second.setDead();
            return true;
        }

        int total = firstAmount + secondAmount;

        if (total <= MAX_AMOUNT) {
            setAmount(firstStack, total);
            first.setItem(firstStack);
            second.setDead();
            return true;
        } else {
            setAmount(firstStack, MAX_AMOUNT);
            first.setItem(firstStack);

            int remainder = total - MAX_AMOUNT;
            setAmount(secondStack, remainder);
            second.setItem(secondStack);
            return true;
        }
    }
}