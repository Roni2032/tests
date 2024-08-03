package com.enchantment.MiningEnchant.main.mixin.Boss.EnderDragon.Phase;

import com.enchantment.MiningEnchant.main.Entity.EnderDragonFireBall;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.AbstractDragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonStrafePlayerPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Mixin(DragonStrafePlayerPhase.class)
public abstract class DragonStrafeMixin extends AbstractDragonPhaseInstance {
    private int phaseTick;
    private int mode;

    @Shadow   @Nullable
    private LivingEntity attackTarget;
    @Shadow private int fireballCharge;
    @Shadow @Nullable
    private Path currentPath;
    @Shadow @Nullable
    private Vec3 targetLocation;

    @Shadow protected abstract void findNewTarget();

    private int fireballCount = 0;

    private DragonStrafePlayerPhase self(){
        return (DragonStrafePlayerPhase) (Object)this;
    }
    public DragonStrafeMixin(EnderDragon p_31178_) {
        super(p_31178_);
    }

    @Override
    public EnderDragonPhase<? extends DragonPhaseInstance> getPhase() {
        return EnderDragonPhase.STRAFE_PLAYER;
    }

    /**
     * @author mining_enchant
     * @reason don't ask
     */
    @Overwrite
    public void doServerTick() {
        if (this.attackTarget == null) {
            //LOGGER.warn("Skipping player strafe phase because no player was found");
            this.dragon.getPhaseManager().setPhase(EnderDragonPhase.HOLDING_PATTERN);
        } else {
            if(mode < 20){
                Rain();
            }else {
                Bless();
            }
        }
    }


    private void Bless(){
        double $$6;
        double $$7;
        double $$12;
        if (this.currentPath != null && this.currentPath.isDone()) {
            $$6 = this.attackTarget.getX();
            $$7 = this.attackTarget.getZ();
            double $$2 = $$6 - this.dragon.getX();
            double $$3 = $$7 - this.dragon.getZ();
            $$12 = Math.sqrt($$2 * $$2 + $$3 * $$3);
            double $$5 = Math.min(0.4000000059604645 + $$12 / 80.0 - 1.0, 10.0);
            this.targetLocation = new Vec3($$6, this.attackTarget.getY() + $$5, $$7);
        }

        $$6 = this.targetLocation == null ? 0.0 : this.targetLocation.distanceToSqr(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
        if ($$6 < 100.0 || $$6 > 22500.0) {
            this.findNewTarget();
        }

        $$7 = 64.0;
        if (this.attackTarget.distanceToSqr(this.dragon) < 4096.0) {
            if (this.dragon.hasLineOfSight(this.attackTarget)) {
                ++this.fireballCharge;
                Vec3 $$8 = (new Vec3(this.attackTarget.getX() - this.dragon.getX(), 0.0, this.attackTarget.getZ() - this.dragon.getZ())).normalize();
                Vec3 $$9 = (new Vec3((double) Mth.sin(this.dragon.getYRot() * 0.017453292F), 0.0, (double)(-Mth.cos(this.dragon.getYRot() * 0.017453292F)))).normalize();
                float $$10 = (float)$$9.dot($$8);
                float $$11 = (float)(Math.acos((double)$$10) * 57.2957763671875);
                $$11 += 0.5F;
                if (this.fireballCharge >= 10 && $$11 >= 0.0F && $$11 < 10.0F) {
                    $$12 = 1.0;
                    Vec3 $$13 = this.dragon.getViewVector(1.0F);
                    double $$14 = this.dragon.head.getX() - $$13.x * 1.0;
                    double $$15 = this.dragon.head.getY(0.5) + 0.5;
                    double $$16 = this.dragon.head.getZ() - $$13.z * 1.0;
                    double $$17 = this.attackTarget.getX() - $$14;
                    double $$18 = this.attackTarget.getY(0.5) - $$15;
                    double $$19 = this.attackTarget.getZ() - $$16;
                    if (!this.dragon.isSilent()) {
                        this.dragon.level().levelEvent((Player)null, 1017, this.dragon.blockPosition(), 0);
                    }
                    AbstractHurtingProjectile fireball;
                    if(new Random().nextInt(0,2) == 0){
                        fireball = new DragonFireball(this.dragon.level(), this.dragon, $$17, $$18, $$19);
                    }else{
                        fireball = new EnderDragonFireBall(this.dragon.level(), this.dragon, $$17, $$18, $$19, 3);
                    }
                    fireball.hurt(new DamageSource(new Holder<DamageType>() {
                        @Override
                        public DamageType value() {
                            return null;
                        }

                        @Override
                        public boolean isBound() {
                            return false;
                        }

                        @Override
                        public boolean is(ResourceLocation resourceLocation) {
                            return false;
                        }

                        @Override
                        public boolean is(ResourceKey<DamageType> resourceKey) {
                            return false;
                        }

                        @Override
                        public boolean is(Predicate<ResourceKey<DamageType>> predicate) {
                            return false;
                        }

                        @Override
                        public boolean is(TagKey<DamageType> tagKey) {
                            return false;
                        }

                        @Override
                        public Stream<TagKey<DamageType>> tags() {
                            return Stream.empty();
                        }

                        @Override
                        public Either<ResourceKey<DamageType>, DamageType> unwrap() {
                            return null;
                        }

                        @Override
                        public Optional<ResourceKey<DamageType>> unwrapKey() {
                            return Optional.empty();
                        }

                        @Override
                        public Kind kind() {
                            return null;
                        }

                        @Override
                        public boolean canSerializeIn(HolderOwner<DamageType> holderOwner) {
                            return false;
                        }
                    }),1200);
                    fireball.moveTo($$14, $$15, $$16, 0.0F, 0.0F);
                    this.dragon.level().addFreshEntity(fireball);

                    this.fireballCount++;
                    this.fireballCharge = 0;
                    if (this.currentPath != null) {
                        while(!this.currentPath.isDone()) {
                            this.currentPath.advance();
                        }
                    }
                    if(fireballCount >= 5) {
                        this.dragon.getPhaseManager().setPhase(EnderDragonPhase.HOLDING_PATTERN);
                        fireballCount = 0;
                    }
                }
            } else if (this.fireballCharge > 0) {
                --this.fireballCharge;
            }
        } else if (this.fireballCharge > 0) {
            --this.fireballCharge;
        }

    }

    private void Rain(){
        ++phaseTick;
        if (phaseTick >= 600) {
            this.dragon.getPhaseManager().setPhase(EnderDragonPhase.HOLDING_PATTERN);
        } else if (phaseTick >= 20 && phaseTick % 5 == 0) {
            Vec3 attackCenterPos = new Vec3(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
            double x = new Random().nextDouble(attackCenterPos.x - 50.0, attackCenterPos.x + 50.0);
            double z = new Random().nextDouble(attackCenterPos.z - 50.0, attackCenterPos.z + 50.0);

            AbstractHurtingProjectile fireball = new DragonFireball(this.dragon.level(), this.dragon, 0, -1, 0);

            if(this.dragon.getHealth() > this.dragon.getMaxHealth() / 2.0f) {
                if(new Random().nextFloat(100) < 30){
                    fireball = new EnderDragonFireBall(this.dragon.level(), this.dragon, 0, -1, 0, 3);
                }
            }
            fireball.moveTo(x, attackCenterPos.y, z, 0.0f, 0.0f);
            fireball.hurt(null,20);
            this.dragon.level().addFreshEntity(fireball);
        }
    }

    @Inject(method = "begin",at = @At(value = "TAIL"))
    private void start(CallbackInfo ci){
        mode = new Random().nextInt(0,100);
    }
}
