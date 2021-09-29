package com.lielamar.lielsutils.bukkit.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GUIItem {

    private final @NotNull ItemStack itemstack;

    @Deprecated
    public GUIItem(@NotNull ItemStack itemstack) {
        this.itemstack = itemstack;
    }

    @Deprecated
    public @NotNull ItemStack getItemStack() {
        return this.itemstack;
    }

    @Deprecated
    public void onGUIItemClick(@NotNull Player player, @NotNull GUI gui) {}
}
