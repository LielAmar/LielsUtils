package com.lielamar.lielsutils.exceptions;

import org.jetbrains.annotations.NotNull;

public class UUIDNotFoundException extends Exception {

    public UUIDNotFoundException(@NotNull String explanation) {
        super(explanation);
    }
}