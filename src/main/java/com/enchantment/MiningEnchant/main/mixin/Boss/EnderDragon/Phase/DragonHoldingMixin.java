package com.enchantment.MiningEnchant.main.mixin.Boss.EnderDragon.Phase;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.AbstractDragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonChargePlayerPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonHoldingPatternPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DragonHoldingPatternPhase.class)
public abstract class DragonHoldingMixin extends AbstractDragonPhaseInstance {

    private int chargeTicks = 0;
    private static final TargetingConditions NEW_TARGET_TARGETING = TargetingConditions.forCombat().ignoreLineOfSight();

    public DragonHoldingMixin(EnderDragon p_31178_) {
        super(p_31178_);
    }

    @Shadow protected abstract void strafePlayer(Player p_31237_);

    private DragonHoldingPatternPhase self(){
        return (DragonHoldingPatternPhase) (Object)this;
    }

    @Inject(method = "findNewTarget",at = @At(value = "TAIL"))
    private void strafeChange(CallbackInfo ci){
        BlockPos blockPos = this.dragon.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new BlockPos(EndPodiumFeature.getLocation(this.dragon.getFightOrigin())));

        Player player = this.dragon.level().getNearestPlayer(NEW_TARGET_TARGETING, this.dragon, (double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ());
        if (player != null && (this.dragon.getRandom().nextInt((int)(100)) < 10 || this.dragon.getRandom().nextInt(100) < 10)) {
            strafePlayer(player);
        }


    }

    @Inject(method = "doServerTick",at = @At(value = "TAIL"))
    public void chargePlayer(CallbackInfo ci){
        BlockPos blockPos = this.dragon.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new BlockPos(EndPodiumFeature.getLocation(this.dragon.getFightOrigin())));

        Player player = this.dragon.level().getNearestPlayer(NEW_TARGET_TARGETING, this.dragon, (double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ());

        if(player != null){
            if(chargeTicks >= 200) {
                chargeTicks = 0;
                this.dragon.getPhaseManager().setPhase(EnderDragonPhase.CHARGING_PLAYER);
                ((DragonChargePlayerPhase) this.dragon.getPhaseManager().getPhase(EnderDragonPhase.CHARGING_PLAYER)).setTarget(new Vec3(player.getX(), player.getY(), player.getZ()));
            }else{
                chargeTicks++;
            }
        }
    }
}
