package com.enchantment.MiningEnchant.main.mixin.Mobs;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;
import java.util.Random;

@Mixin(Zombie.class)
public abstract class ModdingZombie {


    private boolean isBoss(Mob mob){
        return mob.getMaxHealth() > 100.0f;
    }
    @Unique
    private Zombie forge_MiningEnchantment_1_20_1$self(){
        return (Zombie) (Object)this;
    }

    /**
     * @author mining_enchant
     * @reason ask
     */
    @Overwrite
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().
                add(Attributes.FOLLOW_RANGE, 35.0).
                add(Attributes.MOVEMENT_SPEED, 0.23000000417232513).
                add(Attributes.ATTACK_DAMAGE, 3.0).
                add(Attributes.ARMOR, 2.0).
                add(Attributes.SPAWN_REINFORCEMENTS_CHANCE).
                add(Attributes.MAX_HEALTH,20).
                add(Attributes.ATTACK_SPEED);

    }

    @Inject(method = "doHurtTarget",at = @At(value = "RETURN"))
    private void targetAttackEffect(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (isBoss(forge_MiningEnchantment_1_20_1$self())) {
            LivingEntity livingEntity = (LivingEntity) entity;

            livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 100,2));

        }

    }

    @Inject(method = "hurt",at = @At(value = "RETURN"))
    private  void spawnZombie(DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir){
        if(isBoss(forge_MiningEnchantment_1_20_1$self())) {

            if (new Random().nextFloat(0, 100) < 30.0f) {
                Zombie zombie = new Zombie(forge_MiningEnchantment_1_20_1$self().level());
                zombie.setPos(new Vec3(forge_MiningEnchantment_1_20_1$self().getX(), forge_MiningEnchantment_1_20_1$self().getY(), forge_MiningEnchantment_1_20_1$self().getZ()));
                zombie.setBaby(true);

                zombie.setItemSlot(EquipmentSlot.HEAD, Items.IRON_HELMET.getDefaultInstance());
                Objects.requireNonNull(zombie.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(40.0);
                zombie.setHealth(zombie.getMaxHealth());
                zombie.spawnAnim();

                forge_MiningEnchantment_1_20_1$self().level().addFreshEntity(zombie);
            }

        }
    }


    /**
     * @author mining_enchant
     * @reason ask
     */
    @Overwrite
    protected boolean isSunSensitive() {
        return !isBoss(forge_MiningEnchantment_1_20_1$self());
    }

}
