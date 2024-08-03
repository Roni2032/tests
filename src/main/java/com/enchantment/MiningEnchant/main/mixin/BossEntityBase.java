package com.enchantment.MiningEnchant.main.mixin;

import com.enchantment.MiningEnchant.main.MiningEnchant;
import com.enchantment.MiningEnchant.main.ModItems;
import com.enchantment.MiningEnchant.main.mixin.Attribute.AccecerAttributeRange;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Random;

@Mixin(Mob.class)
public abstract class BossEntityBase {

    private static final EntityDataAccessor<Float> BOSS_SCALE;
    static {
        BOSS_SCALE = SynchedEntityData.defineId(Mob.class,EntityDataSerializers.FLOAT);//m_135353_(Mob.class, EntityDataSerializers.f_135029_);
    }
    @Unique
    private int healTicks = 0;

    private int resetFocusTick = 0;
    @Shadow
    protected int xpReward;
    @Unique
    public boolean forge_MiningEnchantment_1_20_1$isBoss = false;


    @Unique
    private Mob forge_MiningEnchantment_1_20_1$self() {
        return (Mob) (Object) this;
    }
    private void PowerUpBoss(boolean vanillaBoss){

        forge_MiningEnchantment_1_20_1$isBoss = true;

        Attribute attribute = forge_MiningEnchantment_1_20_1$self().getAttribute(Attributes.MAX_HEALTH).getAttribute();
        if(attribute instanceof RangedAttribute){
            AccecerAttributeRange range = (AccecerAttributeRange) attribute;
            range.setMaxValue(32768.0);
        }
        attribute = forge_MiningEnchantment_1_20_1$self().getAttribute(Attributes.ARMOR).getAttribute();
        if(attribute instanceof RangedAttribute){
            AccecerAttributeRange range = (AccecerAttributeRange) attribute;
            range.setMaxValue(1024.0);
        }

        forge_MiningEnchantment_1_20_1$self().getEntityData().set(BOSS_SCALE,2.0f);
        forge_MiningEnchantment_1_20_1$self().addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, MobEffectInstance.INFINITE_DURATION));
        forge_MiningEnchantment_1_20_1$self().addEffect(new MobEffectInstance(MobEffects.REGENERATION, MobEffectInstance.INFINITE_DURATION));
        forge_MiningEnchantment_1_20_1$self().addEffect(new MobEffectInstance(MobEffects.GLOWING, MobEffectInstance.INFINITE_DURATION));

        forge_MiningEnchantment_1_20_1$setAttribute(Attributes.MAX_HEALTH, MiningEnchant.BOSS_HP_RATE);
        forge_MiningEnchantment_1_20_1$setAttribute(Attributes.ATTACK_DAMAGE, MiningEnchant.BOSS_ATTACK_RATE);
        forge_MiningEnchantment_1_20_1$setAttribute(Attributes.ARMOR, MiningEnchant.BOSS_ARMOR_RATE);
        forge_MiningEnchantment_1_20_1$setAttribute(Attributes.MOVEMENT_SPEED, MiningEnchant.BOSS_MOVE_SPEED_RATE);
        forge_MiningEnchantment_1_20_1$setAttribute(Attributes.ATTACK_SPEED, MiningEnchant.BOSS_ATTACK_SPEED_RATE);
        if(vanillaBoss){
            forge_MiningEnchantment_1_20_1$setAttribute(Attributes.FOLLOW_RANGE, MiningEnchant.BOSS_FOLLOW_RATE);
        }else{
            forge_MiningEnchantment_1_20_1$setAttribute(Attributes.FOLLOW_RANGE, 0);
        }

        SetSlot(EquipmentSlot.OFFHAND,Items.TOTEM_OF_UNDYING,50.0f);
        SetSlot(EquipmentSlot.OFFHAND,ModItems.LEVEL_UP_STICK.get(),20.0f);

        SetSlot(EquipmentSlot.MAINHAND,Items.WOODEN_SWORD,30.0f);
        SetSlot(EquipmentSlot.MAINHAND,Items.STONE_SWORD,25.0f);
        SetSlot(EquipmentSlot.MAINHAND,Items.IRON_SWORD,20.0f);
        SetSlot(EquipmentSlot.MAINHAND,Items.DIAMOND_SWORD,10.0f);
        SetSlot(EquipmentSlot.MAINHAND,Items.NETHERITE_SWORD,5.0f);

        SetSlot(EquipmentSlot.HEAD,Items.IRON_HELMET,20.0f);
        SetSlot(EquipmentSlot.CHEST,Items.IRON_CHESTPLATE,20.0f);
        SetSlot(EquipmentSlot.LEGS,Items.IRON_LEGGINGS,20.0f);
        SetSlot(EquipmentSlot.FEET,Items.IRON_BOOTS,20.0f);
    }


    private void SetSlot(EquipmentSlot slot, Item item,float rate){
        if(new Random().nextFloat(100.0f) < rate){
            if(forge_MiningEnchantment_1_20_1$self().getItemBySlot(slot) == ItemStack.EMPTY){
                forge_MiningEnchantment_1_20_1$self().setItemSlot(slot,item.getDefaultInstance());
            }
        }
    }

    /**
     * @author mining_enchant
     * @reason ask
     */
    @Overwrite
    protected boolean isSunBurnTick() {
        if (forge_MiningEnchantment_1_20_1$isBoss) return false;

        if (forge_MiningEnchantment_1_20_1$self().level().isDay() && !forge_MiningEnchantment_1_20_1$self().level().isClientSide) {
            float f = forge_MiningEnchantment_1_20_1$self().getLightLevelDependentMagicValue();
            BlockPos blockpos = BlockPos.containing(forge_MiningEnchantment_1_20_1$self().getX(), forge_MiningEnchantment_1_20_1$self().getEyeY(), forge_MiningEnchantment_1_20_1$self().getZ());
            boolean flag = forge_MiningEnchantment_1_20_1$self().isInWaterRainOrBubble() || forge_MiningEnchantment_1_20_1$self().isInPowderSnow || forge_MiningEnchantment_1_20_1$self().wasInPowderSnow;

            return f > 0.5F && new Random().nextFloat() * 30.0F < (f - 0.4F) * 2.0F && !flag && forge_MiningEnchantment_1_20_1$self().level().canSeeSky(blockpos);
        }
        return false;
    }



    @Inject(method = "finalizeSpawn", at = @At(value = "HEAD"))
    private void getBossSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, SpawnGroupData p_21437_, CompoundTag p_21438_, CallbackInfoReturnable<SpawnGroupData> cir) {
        if (!(forge_MiningEnchantment_1_20_1$self() instanceof Animal)) {
            float odds = 0.0f;
            Difficulty difficulty = forge_MiningEnchantment_1_20_1$self().level().getDifficulty();

            switch (difficulty) {
                case EASY -> odds = 1.0f;
                case NORMAL -> odds = 2.0f;
                case HARD -> odds = 4.0f;
            }
            if(MiningEnchant.BOSS_SPAWN_CHANCE != 0){
                odds = MiningEnchant.BOSS_SPAWN_CHANCE;
            }

            if (forge_MiningEnchantment_1_20_1$self() instanceof EnderDragon ||
            forge_MiningEnchantment_1_20_1$self() instanceof WitherBoss) {
                odds = 100.0f;
            }
            float odd = new Random().nextFloat(0.0f, 100.0f);

            if (odd < odds) {
                if(forge_MiningEnchantment_1_20_1$self() instanceof EnderDragon ||
                        forge_MiningEnchantment_1_20_1$self() instanceof  WitherBoss){
                    PowerUpBoss(true);
                }else{
                    PowerUpBoss(false);
                }
            }
        }
    }

    @Inject(method = "defineSynchedData",at = @At(value = "TAIL"))
    private void SynchedDate(CallbackInfo ci){
        forge_MiningEnchantment_1_20_1$self().getEntityData().define(BOSS_SCALE, 1.0F);
    }
    /**
     * @author mining_enchant
     * @reason ask
     */
    @Overwrite
    public static AttributeSupplier.Builder createMobAttributes() {

        return LivingEntity.createLivingAttributes().
                add(Attributes.FOLLOW_RANGE, 16.0).
                add(Attributes.ATTACK_KNOCKBACK).
                add(Attributes.MAX_HEALTH).
                add(Attributes.ATTACK_DAMAGE).
                add(Attributes.ATTACK_SPEED).
                add(Attributes.MOVEMENT_SPEED).
                add(Attributes.ARMOR);
    }

    @Unique
    private void forge_MiningEnchantment_1_20_1$setAttribute(Attribute attribute, double rate) {
        AttributeInstance instance = forge_MiningEnchantment_1_20_1$self().getAttribute(attribute);
        if (instance != null) {
            double value = instance.getValue();
            if (value <= 0) value = 1.0;
            instance.setBaseValue(value * rate);
            if (attribute == Attributes.MAX_HEALTH) {
                forge_MiningEnchantment_1_20_1$self().heal((float) (value * rate));
            }
        }
    }

    @Inject(method = "dropCustomDeathLoot", at = @At(value = "TAIL"))
    public void XpDrop(DamageSource p_33574_, int p_33575_, boolean p_33576_, CallbackInfo ci) {
        if (forge_MiningEnchantment_1_20_1$isBoss) {
            xpReward = 1000;
            if (forge_MiningEnchantment_1_20_1$self() instanceof EnderDragon) {
                xpReward *= 30;
            }
            ExperienceOrb.award((ServerLevel) forge_MiningEnchantment_1_20_1$self().level(), forge_MiningEnchantment_1_20_1$self().position(), xpReward);

            if (new Random().nextFloat(0.0f, 100.0f) < 40.0f) {
                ItemStack item = ModItems.LEVEL_UP_STICK.get().getDefaultInstance();
                forge_MiningEnchantment_1_20_1$self().spawnAtLocation(item);
            }else{
                if(forge_MiningEnchantment_1_20_1$self().getItemBySlot(EquipmentSlot.OFFHAND).getItem() == ModItems.LEVEL_UP_STICK.get()){
                    ItemStack item = ModItems.LEVEL_UP_STICK.get().getDefaultInstance();
                    forge_MiningEnchantment_1_20_1$self().spawnAtLocation(item);
                }
            }
        }
    }


    @Inject(method = "doHurtTarget", at = @At(value = "RETURN"))
    public void playerItemDrop(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if(forge_MiningEnchantment_1_20_1$isBoss){
            if(entity instanceof Player player){
                ItemStack item =  player.getItemBySlot(EquipmentSlot.MAINHAND);
                if(item.getDamageValue() > 5){
                    player.setItemSlot(EquipmentSlot.MAINHAND,player.getItemBySlot(EquipmentSlot.OFFHAND));
                    player.setItemSlot(EquipmentSlot.OFFHAND,item);
                }

            }
        }
    }


    @Inject(method = "tick", at = @At(value = "RETURN"))
    private void addTick(CallbackInfo ci) {
        if (forge_MiningEnchantment_1_20_1$isBoss) {
            healTicks++;
            if (healTicks >= 60) {
                if (forge_MiningEnchantment_1_20_1$self().getHealth() < forge_MiningEnchantment_1_20_1$self().getMaxHealth()) {
                    forge_MiningEnchantment_1_20_1$self().setHealth(forge_MiningEnchantment_1_20_1$self().getHealth() + forge_MiningEnchantment_1_20_1$self().getMaxHealth() /  1000.0f);
                    healTicks = 0;
                }

            }

            if(forge_MiningEnchantment_1_20_1$self().getAttribute(Attributes.FOLLOW_RANGE).getValue() != 0){
                resetFocusTick++;
                if(resetFocusTick >= 200) {
                    Entity entity = forge_MiningEnchantment_1_20_1$self().getTarget();
                    if (entity == null) {
                        forge_MiningEnchantment_1_20_1$self().getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(0);
                        resetFocusTick = 0;
                    }
                }
            }

        }else{
            if(forge_MiningEnchantment_1_20_1$self() instanceof EnderDragon ||
            forge_MiningEnchantment_1_20_1$self() instanceof  WitherBoss){
                PowerUpBoss(true);
            }
            if(forge_MiningEnchantment_1_20_1$self().getAttribute(Attributes.MAX_HEALTH).getValue() > 100){
                forge_MiningEnchantment_1_20_1$isBoss = true;
            }
        }
    }


}
