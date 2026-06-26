package com.random.randommod.util;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;

import java.util.List;

public final class GuiItems {
    private GuiItems() {}

    public static ItemStack named(net.minecraft.world.item.Item item, Component name, List<Component> lore) {
        ItemStack stack = new ItemStack(item);
        stack.set(DataComponents.ITEM_NAME, name);
        stack.set(DataComponents.LORE, new ItemLore(lore));
        return stack;
    }

    public static ItemStack filler() {
        return named(Items.GRAY_STAINED_GLASS_PANE, Component.literal(" "), List.of());
    }

    public static ItemStack close() {
        return named(Items.BARRIER, Component.literal("Close").withStyle(ChatFormatting.RED), List.of(Component.literal("Close this menu.").withStyle(ChatFormatting.GRAY)));
    }

    public static ItemStack back() {
        return named(Items.ARROW, Component.literal("Back").withStyle(ChatFormatting.YELLOW), List.of(Component.literal("Return to the previous menu.").withStyle(ChatFormatting.GRAY)));
    }
}
