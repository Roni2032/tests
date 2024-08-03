package com.enchantment.MiningEnchant.main.Blocks.entity;

import com.enchantment.MiningEnchant.main.GUI.Menu.ExtractBookMenu;
import com.enchantment.MiningEnchant.main.MiningEnchant;
import com.enchantment.MiningEnchant.main.Types.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EnchantExtractBookEntity extends RandomizableContainerBlockEntity {

    private NonNullList<ItemStack> items = NonNullList.withSize(3,ItemStack.EMPTY);

    public EnchantExtractBookEntity( BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.EXTRACT_BOOK_ENTITY.get(), pos, state);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        items = nonNullList;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container." + MiningEnchant.MOD_ID + ".extractBook");
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new ExtractBookMenu(i,inventory,this);
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ContainerHelper.saveAllItems(tag,this.items);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.loadAllItems(tag,this.items);
    }


}
