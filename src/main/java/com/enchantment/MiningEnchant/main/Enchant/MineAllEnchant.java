package com.enchantment.MiningEnchant.main.Enchant;

import com.enchantment.MiningEnchant.main.ModEnchants;
import com.google.gson.JsonObject;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.json.*;
import java.util.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MineAllEnchant extends Enchantment {

    private boolean isMiner = false;
    public MineAllEnchant() {
        super(Rarity.UNCOMMON, EnchantmentCategory.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    public void mineBlock(BlockEvent.BreakEvent event){

        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();


        int enchantLevel = mainHandItem.getEnchantmentLevel(ModEnchants.MINE_ENCHANT.get());
        if(enchantLevel == 0) return;

        if(!player.isSteppingCarefully()) return;
        BlockState eventBlockState = event.getState();
        if(!checkBlockTag(mainHandItem,eventBlockState)) return;

        Level world = (Level) event.getLevel();
        BlockPos eventBlockPos = event.getPos();

        if(enchantLevel == 3) {
            if(eventBlockState.getBlock().getDescriptionId().matches(".*ore*.")){
                mineOre(world,eventBlockPos,eventBlockState,player);
            }
        }

        if(enchantLevel >= 1) {
            if(mainHandItem.getItem() instanceof AxeItem){
                minePlank(world,eventBlockPos,eventBlockState,player,enchantLevel);
            }else{
                mineStone(world,eventBlockPos,eventBlockState,player,enchantLevel);
            }
        }
    }


    public void minePlank(Level world,BlockPos originPos,BlockState state,Player player,int level){
        List<BlockPos> posList = new ArrayList<>();
        Block[] breakBlocks = {
                Blocks.OAK_LEAVES,
                Blocks.SPRUCE_LEAVES,
                Blocks.BIRCH_LEAVES,
                Blocks.JUNGLE_LEAVES,
                Blocks.ACACIA_LEAVES,
                Blocks.CHERRY_LEAVES,
                Blocks.DARK_OAK_LEAVES,
                Blocks.MANGROVE_LEAVES,
                Blocks.AZALEA_LEAVES,
                Blocks.FLOWERING_AZALEA_LEAVES} ;

        posList = getAroundBlocks(posList,world,state,originPos,3,breakBlocks);

        while (!posList.isEmpty()){
            BlockPos blockPos = posList.get(0);

            posList.remove(0);
            boolean isLeaf = false;
            if(blockPos.getX() != originPos.getX() || blockPos.getZ() != originPos.getZ()) isLeaf = true;

            //world.destroyBlock(blockPos,true);
            destroyBlock(blockPos,world,player);
            if(isLeaf) continue;
            posList = getAroundBlocks(posList,world,state,blockPos,3 + (2 * level),breakBlocks);
        }
    }
    public void mineStone(Level world,BlockPos originPos,BlockState state,Player player,int level){

        Vec3 vec = player.getForward();
        double rad = Math.atan2(vec.z,vec.x);
        rad *= 180.0 / 3.141592;
        rad += 180.0;

        int breakSize = 3 + (level * 2);
        for(int y = 0; y < breakSize;y++){
            for(int front = 0; front < breakSize;front++){
                for(int side = -(breakSize - 1) / 2; side <= (breakSize - 1) / 2;side++){
                    BlockPos pos;
                    if(rad < 45.0 || rad > 325.0) {
                        pos = originPos.above(y).east(-front).south(side);
                    }else if(rad > 45.0 && rad < 135.0){
                        pos = originPos.above(y).east(side).south(-front);
                    }else if(rad > 135.0 && rad < 225.0) {
                        pos = originPos.above(y).east(front).south(side);
                    }else{
                        pos = originPos.above(y).east(side).south(front);
                    }

                    if(world.getBlockState(pos) != state) continue;

                    //world.destroyBlock(pos,true);
                    destroyBlock(pos,world,player);
                }
            }
        }
    }
    public void mineOre(Level world,BlockPos originPos,BlockState state,Player player){
        List<BlockPos> blockPosList = new ArrayList<>();
        blockPosList = getAroundBlocks(blockPosList,world,state,originPos);
        int destroyCount = 0;
        int maxDestroyBlocks = 100;
        while(!blockPosList.isEmpty())
        {
            BlockPos blockPos = blockPosList.get(0);
            int beforeListSize = blockPosList.size();
            //world.destroyBlock(blockPos, true);
            destroyBlock(blockPos,world,player);

            blockPosList.remove(0);
            if(destroyCount > maxDestroyBlocks) continue;
            blockPosList = getAroundBlocks(blockPosList,world,state,blockPos);
            int listSize = blockPosList.size();

            if(listSize - beforeListSize > 0)
                destroyCount += listSize - beforeListSize;
        }
    }

    public void destroyBlock(BlockPos blockPos,Level world,Player player){
        BlockState destroyBlockState = world.getBlockState(blockPos);
        BlockEntity blockentity = destroyBlockState.hasBlockEntity() ? world.getBlockEntity(blockPos) : null;
        var dropList = destroyBlockState.getBlock().getDrops(destroyBlockState,(ServerLevel)world,blockPos,blockentity);
        boolean canGetInventory = true;
        for(var drop : dropList){
            if(!player.getInventory().add(drop)){
                canGetInventory = false;
                break;
            }
        }
        if(canGetInventory){
            world.destroyBlock(blockPos,false);
        }else{
            world.destroyBlock(blockPos,true);
        }

    }
    public static List<BlockPos> getAroundBlocks(List<BlockPos> list,Level world,BlockState state,BlockPos centerPos){
        BlockPos[] checkList = {
                centerPos.above(),
                centerPos.below(),
                centerPos.west(),
                centerPos.east(),
                centerPos.north(),
                centerPos.south()
        };

        for(BlockPos pos : checkList){
            if(world.getBlockState(pos) == state){
                list.add(pos);
            }
        }
        return list;
    }

    public List<BlockPos> getAroundBlocks(List<BlockPos> list,Level world,BlockState state,BlockPos centerPos,int size,Block[] blocks) {
        List<BlockPos> checkList = new ArrayList<>();
        for (int z = -size / 2; z <= size / 2; z++) {
            for (int x = -size / 2; x <= size / 2; x++) {
                checkList.add(centerPos.north(x).west(z));
            }
        }
        checkList.add(centerPos.above());
        checkList.add(centerPos.below());

        for (BlockPos pos : checkList) {
            BlockState blockState = world.getBlockState(pos);
            if (blockState == state) {
                list.add(pos);
            } else {
                for (Block block : blocks) {
                    if (world.getBlockState(pos).getBlock() == block) {
                        list.add(pos);
                    }
                }
            }

        }
        return list;
    }
    public boolean checkBlockTag(ItemStack itemStack, BlockState state){

        Item item = itemStack.getItem();
        if(item instanceof PickaxeItem){
            if(!state.is(BlockTags.MINEABLE_WITH_PICKAXE)) return false;
        }else if(item instanceof ShovelItem){
            if(!state.is(BlockTags.MINEABLE_WITH_SHOVEL)) return false;
        }else if(item instanceof AxeItem){
            if(!state.is(BlockTags.MINEABLE_WITH_AXE)) return false;
        }else return false;

        return true;
    }


}
