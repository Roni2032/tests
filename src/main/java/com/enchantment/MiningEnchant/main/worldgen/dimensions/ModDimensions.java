package com.enchantment.MiningEnchant.main.worldgen.dimensions;

import com.enchantment.MiningEnchant.main.MiningEnchant;
import com.enchantment.MiningEnchant.main.worldgen.Rules.SurfaceRuleData;
import com.enchantment.MiningEnchant.main.worldgen.biome.ModBiomes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;
import java.util.OptionalLong;

public class ModDimensions {
    public static final ResourceKey<LevelStem> DIM_KEY = ResourceKey.create(Registries.LEVEL_STEM,new ResourceLocation(MiningEnchant.MOD_ID,"the_strength"));
    public static final ResourceKey<Level> DIM_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,new ResourceLocation(MiningEnchant.MOD_ID,"the_strength"));
    public static final ResourceKey<DimensionType> DIM_TYPE_KEY = ResourceKey.create(Registries.DIMENSION_TYPE,new ResourceLocation(MiningEnchant.MOD_ID,"the_strength_type"));

    public static void bootStrapType(BootstapContext<DimensionType> context){
        context.register(DIM_TYPE_KEY,new DimensionType(
                OptionalLong.of(12000),
                false,//天窓があるか
                false,//天井があるか(true:例 ネザー)
                false,//水が蒸発するか
                false,
                1.0f,//デイメンションを離れるときの座標のずれ(乗算)
                true,//ベッド爆発
                false,//リスポーンアンカー爆発
                0,//ブロックの設置可能高さの最小
                256,//ブロックの設置可能高さの最大
                256,//ポータルなどで移動可能な高さの最大
                BlockTags.INFINIBURN_OVERWORLD,
                BuiltinDimensionTypes.OVERWORLD_EFFECTS,
                0.2f,//ディメンションの光量
                new DimensionType.MonsterSettings(false,false, ConstantInt.of(0),0)
        ));
    }

    public static void boostStrapStem(BootstapContext<LevelStem> context){
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimType = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenSetting = context.lookup(Registries.NOISE_SETTINGS);

        //一つのバイオームだけを生成したいときに使う
        NoiseBasedChunkGenerator chunkGen = new NoiseBasedChunkGenerator(
                new FixedBiomeSource(biomeRegistry.getOrThrow(ModBiomes.STRENGTH_FOREST)),
                        noiseGenSetting.getOrThrow(NoiseGeneratorSettings.AMPLIFIED));

        //複数のバイオームを生成したいときに使う
        NoiseBasedChunkGenerator noiseBasedChunkGen = new NoiseBasedChunkGenerator(
                MultiNoiseBiomeSource.createFromList(
                        new Climate.ParameterList<>(List.of(
                                Pair.of(
                                        Climate.parameters(0.1f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f),biomeRegistry.getOrThrow(ModBiomes.STRENGTH_FOREST)),
                                Pair.of(
                                        Climate.parameters(0.2f,0.3f,0.2f,0.1f,0.0f,0.0f,0.0f),biomeRegistry.getOrThrow(Biomes.DEEP_DARK))
                                                ))),
                noiseGenSetting.getOrThrow(DIMENSION_NOISE));

        LevelStem stem = new LevelStem(dimType.getOrThrow(ModDimensions.DIM_TYPE_KEY),noiseBasedChunkGen);

        context.register(DIM_KEY,stem);
    }


    public static ResourceKey<NoiseGeneratorSettings> DIMENSION_NOISE = ResourceKey.create(Registries.NOISE_SETTINGS,new ResourceLocation(MiningEnchant.MOD_ID, "path"));
    public static void bootStrapNoise(BootstapContext<NoiseGeneratorSettings> context){
        HolderGetter<DensityFunction> densityFunction = context.lookup(Registries.DENSITY_FUNCTION);
        HolderGetter<NormalNoise.NoiseParameters> noise = context.lookup(Registries.NOISE);

        context.register(DIMENSION_NOISE,noiseSettings(densityFunction,noise));
    }

    public static NoiseGeneratorSettings noiseSettings(HolderGetter<DensityFunction> density,HolderGetter<NormalNoise.NoiseParameters> noise){

        SurfaceRules.RuleSource bottomBedrock = SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_bottom", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(4)), SurfaceRules.state(Blocks.BEDROCK.defaultBlockState()));

        return null;
    }
}
