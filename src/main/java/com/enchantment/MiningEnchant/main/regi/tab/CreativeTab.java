package com.enchantment.MiningEnchant.main.regi.tab;

import com.enchantment.MiningEnchant.main.MiningEnchant;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeTab {
    public static final DeferredRegister<CreativeModeTab> modTabs = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MiningEnchant.MOD_ID);

    public static final RegistryObject<CreativeModeTab> UsefulMainTab = modTabs.register("mod_maintab",
            ()->{return CreativeModeTab.builder()
                    .icon(()->new ItemStack(Items.ENCHANTED_BOOK))//タブのアイコン
                    .title(Component.translatable("itemGroup_Main"))//タブの名前 / Component.translatable : 翻訳機能
                    .displayItems((pram,output)->{  //アイテムの登録
                        for(Item item : ModCreativeItems.items){ //ModCreativeTabで登録したものを歯にfor文で登録
                            output.accept(item);
                        }
                    }).build();
    });
}
