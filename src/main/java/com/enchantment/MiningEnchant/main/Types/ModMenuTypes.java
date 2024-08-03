package com.enchantment.MiningEnchant.main.Types;

import com.enchantment.MiningEnchant.main.GUI.Menu.ExtractBookMenu;
import com.enchantment.MiningEnchant.main.MiningEnchant;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static  final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MiningEnchant.MOD_ID);

    public static final RegistryObject<MenuType<ExtractBookMenu>> EXTRACT_BOOK_MENU = MENU_TYPES.register("mod_chest_menu",()->set(ExtractBookMenu::new));

    private static <T extends AbstractContainerMenu> MenuType<T> set(MenuType.MenuSupplier<T> supplier){
        return new MenuType<>(supplier, FeatureFlags.REGISTRY.allFlags());
    }

}
