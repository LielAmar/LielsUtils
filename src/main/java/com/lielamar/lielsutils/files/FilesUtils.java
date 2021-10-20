package com.lielamar.lielsutils.files;

import com.google.gson.JsonSyntaxException;
import com.lielamar.connections.serializable.SerializableDocument;
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
    public static @NotNull String parseFileIntoString(@NotNull File file) throws IOException {
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

    /**
     * Parses the content of the given file into a SerializableDocument
     *
     * @param file                   File to parse
     * @return                       Parsed file data as SerializableDocument
     * @throws JsonSyntaxException   Throws an JsonSyntaxException if the content of the file is not a valid json
     */
    public static @NotNull SerializableDocument parseFileIntoSerializableDocument(@NotNull File file) throws JsonSyntaxException {
        return new SerializableDocument(parseFileIntoSerializableDocument(file));
    }
}
