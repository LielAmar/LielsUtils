package com.lielamar.lielsutils.bukkit.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface MenuElement<T extends MenuData> {

    /**
     * InputItem is a type of UIElement that handles a click on a single item in an inventory (GUI), while another item is selected by the cursor
     *
     * @param data    Data of the GUI
     * @param frame   The frame of the item, used for animations
     * @return        Returns the created ItemStack
     */

    @NotNull ItemStack toItemStack(@NotNull T data, int frame);


    void execute(Menu<?> menu, InventoryClickEvent event);
}