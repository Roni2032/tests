package com.enchantment.MiningEnchant.main.Blocks.block;

import com.enchantment.MiningEnchant.main.worldgen.dimensions.ModDimensions;
import com.enchantment.MiningEnchant.main.worldgen.dimensions.Potal.ModPotals;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class PortalBlock extends Block {
    public PortalBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult result) {
        if(player.canChangeDimensions()){
            handlePortal(player,blockPos);
            return InteractionResult.SUCCESS;
        }else{
            return InteractionResult.CONSUME;
        }
    }

    private void handlePortal(Entity player,BlockPos pos){
        if(player.level() instanceof ServerLevel serverLevel){
            MinecraftServer server = serverLevel.getServer();
            ResourceKey<Level> key = player.level().dimension() == ModDimensions.DIM_LEVEL_KEY ? Level.OVERWORLD : ModDimensions.DIM_LEVEL_KEY;

            ServerLevel portalDimension = server.getLevel(key);
            if(portalDimension != null && !player.isPassenger()){
                if(key == ModDimensions.DIM_LEVEL_KEY){
                    player.changeDimension(portalDimension,new ModPotals(pos,true));
                }else{
                    player.changeDimension(portalDimension,new ModPotals(pos,false));
                }
            }
        }
    }
}
