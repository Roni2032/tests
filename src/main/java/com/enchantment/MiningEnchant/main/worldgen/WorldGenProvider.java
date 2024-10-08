
package com.enchantment.MiningEnchant.main.worldgen;

import com.enchantment.MiningEnchant.main.worldgen.Features.StrengthTreeFeature;
import com.enchantment.MiningEnchant.main.MiningEnchant;
import com.enchantment.MiningEnchant.main.worldgen.Placements.StrengthTreePlacement;
import com.enchantment.MiningEnchant.main.worldgen.biome.ModBiomes;
import com.enchantment.MiningEnchant.main.worldgen.dimensions.ModDimensions;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class WorldGenProvider extends DatapackBuiltinEntriesProvider {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, StrengthTreeFeature::bootStrap)
            .add(Registries.PLACED_FEATURE, StrengthTreePlacement::bootStrap)
            .add(Registries.BIOME, ModBiomes::bootStrap)
            .add(Registries.NOISE_SETTINGS, ModDimensions::bootStrapNoise)
            .add(Registries.DIMENSION_TYPE, ModDimensions::bootStrapType)
            .add(Registries.LEVEL_STEM,ModDimensions::boostStrapStem);

    public WorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(MiningEnchant.MOD_ID));
    }
}
