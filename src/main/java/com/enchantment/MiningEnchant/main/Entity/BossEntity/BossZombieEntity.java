package com.enchantment.MiningEnchant.main.Entity.BossEntity;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BossZombieEntity extends Monster {

    public BossZombieEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }

    public final AnimationState idleAnimation = new AnimationState();
    public int idleAnimationTimeout = 0;
    public final AnimationState blewAnimation = new AnimationState();
    public int blowAnimationTimeout = 0;
    public final AnimationState useMagicAnimation = new AnimationState();
    public int useMagicAnimationTimeout = 0;


    @Override
    protected void registerGoals() {
        //this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, new Class[0])).setAlertOthers(new Class[]{ZombifiedPiglin.class}));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
    }

    public static AttributeSupplier.Builder createAttributes(){
        return createMonsterAttributes()
                .add(Attributes.MAX_HEALTH,1500.0)
                .add(Attributes.FOLLOW_RANGE,64.0)
                .add(Attributes.ARMOR,40.0)
                .add(Attributes.KNOCKBACK_RESISTANCE,1.0)
                .add(Attributes.ARMOR_TOUGHNESS,0.2f)
                .add(Attributes.ATTACK_DAMAGE,12.0)
                .add(Attributes.MOVEMENT_SPEED,0.3f);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide){
            setupAnimationStates();
        }
    }

    private void setupAnimationStates(){
        if(this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = new Random().nextInt(40) + 80;
            this.idleAnimation.start(this.tickCount);
        }else{
            idleAnimationTimeout--;
        }
    }
    @Override
    protected void updateWalkAnimation(float partialTick) {
        float f;

        if(this.getPose() == Pose.STANDING){
            f = Math.min(partialTick * 6.0f,1.0f);
        }else{
            f = 0.0f;
        }

        this.walkAnimation.update(f,0.2f);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ZOMBIE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        return SoundEvents.ZOMBIE_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ZOMBIE_AMBIENT;
    }
}
