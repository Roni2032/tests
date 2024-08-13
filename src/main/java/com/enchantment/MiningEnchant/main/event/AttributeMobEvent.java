package com.enchantment.MiningEnchant.main.event;

import com.enchantment.MiningEnchant.main.Entity.BossEntity.BossZombieEntity;
import com.enchantment.MiningEnchant.main.MiningEnchant;
import com.enchantment.MiningEnchant.main.ModEntites;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MiningEnchant.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class AttributeMobEvent {
    @SubscribeEvent
    public static void createAttributes(EntityAttributeCreationEvent event){
        event.put(ModEntites.BOSS_ZOMBIE.get(), BossZombieEntity.createAttributes().build());
    }
}
