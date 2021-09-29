package com.lielamar.lielsutils.bukkit.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LocationUtils {

    /**
     * Fetches a location from the given config & location section
     *
     * @param config         Config to fetch location from
     * @param location       Name of the location
     * @return               Location fetched from Config + Name
     */
    public static @Nullable Location fetchLocation(@NotNull FileConfiguration config, @NotNull String location) {
        String worldName = config.getString(location + ".world");

        if(worldName == null) return null;

        return new Location(Bukkit.getWorld(worldName),
                config.getDouble(location + ".x"),
                config.getDouble(location + ".y"),
                config.getDouble(location + ".z"),
                config.contains(location + ".yaw") ? config.getLong(location + ".yaw") : 0,
                config.contains(location + ".pitch") ? config.getLong(location + ".pitch") : 0);
    }

    /**
     * Fetches a list of locations from the given config & configurationSection
     *
     * @param config                Config to fetch locations from
     * @param configurationSection  Section to load locations from
     * @return                      List of location fetched from Config
     */
    public static Location[] fetchLocations(@NotNull FileConfiguration config, @NotNull String configurationSection) {
        if(config.getConfigurationSection(configurationSection) == null) return new Location[0];

        Location[] locations = new Location[config.getConfigurationSection(configurationSection).getKeys(false).size()];

        for(int i = 0; i < locations.length; i++) {
            locations[i] = new Location(Bukkit.getWorld(config.getString(configurationSection + "." + i + ".world")),
                    config.getDouble(configurationSection + "." + i + ".x"),
                    config.getDouble(configurationSection + "." + i + ".y"),
                    config.getDouble(configurationSection + "." + i + ".z"),
                    config.getLong(configurationSection + "." + i + ".yaw"),
                    config.getLong(configurationSection + "." + i + ".pitch"));
        }
        return locations;
    }
}
