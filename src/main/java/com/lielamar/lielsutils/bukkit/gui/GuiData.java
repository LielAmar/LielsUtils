package com.lielamar.lielsutils.bukkit.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public abstract class GuiData {

    private final UUID uuid;

    public GuiData(@NotNull Player player) {
        this.uuid = player.getUniqueId();
    }

    public @Nullable Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }
}