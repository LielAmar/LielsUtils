package com.lielamar.lielsutils.bukkit.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;

public interface Button<T extends GuiData> extends UIElement<T> {

    @NotNull T click(@NotNull Player clicker, @NotNull ClickType clickType, @NotNull T data);
}