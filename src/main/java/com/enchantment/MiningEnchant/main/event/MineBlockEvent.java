package com.enchantment.MiningEnchant.main.event;

import com.enchantment.MiningEnchant.main.Enchant.MineAllEnchant;
import com.enchantment.MiningEnchant.main.MiningEnchant;
import com.enchantment.MiningEnchant.main.ModEnchants;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MineBlockEvent {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void mineEvent(BlockEvent.BreakEvent event) {
        MineAllEnchant mineAllEnchant = (MineAllEnchant) ModEnchants.MINE_ENCHANT.get();
        mineAllEnchant.mineBlock(event);
    }


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void RegisterCommands(RegisterCommandsEvent event){

        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("setStatus")
                .then(Commands.literal("hp")
                        .then(Commands.argument("value", FloatArgumentType.floatArg(0.1f,100.0f))
                                .executes(commandContext -> {
                                    MiningEnchant.BOSS_HP_RATE = FloatArgumentType.getFloat(commandContext, "value");
                                    commandContext.getSource().getPlayerOrException().sendSystemMessage(Component.nullToEmpty("HPの倍率を" + MiningEnchant.BOSS_HP_RATE + "に変更します"));
                                    return Command.SINGLE_SUCCESS;
                                })))
                .then(Commands.literal("attack")
                        .then(Commands.argument("value", FloatArgumentType.floatArg(0.1f,100.0f))
                                .executes(commandContext -> {
                                    MiningEnchant.BOSS_ATTACK_RATE = FloatArgumentType.getFloat(commandContext, "value");
                                    commandContext.getSource().getPlayerOrException().sendSystemMessage(Component.nullToEmpty("攻撃力の倍率を" + MiningEnchant.BOSS_ATTACK_RATE + "に変更します"));
                                    return Command.SINGLE_SUCCESS;
                                })))
                .then(Commands.literal("armor")
                        .then(Commands.argument("value", FloatArgumentType.floatArg(0.1f,100.0f))
                                .executes(commandContext -> {
                                    MiningEnchant.BOSS_ARMOR_RATE = FloatArgumentType.getFloat(commandContext,"value");
                                    commandContext.getSource().getPlayerOrException().sendSystemMessage(Component.nullToEmpty("アーマーの倍率を" + MiningEnchant.BOSS_ARMOR_RATE + "に変更します"));
                                    return Command.SINGLE_SUCCESS;
                                })))
                .then(Commands.literal("moveSpeed")
                        .then(Commands.argument("value", FloatArgumentType.floatArg(0.1f,100.0f))
                                .executes(commandContext -> {
                                    MiningEnchant.BOSS_MOVE_SPEED_RATE = FloatArgumentType.getFloat(commandContext,"value");
                                    commandContext.getSource().getPlayerOrException().sendSystemMessage(Component.nullToEmpty("移動速度の倍率を" + MiningEnchant.BOSS_MOVE_SPEED_RATE + "に変更します"));
                                    return Command.SINGLE_SUCCESS;
                                })))
                .then(Commands.literal("attackSpeed")
                        .then(Commands.argument("value", FloatArgumentType.floatArg(0.1f,100.0f))
                                .executes(commandContext -> {
                                    MiningEnchant.BOSS_ATTACK_SPEED_RATE = FloatArgumentType.getFloat(commandContext,"value");
                                    commandContext.getSource().getPlayerOrException().sendSystemMessage(Component.nullToEmpty("攻撃速度の倍率を" + MiningEnchant.BOSS_ATTACK_SPEED_RATE + "に変更します"));
                                    return Command.SINGLE_SUCCESS;
                                })));
        event.getDispatcher().register(builder);

        builder = Commands.literal("getStatus")
                .executes(commandContext -> {
                    commandContext.getSource().getPlayerOrException().sendSystemMessage(Component.nullToEmpty("HP : " + MiningEnchant.BOSS_HP_RATE + "倍"));
                    commandContext.getSource().getPlayerOrException().sendSystemMessage(Component.nullToEmpty("攻撃力 : " + MiningEnchant.BOSS_ATTACK_RATE + "倍"));
                    commandContext.getSource().getPlayerOrException().sendSystemMessage(Component.nullToEmpty("アーマー : " + MiningEnchant.BOSS_ARMOR_RATE + "倍"));
                    commandContext.getSource().getPlayerOrException().sendSystemMessage(Component.nullToEmpty("移動速度 : " + MiningEnchant.BOSS_MOVE_SPEED_RATE + "倍"));
                    commandContext.getSource().getPlayerOrException().sendSystemMessage(Component.nullToEmpty("攻撃速度 : " + MiningEnchant.BOSS_ATTACK_SPEED_RATE + "倍"));
                    return Command.SINGLE_SUCCESS;
                });

        event.getDispatcher().register(builder);

        builder = Commands.literal("bossSpawn")
                .then(Commands.argument("value",FloatArgumentType.floatArg(0,100))
                        .executes(commandContext -> {
                            MiningEnchant.BOSS_SPAWN_CHANCE = FloatArgumentType.getFloat(commandContext, "value");
                            commandContext.getSource().getPlayerOrException().sendSystemMessage(Component.nullToEmpty("スポーン確率 : " + MiningEnchant.BOSS_SPAWN_CHANCE + "%"));
                            return Command.SINGLE_SUCCESS;
                        }));

        event.getDispatcher().register(builder);

        builder = Commands.literal("resetStatus").executes(commandContext -> {
            MiningEnchant.BOSS_HP_RATE = 25.0f;
            MiningEnchant.BOSS_ATTACK_RATE = 2.0f;
            MiningEnchant.BOSS_ARMOR_RATE = 20.0f;
            MiningEnchant.BOSS_MOVE_SPEED_RATE = 2.0f;
            MiningEnchant.BOSS_ATTACK_SPEED_RATE = 2.0f;
            MiningEnchant.BOSS_FOLLOW_RATE = 3.0f;
            return Command.SINGLE_SUCCESS;
        });

        event.getDispatcher().register(builder);
    }
}
