package com.enchantment.MiningEnchant.main.mixin.Attribute;

import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockBehaviour.class)
public interface AcceserBlockBehaviour {

    @Accessor("properties")
    @Mutable
    BlockBehaviour.Properties GetProperty();
}
