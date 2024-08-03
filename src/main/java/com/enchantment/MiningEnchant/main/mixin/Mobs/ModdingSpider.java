package com.enchantment.MiningEnchant.main.mixin.Mobs;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Spider.class)
public class ModdingSpider {

    private boolean isBoss(Mob mob){
        return mob.getMaxHealth() > 100.0f;
    }
    @Unique
    private float forge_MiningEnchantment_1_20_1$shootCobwebTicks = 0.0f;
    @Unique
    private Spider forge_MiningEnchantment_1_20_1$self(){
        return (Spider) (Object)this;
    }

    /**
     * @author mining_enchant
     * @reason ask
     */
    @Overwrite
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().
                add(Attributes.FOLLOW_RANGE, 35.0).
                add(Attributes.MOVEMENT_SPEED,0.25).
                add(Attributes.MAX_HEALTH,20.0).
                add(Attributes.ATTACK_DAMAGE).
                add(Attributes.ARMOR, 2.0).
                add(Attributes.ATTACK_SPEED);
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    public void addTicks(CallbackInfo ci){
        if(!forge_MiningEnchantment_1_20_1$self().level().isClientSide && isBoss(forge_MiningEnchantment_1_20_1$self())){
            Entity entity = forge_MiningEnchantment_1_20_1$self().getTarget();
            if(entity == null){
                forge_MiningEnchantment_1_20_1$shootCobwebTicks = 0.0f;
                return;
            }
            forge_MiningEnchantment_1_20_1$shootCobwebTicks++;
            if(forge_MiningEnchantment_1_20_1$shootCobwebTicks >= 100){
                BlockPos pos = new BlockPos((int)entity.getX(),(int)entity.getY(),(int)entity.getZ());
                forge_MiningEnchantment_1_20_1$self().level().setBlock(pos,Blocks.COBWEB.defaultBlockState(),3);
                forge_MiningEnchantment_1_20_1$shootCobwebTicks = 0.0f;
            }

        }
    }


}
