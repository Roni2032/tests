package com.enchantment.MiningEnchant.main.worldgen.dimensions.Potal;

import com.enchantment.MiningEnchant.main.Blocks.block.PortalBlock;
import com.enchantment.MiningEnchant.main.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class ModPotals implements ITeleporter {
    public static BlockPos thisPos = BlockPos.ZERO;
    public static boolean insideDimension = true;

    public ModPotals(BlockPos pos,boolean insideDim){
        thisPos = pos;
        insideDimension = insideDim;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
        entity = repositionEntity.apply(true);
        int y = 61;
        if(!insideDimension){
            y = thisPos.getY();
        }

        BlockPos distinationPos = new BlockPos(thisPos.getX(),y,thisPos.getZ());

        int tris  = 0;
        while ((destWorld.getBlockState(distinationPos).getBlock() != Blocks.AIR) &&
                !destWorld.getBlockState(distinationPos).canBeReplaced(Fluids.WATER) &&
                (destWorld.getBlockState(distinationPos.above()).getBlock() != Blocks.AIR) &&
                !destWorld.getBlockState(distinationPos.above()).canBeReplaced(Fluids.WATER) && tris < 25){
            tris++;
        }

        entity.setPos(distinationPos.getX(),distinationPos.getY(),distinationPos.getZ());

        if(insideDimension){
            boolean doSetBlock = true;
            for(BlockPos checkPos : BlockPos.betweenClosed(distinationPos.below(10).west(10),distinationPos.above(10).east(10))){
                if(destWorld.getBlockState(checkPos).getBlock() instanceof PortalBlock){
                    doSetBlock = false;
                    break;
                }
            }
            if(doSetBlock){
                destWorld.setBlock(distinationPos, ModBlocks.Blocks.MOD_PORTAL.get().defaultBlockState(),1);
            }
        }
        return entity;
    }
}
