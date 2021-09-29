package com.lielamar.lielsutils.exceptions;

import org.jetbrains.annotations.NotNull;

public class InvalidResponseException extends Exception {

    public InvalidResponseException(@NotNull String explanation) {
        super(explanation);
    }
}