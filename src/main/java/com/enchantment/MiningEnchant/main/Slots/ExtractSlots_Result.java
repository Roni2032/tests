package com.enchantment.MiningEnchant.main.Slots;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ExtractSlots_Result extends Slot {
    public ExtractSlots_Result(Container container, int slot, int w, int h) {
        super(container, slot, w, h);
    }

    @Override
    public boolean mayPlace(ItemStack item) {

       return false;
    }
}
