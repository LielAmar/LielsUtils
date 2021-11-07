package com.lielamar.lielsutils.bukkit.map;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapManager {

    private final Plugin plugin;
    public MapManager(Plugin plugin) {
        this.plugin = plugin;
    }


    private List<String> loadMapNamesFromDirectories() {
        List<String> maps = new ArrayList<>();
        List<String> excludedFolders = Arrays.asList("crash-reports", "logs", "logs_backup", "plugins", "world", "world_nether", "world_the_end");

        for(File file : Bukkit.getServer().getWorldContainer().listFiles()) {
            if(file.isDirectory() && !excludedFolders.contains(file.getName()))
                maps.add(file.getName());
        }

        return maps;
    }


    /**
     * Saves all current maps
     *
     * @return   this {@link MapManager} object
     */
    public MapManager saveAllMaps() {
        List<String> maps = loadMapNamesFromDirectories();

        for(String map : maps) {
            this.reloadMap(map);
            System.out.println("[MapManager] Re-loaded map " + map);
        }

        return this;
    }

    /**
     * Restores all maps
     */
    public void restoreAllMaps() {
        List<String> maps = loadMapNamesFromDirectories();

        for(String map : maps) {
            this.unloadMap(Bukkit.getWorld(map));
            System.out.println("[MapManager] Unloaded map " + map);
        }
    }

    /**
     * Reloads a map
     *
     * @param mapName      Name of the map to reload
     */
    private void reloadMap(@Nullable String mapName) {
        if(mapName == null) return;

        World world = Bukkit.getWorld(mapName);

        if(world == null) return;

        unloadMap(world);
        loadMap(mapName);
    }

    /**
     * Unloads a map
     *
     * @param world         World to unload
     */
    private void unloadMap(@Nullable World world) {
        if(world != null) {
            for(Entity entity : world.getEntities()) {
                if(entity instanceof Player)
                    entity.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
            }

            Bukkit.getServer().unloadWorld(world, false);
        }
    }

    /**
     * Loads a map
     *
     * @param mapName      Name of the map to load
     */
    private void loadMap(@Nullable String mapName) {
        if(mapName == null) return;

        World world = Bukkit.getServer().createWorld(new WorldCreator(mapName));

        if(world == null) return;

        world.setAutoSave(false);
    }
}