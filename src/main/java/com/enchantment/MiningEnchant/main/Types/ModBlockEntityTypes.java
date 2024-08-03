package com.enchantment.MiningEnchant.main.Types;

import com.enchantment.MiningEnchant.main.Blocks.entity.EnchantExtractBookEntity;
import com.enchantment.MiningEnchant.main.MiningEnchant;
import com.enchantment.MiningEnchant.main.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MiningEnchant.MOD_ID);

    public static final RegistryObject<BlockEntityType<EnchantExtractBookEntity>> EXTRACT_BOOK_ENTITY= BLOCK_ENTITY_TYPES.register("extract_book_entity",()->set(EnchantExtractBookEntity::new, ModBlocks.Blocks.EXTRACT_BOOK_BLOCK.get()));

    private static <T extends BlockEntity> BlockEntityType<T> set(BlockEntityType.BlockEntitySupplier<T> supplier, Block block) {
        return BlockEntityType.Builder.of(supplier, block).build(null);
    }
}
