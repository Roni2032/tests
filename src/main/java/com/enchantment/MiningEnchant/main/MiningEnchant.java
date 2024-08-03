package com.enchantment.MiningEnchant.main;

import com.enchantment.MiningEnchant.main.Types.ModBlockEntityTypes;
import com.enchantment.MiningEnchant.main.Types.ModMenuTypes;
import com.enchantment.MiningEnchant.main.regi.tab.CreativeTab;
import com.enchantment.MiningEnchant.main.worldgen.Region.OverWorldRegion;
import com.enchantment.MiningEnchant.main.worldgen.Rules.SurfaceRuleData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

@Mod(MiningEnchant.MOD_ID)
public class MiningEnchant{
    public static final String MOD_ID = "mining_enchant";

    public static float BOSS_SPAWN_CHANCE = 0.0f;
    public static float BOSS_HP_RATE = 25.0f;
    public static float BOSS_ATTACK_RATE = 2.0f;
    public static float BOSS_ARMOR_RATE = 20.0f;
    public static float BOSS_MOVE_SPEED_RATE = 2.0f;
    public static float BOSS_ATTACK_SPEED_RATE = 2.0f;
    public static float BOSS_FOLLOW_RATE = 3.0f;

    public static final MobType UNDEFINED_MOB = new MobType();

    public MiningEnchant(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);


        ModEnchants.ENCHANTMENT.register(bus);
        ModItems.ITEMS.register(bus);
        CreativeTab.modTabs.register(bus);
        ModBlockEntityTypes.BLOCK_ENTITY_TYPES.register(bus);
        ModBlocks.Items.BLOCK_ITEMS.register(bus);
        ModBlocks.Blocks.BLOCKS.register(bus);
        ModMenuTypes.MENU_TYPES.register(bus);
    }

    private void commonSetup(final FMLCommonSetupEvent event){
        event.enqueueWork(() -> {
            // Modのロード時に、RegionとSurfaceRuleを読み込ませる
            Regions.register(new OverWorldRegion(
                    new ResourceLocation(
                            MiningEnchant.MOD_ID, "overworld"
                    ), 5/*生成される頻度*/));
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD,
                    MOD_ID, SurfaceRuleData.makeRules());
        });
    }
}
