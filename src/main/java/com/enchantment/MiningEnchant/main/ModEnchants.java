package com.enchantment.MiningEnchant.main;

import com.enchantment.MiningEnchant.main.Enchant.CriticalEnchantment;
import com.enchantment.MiningEnchant.main.Enchant.MineAllEnchant;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchants{

    public static final EnchantmentCategory MINEALL_CATEGORY = EnchantmentCategory.create("template_mineall",
            item -> item instanceof PickaxeItem ||
            item instanceof AxeItem ||
            item instanceof ShovelItem);

    public static final DeferredRegister<Enchantment> ENCHANTMENT = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS,MiningEnchant.MOD_ID);

    public static final RegistryObject<Enchantment> MINE_ENCHANT = ENCHANTMENT.register("mineall",MineAllEnchant::new);
    public static final RegistryObject<Enchantment> CRITICAL_ENCHANT = ENCHANTMENT.register("critical", CriticalEnchantment::new);

}
