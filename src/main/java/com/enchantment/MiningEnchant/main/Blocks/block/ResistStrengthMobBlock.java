package com.enchantment.MiningEnchant.main.Blocks.block;

import com.enchantment.MiningEnchant.main.Blocks.entity.EnchantExtractBookEntity;
import com.enchantment.MiningEnchant.main.Blocks.entity.ResistStrengthMobEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ResistStrengthMobBlock extends BaseEntityBlock {

    public static List<BlockPos> worldInBlocks = new ArrayList<>();
    public static int RESIST_AREA = 20;

    public ResistStrengthMobBlock() {
        super(Properties.of().strength(5.0f));
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        ResistStrengthMobEntity entity = new ResistStrengthMobEntity(blockPos,blockState);
        worldInBlocks.add(entity.getBlockPos());
        return entity;
    }


    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState state2, boolean flag) {
        BlockEntity entity = world.getBlockEntity(pos);
        EnchantExtractBookEntity modEntity = (EnchantExtractBookEntity)entity;
        worldInBlocks.remove(modEntity.getBlockPos());
        super.onRemove(state, world, pos, state2, flag);
    }
}
