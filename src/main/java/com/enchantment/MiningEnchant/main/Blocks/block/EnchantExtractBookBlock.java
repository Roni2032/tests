package com.enchantment.MiningEnchant.main.Blocks.block;

import com.enchantment.MiningEnchant.main.Blocks.entity.EnchantExtractBookEntity;
import com.enchantment.MiningEnchant.main.GUI.Menu.ExtractBookMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class EnchantExtractBookBlock extends BaseEntityBlock {

    private EnchantExtractBookEntity blockEntity;
    public EnchantExtractBookBlock() {
        super(Properties.of().strength(5.0f).explosionResistance(200.0f));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        this.blockEntity = new EnchantExtractBookEntity(blockPos,blockState);
        return this.blockEntity;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            MenuProvider menuprovider = this.getMenuProvider(state, world, pos);
            if(menuprovider != null){
                player.openMenu(menuprovider);
                PiglinAi.angerNearbyPiglins(player, true);
            }
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState state2, boolean flag) {
        BlockEntity entity = world.getBlockEntity(pos);
        EnchantExtractBookEntity modEntity = (EnchantExtractBookEntity)entity;
        super.onRemove(state, world, pos, state2, flag);
        Containers.dropContents(world,pos,modEntity);
    }


}
