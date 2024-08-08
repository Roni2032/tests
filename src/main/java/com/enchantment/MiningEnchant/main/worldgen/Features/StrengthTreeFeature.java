package com.enchantment.MiningEnchant.main.worldgen.Features;

import com.enchantment.MiningEnchant.main.MiningEnchant;
import com.enchantment.MiningEnchant.main.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;


public class StrengthTreeFeature {

    public static final ResourceKey<ConfiguredFeature<?,?>> STRENGTH_TREE_KEY = createKey("strength_tree");

    public static void bootStrap(BootstapContext<ConfiguredFeature<?,?>> context){
        FeatureUtils.register(context,STRENGTH_TREE_KEY,Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        //幹部分の設定
                        BlockStateProvider.simple(ModBlocks.Blocks.STRENGTH_LOG_BLOCK.get()),//種類
                        new StraightTrunkPlacer(7,5,0),//形
                        //葉の設定
                        BlockStateProvider.simple(ModBlocks.Blocks.STRENGTH_LEAVE_BLOCK.get()),//種類
                        //半径,幹からの距離,高さ
                        new BlobFoliagePlacer(ConstantInt.of(4),ConstantInt.of(2),3),//形
                        new TwoLayersFeatureSize(1,0,2)).//木の生成に必要なスペース
                        build());
    }

    public static ResourceKey<ConfiguredFeature<?,?>> createKey(String name){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE,new ResourceLocation(MiningEnchant.MOD_ID,name));
    }
}
