package com.enchantment.MiningEnchant.main.mixin.Mobs;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(Skeleton.class)
public class ModdingSkeletonMob {
    private int spawnSkeletonTicks = 0;
    private boolean isBoss(Mob mob){
        return mob.getMaxHealth() > 100.0f;
    }
    @Unique
    private Skeleton forge_MiningEnchantment_1_20_1$self(){
        return (Skeleton) (Object)this;
    }


    @Inject(method = "tick",at = @At(value = "RETURN"))
    private void addTick(CallbackInfo ci){
        if(isBoss(forge_MiningEnchantment_1_20_1$self())) {
            spawnSkeletonTicks++;
            if(spawnSkeletonTicks >= 400) {
                spawnSkeletonTicks = 0;
                if (forge_MiningEnchantment_1_20_1$self().getTarget() != null) {
                    Skeleton skeleton = new Skeleton(EntityType.SKELETON, forge_MiningEnchantment_1_20_1$self().level());
                    skeleton.setItemSlot(EquipmentSlot.MAINHAND, Items.DIAMOND_SWORD.getDefaultInstance());
                    skeleton.setItemSlot(EquipmentSlot.HEAD, Items.IRON_HELMET.getDefaultInstance());
                    skeleton.setItemSlot(EquipmentSlot.CHEST, Items.IRON_CHESTPLATE.getDefaultInstance());
                    skeleton.setPos(new Vec3(forge_MiningEnchantment_1_20_1$self().getX(), forge_MiningEnchantment_1_20_1$self().getY(), forge_MiningEnchantment_1_20_1$self().getZ()));
                    Objects.requireNonNull(skeleton.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(40.0);
                    skeleton.setHealth(skeleton.getMaxHealth());
                    skeleton.spawnAnim();
                    forge_MiningEnchantment_1_20_1$self().level().addFreshEntity(skeleton);
                }
            }
        }
    }
}
