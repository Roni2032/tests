package com.enchantment.MiningEnchant.main.Enchant;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.enchantment.DamageEnchantment;

import java.util.Random;

public class CriticalEnchantment extends DamageEnchantment {
    public CriticalEnchantment() {
        super(Rarity.RARE,0,new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }


    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public float getDamageBonus(int level, MobType type) {

        if(type == MobType.UNDEFINED) return 0.0f;
        Random rnd = new Random();
        int result = rnd.nextInt(0,100);

        if(result < 30){
            return level * 4.5f;
        }else return 0.0f;
    }
}
