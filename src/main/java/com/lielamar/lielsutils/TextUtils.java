package com.lielamar.lielsutils;

import org.bukkit.ChatColor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

public class TextUtils {

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
     * Returns the current date
     *
     * @return       Current date (String Format)
     */
    public static String getDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return dtf.format(LocalDateTime.now());
    }

    /**
     * Formats X seconds to the following format: XX:XX
     *
     * @param seconds        Seconds to format
     * @return               Time left in the XX:XX format
     */
    public static String formatSeconds(int seconds) {
        int minutes = seconds/60;
        seconds = seconds%60;

        String sMinutes = minutes + "";
        String sSeconds = seconds + "";

        if(minutes < 10) sMinutes = "0" + minutes;
        if(seconds < 10) sSeconds = "0" + seconds;

        return sMinutes + ":" + sSeconds;
    }

    /**
     * Formats a double variable - keeps the original number with <precision> digits after the dot
     *
     * @param number       Number to format
     * @param precision    Amount of digits after dot
     * @return             Formatted number
     */
    public static double formatDouble(double number, int precision) {
        int tmp = (int)number*(int)Math.pow(10, precision);
        return tmp/Math.pow(10, precision);
    }

    /**
     * Formats an enumName to a regular name
     * Example:  XXXX_XXXX -> Xxxx Xxxx
     *
     * @param enumName        Name to format
     * @return                Formatted name
     */
    public static String enumToString(String enumName) {
        StringJoiner sb = new StringJoiner(" ");

        for(String s : enumName.split("_"))
            sb.add(s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase());

        return sb.toString();
    }
}
