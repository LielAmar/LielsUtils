package com.lielamar.lielsutils.bukkit.gui.elements;

import com.lielamar.lielsutils.bukkit.gui.MenuData;
import com.lielamar.lielsutils.bukkit.gui.MenuElement;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;

public interface Button<T extends MenuData> extends MenuElement<T> {

    /**
     * Button is a type of UIElement that handles a click on a single item in an inventory (GUI)
     *
     * @param clicker     Player who clicked
     * @param clickType   Type of click
     * @param data        Data of the GUI
     * @return            Returns the data after it's been modified
     */

    @NotNull T click(@NotNull Player clicker, @NotNull ClickType clickType, @NotNull T data);
}