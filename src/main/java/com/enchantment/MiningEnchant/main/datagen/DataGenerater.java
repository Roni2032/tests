package com.enchantment.MiningEnchant.main.datagen;


import com.enchantment.MiningEnchant.main.MiningEnchant;
import com.enchantment.MiningEnchant.main.worldgen.WorldGenProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = MiningEnchant.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerater {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookUpProvider = event.getLookupProvider();


        // WorldGen
        generator.addProvider(event.includeServer(),
                new WorldGenProvider(packOutput, lookUpProvider));

        generator.addProvider(event.includeServer(),new BlockTagsGenerater(packOutput,lookUpProvider,existingFileHelper));
    }
}
