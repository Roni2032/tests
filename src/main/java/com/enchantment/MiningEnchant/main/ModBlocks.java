package com.enchantment.MiningEnchant.main;

import com.enchantment.MiningEnchant.main.Blocks.block.EnchantExtractBookBlock;
import com.enchantment.MiningEnchant.main.Blocks.block.ResistStrengthMobBlock;
import com.enchantment.MiningEnchant.main.Blocks.block.StrengthLeaves;
import com.enchantment.MiningEnchant.main.Blocks.block.StrengthLog;
import com.enchantment.MiningEnchant.main.worldgen.Tree.StrengthTree;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {

    public static class Blocks{
        public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MiningEnchant.MOD_ID);

        public static final RegistryObject<Block> EXTRACT_BOOK_BLOCK = BLOCKS.register((String)"extract_book", EnchantExtractBookBlock::new);

        public static final RegistryObject<Block> STRENGTH_LOG_BLOCK = BLOCKS.register((String)"strength_log", StrengthLog::new);
        public static final RegistryObject<Block> STRENGTH_LEAVE_BLOCK = BLOCKS.register((String)"strength_leave", StrengthLeaves::new);

        public static final RegistryObject<Block> STRENGTH_SAPLING = BLOCKS.register((String)"strength_sapling",()->new SaplingBlock(new StrengthTree(), BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.OAK_SAPLING)));

        public static final RegistryObject<Block> RESIST_STRENGTH_MOB_BLOCK = BLOCKS.register((String)"resist_strength_mob.json", ResistStrengthMobBlock::new);
    }

    public static class Items{
        public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MiningEnchant.MOD_ID);

        public static final RegistryObject<Item> EXTRACT_BOOK_ITEM = BLOCK_ITEMS.register((String)"extract_book",()->new BlockItem(Blocks.EXTRACT_BOOK_BLOCK.get(),new Item.Properties()));

        public static final RegistryObject<Item> STRENGTH_LOG_ITEM = BLOCK_ITEMS.register((String)"strength_log", ()->new BlockItem(Blocks.STRENGTH_LOG_BLOCK.get(),new Item.Properties()));
        public static final RegistryObject<Item> STRENGTH_LEAVE_ITEM = BLOCK_ITEMS.register((String)"strength_leave",()->new BlockItem(Blocks.STRENGTH_LEAVE_BLOCK.get(),new Item.Properties()));

        public static final RegistryObject<Item> STRENGTH_SAPLING_ITEM = BLOCK_ITEMS.register((String)"strength_sapling",()->new BlockItem(Blocks.STRENGTH_SAPLING.get(),new Item.Properties()));

        public static final RegistryObject<Item> RESIST_STRENGTH_MOB_ITEM = BLOCK_ITEMS.register((String)"resist_strength_mob.json",()->new BlockItem(Blocks.RESIST_STRENGTH_MOB_BLOCK.get(),new Item.Properties()));

    }

}
