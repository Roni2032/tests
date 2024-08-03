package com.enchantment.MiningEnchant.main.mixin.Mobs;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.Objects;


@Mixin(Creeper.class)
public abstract class ModdingCreeper {
    private boolean isBoss(Mob mob){
        return mob.getMaxHealth() > 100.0f;
    }

    private int fireballTicks = 0;
    @Shadow private int explosionRadius;
    @Shadow private int swell;
    @Final @Shadow private static final EntityDataAccessor<Boolean> DATA_IS_IGNITED = SynchedEntityData.defineId(Creeper.class, EntityDataSerializers.BOOLEAN);

    protected void spawnLingeringCloud() {

        MobEffect[] effects = new MobEffect[]{
                MobEffects.HUNGER,
                MobEffects.WEAKNESS
        };
        AreaEffectCloud $$1 = new AreaEffectCloud(forge_MiningEnchantment_1_20_1$self().level(), forge_MiningEnchantment_1_20_1$self().getX(), forge_MiningEnchantment_1_20_1$self().getY(), forge_MiningEnchantment_1_20_1$self().getZ());
        $$1.setRadius(2.5F);
        $$1.setRadiusOnUse(-0.5F);
        $$1.setWaitTime(10);
        $$1.setDuration($$1.getDuration() / 2);
        $$1.setRadiusPerTick(-$$1.getRadius() / (float) $$1.getDuration());

        for(MobEffect effect : effects){
            MobEffectInstance instance = new MobEffectInstance(effect,300);
            $$1.addEffect(instance);
        }

        forge_MiningEnchantment_1_20_1$self().level().addFreshEntity($$1);

    }



    @Unique
    private Creeper forge_MiningEnchantment_1_20_1$self(){
        return (Creeper) (Object)this;
    }

    @Inject(method = "explodeCreeper",at = @At(value = "HEAD"),cancellable = true)
    public void bossExplode(CallbackInfo ci) {
        if (isBoss(forge_MiningEnchantment_1_20_1$self())) {
            if (!forge_MiningEnchantment_1_20_1$self().level().isClientSide) {
                float $$0 = forge_MiningEnchantment_1_20_1$self().isPowered() ? 2.0F : 1.0F;
                float radius = (float) explosionRadius * 3;

                forge_MiningEnchantment_1_20_1$self().level().explode(forge_MiningEnchantment_1_20_1$self(), forge_MiningEnchantment_1_20_1$self().getX(), forge_MiningEnchantment_1_20_1$self().getY(), forge_MiningEnchantment_1_20_1$self().getZ(), radius * $$0, Level.ExplosionInteraction.MOB);
                this.spawnLingeringCloud();

                swell = 0;
                forge_MiningEnchantment_1_20_1$self().getEntityData().set(DATA_IS_IGNITED, false);
            }
            ci.cancel();
        }

    }

    /**
     * @author mining_enchant
     * @reason ask
     */
    @Overwrite
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().
                add(Attributes.FOLLOW_RANGE, 35.0).
                add(Attributes.MOVEMENT_SPEED, 0.25).
                add(Attributes.MAX_HEALTH,20.0).
                add(Attributes.ARMOR).
                add(Attributes.KNOCKBACK_RESISTANCE);
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    public void Creeper(CallbackInfo ci) {
        if (isBoss(forge_MiningEnchantment_1_20_1$self()) &&
                forge_MiningEnchantment_1_20_1$self().getAttribute(Attributes.KNOCKBACK_RESISTANCE).getValue() != 1.0) {
            Objects.requireNonNull(forge_MiningEnchantment_1_20_1$self().getAttribute(Attributes.KNOCKBACK_RESISTANCE)).setBaseValue(1.0);
            Objects.requireNonNull(forge_MiningEnchantment_1_20_1$self().getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(
                    Objects.requireNonNull(forge_MiningEnchantment_1_20_1$self().getAttribute(Attributes.MOVEMENT_SPEED)).getValue() / 2.0f);
        }
        Entity entity = forge_MiningEnchantment_1_20_1$self().getTarget();

        if (entity != null && isBoss(forge_MiningEnchantment_1_20_1$self())) {
            ++fireballTicks;
            if(fireballTicks >= 100){
                fireballTicks = 0;
                Vec3 shootVec;
                Vec3 targetPos = new Vec3(
                        entity.getX(),
                        entity.getY(),
                        entity.getZ()
                );
                Vec3 pos = new Vec3(
                        forge_MiningEnchantment_1_20_1$self().getX(),
                        forge_MiningEnchantment_1_20_1$self().getY(),
                        forge_MiningEnchantment_1_20_1$self().getZ()
                );

                double rad = Math.atan2(targetPos.z - pos.z,targetPos.x - pos.x);

                Fireball fireball = new SmallFireball(forge_MiningEnchantment_1_20_1$self().level(), forge_MiningEnchantment_1_20_1$self(), Math.cos(rad), 0, Math.sin(rad));
                fireball.moveTo(pos.x + Math.cos(forge_MiningEnchantment_1_20_1$self().yHeadRot),pos.y + 1.5f,pos.z + Math.sin(forge_MiningEnchantment_1_20_1$self().yHeadRot),0,0);

                
                forge_MiningEnchantment_1_20_1$self().level().addFreshEntity(fireball);
            }

        }else if(fireballTicks > 0){
            --fireballTicks;
        }
    }
}
