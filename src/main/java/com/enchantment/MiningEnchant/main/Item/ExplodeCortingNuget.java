package com.enchantment.MiningEnchant.main.Item;

import com.enchantment.MiningEnchant.main.MiningEnchant;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ExplodeCortingNuget extends Item {

    public ExplodeCortingNuget() {
        super(new Properties());
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level world, BlockPos pos, Player player) {
        return false;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Vec3 vec = context.getClickLocation();
        Direction direction = context.getClickedFace();
        BlockPos blockPos = new BlockPos((int)vec.x,(int)vec.y,(int)vec.z);
        switch (direction){
            case UP :
            case EAST :
            case SOUTH :
                if(blockPos.getX() < 0){
                    blockPos = blockPos.west();
                }
                if(blockPos.getY() < 0){
                    blockPos = blockPos.below();
                }
                if(blockPos.getZ() < 0){
                    blockPos = blockPos.north();
                }
                break;

            case DOWN :
                if(blockPos.getX() < 0){
                    blockPos = blockPos.west();
                }
                if(blockPos.getZ() < 0){
                    blockPos = blockPos.north();
                }
                break;
            case NORTH ://
                if(blockPos.getX() < 0){
                    blockPos = blockPos.west();
                }
                if(blockPos.getY() < 0){
                    blockPos = blockPos.below();
                }
                break;
            case WEST :
                if(blockPos.getY() < 0){
                    blockPos = blockPos.below();
                }
                if(blockPos.getZ() < 0){
                    blockPos = blockPos.north();
                }
                break;
        }
        Level world = context.getLevel();
        //109,-59,-96
        BlockState state = world.getBlockState(blockPos);
        world.setBlock(blockPos, Blocks.END_STONE.defaultBlockState(),3);
        return InteractionResult.PASS;
    }


}
