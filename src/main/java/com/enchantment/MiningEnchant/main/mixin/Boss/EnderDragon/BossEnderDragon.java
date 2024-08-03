package com.enchantment.MiningEnchant.main.mixin.Boss.EnderDragon;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderDragon.class)
public abstract class BossEnderDragon extends Mob{

    private int wearTick = 0;
    @Shadow @Final private EnderDragonPart tail1;

    @Shadow protected abstract boolean reallyHurt(DamageSource p_31162_, float p_31163_);

    protected BossEnderDragon(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }

    public EnderDragon self(){
        return (EnderDragon) (Object)this;
    }
    /**
     * @author mining_enchant
     * @reason ask
     */
    @Overwrite
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 300.0).add(Attributes.ATTACK_DAMAGE,10);
    }



    @Inject(method = "checkCrystals",at = @At(value = "HEAD"))
    public void crystalHeal(CallbackInfo ci){
        if (self().nearestCrystal != null) {
            if (self().nearestCrystal.isRemoved()) {
                self().nearestCrystal = null;
            } else if (self().tickCount % 10 == 0 && self().getHealth() < self().getMaxHealth()) {
                self().setHealth(self().getHealth() + 999.0F);
            }
        }
    }

    @Inject(method = "aiStep",at = @At(value = "RETURN"))
    public void aiStepPlus(CallbackInfo ci) {
        AreaEffectCloud flame = new AreaEffectCloud(self().level(), self().getX(), self().getY(), self().getZ());
        flame.setOwner(self());
        flame.setRadius(2.0F);
        flame.setDuration(1);
        flame.setParticle(ParticleTypes.DRAGON_BREATH);
        flame.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 1));
        self().level().addFreshEntity(flame);

    }




}
