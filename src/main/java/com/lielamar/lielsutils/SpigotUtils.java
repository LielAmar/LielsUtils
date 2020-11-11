package com.lielamar.lielsutils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.lielamar.lielsutils.modules.Pair;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class SpigotUtils {

    /**
     * Creates an ItemStack
     *
     * @param material     Material of the ItemStack
     * @param amount       Amount of the ItemStack
     * @param name         Name of the ItemStack
     * @param lore         Lore of the ItemStack
     * @param enchants     Enchants of the ItemStack
     * @return             ItemStack created through properties
     */
    public static ItemStack getItem(@NotNull Material material, int amount, String name, String[] lore, Map<Enchantment, Integer> enchants) {
        ItemStack item = new ItemStack(material, amount);

        ItemMeta meta = item.getItemMeta();
        if(name != null) meta.setDisplayName(name);
        if(lore != null) meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);

        if(enchants != null && enchants.size() > 0)
            item.addUnsafeEnchantments(enchants);

        return item;
    }

    public static ItemStack getItem(@NotNull Material material, int amount, String name, String[] lore, Pair<Enchantment, Integer>... enchants) {
        ItemStack item = getItem(material, amount, name, lore, (Map<Enchantment, Integer>) null);

        if(enchants == null || enchants.length == 0) return item;
        
        for(Pair<Enchantment, Integer> enchantment : enchants)
            item.addEnchantment(enchantment.getKey(), enchantment.getValue());

        return item;
    }

    public static ItemStack getItem(@NotNull Material material, int amount, String name, String[] lore, Enchantment... enchants) {
        ItemStack item = getItem(material, amount, name, lore, (Map<Enchantment, Integer>) null);

        if(enchants == null || enchants.length == 0) return item;

        for(Enchantment enchantment : enchants)
            item.addEnchantment(enchantment, 1);

        return item;
    }

    public static ItemStack getItem(@NotNull Material material, int amount, String name, String[] lore) {
        return getItem(material, amount, name, lore, (Map<Enchantment, Integer>) null);
    }

    public static ItemStack getItem(@NotNull Material material, int amount, String name) {
        return getItem(material, amount, name, null, (Map<Enchantment, Integer>) null);
    }

    public static ItemStack getItem(@NotNull Material material, int amount) {
        return getItem(material, amount, null, null, (Map<Enchantment, Integer>) null);
    }

    public static ItemStack getItem(@NotNull Material material) {
        return getItem(material, 1, null, null, (Map<Enchantment, Integer>) null);
    }


    /**
     * Fetches a location from the given config & location section
     *
     * @param config         Config to fetch location from
     * @param location       Name of the location
     * @return               Location fetched from Config + Name
     */
    public static Location fetchLocation(@NotNull FileConfiguration config, String location) {
        return new Location(Bukkit.getWorld(config.getString(location + ".world")),
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
    public static Location[] fetchLocations(@NotNull FileConfiguration config, String configurationSection) {
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


    /**
     * Translates a DyeColor object to an integer
     *
     * @param color       The {@link DyeColor} object
     * @return            Integer equivalent of the color
     */
    public static int translateColorToInt(DyeColor color) {
        switch(color.name()) {
            case "WHITE": return 0;
            case "ORANGE": return 1;
            case "MAGENTA": return 2;
            case "LIGHT_BLUE": return 3;
            case "YELLOW": return 4;
            case "LIME": return 5;
            case "PINK": return 6;
            case "GRAY": return 7;
            case "SILVER": return 8;
            case "CYAN": return 9;
            case "PURPLE": return 10;
            case "BLUE": return 11;
            case "BROWN": return 12;
            case "GREEN": return 13;
            case "RED": return 14;
            case "BLACK": return 15;
            default: return -1;
        }
    }

    /**
     * Translates an integer to a DyeColor object
     *
     * @param color       The color's id
     * @return            {@link DyeColor} equivalent of the color
     */
    public static DyeColor translateIntToColor(int color) {
        switch(color) {
            case 0: return DyeColor.WHITE;
            case 1: return DyeColor.ORANGE;
            case 2: return DyeColor.MAGENTA;
            case 3: return DyeColor.LIGHT_BLUE;
            case 4: return DyeColor.YELLOW;
            case 5: return DyeColor.LIME;
            case 6: return DyeColor.PINK;
            case 7: return DyeColor.GRAY;
            case 8: return DyeColor.SILVER;
            case 9: return DyeColor.CYAN;
            case 10: return DyeColor.PURPLE;
            case 11: return DyeColor.BLUE;
            case 12: return DyeColor.BROWN;
            case 13: return DyeColor.GREEN;
            case 14: return DyeColor.RED;
            case 15: return DyeColor.BLACK;
            default: return null;
        }
    }


    /**
     * Communicates with Mojang's API to get a player's uuid through their IGN
     *
     * @param name   Name to get the UUID of
     * @return       UUID of the player
     */
    public static UUID fetchUUID(String name) {
        try {
            String MOJANG_API = "https://api.mojang.com/users/profiles/minecraft/%s";
            HttpURLConnection connection = (HttpURLConnection) new URL(String.format(MOJANG_API, name)).openConnection();
            connection.setReadTimeout(5000);
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine;
            StringBuilder response = new StringBuilder();

            while((inputLine = in.readLine()) != null)
                response.append(inputLine);
            in.close();

            JsonElement json = new JsonParser().parse(response.toString());
            String uuid = json.getAsJsonObject().get("id").getAsString();

            return UUID.fromString(uuid);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
