package com.lielamar.lielsutils.bukkit.gui;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface UIElement<T extends GuiData> {

    @NotNull ItemStack toItemStack(@NotNull T data, int frame);
}