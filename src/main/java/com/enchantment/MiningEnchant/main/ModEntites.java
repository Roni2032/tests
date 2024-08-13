package com.enchantment.MiningEnchant.main;

import com.enchantment.MiningEnchant.main.Entity.BossEntity.BossZombieEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntites {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,MiningEnchant.MOD_ID);

    public static final RegistryObject<EntityType<BossZombieEntity>> BOSS_ZOMBIE = ENTITY_TYPES.register("boss_zombie",()-> EntityType.Builder.of(BossZombieEntity::new, MobCategory.MONSTER).sized(0.6F, 1.95F).build("boss_zombie"));


}
