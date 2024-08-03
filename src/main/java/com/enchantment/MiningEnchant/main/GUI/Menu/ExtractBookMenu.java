package com.enchantment.MiningEnchant.main.GUI.Menu;

import com.enchantment.MiningEnchant.main.Types.ModMenuTypes;
import com.enchantment.MiningEnchant.main.Slots.ExtractSlots_Book;
import com.enchantment.MiningEnchant.main.Slots.ExtractSlots_Result;
import com.enchantment.MiningEnchant.main.Slots.ExtractSlots_Tools;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.HashMap;

public class ExtractBookMenu extends AbstractContainerMenu {

    private Container container;
    public ExtractBookMenu(int a,Inventory inventory){
        this(a,inventory,new SimpleContainer(3));
    }
    public ExtractBookMenu(int a, Inventory inventory, Container container) {
        super(ModMenuTypes.EXTRACT_BOOK_MENU.get(), a);

        this.container = container;

        checkContainerSize(container,3);
        container.startOpen(inventory.player);

        this.addSlot(new ExtractSlots_Tools(this.container,0,26,34));
        this.addSlot(new ExtractSlots_Book(this.container,1,75,34));
        this.addSlot(new ExtractSlots_Result(this.container,2,133,34));

        for (int i = 0; i < 3; i++) {
            for(int j = 0;j < 9;j++) {
                this.addSlot(new Slot(inventory,j + 9 + i * 9,8 + j * 18,84 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(inventory,i,8 + i * 18,142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        ItemStack $$2 = ItemStack.EMPTY;
        Slot $$3 = (Slot)this.slots.get(i);
        if ($$3 != null && $$3.hasItem()) {
            ItemStack $$4 = $$3.getItem();
            $$2 = $$4.copy();
            if (i < this.container.getContainerSize()) {
                if (!this.moveItemStackTo($$4, this.container.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo($$4, 0, this.container.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if ($$4.isEmpty()) {
                $$3.setByPlayer(ItemStack.EMPTY);
            } else {
                $$3.setChanged();
            }
        }

        return $$2;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }

    @Override
    public void resumeRemoteUpdates() {
        super.resumeRemoteUpdates();

        ItemStack item1 = this.slots.get(0).getItem();
        ItemStack item2 = this.slots.get(1).getItem();
        if(!item1.isEmpty() && !item2.isEmpty()){
            ItemStack result = new ItemStack(Items.ENCHANTED_BOOK);
            var enchants = item1.getAllEnchantments();
            EnchantmentHelper.setEnchantments(enchants,result);
            this.slots.get(2).set(result);
        }else{
            this.slots.get(2).set(ItemStack.EMPTY);
        }
    }

    @Override
    public void clicked(int slot, int p_150401_, ClickType type, Player player) {
        if(slot == 2 && type == ClickType.PICKUP){
            ItemStack item = this.slots.get(slot).getItem();
            if(item.getItem() != Items.AIR){
                this.slots.get(1).getItem().setCount(this.slots.get(1).getItem().getCount() - 1);
                ItemStack tool = this.slots.get(0).getItem();
                EnchantmentHelper.setEnchantments(new HashMap<>(),tool);
            }
        }
        super.clicked(slot, p_150401_, type, player);
    }
}
