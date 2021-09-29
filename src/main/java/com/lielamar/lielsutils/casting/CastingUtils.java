package com.lielamar.lielsutils.casting;

import org.jetbrains.annotations.NotNull;

public class CastingUtils {

    /**
     * Util function to cast an element from string to the correct type
     *
     * @param element   Element to load the type of
     * @return          Object of the right type
     */
    public static @NotNull Object castToRightType(@NotNull String element) {
        try {
            return Byte.parseByte(element);
        } catch(Exception ignored) {}

        try {
            return Short.parseShort(element);
        } catch(Exception ignored) {}

        try {
            return Integer.parseInt(element);
        } catch(Exception ignored) {}

        try {
            return Long.parseLong(element);
        } catch(Exception ignored) {}

        try {
            return Float.parseFloat(element);
        } catch(Exception ignored) {}

        try {
            return Double.parseDouble(element);
        } catch(Exception ignored) {}

        if(element.equalsIgnoreCase("true"))
            return true;
        else if(element.equalsIgnoreCase("false"))
            return false;

        if(element.length() == 1)
            return element.charAt(0);

        return element;
    }
}