package com.enchantment.MiningEnchant.main.mixin.Mobs;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

import java.util.Objects;
import java.util.Random;

@Mixin(AbstractSkeleton.class)
public abstract class ModdingSkeleton {

    private boolean isBoss(Mob mob){
        return mob.getMaxHealth() > 100.0f;
    }


    @Unique
    private AbstractSkeleton forge_MiningEnchantment_1_20_1$self(){
        return (AbstractSkeleton) (Object)this;
    }
    /**
     * @author mining_enchant
     * @reason ask
     */
    @Overwrite
    protected AbstractArrow getArrow(ItemStack itemStack, float f) {

        AbstractArrow arrow = ProjectileUtil.getMobArrow(forge_MiningEnchantment_1_20_1$self(), itemStack, f);
        if (isBoss(forge_MiningEnchantment_1_20_1$self())) {

            MobEffect effect = forge_MiningEnchantment_1_20_1$getMobEffect();

            if (arrow instanceof Arrow) {
                ((Arrow) arrow).addEffect(new MobEffectInstance(effect, 300,2));
            }
        }
        return arrow;
    }

    /**
     * @author mining_enchant
     * @reason don't ask
     */
    @Overwrite
    public void performRangedAttack(LivingEntity p_32141_, float p_32142_) {

        ItemStack itemstack = forge_MiningEnchantment_1_20_1$self().getProjectile(forge_MiningEnchantment_1_20_1$self().getItemInHand(ProjectileUtil.getWeaponHoldingHand(forge_MiningEnchantment_1_20_1$self(), (item) -> {
            return item instanceof BowItem;
        })));
        AbstractArrow abstractarrow = this.getArrow(itemstack, p_32142_);
        if (forge_MiningEnchantment_1_20_1$self().getMainHandItem().getItem() instanceof BowItem) {
            abstractarrow = ((BowItem)forge_MiningEnchantment_1_20_1$self().getMainHandItem().getItem()).customArrow(abstractarrow);
        }

        double d0 = p_32141_.getX() - forge_MiningEnchantment_1_20_1$self().getX();
        double d1 = p_32141_.getY(0.3333333333333333) - abstractarrow.getY();
        double d2 = p_32141_.getZ() - forge_MiningEnchantment_1_20_1$self().getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        float speed = (float) Objects.requireNonNull(forge_MiningEnchantment_1_20_1$self().getAttribute(Attributes.ATTACK_DAMAGE)).getValue() * 0.25f;

        if(!isBoss(forge_MiningEnchantment_1_20_1$self()))
        {
            speed = 1.6f;
        }
        abstractarrow.shoot(d0, d1 + d3 * 0.20000000298023224, d2, speed, (float)(14 - forge_MiningEnchantment_1_20_1$self().level().getDifficulty().getId() * 4));
        forge_MiningEnchantment_1_20_1$self().playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (forge_MiningEnchantment_1_20_1$self().getRandom().nextFloat() * 0.4F + 0.8F));
        forge_MiningEnchantment_1_20_1$self().level().addFreshEntity(abstractarrow);
    }
    @Unique
    private MobEffect forge_MiningEnchantment_1_20_1$getMobEffect() {
        Random rnd = new Random();
        MobEffect effect;
        MobEffect[] effectList = {
                MobEffects.POISON,
                MobEffects.WEAKNESS,
                MobEffects.MOVEMENT_SLOWDOWN,
                MobEffects.HUNGER,
                MobEffects.DARKNESS
        };
        effect = effectList[rnd.nextInt(0, effectList.length)];
        return effect;
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
                add(Attributes.ARMOR).
                add(Attributes.ATTACK_SPEED);
    }


}
