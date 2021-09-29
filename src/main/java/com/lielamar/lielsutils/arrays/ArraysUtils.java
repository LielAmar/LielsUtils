package com.lielamar.lielsutils.arrays;

import org.jetbrains.annotations.NotNull;

public class ArraysUtils {

    public static String[] removeFirstElement(@NotNull String[] arguments) {
        String[] placeholder = new String[arguments.length - 1];

        if(placeholder.length >= 0)
            System.arraycopy(arguments, 1, placeholder, 0, placeholder.length);

        return placeholder;
    }
}