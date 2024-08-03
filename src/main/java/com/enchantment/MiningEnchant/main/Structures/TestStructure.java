package com.enchantment.MiningEnchant.main.Structures;


import net.minecraft.world.level.levelgen.structure.SinglePieceStructure;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class TestStructure extends SinglePieceStructure {

    protected TestStructure(Structure.StructureSettings settings) {
        super(null,10,10,settings);
    }

    @Override
    public StructureType<?> type() {
        return null;
    }
}
