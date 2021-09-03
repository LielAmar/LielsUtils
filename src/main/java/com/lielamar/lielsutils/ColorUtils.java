package com.lielamar.lielsutils;

import net.md_5.bungee.api.ChatColor;

public class ColorUtils {

    /**
     * Removes all colors from the given string and replaces them with &<code>
     *
     * @param text          Text to replace
     * @return              Untranslated String
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
     * Supports hex color codes
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
}
