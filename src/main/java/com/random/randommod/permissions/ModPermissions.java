package com.random.randommod.permissions;

import com.random.randommod.RandomMod;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

public final class ModPermissions {
    public static final String ADMIN = "randommod.admin";
    public static final String BAN = "randommod.ban";
    public static final String KICK = "randommod.kick";
    public static final String MUTE = "randommod.mute";
    public static final String WARN = "randommod.warn";
    public static final String SETTINGS = "randommod.settings";

    private ModPermissions() {}

    public static boolean check(CommandSourceStack source, String permission) {
        if (!RandomMod.config().requirePermissions) return source.hasPermission(2);
        return Permissions.check(source, permission, 2) || Permissions.check(source, ADMIN, 2);
    }

    public static boolean check(ServerPlayer player, String permission) {
        if (!RandomMod.config().requirePermissions) return player.hasPermissions(2);
        return Permissions.check(player, permission, 2) || Permissions.check(player, ADMIN, 2);
    }
}
