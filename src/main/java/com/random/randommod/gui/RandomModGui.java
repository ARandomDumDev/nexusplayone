package com.random.randommod.gui;

import com.random.randommod.RandomMod;
import com.random.randommod.permissions.ModPermissions;
import com.random.randommod.util.GuiItems;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.gui.SimpleGui;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.UserBanListEntry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Items;

import java.util.Date;

public final class RandomModGui {
    private RandomModGui() {}

    public static void openMain(ServerPlayer player) {
        SimpleGui gui = base(player, Component.literal("RandomMod"));
        setButton(gui, 20, Items.IRON_AXE, "🔨 Ban", ModPermissions.BAN, "Ban an online player.", () -> openBan(player));
        setButton(gui, 22, Items.IRON_BOOTS, "👢 Kick", ModPermissions.KICK, "Kick tools are logged and permission gated.", () -> logOnly(player, "KICK_MENU"));
        setButton(gui, 24, Items.NOTE_BLOCK, "🔇 Mute", ModPermissions.MUTE, "Mute tools are logged and permission gated.", () -> logOnly(player, "MUTE_MENU"));
        setButton(gui, 29, Items.WRITABLE_BOOK, "⚠️ Warn", ModPermissions.WARN, "Warn tools are logged and permission gated.", () -> logOnly(player, "WARN_MENU"));
        setButton(gui, 31, Items.BOOK, "📋 Ban History", ModPermissions.BAN, "Open Minecraft's ban history file externally.", () -> logOnly(player, "BAN_HISTORY_MENU"));
        setButton(gui, 33, Items.COMPARATOR, "⚙️ Settings", ModPermissions.SETTINGS, "RandomMod configuration status.", () -> logOnly(player, "SETTINGS_MENU"));
        gui.setSlot(49, GuiItems.close(), (i, type, action) -> { click(player); gui.close(); });
        gui.open();
    }

    public static void openBan(ServerPlayer admin) {
        SimpleGui gui = base(admin, Component.literal("RandomMod - Ban"));
        int[] slots = {10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34};
        int index = 0;
        for (ServerPlayer target : admin.server.getPlayerList().getPlayers()) {
            if (index >= slots.length) break;
            gui.setSlot(slots[index++], new GuiElementBuilder(Items.PLAYER_HEAD)
                    .setName(Component.literal(target.getGameProfile().getName()).withStyle(ChatFormatting.AQUA))
                    .addLoreLine(Component.literal("Click to review this ban.").withStyle(ChatFormatting.GRAY))
                    .asStack(), (i, type, action) -> { click(admin); openBanConfirm(admin, target); });
        }
        gui.setSlot(45, GuiItems.back(), (i, type, action) -> { click(admin); openMain(admin); });
        gui.setSlot(49, GuiItems.close(), (i, type, action) -> { click(admin); gui.close(); });
        gui.open();
    }

    private static void openBanConfirm(ServerPlayer admin, ServerPlayer target) {
        SimpleGui gui = base(admin, Component.literal("Confirm Ban"));
        gui.setSlot(22, new GuiElementBuilder(Items.PLAYER_HEAD).setName(Component.literal(target.getGameProfile().getName()).withStyle(ChatFormatting.AQUA)).addLoreLine(Component.literal("Target player").withStyle(ChatFormatting.GRAY)).asStack());
        gui.setSlot(30, new GuiElementBuilder(Items.LIME_WOOL).setName(Component.literal("Green Confirm").withStyle(ChatFormatting.GREEN)).addLoreLine(Component.literal("Ban using Minecraft's built-in ban list.").withStyle(ChatFormatting.GRAY)).asStack(), (i, type, action) -> ban(admin, target));
        gui.setSlot(32, new GuiElementBuilder(Items.RED_WOOL).setName(Component.literal("Red Cancel").withStyle(ChatFormatting.RED)).addLoreLine(Component.literal("Return without taking action.").withStyle(ChatFormatting.GRAY)).asStack(), (i, type, action) -> { click(admin); openBan(admin); });
        gui.setSlot(45, GuiItems.back(), (i, type, action) -> { click(admin); openBan(admin); });
        gui.setSlot(49, GuiItems.close(), (i, type, action) -> { click(admin); gui.close(); });
        gui.open();
    }

    private static SimpleGui base(ServerPlayer player, Component title) {
        SimpleGui gui = new SimpleGui(MenuType.GENERIC_9x6, player, false);
        gui.setTitle(title);
        for (int slot = 0; slot < 54; slot++) gui.setSlot(slot, GuiItems.filler());
        return gui;
    }

    private static void setButton(SimpleGui gui, int slot, net.minecraft.world.item.Item item, String title, String permission, String lore, Runnable action) {
        boolean allowed = ModPermissions.check(gui.getPlayer(), permission);
        gui.setSlot(slot, new GuiElementBuilder(allowed ? item : Items.GRAY_DYE).setName(Component.literal(title).withStyle(allowed ? ChatFormatting.GOLD : ChatFormatting.DARK_GRAY)).addLoreLine(Component.literal(lore).withStyle(ChatFormatting.GRAY)).addLoreLine(Component.literal(allowed ? "Click to open." : "Missing permission: " + permission).withStyle(allowed ? ChatFormatting.GREEN : ChatFormatting.RED)).asStack(), (i, type, click) -> { click(gui.getPlayer()); if (allowed) action.run(); });
    }

    private static void ban(ServerPlayer admin, ServerPlayer target) {
        click(admin);
        if (!ModPermissions.check(admin, ModPermissions.BAN)) return;
        admin.server.getPlayerList().getBans().add(new UserBanListEntry(target.getGameProfile(), new Date(), admin.getGameProfile().getName(), null, RandomMod.config().banReason));
        target.connection.disconnect(Component.literal(RandomMod.config().banReason));
        Component message = Component.literal(target.getGameProfile().getName() + " has been banned by " + admin.getGameProfile().getName() + ".");
        if (RandomMod.config().broadcastModerationActions) admin.server.getPlayerList().broadcastSystemMessage(message, false);
        RandomMod.moderationLogger().log("BAN", admin.getGameProfile().getName(), target.getGameProfile().getName(), RandomMod.config().banReason);
        openBan(admin);
    }

    private static void click(ServerPlayer player) { player.playNotifySound(SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.MASTER, 0.8f, 1.0f); }

    private static void logOnly(ServerPlayer player, String action) { RandomMod.moderationLogger().log(action, player.getGameProfile().getName(), "-", "Opened GUI section"); }
}
