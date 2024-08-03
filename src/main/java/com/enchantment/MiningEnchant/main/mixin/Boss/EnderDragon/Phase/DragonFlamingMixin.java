package com.enchantment.MiningEnchant.main.mixin.Boss.EnderDragon.Phase;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.AbstractDragonSittingPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonSittingFlamingPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;

@Mixin(DragonSittingFlamingPhase.class)
public class DragonFlamingMixin extends AbstractDragonSittingPhase {

    @Shadow private int flameTicks;
    @Shadow private int flameCount;
    @Shadow @Nullable
    private AreaEffectCloud flame;

    private DragonSittingFlamingPhase self(){
        return (DragonSittingFlamingPhase) (Object)this;
    }
    public DragonFlamingMixin(EnderDragon p_31196_) {
        super(p_31196_);
    }

    @Override
    public EnderDragonPhase<? extends DragonPhaseInstance> getPhase() {
        return EnderDragonPhase.SITTING_FLAMING;
    }

    /**
     * @author mining_enchant
     * @reason don't ask
     */
    @Overwrite
    public void doServerTick() {
        ++this.flameTicks;
        if (this.flameTicks >= 220) {
            if (this.flameCount >= 1) {
                this.dragon.getPhaseManager().setPhase(EnderDragonPhase.TAKEOFF);
            } else {
                this.dragon.getPhaseManager().setPhase(EnderDragonPhase.SITTING_SCANNING);
            }
        } else if (this.flameTicks >= 10 && this.flameTicks < 140) {
            if (this.dragon.getHealth() > this.dragon.getMaxHealth() / 2.0) {
                this.dragon.setYRot(this.dragon.getYRot() + 2.0f);
                Vec3 $$0 = (new Vec3(this.dragon.head.getX() - this.dragon.getX(), 0.0, this.dragon.head.getZ() - this.dragon.getZ())).normalize();
                float $$1 = 5.0F;
                double $$2 = this.dragon.head.getX() + $$0.x * 5.0 / 2.0;
                double $$3 = this.dragon.head.getZ() + $$0.z * 5.0 / 2.0;
                double $$4 = this.dragon.head.getY(0.5);
                double $$5 = $$4;
                BlockPos.MutableBlockPos $$6 = new BlockPos.MutableBlockPos($$2, $$5, $$3);

                while (this.dragon.level().isEmptyBlock($$6)) {
                    --$$5;
                    if ($$5 < 0.0) {
                        $$5 = $$4;
                        break;
                    }

                    $$6.set($$2, $$5, $$3);
                }

                $$5 = (double) (Mth.floor($$5) + 1);
                if (flameTicks % 20 == 0) {
                    this.flame = new AreaEffectCloud(this.dragon.level(), $$2, $$5, $$3);
                    this.flame.setOwner(this.dragon);
                    this.flame.setRadius(5.0F);
                    this.flame.setDuration(200);
                    this.flame.setParticle(ParticleTypes.DRAGON_BREATH);
                    this.flame.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 3));
                    this.flame.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1, 3));
                    this.dragon.level().addFreshEntity(this.flame);
                }
            }else{

            }
        }

    }

}
