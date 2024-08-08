package com.enchantment.MiningEnchant.main.mixin.BlockState;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.extensions.IForgeBlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Optional;

@Mixin(ExplosionDamageCalculator.class)
public class IForgeBlockStateMixin {
    /**
     * @author mining_enchant
     * @reason ask
     */
    @Overwrite
    public Optional<Float> getBlockExplosionResistance(Explosion p_46099_, BlockGetter p_46100_, BlockPos p_46101_, BlockState p_46102_, FluidState p_46103_) {

        return p_46102_.isAir() && p_46103_.isEmpty() ? Optional.empty() : Optional.of(Math.max(p_46102_.getExplosionResistance(p_46100_, p_46101_, p_46099_) + 1500.0f, p_46103_.getExplosionResistance(p_46100_, p_46101_, p_46099_)));
    }
}
