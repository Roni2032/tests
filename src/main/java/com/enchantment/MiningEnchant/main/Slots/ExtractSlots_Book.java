package com.enchantment.MiningEnchant.main.Slots;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BookItem;
import net.minecraft.world.item.ItemStack;

public class ExtractSlots_Book extends Slot {
    public ExtractSlots_Book(Container container, int slot, int w, int h) {
        super(container, slot, w, h);
    }


    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean mayPlace(ItemStack item) {
       if(item.getItem() instanceof BookItem){
           return item.getAllEnchantments().isEmpty();
       }
       return false;
    }
}
