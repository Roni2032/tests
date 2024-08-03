package com.enchantment.MiningEnchant.main.mixin.Enchants;

import net.minecraft.world.item.enchantment.DamageEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;



@Mixin(DamageEnchantment.class)
public class NewDamegeEnchantment {
    /**
     * @author mining_enchant
     * @reason ask
     */
    @Overwrite
    public int getMaxLevel() {
        return 10;
    }
}
