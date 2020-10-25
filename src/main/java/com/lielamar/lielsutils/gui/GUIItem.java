package com.lielamar.lielsutils.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GUIItem {

    private ItemStack itemstack;

    public GUIItem(ItemStack itemstack) {
        this.itemstack = itemstack;
    }

    public ItemStack getItemStack() {
        return this.itemstack;
    }

    public void onGUIItemClick(Player player, GUI gui) { }
}
