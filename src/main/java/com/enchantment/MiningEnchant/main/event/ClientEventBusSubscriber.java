package com.enchantment.MiningEnchant.main.event;


import com.enchantment.MiningEnchant.main.GUI.Screen.ExtractBookScreen;
import com.enchantment.MiningEnchant.main.MiningEnchant;
import com.enchantment.MiningEnchant.main.Types.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.stringtemplate.v4.ST;

//mainクラスが呼び出されたら自動で呼ばれるように設定
@Mod.EventBusSubscriber(modid = MiningEnchant.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event){
        BlockScreenRegister();
    }

    private static void BlockScreenRegister(){
        MenuScreens.register(ModMenuTypes.EXTRACT_BOOK_MENU.get(), ExtractBookScreen::new);
    }


}
