package com.lielamar.lielsutils.bukkit.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public interface GUI extends InventoryHolder {

    @Deprecated @NotNull GUIItem[] getGUIItems();
    @Deprecated @NotNull Inventory getInventory();
    @Deprecated void onGUIClick(@NotNull Player player, int slot);

    @Deprecated void build();
}
