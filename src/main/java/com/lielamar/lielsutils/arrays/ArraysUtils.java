package com.lielamar.lielsutils.arrays;

import org.jetbrains.annotations.NotNull;

public class ArraysUtils {

    public static String[] removeFirstElement(@NotNull String[] arguments) {
        if(arguments.length <= 1)
            return new String[0];

        String[] placeholder = new String[arguments.length - 1];

        System.arraycopy(arguments, 1, placeholder, 0, arguments.length);
        return placeholder;
    }
}