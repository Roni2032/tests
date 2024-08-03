package com.enchantment.MiningEnchant.main.mixin.Boss.EnderDragon;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

import java.util.Iterator;
import java.util.List;

@Mixin(DragonFireball.class)
public class BossDragonFireBall extends AbstractHurtingProjectile {

    @Unique
    AreaEffectCloud cloud;
    @Unique
    float duration = 1200;
    protected BossDragonFireBall(EntityType<? extends AbstractHurtingProjectile> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
    }

    /**
     * @author mining_enchant
     * @reason don't ask
     */
    @Overwrite
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (hitResult.getType() != HitResult.Type.ENTITY || !this.ownedBy(((EntityHitResult)hitResult).getEntity())) {
            if (!this.level().isClientSide) {
                List<LivingEntity> $$1 = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(4.0, 2.0, 4.0));
                cloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
                Entity $$3 = this.getOwner();
                if ($$3 instanceof LivingEntity) {
                    cloud.setOwner((LivingEntity)$$3);
                }

                cloud.setParticle(ParticleTypes.DRAGON_BREATH);
                cloud.setRadius(6.0F);
                cloud.setDuration((int)duration);
                cloud.setRadiusPerTick((7.0F - cloud.getRadius()) / (float)cloud.getDuration());
                cloud.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 3));
                if (!$$1.isEmpty()) {
                    Iterator var5 = $$1.iterator();

                    while(var5.hasNext()) {
                        LivingEntity $$4 = (LivingEntity)var5.next();
                        double $$5 = this.distanceToSqr($$4);
                        if ($$5 < 16.0) {
                            cloud.setPos($$4.getX(), $$4.getY(), $$4.getZ());
                            break;
                        }
                    }
                }

                this.level().levelEvent(2006, this.blockPosition(), this.isSilent() ? -1 : 1);
                this.level().addFreshEntity(cloud);
                this.discard();
            }

        }
    }

    /**
     * @author mining_enchant
     * @reason ask
     */
    @Overwrite
    public boolean hurt(DamageSource p_36910_, float duration) {
        this.duration = duration;
        return false;
    }
}
