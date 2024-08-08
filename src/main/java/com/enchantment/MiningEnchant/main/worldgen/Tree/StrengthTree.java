package com.enchantment.MiningEnchant.main.worldgen.Tree;

import com.enchantment.MiningEnchant.main.worldgen.Features.StrengthTreeFeature;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class StrengthTree extends AbstractTreeGrower {
    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource randomSource, boolean b) {
        return StrengthTreeFeature.STRENGTH_TREE_KEY;
    }
}
