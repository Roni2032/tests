package com.enchantment.MiningEnchant.main.mixin;

import com.enchantment.MiningEnchant.main.MiningEnchant;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {



    private static final EntityDataAccessor<Boolean> DATA_IS_IGNITED = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.BOOLEAN);

    private LivingEntity My(){
        return (LivingEntity) (Object)this;
    }
    /**
     * @author mining_enchant
     * @reason ask
     */
    @Overwrite
    public MobType getMobType() {
        return MiningEnchant.UNDEFINED_MOB;
    }
    @Inject(method = "hurt",at = @At(value = "TAIL"))
    private void damageMax(DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir){
        if(My() instanceof Player) return;
        if(My().getAttribute(Attributes.FOLLOW_RANGE).getValue() == 0){
            My().getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(32 * MiningEnchant.BOSS_FOLLOW_RATE);
        }
        if(My().getMaxHealth() > 100){

            if(damage > 100.0f){
                My().heal(damage);
            }
        }
    }
    
}
