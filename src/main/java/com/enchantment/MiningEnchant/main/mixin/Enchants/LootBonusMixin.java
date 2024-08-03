package com.enchantment.MiningEnchant.main.mixin.Enchants;

import net.minecraft.world.item.enchantment.LootBonusEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(LootBonusEnchantment.class)
public class LootBonusMixin {

    /**
     * @author mining_enchant
     * @reason ask
     */
    @Overwrite
    public int getMaxLevel() {
        return 7;
    }
}
