package com.enchantment.MiningEnchant.main.mixin.Enchants;

import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ProtectionEnchantment.class)
public class NewProtectedEnchantment {

    /**
     * @author mining_enchant
     * @reason ask
     */
    @Overwrite
    public int getMaxLevel(){
        return 10;
    }
}
