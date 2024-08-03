package com.enchantment.MiningEnchant.main.Item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.*;

public class EnchantmentLevelUpItem extends Item {
    public EnchantmentLevelUpItem() {
        super(new Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack offHandItem = player.getItemBySlot(EquipmentSlot.OFFHAND);
        ItemStack itemstack = player.getItemInHand(hand);
        if(!offHandItem.getAllEnchantments().isEmpty()){
            player.startUsingItem(hand);
            boolean isUpdate = false;
            var enchantments = offHandItem.getAllEnchantments();
            for(var key : enchantments.keySet()){
                if(enchantments.get(key) == null) continue;

                if(key.getMaxLevel() > enchantments.get(key)) {
                    isUpdate = true;
                }
            }
            if(isUpdate){
                while(true){
                    Set<Enchantment> sets = new HashSet<>();
                    sets.add(Enchantments.UNBREAKING);
                    sets.addAll(enchantments.keySet());
                    var it = sets.iterator();

                    int size = offHandItem.getAllEnchantments().size();
                    int rnd = new Random().nextInt(0,size);
                    for (int i = 0; i < rnd - 1; i++) {
                        it.next();
                    }

                    Enchantment enchantment = it.next();
                    if(enchantment.getMaxLevel() > enchantments.get(enchantment)){
                        enchantments.replace(enchantment,enchantments.get(enchantment) + 1);
                        EnchantmentHelper.setEnchantments(enchantments, offHandItem);
                        break;
                    }

                }
                itemstack.setCount(itemstack.getCount() - 1);
            }

        }


        if (itemstack.isEdible()) {
            if (player.canEat(itemstack.getFoodProperties(player).canAlwaysEat())) {
                player.startUsingItem(hand);
                return InteractionResultHolder.consume(itemstack);
            } else {
                return InteractionResultHolder.fail(itemstack);
            }
        } else {
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        }
    }
}
