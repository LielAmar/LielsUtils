package com.lielamar.lielsutils.files;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FilesUtils {

    /**
     * Parses the content of the given file into String
     *
     * @param file           File to parse
     * @return               Parsed file data as String
     * @throws IOException   Throws an IOException if an IO operation failed
     */
    public static String parseFileIntoString(@NotNull File file) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(file));

        StringBuilder fileData = new StringBuilder();
        String line = fileReader.readLine();

        while(line != null) {
            fileData.append(line);
            fileData.append(System.lineSeparator());
            line = fileReader.readLine();
        }

        fileReader.close();

        return fileData.toString();
    }
}
