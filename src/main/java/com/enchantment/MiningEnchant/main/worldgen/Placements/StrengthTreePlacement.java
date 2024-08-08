package com.enchantment.MiningEnchant.main.worldgen.Placements;

import com.enchantment.MiningEnchant.main.worldgen.Features.StrengthTreeFeature;
import com.enchantment.MiningEnchant.main.MiningEnchant;
import com.enchantment.MiningEnchant.main.ModBlocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class StrengthTreePlacement {



    public static ResourceKey<PlacedFeature> STRENGTH_TREE = createKey("strength_tree");

    public static void bootStrap(BootstapContext<PlacedFeature> context){
        HolderGetter<ConfiguredFeature<?,?>> configuredFeature = context.lookup(Registries.CONFIGURED_FEATURE);
        PlacementUtils.register(context,STRENGTH_TREE,
                configuredFeature.getOrThrow(StrengthTreeFeature.STRENGTH_TREE_KEY),
                //生える木の数(Base + randomA + randomB)
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(10,0.1f,1),
                        //苗木の設定
                        ModBlocks.Blocks.STRENGTH_SAPLING.get()));
    }

    public static ResourceKey<PlacedFeature> createKey(String name){
        return ResourceKey.create(Registries.PLACED_FEATURE,new ResourceLocation(MiningEnchant.MOD_ID,name));
    }
}
