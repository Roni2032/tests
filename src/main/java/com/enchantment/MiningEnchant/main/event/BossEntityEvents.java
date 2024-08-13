package com.enchantment.MiningEnchant.main.event;

import com.enchantment.MiningEnchant.main.Blocks.block.ResistStrengthMobBlock;
import com.enchantment.MiningEnchant.main.MiningEnchant;
import com.enchantment.MiningEnchant.main.ModItems;
import com.enchantment.MiningEnchant.main.mixin.Attribute.AccecerAttributeRange;
import com.enchantment.MiningEnchant.main.worldgen.biome.ModBiomes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid =MiningEnchant.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BossEntityEvents {


    @SubscribeEvent
    public static void tick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if(!(entity instanceof Mob)) return;
        if (entity.getEntityData().get(MiningEnchant.STRENGTH_MOB)) {

            long time = entity.level().getDayTime();

            var biome = entity.level().getBiome(new BlockPos(entity.getBlockX(), entity.getBlockY(), entity.getBlockZ()));
            AttributeInstance attribute = entity.getAttribute(Attributes.FOLLOW_RANGE);
            if(attribute != null) {
                if (biome.is(ModBiomes.STRENGTH_FOREST)) {
                    if (attribute.getValue() == 0) {
                        attribute.setBaseValue(32 * MiningEnchant.BOSS_FOLLOW_RATE);
                    }
                } else {
                    if (time % 10 == 0) {
                        if (attribute.getValue() != 0) {
                            attribute.setBaseValue(0);
                        }
                    }
                }
            }

            if (time % 0.27 == 0) {
                if (entity.getHealth() < entity.getMaxHealth()) {
                    entity.setHealth(entity.getHealth() + entity.getMaxHealth() / 1000.0f);
                }
            }
        }else {
            if (entity instanceof EnderDragon || entity instanceof WitherBoss) {
                PowerUp(entity, true,null);
            }
            AttributeInstance attribute = entity.getAttribute(Attributes.MAX_HEALTH);
            double max = attribute.getValue();
            if (max == 100.0f) {
                entity.getEntityData().set(MiningEnchant.STRENGTH_MOB, true);
            }
        }
    }
    @SubscribeEvent
    public static void hurt(LivingDamageEvent event) {
        LivingEntity entity = event.getEntity();

        if (entity instanceof Player player){
            Entity damageSource = event.getSource().getEntity();
            if(damageSource instanceof Mob && damageSource.getEntityData().get(MiningEnchant.STRENGTH_MOB)){
                ItemStack item =  player.getItemBySlot(EquipmentSlot.MAINHAND);
                if(item.getDamageValue() > 5){
                    player.setItemSlot(EquipmentSlot.MAINHAND,player.getItemBySlot(EquipmentSlot.OFFHAND));
                    player.setItemSlot(EquipmentSlot.OFFHAND,item);
                }
            }
        }else{
            if(!(entity instanceof Mob)) return;

            if (entity.getEntityData().get(MiningEnchant.STRENGTH_MOB)) {
                float damage = event.getAmount();
                if(damage > 100){
                    entity.heal(damage);
                }
            }
        }

    }

    @SubscribeEvent
    public static void drop(LivingDeathEvent event){
        LivingEntity entity = event.getEntity();
        if(!(entity instanceof Mob)) return;
        boolean boss = entity.getEntityData().get(MiningEnchant.STRENGTH_MOB);
        if(boss){
            int xp = 1000;
            if(entity instanceof EnderDragon || entity instanceof WitherBoss){
                xp *= 30;
            }
            ExperienceOrb.award((ServerLevel) entity.level(), entity.position(), xp);
        }

        float itemDrop = 40.0f;
        if (new Random().nextFloat(100.0f) < itemDrop) {
            ItemStack item = ModItems.LEVEL_UP_STICK.get().getDefaultInstance();
            entity.spawnAtLocation(item);
        }else{
            if(entity.getItemBySlot(EquipmentSlot.OFFHAND).getItem() == ModItems.LEVEL_UP_STICK.get()){
                ItemStack item = ModItems.LEVEL_UP_STICK.get().getDefaultInstance();
                entity.spawnAtLocation(item);
            }
        }
    }

    @SubscribeEvent
    public static void spawnEvent(MobSpawnEvent.FinalizeSpawn event){
        Entity entity = event.getEntity();
        if(!canSpawnBoss(entity)) return;

        BlockPos spawnBlockPos = new BlockPos(entity.getBlockX(),entity.getBlockY(),entity.getBlockZ());
        for(BlockPos pos : ResistStrengthMobBlock.worldInBlocks){
            Vec3 diff = new Vec3(
                    spawnBlockPos.getX() - pos.getX(),
                    spawnBlockPos.getY() - pos.getY(),
                    spawnBlockPos.getZ() - pos.getZ()
            );
            if(diff.length() < ResistStrengthMobBlock.RESIST_AREA) return;
        }

        Level world = entity.level();
        float odds = 0.0f;

        switch (world.getDifficulty()){
            case EASY ->    odds = 1.0f;
            case NORMAL ->  odds = 2.0f;
            case HARD ->    odds = 4.0f;
        }
        var biome = event.getLevel().getBiome(spawnBlockPos);
        if(biome.is(ModBiomes.STRENGTH_FOREST)){
            odds = 75.0f;
        }
        if(MiningEnchant.BOSS_SPAWN_CHANCE != 0){
            odds = MiningEnchant.BOSS_SPAWN_CHANCE;
        }

        if(entity instanceof EnderDragon || entity instanceof WitherBoss){
            PowerUp(entity,true,biome);
        }else{
            if(new Random().nextFloat(100) < odds){
                PowerUp(entity,false,biome);
            }
        }
    }


    public static boolean canSpawnBoss(Entity entity){
        if(entity == null) return false;
        if(!(entity instanceof Enemy)) return false;

        return true;
    }
    public static void PowerUp(Entity entity, boolean vanillaBoss, Holder<Biome> spawnBiome){
        ((Mob)entity).getEntityData().set(MiningEnchant.STRENGTH_MOB,true);
        LivingEntity mob = (LivingEntity) entity;
        Attribute attribute = mob.getAttribute(Attributes.MAX_HEALTH).getAttribute();
        if (attribute instanceof RangedAttribute) {
            AccecerAttributeRange range = (AccecerAttributeRange) attribute;
            range.setMaxValue(32768.0);
        }
        attribute = mob.getAttribute(Attributes.ARMOR).getAttribute();
        if (attribute instanceof RangedAttribute) {
            AccecerAttributeRange range = (AccecerAttributeRange) attribute;
            range.setMaxValue(1024.0);
        }

        mob.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, MobEffectInstance.INFINITE_DURATION));
        mob.addEffect(new MobEffectInstance(MobEffects.REGENERATION, MobEffectInstance.INFINITE_DURATION));
        mob.addEffect(new MobEffectInstance(MobEffects.GLOWING, MobEffectInstance.INFINITE_DURATION));

        SetAttribute(mob,Attributes.MAX_HEALTH, MiningEnchant.BOSS_HP_RATE,spawnBiome);
        SetAttribute(mob,Attributes.ATTACK_DAMAGE, MiningEnchant.BOSS_ATTACK_RATE,spawnBiome);
        SetAttribute(mob,Attributes.ARMOR, MiningEnchant.BOSS_ARMOR_RATE,spawnBiome);
        SetAttribute(mob,Attributes.MOVEMENT_SPEED, MiningEnchant.BOSS_MOVE_SPEED_RATE,spawnBiome);
        SetAttribute(mob,Attributes.ATTACK_SPEED, MiningEnchant.BOSS_ATTACK_SPEED_RATE,spawnBiome);

        if (vanillaBoss) {
            SetAttribute(mob,Attributes.FOLLOW_RANGE, MiningEnchant.BOSS_FOLLOW_RATE,spawnBiome);
        } else {
            SetAttribute(mob,Attributes.FOLLOW_RANGE, 0,spawnBiome);
        }

        SetSlot(mob,EquipmentSlot.OFFHAND, Items.TOTEM_OF_UNDYING, 50.0f);
        SetSlot(mob,EquipmentSlot.OFFHAND, ModItems.LEVEL_UP_STICK.get(), 20.0f);

        SetSlot(mob,EquipmentSlot.MAINHAND, Items.WOODEN_SWORD, 30.0f);
        SetSlot(mob,EquipmentSlot.MAINHAND, Items.STONE_SWORD, 25.0f);
        SetSlot(mob,EquipmentSlot.MAINHAND, Items.IRON_SWORD, 20.0f);
        SetSlot(mob,EquipmentSlot.MAINHAND, Items.DIAMOND_SWORD, 10.0f);
        SetSlot(mob,EquipmentSlot.MAINHAND, Items.NETHERITE_SWORD, 5.0f);

        SetSlot(mob,EquipmentSlot.HEAD, Items.IRON_HELMET, 20.0f);
        SetSlot(mob,EquipmentSlot.CHEST, Items.IRON_CHESTPLATE, 20.0f);
        SetSlot(mob,EquipmentSlot.LEGS, Items.IRON_LEGGINGS, 20.0f);
        SetSlot(mob,EquipmentSlot.FEET, Items.IRON_BOOTS, 20.0f);
    }


    private static void SetSlot(LivingEntity entity,EquipmentSlot slot, Item item, float rate){
        if(new Random().nextFloat(100.0f) < rate){
            if(entity.getItemBySlot(slot) == ItemStack.EMPTY){
                entity.setItemSlot(slot,item.getDefaultInstance());
            }
        }
    }


    private static void SetAttribute(LivingEntity entity,Attribute attribute, double rate,Holder<Biome> spawnBiome) {
        AttributeInstance instance = entity.getAttribute(attribute);

        if (instance != null) {
            if(spawnBiome.is(ModBiomes.STRENGTH_FOREST)){
                rate *= 0.8;
                if(rate <= 1.0){
                    rate = 1.5;
                }
            }
            double value = instance.getValue();
            if (value <= 0) value = 1.0;
            if(attribute == Attributes.ARMOR){
                instance.setBaseValue(value + rate);
            }else {
                instance.setBaseValue(value * rate);
            }
            if (attribute == Attributes.MAX_HEALTH) {
                entity.setHealth(entity.getMaxHealth());
            }
        }

    }
}
