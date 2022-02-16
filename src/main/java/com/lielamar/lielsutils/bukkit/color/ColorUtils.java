package com.lielamar.lielsutils.bukkit.color;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.DyeColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class ColorUtils {

    /**
     * Colors a Bukkit message with support to hex color codes
     *
     * @param codeChar   char to use to translate color codes
     * @param message    Message to color
     * @return           Colored message
     */
    public static String translateAlternateColorCodes(char codeChar, String message) {
        char[] chars = message.toCharArray();

        StringBuilder builder  = new StringBuilder();
        String colorHex = "";

        boolean isHex = false;

        for(int i = 0; i < chars.length; i++) {
            if(chars[i] == codeChar && i < chars.length - 1 && chars[i+1] == '#'){
                colorHex = "";
                isHex = true;
            } else if(isHex) {
                colorHex += chars[i];
                isHex = colorHex.length() < 7;

                if(!isHex)
                    builder.append(ChatColor.of(colorHex));
            } else
                builder.append(chars[i]);
        }

        return ChatColor.translateAlternateColorCodes(codeChar, builder.toString());
    }

    /**
     * Removes all colors from the given string and replaces them with &<code>
     *
     * @param text   Text to replace
     * @return       Untranslated String
     */
    public static String unTranslateAlternateColorCodes(String text) {
        char[] array = text.toCharArray();
        for(int i = 0; i < array.length - 1; i++) {
            if(array[i] == ChatColor.COLOR_CHAR && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(array[i + 1]) != -1) {
                array[i] = '&';
                array[i + 1] = Character.toLowerCase(array[i + 1]);
            }
        }
        return new String(array);
    }

    /**
     * Returns the last color used in an untranslated message
     *
     * @param untranslatedColor   Untranslated message
     * @return                    Last color
     */
    public static String getLastColor(String untranslatedColor) {
        String[] coloredSections = untranslatedColor.split("&");
        String lastColor = coloredSections[coloredSections.length - 1];

        if(lastColor.startsWith("#"))
            return lastColor.substring(0, 7);

        return lastColor.charAt(0) + "";
    }

    /**
     * Calculates the closest color to the given rgb
     *
     * @return   Closest color to the given rgb
     */
    public static org.bukkit.ChatColor getClosestColor(int red, int green, int blue) {
        org.bukkit.ChatColor closest = null;

        for(org.bukkit.ChatColor chatColor : org.bukkit.ChatColor.values()) {
            if(chatColor.isColor()) {
                if(closest == null)
                    closest = chatColor;
                else if(distance(red, green, blue, chatColor) < distance(red, green, blue, closest))
                    closest = chatColor;
            }
        }

        return closest;
    }

    private static int distance(int red, int green, int blue, @NotNull org.bukkit.ChatColor chatColor) {
        Color color = chatColor.asBungee().getColor();

        return Math.abs(color.getRed() - red)
                + Math.abs(color.getGreen() - green)
                + Math.abs(color.getBlue() - blue);
    }


    /**
     * Translates a DyeColor object to an integer
     *
     * @param color   The {@link DyeColor} object
     * @return        Integer equivalent of the color
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
//            case 8: return DyeColor.SILVER;
            case 8: return null;
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
}
