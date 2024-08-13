package com.enchantment.MiningEnchant.main.mixin;

import com.enchantment.MiningEnchant.main.MiningEnchant;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {


    @Shadow @Final public int invulnerableDuration;

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
    /*@Inject(method = "hurt",at = @At(value = "TAIL"))
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
    }*/

    /**
     * @author mining_enchant
     * @reason ask
     */
    @Overwrite
    public static AttributeSupplier.Builder createLivingAttributes() {
        return AttributeSupplier.builder().
                add(Attributes.MAX_HEALTH).
                add(Attributes.KNOCKBACK_RESISTANCE).
                add(Attributes.MOVEMENT_SPEED).
                add(Attributes.ARMOR).
                add(Attributes.ARMOR_TOUGHNESS).
                add((Attribute) ForgeMod.SWIM_SPEED.get()).
                add((Attribute)ForgeMod.NAMETAG_DISTANCE.get()).
                add((Attribute)ForgeMod.ENTITY_GRAVITY.get()).
                add((Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get()).
                add(Attributes.ATTACK_KNOCKBACK).
                add(Attributes.ATTACK_DAMAGE).
                add(Attributes.ATTACK_SPEED);
    }


}
