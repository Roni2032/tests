package com.enchantment.MiningEnchant.main.Item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Random;

public class Coin extends Item {

    public Coin upperCoin;
    private static final float FAILED_RANK_UP_ODDS = 95.0f;

    public Coin(Coin upperCoin) {
        super(new Properties());
        this.upperCoin = upperCoin;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(upperCoin != null) {
            player.startUsingItem(hand);
            itemstack.setCount(itemstack.getCount() - 1);
            float rnd = new Random().nextFloat(100);

            if(rnd < FAILED_RANK_UP_ODDS){
                player.displayClientMessage(Component.literal("チャレンジ失敗..."),true);
            }
            else if ( rnd < 98) {
                player.getInventory().add(upperCoin.getDefaultInstance());
                player.displayClientMessage(Component.literal("チャレンジ成功!!!"),true);
            } else{
                if(upperCoin.upperCoin != null){
                    player.getInventory().add(upperCoin.upperCoin.getDefaultInstance());
                    player.displayClientMessage(Component.literal("チャレンジ大成功!!!"),true);
                }else{
                    player.getInventory().add(upperCoin.getDefaultInstance());
                    player.displayClientMessage(Component.literal("チャレンジ成功!!!"),true);
                }
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
