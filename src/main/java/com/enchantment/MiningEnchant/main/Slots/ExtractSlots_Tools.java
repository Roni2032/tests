package com.enchantment.MiningEnchant.main.Slots;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ExtractSlots_Tools extends Slot {
    public ExtractSlots_Tools(Container container, int slot, int w, int h) {
        super(container, slot, w, h);
    }

    @Override
    public boolean mayPlace(ItemStack item) {
       if(!item.getAllEnchantments().isEmpty()){
           return true;
       }
       return false;
    }
}
