package com.lielamar.lielsutils.text;

import org.jetbrains.annotations.NotNull;

import java.util.Base64;
import java.util.StringJoiner;

public class TextUtils {

    /**
     * Formats a text to a Title case
     * Example:  XXXX_XXXX -> Xxxx Xxxx
     *
     * @param text   Name to format
     * @return       Formatted name
     */
    public static @NotNull String stringToTitleCase(@NotNull String text) {
        text = stringToLowerCase(text);

        StringJoiner sb = new StringJoiner(" ");

        for(String s : text.split(" "))
            sb.add(s.substring(0, 1).toUpperCase() + s.substring(1));

        return sb.toString();
    }

    /**
     * Formats a text to a Name case
     * Example:  XXXx_xXxX -> Xxxx xxxx
     *
     * @param text   Name to format
     * @return       Formatted name
     */
    public static @NotNull String stringToNameCase(@NotNull String text) {
        text = stringToLowerCase(text);

        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    /**
     * Formats a text to a Lower case
     * Example:  XXXX_XXXX -> xxxx xxxx
     *
     * @param text   Name to format
     * @return       Formatted name
     */
    public static @NotNull String stringToLowerCase(@NotNull String text) {
        return text.replaceAll("_", " ").toLowerCase();
    }

    public static byte[] encodeToBase64(@NotNull String text) {
        return Base64.getDecoder().decode(text);
    }

    public static String decodeFromBase64(@NotNull byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }
}