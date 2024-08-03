package com.enchantment.MiningEnchant.main.worldgen.Rules;

import com.enchantment.MiningEnchant.main.worldgen.biome.ModBiomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class SurfaceRuleData {
    private static final SurfaceRules.RuleSource MUD = makeStateRule(Blocks.MUD);
    private static final SurfaceRules.RuleSource DEEP_SLATE =
            makeStateRule(Blocks.DEEPSLATE);

    public static SurfaceRules.RuleSource makeRules() {
        return SurfaceRules.sequence(
                // 呪われた森バイオームの地面を泥ブロックにする
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(ModBiomes.STRENGTH_FOREST),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, MUD)),
                // 呪われた森バイオームの天井をオリハルコンブロックにする
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(ModBiomes.STRENGTH_FOREST),
                        SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, DEEP_SLATE))
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
