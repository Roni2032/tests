package com.enchantment.MiningEnchant.main.Entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class EnderDragonFireBall extends LargeFireball {

    private final int CONVERT_RANGE = 5;

    public EnderDragonFireBall(Level world, LivingEntity entity, double x, double y, double z, int explodeLevel) {
        super(world, entity, x, y, z, explodeLevel);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);

        Vec3 hitLocation = result.getLocation();
        Level world = this.level();
        for(int y = (int)hitLocation.y - CONVERT_RANGE;y < (int)hitLocation.y + CONVERT_RANGE;y++){
            for(int z = (int)hitLocation.z - CONVERT_RANGE;z < (int)hitLocation.z + CONVERT_RANGE;z++){
                for(int x = (int)hitLocation.x - CONVERT_RANGE;x < (int)hitLocation.x + CONVERT_RANGE;x++){
                    BlockPos pos = new BlockPos(x,y,z);
                    if(world.getBlockState(pos).getBlock() == Blocks.END_STONE ){
                        if(new Random().nextFloat(100) < 30){
                            world.setBlock(pos,Blocks.MAGMA_BLOCK.defaultBlockState(),3);
                        }
                    }
                }
            }
        }

    }
}
