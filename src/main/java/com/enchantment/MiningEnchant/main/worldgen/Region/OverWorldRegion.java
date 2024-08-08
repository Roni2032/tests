package com.enchantment.MiningEnchant.main.worldgen.Region;

import com.enchantment.MiningEnchant.main.worldgen.biome.ModBiomes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.VanillaParameterOverlayBuilder;

import java.util.function.Consumer;

public class OverWorldRegion extends Region {
    public OverWorldRegion(ResourceLocation name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        VanillaParameterOverlayBuilder builder = new VanillaParameterOverlayBuilder();
        new ParameterUtils.ParameterPointListBuilder()
                //生成される土地の種類(INLAND : 島 COAST : 海　等)
                .continentalness(ParameterUtils.Continentalness.INLAND)
                .depth(ParameterUtils.Depth.SURFACE)
                .build().forEach(point -> builder.add(point, ModBiomes.STRENGTH_FOREST));

        builder.build().forEach(mapper);
    }
}
