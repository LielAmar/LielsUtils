package com.lielamar.lielsutils.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public interface GUI extends InventoryHolder {

    GUIItem[] getGUIItems();
    Inventory getInventory();
    void onGUIClick(Player player, int slot);

    void build();
}
