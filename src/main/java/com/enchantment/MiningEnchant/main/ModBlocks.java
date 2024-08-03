package com.enchantment.MiningEnchant.main;

import com.enchantment.MiningEnchant.main.Blocks.block.EnchantExtractBookBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {

    public static class Blocks{
        public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MiningEnchant.MOD_ID);

        public static final RegistryObject<Block> EXTRACT_BOOK_BLOCK = BLOCKS.register((String)"extract_book", EnchantExtractBookBlock::new);
    }

    public static class Items{
        public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MiningEnchant.MOD_ID);

        public static final RegistryObject<Item> EXTRACT_BOOK_ITEM = BLOCK_ITEMS.register((String)"extract_book",()->new BlockItem(Blocks.EXTRACT_BOOK_BLOCK.get(),new Item.Properties()));
    }

}
