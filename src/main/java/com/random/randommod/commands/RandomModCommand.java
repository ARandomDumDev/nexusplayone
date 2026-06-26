package com.random.randommod.commands;

import com.mojang.brigadier.Command;
import com.random.randommod.gui.RandomModGui;
import com.random.randommod.permissions.ModPermissions;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public final class RandomModCommand {
    private RandomModCommand() {}

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(Commands.literal("randommod")
                .requires(source -> ModPermissions.check(source, ModPermissions.ADMIN))
                .executes(context -> {
                    ServerPlayer player = context.getSource().getPlayerOrException();
                    RandomModGui.openMain(player);
                    context.getSource().sendSuccess(() -> Component.literal("Opened RandomMod."), false);
                    return Command.SINGLE_SUCCESS;
                })));
    }
}
