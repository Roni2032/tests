package com.enchantment.MiningEnchant.main.event;


import com.enchantment.MiningEnchant.main.Entity.model.BossZombieModel;
import com.enchantment.MiningEnchant.main.Entity.model.BossZombieRenderer;
import com.enchantment.MiningEnchant.main.GUI.Screen.ExtractBookScreen;
import com.enchantment.MiningEnchant.main.MiningEnchant;
import com.enchantment.MiningEnchant.main.ModEntites;
import com.enchantment.MiningEnchant.main.Types.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

//mainクラスが呼び出されたら自動で呼ばれるように設定
@Mod.EventBusSubscriber(modid = MiningEnchant.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event){
        BlockScreenRegister();
        EntityRenderers.register(ModEntites.BOSS_ZOMBIE.get(), BossZombieRenderer::new);
    }

    private static void BlockScreenRegister(){
        MenuScreens.register(ModMenuTypes.EXTRACT_BOOK_MENU.get(), ExtractBookScreen::new);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(BossZombieModel.LAYER_LOCATION,BossZombieModel::createBodyLayer);
    }
}
