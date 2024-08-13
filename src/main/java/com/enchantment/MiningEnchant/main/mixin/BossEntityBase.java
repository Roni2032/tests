package com.enchantment.MiningEnchant.main.mixin;

import com.enchantment.MiningEnchant.main.MiningEnchant;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(Mob.class)
public abstract class BossEntityBase {

    @Unique
    public boolean forge_MiningEnchantment_1_20_1$isBoss = false;
    @Unique
    private Mob forge_MiningEnchantment_1_20_1$self() {
        return (Mob) (Object) this;
    }

    /**
     * @author mining_enchant
     * @reason ask
     */
    @Overwrite
    protected boolean isSunBurnTick() {
        if (forge_MiningEnchantment_1_20_1$self().getEntityData().get(MiningEnchant.STRENGTH_MOB)) return false;

        if (forge_MiningEnchantment_1_20_1$self().level().isDay() && !forge_MiningEnchantment_1_20_1$self().level().isClientSide) {
            float f = forge_MiningEnchantment_1_20_1$self().getLightLevelDependentMagicValue();
            BlockPos blockpos = BlockPos.containing(forge_MiningEnchantment_1_20_1$self().getX(), forge_MiningEnchantment_1_20_1$self().getEyeY(), forge_MiningEnchantment_1_20_1$self().getZ());
            boolean flag = forge_MiningEnchantment_1_20_1$self().isInWaterRainOrBubble() || forge_MiningEnchantment_1_20_1$self().isInPowderSnow || forge_MiningEnchantment_1_20_1$self().wasInPowderSnow;

            return f > 0.5F && new Random().nextFloat() * 30.0F < (f - 0.4F) * 2.0F && !flag && forge_MiningEnchantment_1_20_1$self().level().canSeeSky(blockpos);
        }
        return false;
    }

    @Inject(method = "defineSynchedData",at = @At(value = "TAIL"))
    private void SynchedDate(CallbackInfo ci){
        forge_MiningEnchantment_1_20_1$self().getEntityData().define(MiningEnchant.STRENGTH_MOB, false);
    }
}
