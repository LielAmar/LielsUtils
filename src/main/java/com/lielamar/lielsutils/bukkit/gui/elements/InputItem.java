package com.lielamar.lielsutils.bukkit.gui.elements;

import com.lielamar.lielsutils.bukkit.gui.MenuData;
import com.lielamar.lielsutils.bukkit.gui.MenuElement;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface InputItem<T extends MenuData> extends MenuElement<T> {

    /**
     * InputItem is a type of UIElement that handles a click on a single item in an inventory (GUI), while another item is selected by the cursor
     *
     * @param clicker     Player who clicked
     * @param clickType   Type of click
     * @param data        Data of the GUI
     * @param itemStack   Item on cursor
     * @return            Returns the data after it's been modified
     */

    @NotNull T click(@NotNull Player clicker, @NotNull ClickType clickType, @NotNull T data, @NotNull ItemStack itemStack);
}