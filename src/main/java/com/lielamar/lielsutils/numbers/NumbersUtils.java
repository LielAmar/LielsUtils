package com.lielamar.lielsutils.numbers;

import org.jetbrains.annotations.NotNull;

public class NumbersUtils {

    public static boolean isInteger(@NotNull String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch(NumberFormatException ignored) {}
        return false;
    }
}
