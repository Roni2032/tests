package com.enchantment.MiningEnchant.main.worldgen.biome;

import com.enchantment.MiningEnchant.main.MiningEnchant;
import com.enchantment.MiningEnchant.main.worldgen.Placements.StrengthTreePlacement;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;

public class ModBiomes {
    public static final ResourceKey<Biome> STRENGTH_FOREST = ResourceKey.create(Registries.BIOME,new ResourceLocation(MiningEnchant.MOD_ID,"strength_forest"));


    // OverworldBiomesクラスからコピー
    private static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
        BiomeDefaultFeatures.addDefaultSprings(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
    }

    public static void bootStrap(BootstapContext<Biome> context){
        context.register(STRENGTH_FOREST,strengthForest(context));
    }

    private static Biome strengthForest(BootstapContext<Biome> context) {
        // モブのスポーンの設定
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        spawnBuilder.addSpawn(MobCategory.MONSTER,
                new MobSpawnSettings.SpawnerData(
                        EntityType.ZOMBIE, 100, 4, 4));

        // ヒツジなど
//        BiomeDefaultFeatures.farmAnimals(spawnBuilder);
        // ゾンビ、スケルトン、クリーパーなど
        BiomeDefaultFeatures.commonSpawns(spawnBuilder);



        // Carver：渓谷や洞窟を設置するもの
        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE),
                        context.lookup(Registries.CONFIGURED_CARVER));

        // バニラの設定をいい感じにコピってくる（順番をバニラ通りにしないとエラーが起きる）
        globalOverworldGeneration(biomeBuilder);
        BiomeDefaultFeatures.addFerns(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addExtraGold(biomeBuilder);


        BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);

        // かぼちゃ、サトウキビ
        BiomeDefaultFeatures.addDefaultExtraVegetation(biomeBuilder);
        // 呪われた木
       biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
               StrengthTreePlacement.STRENGTH_TREE);

        return new Biome.BiomeBuilder()
                // 雨が降るかどうか
                .hasPrecipitation(true)
                // 降水確率（沼地：0.9f 森林：0.8f）
                .downfall(0.8f)
                // 気温（森林：0.7f メサ：2.0f 雪タイガ：-0.5f）
                .temperature(0.7f)
                // 上の設定を読み込ませる
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                // その他の設定
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(7832230)
                        .waterFogColor(7832213)
                        .skyColor(7289685)
                        .grassColorOverride(3488597)
                        .foliageColorOverride(14222332)
                        .fogColor(10854566)
                        .ambientParticle(new AmbientParticleSettings(ParticleTypes.ENCHANT, 0.00625F))
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_BADLANDS))
                        .build())
                .build();
    }
}
