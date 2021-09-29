package com.lielamar.lielsutils.bukkit.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class Menu<T extends GuiData> {

    private final @NotNull String name;
    private final int size;
    private final boolean needUpdates;
    private final long ticksBetweenUpdates;

    protected Map<Integer, UIElement<T>> elements = new HashMap<>();
    protected HashMap<UUID, T> data;

    public int frame;

    public Menu(@NotNull String name, int size, boolean needUpdates, long ticksBetweenUpdates) {
        this.name = name;
        this.size = size;
        this.needUpdates = needUpdates;
        this.ticksBetweenUpdates = ticksBetweenUpdates;

        this.data = new HashMap<>();
    }


    public abstract T getData(Player player);


    public @NotNull String getName() {
        return name;
    }
    public int getSize() {
        return size;
    }
    public Map<Integer, UIElement<T>> getElements() {
        return elements;
    }

    public void load(@NotNull T data) {}

    public static int findIdFromPos(int x, int y) {
        return y * 9 + x;
    }

    protected void fill(int startX, int startY, int endX, int endY, UIElement<T> uiElement) {
        for(int x = startX; x < endX; x++) {
            for(int y = startY; y < endY; y++)
                elements.put(findIdFromPos(x,y), uiElement);
        }
    }

    public void setData(UUID key, T value) {
        data.put(key,value);
    }

    public Inventory render(Player player) {
        Inventory inv = Bukkit.createInventory(player, size, name);
        data.put(player.getUniqueId(), getData(player));
        load(data.get(player.getUniqueId()));

        for(Map.Entry<Integer, UIElement<T>> entry : elements.entrySet()) {
            if(entry.getValue() != null)
                inv.setItem(entry.getKey(), entry.getValue().toItemStack(data.get(player.getUniqueId()), 0));
        }

        return inv;
    }

    public void update(int frame, Player player) {
        load(data.get(player.getUniqueId()));

        for(Map.Entry<Integer, UIElement<T>> entry : elements.entrySet()) {
            if(entry.getValue() != null)
                player.getOpenInventory().getTopInventory().setItem(entry.getKey(), entry.getValue().toItemStack(data.get(player.getUniqueId()), frame));
        }
    }


    public void register(@NotNull JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(new EventHandler<>(this), plugin);

        if(needUpdates) {
            Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    if(player.getOpenInventory().getTitle().equals(this.getName())
                            && player.getOpenInventory().getTopInventory().getSize() == this.getSize()
                            && player.getOpenInventory().getTopInventory().getHolder() == player) {
                        this.update(++frame, player);
                    }
                }
            }, 0, ticksBetweenUpdates);
        }
    }
}