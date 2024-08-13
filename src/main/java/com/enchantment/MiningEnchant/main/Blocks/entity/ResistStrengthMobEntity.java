package com.enchantment.MiningEnchant.main.Blocks.entity;

import com.enchantment.MiningEnchant.main.Types.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ResistStrengthMobEntity extends BlockEntity {

    public ResistStrengthMobEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlockEntityTypes.RESIST_STRENGTH_ENTITY.get(), p_155229_, p_155230_);
    }
}
