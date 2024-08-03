package com.enchantment.MiningEnchant.main;

import com.enchantment.MiningEnchant.main.Item.Coin;
import com.enchantment.MiningEnchant.main.Item.EnchantmentLevelUpItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,MiningEnchant.MOD_ID);

    public static final RegistryObject<Item> LEVEL_UP_STICK = ITEMS.register("level_up_stick", EnchantmentLevelUpItem::new);

    public static final RegistryObject<Item> CHIP_100 = ITEMS.register("chip_100",()->new Coin(null));
    public static final RegistryObject<Item> CHIP_50 = ITEMS.register("chip_50",()->new Coin((Coin) CHIP_100.get().asItem()));
    public static final RegistryObject<Item> CHIP_10 = ITEMS.register("chip_10",()->new Coin((Coin) CHIP_50.get().asItem()));
    public static final RegistryObject<Item> CHIP_5 = ITEMS.register("chip_5",()->new Coin((Coin) CHIP_10.get().asItem()));
    public static final RegistryObject<Item> CHIP_1 = ITEMS.register("chip_1",()->new Coin((Coin) CHIP_5.get().asItem()));


}
